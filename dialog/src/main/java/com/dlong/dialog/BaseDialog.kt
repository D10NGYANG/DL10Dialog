package com.dlong.dialog

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.dlong.dialog.databinding.DialogDefLoadingViewBinding

/**
 * 基础弹窗
 *
 * @author D10NG
 * @date on 2019-10-25 14:49
 */
open class BaseDialog<T> constructor(
    private val context: Context
) {
    /** 弹窗创建器 */
    protected val builder = AlertDialog.Builder(context)
    /** 最终的弹窗实例 */
    protected var alert: AlertDialog? = null

    /** 按键存储 */
    protected val buttonMap = mutableMapOf<String, View>()

    /** 基础弹窗布局 */
    val binding: DialogDefLoadingViewBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.dialog_def_loading_view, null, false)

    /**
     * 创建
     */
    open fun create() : T {
        binding.loadIndeterminate = true
        binding.loadVisible = false
        builder.setView(binding.root)
        builder.setCancelable(false)
        alert = builder.create()
        return this as T
    }

    /**
     * 移除所有button
     */
    open fun removeAllButtons() {
        binding.buttonLayout.removeAllViews()
        buttonMap.clear()
    }

    /**
     * 移除content内容
     */
    open fun removeContent() {
        binding.contentLayout.removeAllViews()
    }

    /**
     * 设置标题
     */
    open fun setTittle(tittle: String) : T {
        binding.tittle = tittle
        return this as T
    }

    /**
     * 设置二级文本
     */
    open fun setMsg(msg: String) : T {
        binding.message = msg
        return this as T
    }

    /**
     * 设置图标
     */
    open fun setIcon(resId: Int) : T {
        binding.image.setImageResource(resId)
        return this as T
    }

    /**
     * 设置图标
     */
    open fun setIcon(bitmap: Bitmap) : T {
        binding.image.setImageBitmap(bitmap)
        return this as T
    }

    /**
     * 开始加载中
     */
    open fun startLoad(indeterminate: Boolean, progress: Int, max: Int) {
        binding.loadIndeterminate = indeterminate
        binding.loadProgress = progress
        binding.loadMax = max
        binding.loadVisible = true
    }

    /**
     * 获取总值
     */
    open fun getLoadMax() : Int = binding.loadMax

    /**
     * 获取进度
     */
    open fun getLoadProgress() : Int = binding.loadProgress

    /**
     * 更新进度
     */
    open fun  updateLoadProgress(progress: Int) {
        binding.loadProgress = progress
    }

    /**
     * 停止加载中
     */
    open fun stopLoad() {
        binding.loadVisible = false
    }

    /**
     * 添加button事件
     */
    open fun addAction(text: String, style: Int, onBtnClick: OnBtnClick?) : T {
        val view = createButton(text, style, onBtnClick)
        removeAction(text)
        binding.buttonLayout.addView(view)
        buttonMap[text] = view
        return this as T
    }

    /**
     * 删除按键
     */
    open fun removeAction(text: String) {
        if (buttonMap.containsKey(text)) {
            binding.buttonLayout.removeView(buttonMap[text])
            buttonMap.remove(text)
        }
    }

    /**
     * 显示
     */
    open fun show() {
        val act = context as Activity
        if (!act.isFinishing) alert?.show()
        alert?.window?.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dialog_def_bg))
    }

    /**
     * 关闭
     */
    open fun dismiss() {
        alert?.dismiss()
    }

    protected fun createButton(text: String, style: Int, onBtnClick: OnBtnClick?) : Button {
        val button = Button(context)
        button.background = ContextCompat.getDrawable(context, R.drawable.button_top_line_bg)
        button.text = text
        when(style) {
            ButtonStyle.THEME -> button.setTextColor(ContextCompat.getColor(context, R.color.colorTextTheme))
            ButtonStyle.NORMAL -> button.setTextColor(ContextCompat.getColor(context, R.color.colorTextHint))
            ButtonStyle.ERROR -> button.setTextColor(ContextCompat.getColor(context, R.color.colorTextError))
        }
        button.setOnClickListener {
            if (null == onBtnClick) {
                dismiss()
            } else {
                onBtnClick.click(this, text)
            }
        }
        return button
    }
}