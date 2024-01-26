package com.chris.dictionmaster.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Definition(
    val word: String?,
    val phonetic: String?,
    val phonetics: List<Phonetic>,
    val origin: String?,
    val meanings: List<Meaning>,
    val example: String?
) : Parcelable

@Parcelize
data class Phonetic(
    val text: String?,
    val audio: String?
) : Parcelable

@Parcelize
data class Meaning(
    val partOfSpeech: String,
    val definitions: List<DefinitionItem>,
    val example: String?
) : Parcelable

@Parcelize
data class DefinitionItem(
    val definition: String,
    val example: String?,
    val synonyms: List<String>,
    val antonyms: List<String>
) : Parcelable
