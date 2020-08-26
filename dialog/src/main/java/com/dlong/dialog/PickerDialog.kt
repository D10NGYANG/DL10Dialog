package com.dlong.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.dlong.dialog.databinding.DialogPickerViewBinding
import com.dlong.dialog.impl.OnPickItemSelectListener
import kotlin.collections.List
import kotlin.collections.MutableMap
import kotlin.collections.mutableMapOf
import kotlin.collections.set
import kotlin.collections.toTypedArray

/**
 * @author D10NG
 * @date on 2020/2/26 1:48 PM
 */
open class PickerDialog constructor(
    context: Context
) : BaseDialog(context) {

    init {
        // 改变内容排列方向
        binding.contentLayout.orientation = LinearLayout.HORIZONTAL
    }

    /** 选择项列表 */
    val bindingMap: MutableMap<String, DialogPickerViewBinding> = mutableMapOf()

    /** 获取选中项文本内容 */
    fun getPickOnTag(tag: String) : String {
        val viewBinding = bindingMap[tag]?: return ""
        return viewBinding.picker.displayedValues[viewBinding.picker.value]
    }

    /** 是否有某个tag */
    fun isHasTag(tag: String): Boolean = bindingMap.containsKey(tag)
}

/**
 * 添加选择列表
 * @param tag 标签
 * @param selectItem 选择项
 * @param list 全部选项
 * @param start 开始文本
 * @param end 结束文本
 */
fun <T: PickerDialog> T.addPickList(
    tag: String,
    selectItem: String,
    list: List<String>,
    start: String,
    end: String,
    listener: (OnPickItemSelectListener<T>.() -> Unit)? = null,
    IListener: OnPickItemSelectListener<T>? = null
) : T {
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
        if (listener != null) {
            val selectItemListener = OnPickItemSelectListener<T>()
            selectItemListener.listener()
            selectItemListener.select(this as T, tag, newVal, list[newVal])
        }
        IListener?.select(this as T, tag, newVal, list[newVal])
    }
    if (bindingMap[tag] != null) {
        binding.contentLayout.removeView(bindingMap[tag]?.root)
    }
    binding.contentLayout.addView(viewBinding.root)
    bindingMap[tag] = viewBinding
    return this
}

/** 移除选择列表 */
fun <T: PickerDialog> T.removePickList(tag: String): T {
    if (bindingMap[tag] != null) {
        binding.contentLayout.removeView(bindingMap[tag]?.root)
        bindingMap.remove(tag)
    }
    return this
}
