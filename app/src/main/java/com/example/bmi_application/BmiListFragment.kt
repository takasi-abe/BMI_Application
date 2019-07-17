package com.example.bmi_application

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_bmi_list.*


class BmiListFragment : Fragment() {

    lateinit var mainContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bmi_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = mainContext as MainActivity
        val recyclerView = list
        val adapter = MybmiRecyclerViewAdapter(mainActivity.ConvertToBmiUser())

        val pref = PreferenceManager.getDefaultSharedPreferences(this.activity) // getActivity()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    fun setContext(context: Context) {
        this.mainContext = context
    }
}
