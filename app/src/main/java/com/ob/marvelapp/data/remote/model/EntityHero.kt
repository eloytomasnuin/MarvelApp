package com.ob.marvelapp.data.remote.model

data class EntityHero (
    val id : Int,
    val name : String,
    val description : String,
    val modified : String,
    val thumbnail : Thumbnail,
    val resourceURI : String,
    val comics : Comics,
    val series : Series,
    val stories : Stories,
    val events : Events,
    val urls : List<Urls>
)

data class Thumbnail (
    val path : String,
    val extension : String
)

data class Comics (
    val available : Int,
    val collectionURI : String,
    val items : List<Items>,
    val returned : Int
)

data class Series (
    val available : Int,
    val collectionURI : String,
    val items : List<Items>,
    val returned : Int
)

data class Stories (
    val available : Int,
    val collectionURI : String,
    val items : List<Items>,
    val returned : Int
)

data class Events (
    val available : Int,
    val collectionURI : String,
    val items : List<Items>,
    val returned : Int
)

data class Urls (
    val type : String,
    val url : String
)

data class Items (
    val resourceURI : String,
    val name : String
)