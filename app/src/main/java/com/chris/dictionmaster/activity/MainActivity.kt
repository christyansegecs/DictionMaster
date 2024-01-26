package com.chris.dictionmaster.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.chris.dictionmaster.R
import com.chris.dictionmaster.database.models.Word
import com.chris.dictionmaster.databinding.ActivityMainBinding
import com.chris.dictionmaster.model.Definition
import com.chris.dictionmaster.util.WordState
import com.chris.dictionmaster.viewmodel.DictionaryViewModel
import com.chris.dictionmaster.viewmodel.WordViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dictionaryViewModel: DictionaryViewModel by viewModel()
    private val wordViewModel: WordViewModel by viewModel()

    private val flags = arrayOf(R.drawable.english_flag, R.drawable.spanish_flag, R.drawable.french_flag)
    private var flagIndex = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                splashScreenView.remove()
            }
        }

        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        setupSearchButton()
        setupEditTextWatcher()
        setupClickListener()
        handleWordDefinition()
        setupImageSwitcher()
    }

    private fun setupSearchButton() {
        binding.btnSearch.visibility = View.GONE
    }

    private fun setupImageSwitcher() {
        binding.flagImageSwitcher.setFactory {
            ImageView(this).apply {
                scaleType = ImageView.ScaleType.FIT_CENTER
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            }
        }
        updateFlagImageAndText()

        binding.flagImageSwitcher.setOnClickListener {
            flagIndex = (flagIndex + 1) % flags.size
            updateFlagImageAndText()
        }
    }

    private fun updateFlagImageAndText() {
        binding.flagImageSwitcher.setImageResource(flags[flagIndex])
        val languageText = when (flagIndex) {
            0 -> getString(R.string.english)
            1 -> getString(R.string.spanish)
            2 -> getString(R.string.french)
            else -> ""
        }
        binding.tvLanguage.text = languageText
    }

    private fun setupEditTextWatcher() {
        binding.etWord.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (charSequence?.isNotBlank() == true) {
                    binding.btnSearch.visibility = View.VISIBLE
                    binding.etWord.typeface = ResourcesCompat.getFont(this@MainActivity, R.font.roboto_condensed_bold)

                } else {
                    binding.btnSearch.visibility = View.GONE
                    binding.etWord.typeface = ResourcesCompat.getFont(this@MainActivity, R.font.roboto_condensed_light)
                }
            }
            override fun afterTextChanged(editable: Editable?) {}
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupClickListener() {
        binding.btnSearch.setOnClickListener {
            val currentWord = binding.etWord.text.toString()
            if (currentWord.isNotEmpty()) {
                lifecycleScope.launch {
                    val word = wordViewModel.getWord(currentWord)
                    if (word != null) {
                        fetchDatabase(word)
                    } else {
                        if (!wordViewModel.isWordLimitReached(LocalDate.now())) {
                            dictionaryViewModel.getDefinition(currentWord)
                        } else {
                            showAlertDialog()
                        }
                    }
                }
            }
        }
    }

    private fun fetchDatabase(word: Word) {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Definition>>() {}.type
        val definitions = gson.fromJson<ArrayList<Definition>>(word.response, type)

        val intent = Intent(this, DefinitionActivity::class.java)
        intent.putParcelableArrayListExtra("definitions", definitions)
        Log.d("DataBase", "Passando o Objeto pelo Banco de Dados")
        Log.d("DataBase", definitions.toString())
        startActivity(intent)
    }


    private fun showAlertDialog() {
        val intent = Intent(this, PurchaseActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleWordDefinition() = lifecycleScope.launchWhenCreated {
        dictionaryViewModel.wordStateFlow.collect { state ->
            when (state) {
                is WordState.onSuccess -> {
                    val definitions = state.data.body()
                    if (!definitions.isNullOrEmpty()) {
                        updateDefinitionsListInActivity(definitions)

                        val gson = Gson()
                        val jsonDefinitions = gson.toJson(definitions)
                        val currentWord = binding.etWord.text.toString()

                        wordViewModel.insert(currentWord, jsonDefinitions)
                    }
0                }
                else -> {
                    Log.d("MainActivity", "State is not onSuccess: $state")
                }
            }
        }
    }

    private fun updateDefinitionsListInActivity(definitions: List<Definition>) {
        Log.d("MainActivity", "Updating definitions list in activity")
        val intent = Intent(this, DefinitionActivity::class.java)
        Log.d("DataBase", "Passando o Objeto pela API")
        intent.putParcelableArrayListExtra("definitions", ArrayList(definitions))
        startActivity(intent)
    }
}