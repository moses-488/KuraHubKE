package com.moseswn.kurahubke.modules

data class ChecklistItem(
    val title: String = "",
    val description: String = "",
    val order: Int = 0,
    val isRequired: Boolean = true
)
