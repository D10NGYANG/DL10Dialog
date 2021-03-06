package com.dlong.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.dlong.dialog.databinding.DialogEditViewBinding

/**
 * 带编辑框的弹窗
 *
 * @author D10NG
 * @date on 2019-11-23 10:05
 */
open class EditDialog constructor(
    context: Context
) : BaseDialog(context) {

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
    private val edtMap: MutableMap<String, DialogEditViewBinding> = mutableMapOf()

    /**
     * 添加一个编辑框
     * @param tag 标签
     * @param text 初始文本
     * @param hint 提示文本
     */
    fun addEdit(tag: String, text: String, hint: String) : EditDialog {
        val viewBinding: DialogEditViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_edit_view, null, false
        )
        viewBinding.edtText.setText(text)
        viewBinding.tiLayout.hint = hint
        binding.contentLayout.addView(viewBinding.root)
        edtMap[tag] = viewBinding
        return this
    }

    /**
     * 移除编辑框
     * @param tag 标签
     */
    fun removeEdit(tag: String) : EditDialog {
        if (edtMap[tag] != null) {
            binding.contentLayout.removeView(edtMap[tag]?.root)
            edtMap.remove(tag)
        }
        return this
    }

    /**
     * 设置输入类型
     * @param tag 标签
     * @param type 类型
     */
    fun setInputType(tag: String, type: Int) : EditDialog {
        edtMap[tag]?.edtText?.inputType = type
        return this
    }

    /**
     * 获取输入文本
     * @param tag 标签
     */
    fun getInputText(tag: String) : String {
        return edtMap[tag]?.edtText?.text.toString().trim()
    }

    /**
     * 显示错误信息
     * @param tag 标签
     * @param value 信息
     */
    fun setError(tag: String, value: String) : EditDialog {
        edtMap[tag]?.tiLayout?.error = value
        return this
    }
}