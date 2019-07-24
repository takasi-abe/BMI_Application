package com.example.bmi_application

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var mainContext: Context
    private var bmiListFunction: BmiListFunction = BmiListFunction()

    // TODO SuppressLint は AndroidStudio の警告を無効化してしまうので、極力使わないようにしてください。
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainContext = this // TODO 結果としてmainContext は未使用になっているので削除してください
        callInsertFragment()

        //履歴画面のフラグメント表示
        bmiList.setOnClickListener {
          callBmiList()
        }

        //入力画面のフラグメント表示
        insert.setOnClickListener {
         callInsertFragment()
        }

    }

    //履歴画面の呼び出し
    private fun callBmiList() {
        val fragment = BmiListFragment()
        val fragmentManager = this.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        textView.text = "履歴"
        fragment.setContext(this)
        fragment.intoBmiListFunction(bmiListFunction)
        fragmentTransaction.replace(R.id.container, fragment)
            .commit()
    }

    //入力画面の呼び出し
    private fun callInsertFragment() {
        val fragment = BmiInsertFragment()
        // TODO this.getSupportFragmentManager() は alt + Enter を押すと、Kotlinにあった形式にAndroidStudioが変換してくれます
        val fragmentManager = this.getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()

        textView.text = "入力"
        fragment.setContext(this)
        fragment.intoBmiListFunction(bmiListFunction)
        fragmentTransaction.replace(R.id.container, fragment)
            .commit()
    }

}
