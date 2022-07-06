package com.example.bonprixchallenge.navigation

import com.example.bonprixchallenge.domain.Category

class Navigation {
    var stack: MutableList<Category> = emptyList<Category>().toMutableList()
}