package com.example.bonprixchallenge.domain

data class Categories (
    val categories: List<Category>
) {
    data class Category(
        val children: List<Category>? = null,
        val image: String? = null,
        val label: String? = null,
        val url: String? = null
    )

    fun isEmpty(): Boolean {
        return categories.isEmpty()
    }
}