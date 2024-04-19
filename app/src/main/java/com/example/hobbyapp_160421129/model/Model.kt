package com.example.hobbyapp_160421129.model

data class News(
    var id:String?,
    var image:String?,
    var title:String?,
    var author:String?,
    var desc:String?,
    var news:List<String>?
)

data class Users(
    var id:String?,
    var first_name:String?,
    var last_name:String?,
    var email: String?,
    var username:String?,
    var password:String?,
    var photo:String?
)