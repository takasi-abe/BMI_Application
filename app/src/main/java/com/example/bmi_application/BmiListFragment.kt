package com.example.bmi_application

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_bmi_list.*


class BmiListFragment : Fragment() {

    lateinit var mainContext: Context
    lateinit var bmiListFunction: BmiListFunction

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bmi_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO 警告は対応してください
        val pref = PreferenceManager.getDefaultSharedPreferences(mainContext)
        val recyclerView = list
        val bmiList = bmiListFunction.lordBmiList(pref, mainContext)
        val adapter = MybmiRecyclerViewAdapter(bmiListFunction.ConvertToBmiUser(bmiList))

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    fun setContext(context: Context) {
        this.mainContext = context
    }

    fun intoBmiListFunction(function: BmiListFunction){
        this.bmiListFunction = function
    }

}
