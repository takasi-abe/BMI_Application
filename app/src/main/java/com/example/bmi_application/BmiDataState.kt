package com.example.bmi_application

data class BmiDataState (val type: BmiDataType, val bmiUser: BmiUser)

enum class BmiDataType (val value: Int) {
    SECTION(0),
    BMI(1),
    COMMENT(2);

    companion object {
        fun fromInt(value: Int): BmiDataType {
            return  values().firstOrNull {
                it.value == value
            } ?:BMI
        }
    }

}
