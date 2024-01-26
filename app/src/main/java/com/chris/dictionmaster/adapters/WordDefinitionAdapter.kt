package com.chris.dictionmaster.adapters

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chris.dictionmaster.R
import com.chris.dictionmaster.databinding.RecyclerviewWordBinding
import com.chris.dictionmaster.model.Definition
import android.text.SpannableStringBuilder


class WordDefinitionAdapter(
    private val onAudioIconClick: (String) -> Unit,
    private val onAudioUnavailable: () -> Unit,
    private val onBackButtonClick: () -> Unit
) : RecyclerView.Adapter<WordDefinitionAdapter.DefinitionViewHolder>() {

    private val definitions: MutableList<Definition> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewWordBinding.inflate(inflater, parent, false)
        return DefinitionViewHolder(binding, onBackButtonClick)
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        val definition = definitions[position]
        holder.bind(definition, position + 1, definitions.size, onAudioIconClick, onAudioUnavailable)
    }

    override fun getItemCount(): Int {
        return definitions.size
    }

    fun setDefinitions(newDefinitions: List<Definition>) {
        definitions.clear()
        definitions.addAll(newDefinitions)
        notifyDataSetChanged()
    }

    class DefinitionViewHolder(private val binding: RecyclerviewWordBinding, private val onBackButtonClick: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            definition: Definition,
            currentNumber: Int,
            definitionsSize: Int,
            onAudioIconClick: (String) -> Unit,
            onAudioUnavailable: () -> Unit,
        ) {
            val audioUrl = definition.phonetics.firstOrNull { !it.audio.isNullOrEmpty() }?.audio
            val context = binding.root.context

            binding.icAudio.setOnClickListener {
                if (audioUrl.isNullOrEmpty()) {
                    onAudioUnavailable()
                } else {
                    onAudioIconClick(audioUrl)
                }
            }

            binding.btnBack.setOnClickListener {
                onBackButtonClick()
            }

            configureViews(definition, currentNumber, definitionsSize, context)
        }

        private fun configureViews(definition: Definition, currentNumber: Int, definitionsSize: Int, context: Context) {
            binding.layoutMeanings.removeAllViews()

            if (currentNumber == 1) {
                binding.tvWord.text = definition.word
                binding.tvAudio.text = definition.phonetic
                binding.icAudio.visibility = View.VISIBLE

            } else {
                binding.tvWord.visibility = View.GONE
                binding.tvAudio.visibility = View.GONE
                binding.icAudio.visibility = View.GONE
            }

            if (adapterPosition == definitionsSize - 1) {
                binding.tvEducation.text = "That’s it for “${definition.word}”! ☺\uFE0F"
                binding.tvEducation.visibility = View.VISIBLE
            } else {
                binding.tvEducation.visibility = View.GONE
                binding.tvAnother.visibility = View.GONE
                binding.btnBack.visibility = View.GONE
                binding.btnInvisible.visibility = View.GONE
            }

            var number = currentNumber
            definition.meanings.forEach { meaning ->
                val meaningView = LayoutInflater.from(context).inflate(R.layout.meaning_item, null) as LinearLayout
                val tvDefinition = meaningView.findViewById<TextView>(R.id.tvDefinition)
                val tvExample = meaningView.findViewById<TextView>(R.id.tvExample)

                val spannableBuilder = SpannableStringBuilder()

                val totalDefinitionItems = meaning.definitions.size
                meaning.definitions.forEachIndexed { index, definitionItem ->
                    val partOfSpeechWithBrackets = "[${meaning.partOfSpeech}] "
                    val definitionText = StringBuilder("$number) $partOfSpeechWithBrackets${definitionItem.definition}")

                    if (index < totalDefinitionItems - 1 || definitionItem.example != null) {
                        definitionText.append("\n")
                    }

                    val spannableText = SpannableString(definitionText.toString())
                    applySpanStyles(spannableText, number.toString().length, partOfSpeechWithBrackets, context)
                    spannableBuilder.append(spannableText)

                    definitionItem.example?.let { example ->
                        tvExample.append("• $example\n")
                    }

                    number++
                }

                tvDefinition.text = spannableBuilder
                binding.layoutMeanings.addView(meaningView)
            }
        }

        private fun applySpanStyles(spannableText: SpannableString, numberLength: Int, partOfSpeech: String, context: Context) {
            val numberColorSpan = ForegroundColorSpan(Color.parseColor("#052D39"))
            spannableText.setSpan(numberColorSpan, 0, numberLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            val partOfSpeechWithBrackets = "[$partOfSpeech] "
            val partOfSpeechStart = numberLength + 1 // Considera o número, o parêntese e o espaço
            val partOfSpeechEnd = partOfSpeechStart + partOfSpeechWithBrackets.length -2

            val partOfSpeechColorSpan = ForegroundColorSpan(Color.parseColor("#82969c"))
            spannableText.setSpan(partOfSpeechColorSpan, partOfSpeechStart, partOfSpeechEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

    }
}