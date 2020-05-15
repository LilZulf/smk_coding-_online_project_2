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

