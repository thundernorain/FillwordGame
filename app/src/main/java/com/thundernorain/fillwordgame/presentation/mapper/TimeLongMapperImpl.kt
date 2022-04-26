package com.thundernorain.fillwordgame.presentation.mapper

import com.thundernorain.fillwordgame.domain.mapper.TimeLongMapper

class TimeLongMapperImpl : TimeLongMapper {

    override fun toString(timeInt: Long): String {
        val minutes = timeInt / 60
        val seconds = timeInt % 60

        if (minutes == 0L) return seconds.toString()

        val secondsString = seconds.toString()
        return "$minutes : ${if (secondsString.length == 1) "0$secondsString" else secondsString}"
    }
}