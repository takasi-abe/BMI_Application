package com.example.bmi_application

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager

import android.util.Log
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_bmi_insert.*
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import org.json.JSONArray
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var mainContext: Context
    var bmiListFunction: BmiListFunction = BmiListFunction()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainContext = this


        //TODO 入力データがnullの場合のエラー処理

        //履歴画面のフラグメント表示
        bmiList.setOnClickListener {
            val pref = PreferenceManager.getDefaultSharedPreferences(this)
            textView.text = "履歴"
            val fragment = BmiListFragment()
            val fragmentManager = this.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragment.setContext(this)

            fragmentTransaction.replace(R.id.container, fragment)
                .commit()
        }

        //入力画面のフラグメント表示
        insert.setOnClickListener {
            textView.text = "入力"
            val fragment = BmiInsertFragment()
            val fragmentManager = this.getSupportFragmentManager()
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.container, fragment)
                .commit()
        }

        //BMI計算時に用いる変数の宣言
        var bmi: String? = null     // BMI
        var height: Double = 0.0    // 身長
        var weight: Double = 0.0    // 体重
        val comment: String? = null // コメント
        val date = SimpleDateFormat("yyyy/MM/dd").format(Date()) // 登録した日付
        val month: String = SimpleDateFormat("MM").format(Date())  // 履歴表示用
        val day: String = SimpleDateFormat("dd").format(Date())    // 履歴表示用
        var user = BmiUser(bmi, height, weight, comment, date, month, day)


        //BMI計算機能
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

            //入力したデータをオブジェクトに代入
            user.date = date
            user.bmi = bmi
            user.height = height
            user.weight = weight
            user.comment = inputComment.toString()
            user.month = month
            user.day = day
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

    //保存ボタンを押下した際のメソッド
    fun onSaveTapped(date: String, user: BmiUser) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        var dateList = bmiListFunction.lordDateList(pref, this)

        // 保存した日のデータがない場合のみ その日付を保存する
        if (dateList.binarySearch ( date ) < 0 ) {
        pref.edit {
            putString("${dateList.size}", date)
                .apply()
            }
        }

        //Viewで入力したBMIデータを保存する
        pref.edit {
            val bmiGson = Gson()
            bmiGson.toJson(user)
            putString(date, bmiGson.toJson(user))
                .apply()
        }

    }

    //BMi計算用メソッド
    fun bmiCalculate(a: Double, b: Double) = b / (a * a) * 10000








}
