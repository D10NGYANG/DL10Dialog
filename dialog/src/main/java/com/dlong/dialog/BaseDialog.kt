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
import com.dlong.dialog.impl.OnBtnClickListener

/**
 * 基础弹窗
 *
 * @author D10NG
 * @date on 2019-10-25 14:49
 */
open class BaseDialog constructor(
    val context: Context
) {
    /** 弹窗创建器 */
    val builder = AlertDialog.Builder(context)
    /** 最终的弹窗实例 */
    var alert: AlertDialog? = null

    /** 按键存储 */
    val buttonMap = mutableMapOf<String, View>()

    /** 基础弹窗布局 */
    val binding: DialogDefLoadingViewBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.dialog_def_loading_view, null, false)

    /**
     * 获取总值
     */
    open fun getLoadMax() : Int = binding.loadMax

    /**
     * 获取进度
     */
    open fun getLoadProgress() : Int = binding.loadProgress

    /**
     * 关闭
     */
    open fun dismiss() {
        alert?.dismiss()
    }
}

/**
 * 创建
 * @return [T]
 */
fun <T : BaseDialog> T.create() : T {
    // 初始化数据
    binding.loadIndeterminate = true
    binding.loadVisible = false
    builder.setView(binding.root)
    // 禁止点击外部隐藏
    builder.setCancelable(false)
    alert = builder.create()
    return this
}

/**
 * 移除所有button
 * @return [T]
 */
fun <T : BaseDialog> T.removeAllButtons(): T {
    binding.buttonLayout.removeAllViews()
    buttonMap.clear()
    return this
}

/**
 * 移除content内容
 * @return [T]
 */
fun <T : BaseDialog> T.removeContent(): T {
    binding.contentLayout.removeAllViews()
    return this
}

/**
 * 设置标题
 * @param tittle 标题文本
 * @return [T]
 */
fun <T : BaseDialog> T.setTittle(tittle: String) : T {
    binding.tittle = tittle
    return this
}

/**
 * 设置二级文本
 * @param msg 二级文本
 * @return [T]
 */
fun <T : BaseDialog> T.setMsg(msg: String) : T {
    binding.message = msg
    return this
}

/**
 * 设置图标
 * @param resId 图片资源ID
 * @return [T]
 */
fun <T : BaseDialog> T.setIcon(resId: Int) : T {
    binding.image.setImageResource(resId)
    return this
}

/**
 * 设置图标
 * @param bitmap 图片矢量图
 * @return [T]
 */
fun <T : BaseDialog> T.setIcon(bitmap: Bitmap) : T {
    binding.image.setImageBitmap(bitmap)
    return this
}

/**
 * 开始加载中
 * @param indeterminate 是否循环滚动
 * @param progress 进度
 * @param max 最大值
 * @return [T]
 */
fun <T : BaseDialog> T.startLoad(indeterminate: Boolean, progress: Int, max: Int) : T {
    binding.loadIndeterminate = indeterminate
    binding.loadProgress = progress
    binding.loadMax = max
    binding.loadVisible = true
    return this
}

/**
 * 更新进度
 * @param progress 进度
 * @return [T]
 */
fun <T : BaseDialog> T.updateLoadProgress(progress: Int) : T {
    binding.loadProgress = progress
    return this
}

/**
 * 停止加载中
 * @return [T]
 */
fun <T : BaseDialog> T.stopLoad(): T {
    binding.loadVisible = false
    return this
}

/**
 * 添加button事件
 * @param text 按钮文本
 * @param style 按钮字体颜色 [ButtonStyle]
 * @param onBtnClick 点击事件
 * @return [T]
 */
fun <T : BaseDialog> T.addAction(text: String, style: Int, onBtnClick: (OnBtnClickListener<T>.() -> Unit)?) : T {
    val view = createButton(text, style, onBtnClick)
    removeAction(text)
    binding.buttonLayout.addView(view)
    buttonMap[text] = view
    return this
}

/**
 * 删除按键
 * @param text 按钮文本
 * @return [T]
 */
fun <T : BaseDialog> T.removeAction(text: String): T {
    if (buttonMap.containsKey(text)) {
        binding.buttonLayout.removeView(buttonMap[text])
        buttonMap.remove(text)
    }
    return this
}

/**
 * 显示
 * @return [T]
 */
fun <T : BaseDialog> T.show(): T {
    val act = context as Activity
    if (!act.isFinishing && alert?.isShowing == false) {
        alert?.show()
        alert?.window?.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dialog_def_bg))
    }
    return this
}

/**
 * 创建按钮
 * @param text 按钮文本
 * @param style 按钮字体颜色 [ButtonStyle]
 * @param onBtnClick 点击事件
 * @return [Button]
 */
private fun <T : BaseDialog> T.createButton(text: String, style: Int, onBtnClick: (OnBtnClickListener<T>.() -> Unit)?) : Button {
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
            val clickBuilder = OnBtnClickListener<T>()
            clickBuilder.onBtnClick()
            clickBuilder.click(this, text)
        }
    }
    return button
}