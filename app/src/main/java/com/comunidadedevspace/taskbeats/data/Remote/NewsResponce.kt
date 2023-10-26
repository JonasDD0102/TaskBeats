package com.comunidadedevspace.taskbeats.data.Remote
//essa class vai fazer a Serializaçao da resposta que tenho do beckend
data class NewsResponce(
    val category : String,
    val data: List<NewsDto>
)

data class NewsDto(
    val content : String,
    val id : String,
    val imageUrl : String,
    val title : String
)