package com.example.bmi_application

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson


class BmiListFunction {

    //SharedPreferenceからBMIデータを呼び出すためのキーのリストを作成
    fun lordDateList(pref: SharedPreferences, mainContext: Context): ArrayList<String?> {
        var dateId: Int = 0
        var dateKey: String? = pref.getString("0", null)
        var datelist: ArrayList<String?> = arrayListOf()

        while (dateKey != null) {
            datelist.add(dateKey)
            dateId++
            dateKey = pref.getString("$dateId", null)
        }
        return datelist
    }

    //        SharedPreferenceからBMIデータをリストとして呼び出す
    fun lordBmiList(pref: SharedPreferences, mainContext: Context): ArrayList<String?> {
        val dateList = lordDateList(pref, mainContext)
        var dateKey: String?
        val bmiList = ArrayList<String?>()
        for (i in 0 until dateList.size) {
            dateKey = dateList.get(i)
            bmiList.add(pref.getString(dateKey, ""))
        }
        return bmiList
    }

    fun ConvertToBmiUser(bmiList: ArrayList<String?>): ArrayList<BmiDataState> {
        val bmiUserList = mutableListOf<BmiUser>()
        val gson = Gson()

        bmiList.forEach {
            it?.let {
                val comvertList = gson.fromJson(it, BmiUser::class.java);
                bmiUserList.add(comvertList)
            }
        }

        val bmiDataStatusList = arrayListOf<BmiDataState>()

        var sectionUniqueCheak: String = "MM"
        for (data in bmiUserList) {
            if (data.month != sectionUniqueCheak) {
                val section = BmiDataState(BmiDataType.SECTION, data)
                bmiDataStatusList.add(section)
                sectionUniqueCheak = data.month
            }
            val bmiData = BmiDataState(BmiDataType.BMI, data)
            bmiDataStatusList.add(bmiData)

            val comment = BmiDataState(BmiDataType.COMMENT, data)
            bmiDataStatusList.add(comment)
         }

        return bmiDataStatusList
    }
}
