package com.thundernorain.fillwordgame.domain.model.check_word

import com.google.gson.annotations.SerializedName

enum class CheckWordResult {

    @SerializedName("correct")
    CORRECT,

    @SerializedName("wrong")
    WRONG,
}
