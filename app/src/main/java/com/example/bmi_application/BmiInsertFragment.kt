package com.example.bmi_application

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_bmi_insert.*
import java.text.SimpleDateFormat
import java.util.*


class BmiInsertFragment : Fragment() {
    private var bmiListFunction: BmiListFunction = BmiListFunction()

    //BMIデータに用いる変数の宣言
    var height: Double = 0.0     //身長
    var weight: Double = 0.0     //体重
    var bmi: String = "      "   //BMIの計算結果
    var comment: String? = null  //コメント
    var date = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN).format(Date()) //登録した日付
    var month = SimpleDateFormat("MM", Locale.JAPAN).format(Date())        //月(履歴表示用)
    var day = SimpleDateFormat("dd", Locale.JAPAN).format(Date())          //日(履歴表示用)
    var dateKey: String? = null

    //SharedPreferenceの保存先
    private val preference = "com.example.bmi_application_preferences"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bmi_insert, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        //BMI計算機能
        calcButton.setOnClickListener {
            //身長・体重が入力されていない場合、アラートダイアログを表示
            if (inputHeight.text.isEmpty() || inputWeight.text.isEmpty()) {
                insertEmptyAlertDialog()
            } else {
                bmiResult.text = ("あなたのBMIは" + bmiCalculate() + "です。")
            }

        }

        //入力フォームのクリア
        delete.setOnClickListener {
            deleteTodayData()
        }

        //bmi計算結果の保存
        save.setOnClickListener {
            onSaveTapped()
        }
    }

    //BMi計算用メソッド
    private fun bmiCalculate(): String {

        //入力された値を取得
        height = inputHeight.text.toString().toDouble()
        weight = inputWeight.text.toString().toDouble()

        //入力値の規定パターン
        val pattern = Regex(
            """\d{1,3}\.\d""".trimIndent()
        )

        //規定のパターン以外の値が入力された場合アラートダイアログを表示
        if (!pattern.matches(inputHeight.text) || !pattern.matches(inputWeight.text)) {
            alertDialog("小数点第一位まで入力してください。")
            return bmi

        } else if (height == 0.0 || weight == 0.0) {

            //異常な値が入力された場合、アラートダイアログを表示
            alertDialog("値が小さすぎます。正しい値を入力してください。")
            return bmi
        }

        //bmiの計算
        bmi = String.format("%1$.1f", weight / (height * height) * 10000)

        return bmi
    }



    //BMIデータの保存
    private fun onSaveTapped() {
        // BMIが計算されていない場合、アラートダイアログを表示
        if (bmi == "      ") {
            saveEmptyAlertDialog()
            return
        }

        //保存するオブジェクトを宣言
        val user = BmiUser(bmi, height, weight, comment, date, month, day)    //データ保存用オブジェクト

        //入力されたコメントを変数に代入
        comment = inputComment.text.toString()

        //日付データを呼び出す
        val pref = activity?.getSharedPreferences(preference, MODE_PRIVATE)
        var dateList = bmiListFunction.lordDateList(pref)

        //入力したデータをオブジェクトに代入
        user.bmi = bmi
        user.height = height
        user.weight = weight
        user.date = date
        user.month = month
        user.day = day
        user.comment = comment

        // 保存した日のデータがない場合のみ その日付を保存する
        if (dateList.binarySearch(date) < 0) {

            //保存するBMIデータのキーを取得
            dateKey = dateList.size.toString()

            pref?.edit {
                putString(dateKey, date)
                    .apply()
            }
        }

        //Viewで入力したBMIデータを保存する
        pref?.edit {
            val bmiGson = Gson()
            bmiGson.toJson(user)
            putString(date, bmiGson.toJson(user))
                .apply()
        }

    }

    //当日のデータを消去する処理
    private fun deleteTodayData() {
        val pref = activity?.getSharedPreferences(preference, MODE_PRIVATE)

        //当日のデータのキーを取得
        val dateList = bmiListFunction.lordDateList(pref)


        //該当するデータを削除
        //
        if (!dateList.isEmpty() && date == dateList.last()) {
            dateKey = (dateList.size - 1).toString()
            bmiListFunction.deleteToday(pref, SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN).format(Date()), dateKey)

        }
    }

    //BMIデータを扱う処理を管理するメソッド
    fun intoBmiListFunction(function: BmiListFunction) {
        this.bmiListFunction = function
    }

    //計算する数値が空文字の場合
    private fun insertEmptyAlertDialog() {
        alertDialog("身長・体重を入力してください")
    }

    //保存する数値が空文字の場合
    private fun saveEmptyAlertDialog() {
        if (inputHeight.text.isEmpty() || inputWeight.text.isEmpty()) {

            insertEmptyAlertDialog()

        } else if (bmi == "      ") {
            alertDialog("BMIを計算してください")
        }
    }

    //アラートダイアログの表示
    /**
     * アラートダイアログの表示
     *
     * @param title
     */
    private fun alertDialog (title: String) {
        AlertDialog.Builder(activity)
            .setTitle(title)
            .setPositiveButton("ok") { _, _ ->
            }.show()
    }
}
