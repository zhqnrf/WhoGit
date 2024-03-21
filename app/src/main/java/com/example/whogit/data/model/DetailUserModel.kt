package com.example.whogit.data.model

import com.google.gson.annotations.SerializedName

data class DetailUserModel(

	val createdAt: String,

	val login: String,

	val type: String,

	val blog: String,

	val id: Int,

	val email: Any,

	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	val following: Int,

	val name: String,

)
