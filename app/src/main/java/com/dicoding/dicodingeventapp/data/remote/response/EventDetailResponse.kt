package com.dicoding.dicodingeventapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class EventDetailResponse(

	@field:SerializedName("error")
	val error: Boolean = false,

	@field:SerializedName("message")
	val message: String = "",

	@field:SerializedName("event")
	val event: Event? = null
)

data class Event(

	@field:SerializedName("summary")
	val summary: String = "",

	@field:SerializedName("mediaCover")
	val mediaCover: String = "",

	@field:SerializedName("registrants")
	val registrants: Int = 0,

	@field:SerializedName("imageLogo")
	val imageLogo: String = "",

	@field:SerializedName("link")
	val link: String = "",

	@field:SerializedName("description")
	val description: String = "",

	@field:SerializedName("ownerName")
	val ownerName: String = "",

	@field:SerializedName("cityName")
	val cityName: String = "",

	@field:SerializedName("quota")
	val quota: Int = 0,

	@field:SerializedName("name")
	val name: String = "",

	@field:SerializedName("id")
	val id: Int = 0,

	@field:SerializedName("beginTime")
	val beginTime: String = "",

	@field:SerializedName("endTime")
	val endTime: String = "",

	@field:SerializedName("category")
	val category: String = ""
)
