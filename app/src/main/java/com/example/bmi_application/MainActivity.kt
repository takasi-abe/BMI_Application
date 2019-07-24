package com.example.bmi_application

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var bmiListFunction: BmiListFunction = BmiListFunction()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        fragment.intoBmiListFunction(bmiListFunction)
        fragmentTransaction.replace(R.id.container, fragment)
            .commit()
    }

    //入力画面の呼び出し
    private fun callInsertFragment() {
        val fragment = BmiInsertFragment()
        val fragmentManager = this.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        textView.text = "入力"
        fragment.intoBmiListFunction(bmiListFunction)
        fragmentTransaction.replace(R.id.container, fragment)
            .commit()
    }

}
