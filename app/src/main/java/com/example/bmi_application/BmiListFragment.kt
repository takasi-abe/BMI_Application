package com.example.bmi_application

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_bmi_list.*
import com.google.gson.Gson


class BmiListFragment : Fragment() {

    fun onViewCreated(view: RecyclerView, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = MainActivity()
        val recyclerView = list

        var bmiList = mainActivity.lordBmiList()
        var bmiUserList = mutableListOf<BmiUser>()
        val gson = Gson()

        bmiList.forEach {
            it?.let {
                val comvertList = gson.fromJson(it, BmiUser::class.java);
                bmiUserList.add(comvertList)
            }
        }

        val adapter = MybmiRecyclerViewAdapter(bmiUserList)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bmi_list, container, false)

        return view
    }
}
