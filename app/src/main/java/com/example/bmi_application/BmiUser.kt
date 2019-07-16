package com.example.bmi_application

import java.text.SimpleDateFormat
import java.util.*

class BmiUser{
    var bmi: String? = null
    var height: Double = 0.0
    var weight: Double = 0.0
    var comment: String? = null
    lateinit var date: String
    val month: String = SimpleDateFormat("MM").format(date)
    val day: String = SimpleDateFormat("dd").format(date)

    constructor(bmi: String?, height: Double, weight: Double, comment: String?, date: String) {
        this.bmi = bmi
        this.height = height
        this.weight = weight
        this.comment = comment
        this.date = date
    }

}