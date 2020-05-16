package com.github.lilzulf.masaya.Object

data class MoodDetail(
	val code: Int? = null,
	val data: Detail? = null,
	val message: String? = null,
	val status: Boolean? = null
)

data class Detail(
	val total: String? = null,
	val GreatCount: String? = null,
	val NormalCount: String? = null,
	val BadCount: String? = null,
	val id_user: String? = null,
	val GoodCount: String? = null,
	val SadCount: String? = null
)

