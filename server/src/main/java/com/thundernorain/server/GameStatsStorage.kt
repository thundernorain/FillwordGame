package com.thundernorain.server

class GameStatsStorage {

    private val words = mutableListOf<String>()

    fun onNewGameStarted() {
        words.clear()
    }

    fun addWords(words: List<String>) {
        this.words.addAll(words)
        println(words)
    }

    fun checkWordInList(word: String) = words.contains(word)
}