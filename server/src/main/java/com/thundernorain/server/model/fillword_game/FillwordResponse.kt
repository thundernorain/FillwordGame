package com.thundernorain.server.model.fillword_game

import com.google.gson.annotations.SerializedName

data class FillwordResponse(

    @SerializedName("gameField")
    val gameField: List<List<Char>>?,

    @SerializedName("wordsCount")
    val wordsCount: Int?,

    @SerializedName("timeToSolve")
    val timeToSolve: Long?,
)
