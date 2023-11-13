package com.comunidadedevspace.taskbeats.data.Remote

import com.google.gson.annotations.SerializedName

//essa class vai fazer a Serializaçao da resposta que tenho do beckend
data class  NewsResponce(
    val data: List<NewsDto>
)
data class NewsDto(
    @SerializedName("uuid")
    val id : String,
    @SerializedName("snippet")
    val content : String,
    @SerializedName("image_url")
    val imageUrl : String,

    val title : String
)