package com.github.lilzulf.masaya.Object

data class MoodResponse(
	val code: Int? = null,
	val data: DataMood? = null,
	val message: String? = null,
	val status: Boolean? = null
)

data class DataMood(
	val date: String? = null,
	val state: String? = null,
	val id_user: String? = null,
    val id_diary : String? = null
)

