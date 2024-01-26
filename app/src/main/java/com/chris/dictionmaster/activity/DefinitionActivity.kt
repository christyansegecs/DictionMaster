package com.chris.dictionmaster.activity

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chris.dictionmaster.adapters.WordDefinitionAdapter
import com.chris.dictionmaster.databinding.ActivityDefinitionBinding
import com.chris.dictionmaster.model.Definition
import java.io.IOException

class DefinitionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDefinitionBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WordDefinitionAdapter
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDefinitionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val definitions = intent.getParcelableArrayListExtra<Definition>("definitions")
        setupRecyclerView(definitions)
    }

    private fun setupRecyclerView(definitions: List<Definition>?) {
        recyclerView = binding.recyclerview
        adapter = WordDefinitionAdapter(
            onAudioIconClick = { audioUrl -> playAudio(audioUrl) },
            onAudioUnavailable = { showAudioUnavailableDialog() },
            onBackButtonClick = { finish() }
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        definitions?.let { adapter.setDefinitions(it) }
    }

    private fun playAudio(audioUrl: String?) {
        if (audioUrl.isNullOrEmpty()) {
            Log.d("DefinitionActivity", "Áudio não disponível.")
            return
        }

        if (this::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            try {
                setDataSource(audioUrl)
                prepareAsync()
                setOnPreparedListener { start() }
            } catch (e: IOException) {
                Log.e("MediaPlayer", "Erro ao configurar o MediaPlayer: ${e.message}")
                showAudioPlaybackErrorDialog()
            }

            setOnErrorListener { mp, what, extra ->
                Log.e("MediaPlayer", "Erro na reprodução do áudio: What: $what, Extra: $extra")
                showAudioPlaybackErrorDialog()
                true
            }
        }
    }

    private fun showAudioPlaybackErrorDialog() {
        AlertDialog.Builder(this)
            .setMessage("Ocorreu um erro ao reproduzir o áudio.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showAudioUnavailableDialog() {
        AlertDialog.Builder(this)
            .setMessage("Áudio não disponível para essa palavra.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}