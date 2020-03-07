package com.dlong.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.dlong.dialog.databinding.DialogSelectViewBinding

/**
 * @author D10NG
 * @date on 2020/3/7 1:49 PM
 */
class MultiSelectDialog constructor(
    private val context: Context
) : BaseDialog<MultiSelectDialog>(context) {

    init {
        // 改变宽度
        val params0 = binding.scrollView.layoutParams
        params0.width = LinearLayout.LayoutParams.MATCH_PARENT
        binding.scrollView.layoutParams = params0
        val params = binding.contentLayout.layoutParams
        params.width = LinearLayout.LayoutParams.MATCH_PARENT
        binding.contentLayout.layoutParams = params
    }

    /** 编辑框列表 */
    private val bindMap: MutableMap<String, DialogSelectViewBinding> = mutableMapOf()

    /** 添加一个选项 */
    fun addSelectItem(text: String, isSelect: Boolean) : MultiSelectDialog {
        val viewBinding: DialogSelectViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_select_view, null, false
        )
        viewBinding.cbItem.text = text
        viewBinding.cbItem.isChecked = isSelect
        binding.contentLayout.addView(viewBinding.root)
        bindMap[text] = viewBinding
        return this
    }

    /** 移除一个选项 */
    fun removeSelectItem(text: String) {
        if (bindMap[text] != null) {
            binding.contentLayout.removeView(bindMap[text]?.root)
            bindMap.remove(text)
        }
    }

    /** 选项是否被选中 */
    fun isItemSelect(text: String) : Boolean {
        return bindMap[text]?.cbItem?.isChecked?: false
    }

    /** 获取所有选项结果 */
    fun getAllItemMap() : Map<String, Boolean> {
        val map = mutableMapOf<String, Boolean>()
        for (text in bindMap.keys) {
            map[text] = bindMap[text]?.cbItem?.isChecked?: false
        }
        return map
    }
}