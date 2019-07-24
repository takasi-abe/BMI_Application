package com.example.bmi_application

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_bmi_list.*


class BmiListFragment : Fragment() {

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

        val pref = activity?.getSharedPreferences("com.example.bmi_application_preferences", Context.MODE_PRIVATE)
        val recyclerView = list
        val bmiList = bmiListFunction.lordBmiList(pref)
        val adapter = MybmiRecyclerViewAdapter(bmiListFunction.convertToBmiUser(bmiList))

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    fun intoBmiListFunction(function: BmiListFunction){
        this.bmiListFunction = function
    }

}
