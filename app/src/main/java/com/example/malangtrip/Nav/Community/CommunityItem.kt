package com.example.malangtrip.Nav.Community

data class CommunityItem(
    var id: String?, // 글 ID
    val userId: String?, // 작성자 ID
    val userName: String?, // 작성자 이름
    val title: String?, // 글 제목
    val content: String?, // 글 내용
    val imageUrl: String?, // 이미지 URL
    val time: Long? // 작성 시간
)