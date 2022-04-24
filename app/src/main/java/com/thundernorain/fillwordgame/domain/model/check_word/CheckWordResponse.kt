package com.thundernorain.fillwordgame.domain.model.check_word

import com.google.gson.annotations.SerializedName

data class CheckWordResponse(

    @SerializedName("result")
    val result: CheckWordResult?,
)
