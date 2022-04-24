package com.thundernorain.fillwordgame.domain.model.check_word

import com.google.gson.annotations.SerializedName

data class CheckWordRequest(

    @SerializedName("word")
    val word: String,
)
