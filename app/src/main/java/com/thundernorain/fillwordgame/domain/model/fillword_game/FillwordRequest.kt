package com.thundernorain.fillwordgame.domain.model.fillword_game

import com.google.gson.annotations.SerializedName

data class FillwordRequest(

    @SerializedName("difficulty")
    val difficulty: Difficulty,
)
