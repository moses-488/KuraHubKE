package com.moseswn.kurahubke.models

data class Candidate(
    var id: String = "",
    var name: String = "",
    var party: String = "",
    var position: String = "", // e.g., President, Governor, MP
    var imageUrl: String = "",
    var voteCount: Int = 0
)
