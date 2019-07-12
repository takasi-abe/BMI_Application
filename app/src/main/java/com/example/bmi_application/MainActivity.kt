package com.example.bmi_application

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_bmi_insert.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*
import org.json.JSONArray
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //TODO 入力データがnullの場合のエラー処理

        bmiList.setOnClickListener {
            val pref = PreferenceManager.getDefaultSharedPreferences(this)
            val jsonArray = JSONArray(pref.getString("DATE_LIST", "[]"))

            for (i in 0 until jsonArray.length()) {
                Log.i("loadArrayList", "[$i] -> " + jsonArray.get(i))
            }
//            var bmiList = lordBmiList()
//            println(bmiList)
//            val fragment = bmiListFragment()
//            val fragmentManager = this.getSupportFragmentManager()
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.container, fragment)
//                .commit()
        }

        insert.setOnClickListener {
            val fragment = bmiInsertFragment()
            val fragmentManager = this.getSupportFragmentManager()
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
                .commit()
        }

        //BMI計算機能
        var bmi: String? = null
        var height: Double = 0.0
        var weight: Double = 0.0
        val comment: String? = null
        val df = SimpleDateFormat("yyyy/MM/dd")
        val date = df.format(Date())
        var user = bmiUser(bmi, height, weight, comment, date)
        calcButton.setOnClickListener {
                //入力された数値が正しい場合のみBMIを計算する
//                if(inputHeight.text != null || inputWeight.text != null) {
//                    bmiResult.text = "身長および体重を入力してください"
//                } else {
                    height = inputHeight.text.toString().toDouble()
                    weight = inputWeight.text.toString().toDouble()
                    //計算結果を表示する
                    bmi = String.format("%1$.1f", bmiCalculate(height, weight))
                    bmiResult.text = "あなたのBMIは $bmi です。"

                user.date = date
                user.bmi = bmi
                user.height = height
                user.weight = weight
                user.comment = inputComment.toString()
//                }

            }

        //入力フォームのクリア
        delete.setOnClickListener {
            inputHeight.setText(" ")
            inputWeight.setText(" ")
            bmiResult.setText("あなたのBMIは     です。")
            inputComment.setText(" ")
        }

        //bmi計算結果の保存
        save.setOnClickListener {
            onSaveTapped(date, user)
        }
    }

    fun onSaveTapped(date: String, user: bmiUser) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
//        val jsonArray = JSONArray(pref.getString("DATE_LIST", " "))
//        var datelist = mutableListOf<String?>()
        val datelist: List<String> = loadDateList(pref)

//        for (i in 0 until jsonArray.length()) {
//            datelist.add(jsonArray.get(i) as String)
//        }

//        if (pref.getString(date, null) != null) {
//            datelist.add(date)
            println(datelist)
            saveBmiDateList(datelist)
//        }
        pref.edit {
            val bmiGson = Gson()
            bmiGson.toJson(user)
            putString(date, bmiGson.toJson(user))
                .apply()
        }
    }

    fun bmiCalculate(a: Double, b: Double) = b / (a * a) * 10000

    fun saveBmiDateList(arrayList:List<String?>){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit {
            val dateGson = Gson()
            dateGson.toJson(arrayList)
            putString("DATE_LIST", dateGson.toJson(dateGson))
                .apply()
        }

    }
    fun loadDateList(pref:SharedPreferences): List<String> {
        val dateGson = Gson()
        val dateList: List<String>
        val listType = object : TypeToken<List<String>>() {}.type
        dateList = dateGson.fromJson(pref.getString("DATE_LIST", ""), object : TypeToken<List<String>>() {}.type)
        return dateList
    }

//    fun lordBmiList(): ArrayList<String> {
//        val list = ArrayList<String>()
//        val pref = PreferenceManager.getDefaultSharedPreferences(this)
//        val strJson:
//        val jsonAry = JSONArray(strJson)
//
//        if (strJson != " ") {
//            for (i in 0 until jsonAry.length()) {
//                list = pref.getString(date," ")
//            }
//
//        }
//        return list
//    }
}