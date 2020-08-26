package com.dlong.dialog.impl

import com.dlong.dialog.BaseDialog

/**
 * 弹窗按钮点击接口
 *
 * @author D10NG
 * @date on 2019-10-26 15:40
 */
interface OnBtnClick<T : BaseDialog> {
    fun click(d0: T, text: String)
}

/**
 * 简化回调接口
 */
class OnBtnClickListener<T : BaseDialog> : OnBtnClick<T> {

    private lateinit var onClickVal: (dialog: T, text: String) -> Unit

    fun onClick(listener: (dialog: T, text: String) -> Unit) {
        this.onClickVal = listener
    }

    override fun click(d0: T, text: String) {
        this.onClickVal.invoke(d0, text)
    }
}