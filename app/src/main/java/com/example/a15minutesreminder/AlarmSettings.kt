package com.example.a15minutesreminder

data class AlarmSettings(
    var startTime: List<Int> = mutableListOf<Int>(0, 0),
    val stopTime: List<Int> = mutableListOf<Int>(0, 0),
)