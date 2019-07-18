package com.example.bmi_application

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_bmi_insert.*
import java.text.SimpleDateFormat
import java.util.*


class BmiInsertFragment : Fragment() {
    lateinit var mainContext: Context
    private var bmiListFunction: BmiListFunction = BmiListFunction()

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
            //入力された数値が正しい場合のみBMIを計算する

            if(inputHeight.text.isEmpty() || inputWeight.text.isEmpty() ){

                bmiResult.text = "正しい値を入力してください"

            } else {
                var height = inputHeight.text.toString().toDouble()
                var weight = inputWeight.text.toString().toDouble()
                var bmi = bmiCalculate(height, weight)

                bmiResult.text = "あなたのBMIは $bmi です。"
            }

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
            val comment: String? = inputComment.text.toString() // コメント

            var height: Double = inputHeight.text.toString().toDouble()
            var weight: Double = inputWeight.text.toString().toDouble()
            val bmi: String = bmiCalculate(height, weight)

            println(comment)

            onSaveTapped(bmi, height, weight, comment)
        }
    }

    //BMi計算用メソッド
    fun bmiCalculate(height: Double, weight: Double): String {

        //bmiの計算
        val bmi = String.format("%1$.1f", weight / (height * height) * 10000)

        return bmi
    }

    fun onSaveTapped(bmi: String?, height: Double, weight: Double, comment:String?) {
        val date = "2019/08/10"//SimpleDateFormat("yyyy/MM/dd").format(Date()) // 登録した日付
        val month: String = "08"//SimpleDateFormat("MM").format(Date())  // 履歴表示用
        val day: String = "10"//SimpleDateFormat("dd").format(Date())    // 履歴表示用
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

    fun setContext(context: Context) {

        this.mainContext = context
    }

    fun intoBmiListFunction(function: BmiListFunction){
        this.bmiListFunction = function
    }

}