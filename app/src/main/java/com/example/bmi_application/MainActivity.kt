package com.example.bmi_application

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_bmi_insert.*
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import org.json.JSONArray


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //TODO 入力データがnullの場合のエラー処理

        bmiList.setOnClickListener {
            var bmiList = lordBmiList()
            val fragment = bmiListFragment()
            val fragmentManager = this.getSupportFragmentManager()
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
                .commit()
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
                    height = inputHeight.text.toString().toDouble() / 100
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

    fun onSaveTapped(date: String?, user: bmiUser) {

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit {
            val gson = Gson()
            gson.toJson(user)
            putString(date, gson.toJson(user))
                .apply()
        }

    }

    fun bmiCalculate(a: Double, b: Double) = b / (a * a)

    fun lordBmiList(): ArrayList<String> {
        val list = ArrayList<String>()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val strJson = prefs.getString("20", "")
        if (strJson != "") {
            val jsonAry = JSONArray(strJson)
            for (i in 0 until jsonAry.length()) {
                list.add(jsonAry.getString(i))
            }

        }
        return list
    }
}