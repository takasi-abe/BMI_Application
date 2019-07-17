package com.example.bmi_application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class BmiInsertFragment : Fragment() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
////            //データの保存
////            val pref = PreferenceManager.getDefaultSharedPreferences(AppCompatActivity())
////            val editHeight = pref.getString("Height", " ")
////            val editWeight = pref.getString("Weight", " ")
////            val editBmi = pref.getString("BMI", " ")
////            val editComment = pref.getString("COMMENT", " ")
//
////            //保存ボタンを押された際の処理
////            save2.setOnClickListener {
////
////                onSaveTapped(bmi)
////            }
//    }

//    fun onSaveTapped(bmi: String?) {
//
//        val pref = PreferenceManager.getDefaultSharedPreferences(AppCompatActivity())
//        pref.edit{
//            putString("Height", height2.text.toString())
//            putString("Weight", weight2.text.toString())
//            putString("BMI", bmi)
//            putString("COMMENT", comment2.text.toString())
//                .apply()
//        }
//    }
//
//    fun bmiCalculate(a: Double, b: Double) = b / (a * a)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bmi_insert, container, false)


    }


//
//    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(contentxt.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }

}