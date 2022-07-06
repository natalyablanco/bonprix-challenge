package com.example.bonprixchallenge.domain

data class Categories(val categories: List<Category>)

data class Category(
    val label: String = "",
    val url: String? = null,
    val image: String? = null,
    val children: List<Category>? = null,
)