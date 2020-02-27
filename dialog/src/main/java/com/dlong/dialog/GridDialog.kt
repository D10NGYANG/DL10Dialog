package com.dlong.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.dlong.dialog.databinding.DialogGridViewBinding

/**
 * @author D10NG
 * @date on 2020-01-07 13:10
 */
class GridDialog constructor(
    private val context: Context
) : BaseDialog<GridDialog>(context) {

    /** 编辑框列表 */
    private val gridMap: MutableMap<String, DialogGridViewBinding> = mutableMapOf()
    private companion object{
        const val TAG = "GridDialog"
    }

    /** 设置grid列表显示 */
    fun setGridList(adapter: BaseAdapter, numColumns: Int, onItemClick: OnItemClick?) : GridDialog {
        removeContent()
        gridMap.clear()
        val viewBinding: DialogGridViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_grid_view, null, false
        )
        viewBinding.gridView.adapter = adapter
        viewBinding.gridView.numColumns = numColumns
        viewBinding.gridView.setOnItemClickListener { p0, p1, p2, p3 ->
            onItemClick?.click(this@GridDialog, p2) }
        binding.contentLayout.addView(viewBinding.root)
        gridMap[TAG] = viewBinding
        return this
    }
}

/**
 * 点击选项
 */
interface OnItemClick{
    fun click(d: GridDialog, p: Int)
}