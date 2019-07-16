package com.example.bmi_application

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import kotlinx.android.synthetic.main.fragment_bmi.view.*

data class RecyclerViewStatus(val type: Int ,val item: BmiUser)
/**
 *
 */
class MybmiRecyclerViewAdapter(
    private val mValues: List<BmiUser> // 表示したいリスト
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    /**
     * Rowをここで作る
     * ここで使用するレイアウトを選択する。
     * 今回だと３パターンあるので、３つ返却すれば良い
     *
     * @param viewType 表示するRowのタイプ セクション、バディ、めも
     * @param parent Activityの情報 あんまきにしなくて良い
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        /** ここに３パターンのreturnを書く */

        when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.section_row, parent, false)
                return sectionViewHolder(view)
            }

            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_bmi, parent, false)
                return bmiViewHolder(view)
            }

            2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.comment_row, parent, false)
                return commentViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_bmi, parent, false)
                return bmiViewHolder(view)
            }

        }
    }

    /**
     * レイアウトに値をバインドする
     * レイアウトの表示したいViewに値を結びつけるのがここ
     *
     * [bmiuser1 ,bmiuser2 ,bmiuser3] 共有ぷリファレンスから取得したリスト
     *
     * [(section1 ,type_section) ,(bmiuser1 ,type_body) ,bmiuser2 ,bmiuser2_memo ,section2 ,bmiuser3 ,bmiuser3_memo]
     *
     * sectionで欲しいデータは月情報のみ
     * bodyで欲しいデータは、bmi 身長 体重 日付
     * memoで欲しいデータは、コメントのみ
     *
     * typeが0ならbody
     * typeが1ならsection
     * typeが2ならmemo
     *
     * [RecyclerViewStatus(type_section=1 ,bmiuser1) ,RecyclerViewStatus(type_body=0 ,bmiuser1) ,...]
     *
     * val list: List<String> = mutableListOf("a" ,"b" ,"c" ,"d")
     * val position = 0
     *
     * list[position]
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is sectionViewHolder -> {
                val item = mValues[position]

                holder.sectionView.text = item.month

            }

            is bmiViewHolder -> {
                val item = mValues[position]

                holder.bmiView.text = item.bmi
                holder.heightView.text = item.height.toString()
                holder.weightView.text = item.weight.toString()
                holder.dayView.text = item.day

            }

            is commentViewHolder -> {
                val item = mValues[position]

                holder.commentView.text = item.comment

            }
        }
        // holder.mIdView.text = item.id


        /** レイアウトのViewに値を入れる処理を書く viewのID.text = "value" */
//        holder.mContentView.text = item.content

//        with(holder.mView) {
//            tag = item
//            setOnClickListener(mOnClickListener)
//        }
    }

    /** リストのサイズを返却する */
    override fun getItemCount(): Int = mValues.size

    /** RecyclerViewで使用するレイアウトの数だけ作成する。 */

    /** バディ用 3つバインドする*/
    inner class bmiViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val bmiView: TextView = mView.findViewById(R.id.item_bmi)
        val heightView: TextView = mView.findViewById(R.id.item_height)
        val weightView: TextView = mView.findViewById(R.id.item_weight)
        val dayView: TextView = mView.findViewById(R.id.item_day)
    }

    /** セクション用 1つバインドする */
    inner class sectionViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val sectionView: TextView = mView.findViewById(R.id.section)
    }

    /** メモ用 1つバインドする */
    inner class commentViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val commentView: TextView = mView.findViewById(R.id.comment)
    }
}
