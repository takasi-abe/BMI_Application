package com.example.bmi_application

import java.util.*

class bmiUser{
    var bmi: String? = null
    var height: Double = 0.0
    var weight: Double = 0.0
    var comment: String? = null
    lateinit var date: String

    constructor(bmi: String?, height: Double, weight: Double, comment: String?, date: String) {
        this.bmi = bmi
        this.height = height
        this.weight = weight
        this.comment = comment
        this.date = date
    }

}