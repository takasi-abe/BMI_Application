package com.example.bmi_application

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class MybmiRecyclerViewAdapter(
    private val mValues: ArrayList<BmiDataState>// 表示したいリスト
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

        when (BmiDataType.fromInt(viewType)) {

            BmiDataType.SECTION -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.section_row, parent, false)
                return sectionViewHolder(view)
            }
            BmiDataType.BMI -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.bmi_row, parent, false)
                return bmiViewHolder(view)
            }

            BmiDataType.COMMENT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.comment_row, parent, false)
                return commentViewHolder(view)
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
                val item = mValues[position].bmiUser

                holder.sectionView.text = (item.month + "月")

            }

            is bmiViewHolder -> {
                val item = mValues[position].bmiUser

                holder.dayView.text = (item.day + "日")
                holder.bmiView.text = ("BMI：" + item.bmi)
                holder.heightView.text = ("身長：" +item.height.toString() + "cm")
                holder.weightView.text = ("体重："+ item.weight.toString() + "kg")

            }

            is commentViewHolder -> {
                val item = mValues[position].bmiUser

                holder.commentView.text = ("コメント：" + item.comment)

            }
        }

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
    inner class bmiViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val bmiView: TextView = mView.findViewById(R.id.bmi)
        val heightView: TextView = mView.findViewById(R.id.height)
        val weightView: TextView = mView.findViewById(R.id.weight)
        val dayView: TextView = mView.findViewById(R.id.day)
    }

    /** セクション用 1つバインドする */
    inner class sectionViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val sectionView: TextView = mView.findViewById(R.id.section)
    }

    /** メモ用 1つバインドする */
    inner class commentViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val commentView: TextView = mView.findViewById(R.id.comment)
    }

    override fun getItemViewType(position: Int): Int {
        return mValues[position].type.value
    }
}
