package com.thundernorain.server.model.fillword_game

import com.google.gson.annotations.SerializedName
import com.thundernorain.server.model.fillword_game.Difficulty

data class FillwordRequest(

    @SerializedName("difficulty")
    val difficulty: Difficulty,
)
