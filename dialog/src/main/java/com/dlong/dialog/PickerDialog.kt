package com.dlong.dialog

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import com.dlong.dialog.databinding.DialogPickerViewBinding

/**
 * @author D10NG
 * @date on 2020/2/26 1:48 PM
 */
class PickerDialog constructor(
    private val context: Context
) : BaseDialog<PickerDialog>(context) {

    init {
        // 改变内容排列方向
        binding.contentLayout.orientation = LinearLayout.HORIZONTAL
    }

    /** 选择项列表 */
    private val bindingMap: MutableMap<String, DialogPickerViewBinding> = mutableMapOf()

    interface OnSelectItemListener{
        fun onSelect(dialog: PickerDialog, tag: String, position:Int, selectItem: String)
    }

    fun addPickList(tag: String, selectItem: String, list: List<String>, start: String, end: String) : PickerDialog {
        addPickList(tag, selectItem, list, start, end, null)
        return this
    }

    /**
     * 添加选择列表
     * @param tag 标签
     * @param selectItem 选择项
     * @param list 全部选项
     * @param start 开始文本
     * @param end 结束文本
     */
    fun addPickList(
        tag: String,
        selectItem: String,
        list: List<String>,
        start: String,
        end: String,
        listener: OnSelectItemListener?
    ) : PickerDialog {
        val viewBinding: DialogPickerViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_picker_view, null, false
        )
        // 设置前后文本
        viewBinding.startText = start
        viewBinding.endText = end
        // 设置选项
        viewBinding.picker.displayedValues = list.toTypedArray()
        viewBinding.picker.minValue = 0
        viewBinding.picker.maxValue = list.size -1
        // 设置选中
        viewBinding.picker.value = list.indexOf(selectItem)
        // 选择监听
        viewBinding.picker.setOnValueChangedListener { picker, oldVal, newVal ->
            listener?.onSelect(this, tag, newVal, list[newVal])
        }
        if (bindingMap[tag] != null) {
            binding.contentLayout.removeView(bindingMap[tag]?.root)
        }
        binding.contentLayout.addView(viewBinding.root)
        bindingMap[tag] = viewBinding
        return this
    }

    /** 移除选择列表 */
    fun removePickList(tag: String) {
        if (bindingMap[tag] != null) {
            binding.contentLayout.removeView(bindingMap[tag]?.root)
            bindingMap.remove(tag)
        }
    }

    /** 获取选中项文本内容 */
    fun getPickOnTag(tag: String) : String {
        val viewBinding = bindingMap[tag]?: return ""
        return viewBinding.picker.displayedValues[viewBinding.picker.value]
    }
}