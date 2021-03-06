package com.github.lilzulf.masaya.Object

data class ResponseAuth(
	val code: Int? = null,
	val data: Data? = null,
	val message: String? = null,
	val status: Boolean? = null
)

data class Data(
	val password: String? = null,
	val nickname: String? = null,
	val id_user: String? = null,
	val email: String? = null,
	val username: String? = null
)
data class ResponseGrate(
	val code: Int? = null,
	val data: DataGrate? = null,
	val message: String? = null,
	val status: Boolean? = null
)
data class ResponseGrateList(
	val code: Int? = null,
	val data: ArrayList<DataGrate>? = null,
	val message: String? = null,
	val status: Boolean? = null
)
data class DataGrate(
	val date: String? = null,
	val text: String? = null,
	val id_user: String? = null,
	val id_grate : String? = null
)

