package com.example.bmi_application

import java.text.SimpleDateFormat
import java.time.Month

class BmiUser {
    var bmi: String? = null
    var height: Double = 0.0
    var weight: Double = 0.0
    var comment: String? = null
    var date: String
    var month: String
    var day: String

    constructor( // TODO alt + Enter ですごいきれいにしてくれます
        bmi: String?,
        height: Double,
        weight: Double,
        comment: String?,
        date: String,
        month: String,
        day: String
    ) {
        this.bmi = bmi
        this.height = height
        this.weight = weight
        this.comment = comment
        this.date = date
        this.month = month
        this.day = day
    }

}