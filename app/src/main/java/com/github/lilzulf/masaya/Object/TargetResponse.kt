package com.github.lilzulf.masaya.Object

data class TargetResponse(
	val code: Int? = null,
	val data: ArrayList<DataItem>? = null,
	val message: String? = null,
	val status: Boolean? = null
)
