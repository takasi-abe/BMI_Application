package com.example.bmi_application

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
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
    lateinit var mainContext: Context
    private var bmiListFunction: BmiListFunction = BmiListFunction()

    var height: Double = 0.0
    var weight: Double = 0.0
    var bmi: String = "      "
    var comment: String? = null

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
                insertNull()
            } else {
                bmiResult.text = ("あなたのBMIは" + bmiCalculate() + "です。")
            }

        }

        //入力フォームのクリア
        delete.setOnClickListener {
            inputHeight.setText(" ")
            inputWeight.setText(" ")
            bmiResult.text = ("あなたのBMIは      です。")
            inputComment.setText(" ")
        }

        //bmi計算結果の保存
        save.setOnClickListener {
            onSaveTapped()
        }
    }

    //BMi計算用メソッド
    fun bmiCalculate(): String {

        //入力された値を取得
        height = inputHeight.text.toString().toDouble()
        weight = inputWeight.text.toString().toDouble()

        //異常な値が入力された場合、アラートダイアログを表示
        if (height == 0.0 || weight == 0.0) {
            AlertDialog.Builder(mainContext)
                .setTitle("値が小さすぎます。正しい値を入力してください。")
                .setPositiveButton("ok") { dialog, which ->
                }.show()
            return bmi
        }

        //bmiの計算
        bmi = String.format("%1$.1f", weight / (height * height) * 10000)

        return bmi
    }

    fun onSaveTapped() {
        // BMIが計算されていない場合、アラートダイアログを表示
        if (bmi.isEmpty()) {
            saveNull()
        }

        val date = SimpleDateFormat("yyyy/MM/dd").format(Date()) // 登録した日付
        val month: String = SimpleDateFormat("MM").format(Date())  // 履歴表示用
        val day: String = SimpleDateFormat("dd").format(Date())    // 履歴表示用
        val user = BmiUser(bmi, height, weight, comment, date, month, day)
        val pref = PreferenceManager.getDefaultSharedPreferences(mainContext)
        val dateList = bmiListFunction.lordDateList(pref, mainContext)

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

    fun setContext(context: Context) {

        this.mainContext = context
    }

    fun intoBmiListFunction(function: BmiListFunction) {
        this.bmiListFunction = function
    }


    fun insertNull() {

        AlertDialog.Builder(mainContext)
            .setTitle("身長・体重を入力してください")
            .setPositiveButton("ok") { dialog, which ->
            }.show()
    }

    fun saveNull() {
        if (inputHeight.text.isEmpty() || inputWeight.text.isEmpty()) {

            insertNull()

        } else if (bmi == "") {
            AlertDialog.Builder(mainContext)
                .setTitle("BMIを計算してください")
                .setPositiveButton("ok") { dialog, which ->
                }.show()
        }
    }

}
