package com.thundernorain.server.model.check_word

import com.google.gson.annotations.SerializedName

data class CheckWordResponse(

    @SerializedName("result")
    val result: CheckWordResult?,
)
