package com.thundernorain.fillwordgame.domain.model.fillword_game

import com.google.gson.annotations.SerializedName

enum class Difficulty {

    @SerializedName("easy")
    EASY,

    @SerializedName("medium")
    MEDIUM,

    @SerializedName("hard")
    HARD,
}