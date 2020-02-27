package com.dlong.dl10dialog.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.dlong.dl10dialog.R
import com.dlong.dl10dialog.databinding.ItemIconBinding

/**
 * @author D10NG
 * @date on 2020/2/27 11:04 AM
 */
class GridIconAdapter constructor(
    val context: Context,
    var mList: List<Int> = listOf()
) : BaseAdapter() {

    inner class ViewHolder constructor(
        val binding: ItemIconBinding
    ) {
        fun bind(iconRes: Int) {
            binding.imgIcon.setImageResource(iconRes)
            binding.executePendingBindings()
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder = if (convertView == null)
            ViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(context),
                    R.layout.item_icon, parent, false))
        else convertView.tag as ViewHolder
        val iconInfo = this.mList[position]
        holder.bind(iconInfo)

        val convert = holder.binding.root
        convert.tag = holder
        return convert
    }

    override fun getItem(position: Int): Any = this.mList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = this.mList.size
}