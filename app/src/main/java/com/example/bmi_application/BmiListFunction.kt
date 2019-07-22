package com.example.bmi_application

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson


class BmiListFunction {

    //SharedPreferenceからBMIデータを呼び出すためのキーのリストを作成
    fun lordDateList(pref: SharedPreferences, mainContext: Context): ArrayList<String?> {
        //日付データを管理する変数を宣言
        var dateId: Int = 0
        var dateKey: String? = pref.getString("0", null)
        var datelist: ArrayList<String?> = arrayListOf()

        //日付データが存在する場合そのデータを呼び出し、リストを作成
        while (dateKey != null) {
            datelist.add(dateKey)
            dateId++
            dateKey = pref.getString("$dateId", null)
        }

        //呼び出し元に返す
        return datelist
    }

    //SharedPreferenceからBMIデータをリストとして呼び出す
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

    //ArrayList<String?>型のデータからArrayList<BmiDataState>型のデータに変換する
    fun ConvertToBmiUser(bmiList: ArrayList<String?>): ArrayList<BmiDataState> {
        val bmiUserList = mutableListOf<BmiUser>()
        val gson = Gson()

        //型変換をしたデータを別のリストに保存
        bmiList.forEach {
            it?.let {
                val comvertList = gson.fromJson(it, BmiUser::class.java);
                bmiUserList.add(comvertList)
            }
        }

        //一覧画面に表示するリストを宣言
        val bmiDataStatusList = arrayListOf<BmiDataState>()

        //セクションに表示する変数を宣言
        var sectionUniqueCheak: String = "MM"

        //BMIデータをセクション、ボディ、コメントに振り分けて保存
        for (data in bmiUserList) {
            //セクション
            //現在セクションに表示されている値と比較し、異なる場合のみデータを保存
            if (data.month != sectionUniqueCheak) {
                val section = BmiDataState(BmiDataType.SECTION, data)
                bmiDataStatusList.add(section)
                sectionUniqueCheak = data.month
            }

            //ボディ
            val bmiData = BmiDataState(BmiDataType.BMI, data)
            bmiDataStatusList.add(bmiData)

            //コメント
            //コメントがある場合のみ保存
            if (!data.comment.isNullOrEmpty()) {
                val comment = BmiDataState(BmiDataType.COMMENT, data)
                bmiDataStatusList.add(comment)
            }
         }

        //呼び出し元に返す
        return bmiDataStatusList
    }

    //当日のデータを削除する
    fun deleteToday(pref: SharedPreferences,date: String, datekey: String?) {

        pref.edit().remove("0").remove(datekey)
            .apply()

    }
}
