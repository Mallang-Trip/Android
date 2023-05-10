package com.example.malangtrip.Nav.Community

data class CommunityItem(

    val userId: String?, // 작성자 ID
    val userName: String?, // 작성자 이름
    val title: String?, // 글 제목
    val content: String?, // 글 내용
    val time: String // 작성 시간
)