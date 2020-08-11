package com.dlong.dialog

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.dlong.dialog.databinding.DialogJustLoadingViewBinding
import com.dlong.dialog.utils.dp2px

/**
 * 只是加载弹窗
 *
 * @author D10NG
 * @date on 2020/8/11 9:12 AM
 */
class JustLoadDialog constructor(val context: Context) {

    /** 弹窗创建器 */
    protected val builder = AlertDialog.Builder(context)
    /** 最终的弹窗实例 */
    protected var alert: AlertDialog? = null

    /** 基础弹窗布局 */
    val binding: DialogJustLoadingViewBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.dialog_just_loading_view, null, false)

    /**
     * 创建
     */
    fun create() : JustLoadDialog {
        builder.setView(binding.root)
        builder.setCancelable(false)
        alert = builder.create()
        return this
    }

    /**
     * 显示
     */
    fun show(): JustLoadDialog {
        val act = context as Activity
        if (!act.isFinishing && alert?.isShowing == false) {
            alert?.show()
            alert?.window?.setLayout(
                context.dp2px(80f).toInt(),
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
            alert?.window?.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dialog_just_load_bg))
        }
        return this
    }

    /**
     * 关闭
     */
    fun dismiss() {
        alert?.dismiss()
    }
}