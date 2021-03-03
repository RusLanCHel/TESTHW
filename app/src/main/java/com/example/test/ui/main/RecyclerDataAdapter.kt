package com.example.test.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R


class RecyclerDataAdapter(val data: ArrayList<Film>, val onItemClickedCallBack: IRVOnItemClick) : RecyclerView.Adapter<RecyclerDataAdapter.ViewHolder>() {


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var textViewName: TextView
        var textViewImage: ImageView
        var linearLayout: LinearLayout
        init {
            linearLayout = itemView.findViewById(R.id.listItemLayout)
            textViewName = itemView.findViewById(R.id.itemName)
            textViewImage = itemView.findViewById(R.id.itemImage)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.itemrvlayout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (data == null){
            return 0
        } else {
            return data.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setItemText(holder, data.get(position).name)
        setItemImageText(holder, data.get(position).image.toString())
        setOnclickForItem(holder)
    }
    private fun setItemText(holder: ViewHolder, text: String?) {
        holder.textViewName.text = text
    }

    private fun setItemImageText(holder: ViewHolder, text: String){
        holder.textViewImage.setImageResource(R.drawable.ic_baseline_4k_24)
    }
    private fun setOnclickForItem(holder: ViewHolder){
        holder.linearLayout.setOnClickListener({
            if (onItemClickedCallBack != null) {
                onItemClickedCallBack.onItemClicked()
            }
        })
    }
}