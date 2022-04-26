package com.thundernorain.server.creator

import com.google.gson.Gson
import com.thundernorain.server.GameStatsStorage
import com.thundernorain.server.SocketServer
import com.thundernorain.server.Utils
import com.thundernorain.server.model.Words
import com.thundernorain.server.model.fillword_game.Difficulty
import com.thundernorain.server.model.fillword_game.FillwordResponse
import java.io.File
import kotlin.random.Random

class FillwordResponseCreator {

    fun create(
        difficulty: Difficulty,
        gameStatsStorage: GameStatsStorage,
        gson: Gson
    ): FillwordResponse {
        val words = deserializeWords(gson)
        var inGameWords = emptyList<String>()
        words?.let {
            inGameWords = getInGameWords(it.words, difficulty)
        }
        val timeToSolve = getTimeToSolve(inGameWords, difficulty)
        val gameField = getGameField(inGameWords, difficulty)
        gameStatsStorage.addWords(inGameWords)
        return FillwordResponse(
            gameField = gameField,
            wordsCount = inGameWords.count(),
            timeToSolve = timeToSolve
        )
    }

    private fun deserializeWords(gson: Gson): Words? {
        val jsonString: String = File(SocketServer.WORDS_FILE).readText(Charsets.UTF_8)
        return runCatching { gson.fromJson(jsonString, Words::class.java) }
            .getOrNull()
    }

    private fun getInGameWords(
        allWords: List<String>,
        difficulty: Difficulty
    ): List<String> {
        val maxSymbolInWord = when (difficulty) {
            Difficulty.EASY -> EASY_MAX_SYMBOL_IN_WORD_COUNT
            Difficulty.MEDIUM -> MEDIUM_MAX_SYMBOL_IN_WORD_COUNT
            Difficulty.HARD -> HARD_MAX_SYMBOL_IN_WORD_COUNT
        }
        val maxSymbolCount = when (difficulty) {
            Difficulty.EASY -> EASY_SYMBOL_COUNT
            Difficulty.MEDIUM -> MEDIUM_SYMBOL_COUNT
            Difficulty.HARD -> HARD_SYMBOL_COUNT
        }
        var currentSymbolCount = 0
        val result = mutableListOf<String>()

        while (currentSymbolCount < maxSymbolCount) {
            val randomIndex = Random.nextInt(0, allWords.size - 1)
            val currentWord = allWords[randomIndex].uppercase()
            if (result.size >= allWords.size) return result
            if (result.contains(currentWord) || currentWord.length > maxSymbolInWord) {
                continue
            }
            result.add(currentWord)
            currentSymbolCount += currentWord.length
        }

        return result
    }

    private fun getTimeToSolve(words: List<String>, difficulty: Difficulty): Long {
        val multiplier = when (difficulty) {
            Difficulty.EASY -> EASY_MULTIPLIER
            Difficulty.MEDIUM -> MEDIUM_MULTIPLIER
            Difficulty.HARD -> HARD_MULTIPLIER
        }
        return words.fold(0L) { sum, word ->
            sum + word.length * multiplier * TIME_TO_SYMBOL
        }
    }

    private fun getGameField(
        words: List<String>,
        difficulty: Difficulty
    ): List<List<Char>> {
        val fieldHeight = when (difficulty) {
            Difficulty.EASY -> EASY_FIELD_HEIGHT
            Difficulty.MEDIUM -> MEDIUM_FIELD_HEIGHT
            Difficulty.HARD -> HARD_FIELD_HEIGHT
        }
        val result = List(fieldHeight) { MutableList(FIELD_WIDTH) { EMPTY_CHAR } }
        for (word in words) {
            var randomX = Random.nextInt(0, fieldHeight - 1)
            var randomY = Random.nextInt(0, FIELD_WIDTH - 1)
            while (result[randomX][randomY] != EMPTY_CHAR) {
                randomX = Random.nextInt(0, fieldHeight - 1)
                randomY = Random.nextInt(0, FIELD_WIDTH - 1)
            }
            var index = 0
            result[randomX][randomY] = word[index]
            while (index < word.length) {
                when {
                    result.getOrNull(randomX + 1)
                        ?.getOrNull(randomY) == EMPTY_CHAR -> result[randomX++][randomY] = word[index].uppercaseChar()
                    result.getOrNull(randomX - 1)
                        ?.getOrNull(randomY) == EMPTY_CHAR -> result[randomX--][randomY] = word[index].uppercaseChar()
                    result.getOrNull(randomX)
                        ?.getOrNull(randomY + 1) == EMPTY_CHAR -> result[randomX][randomY++] = word[index].uppercaseChar()
                    result.getOrNull(randomX)
                        ?.getOrNull(randomY - 1) == EMPTY_CHAR -> result[randomX][randomY--] = word[index].uppercaseChar()
                }
                index++
            }
        }
        for (list in result) {
            val mappedList = list.map { sym ->
                if (sym != EMPTY_CHAR) return@map sym
                val randIndex = Random.nextInt(0, Utils.rusAlphabet.size - 1)
                Utils.rusAlphabet[randIndex]
            }
            list.clear()
            list.addAll(mappedList)
        }
        return result
    }

    companion object {
        private const val LOG_TAG = "FillwordResponseCreator"

        private const val EMPTY_CHAR = '*'

        private const val FIELD_WIDTH = 7

        private const val EASY_FIELD_HEIGHT = 5
        private const val MEDIUM_FIELD_HEIGHT = 9
        private const val HARD_FIELD_HEIGHT = 14

        private const val EASY_SYMBOL_COUNT = 12
        private const val MEDIUM_SYMBOL_COUNT = 28
        private const val HARD_SYMBOL_COUNT = 48

        private const val EASY_MAX_SYMBOL_IN_WORD_COUNT = 5
        private const val MEDIUM_MAX_SYMBOL_IN_WORD_COUNT = 8
        private const val HARD_MAX_SYMBOL_IN_WORD_COUNT = 24

        private const val TIME_TO_SYMBOL = 3

        private const val EASY_MULTIPLIER = 3
        private const val MEDIUM_MULTIPLIER = 2
        private const val HARD_MULTIPLIER = 1
    }
}