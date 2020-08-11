package com.dlong.dialog

/**
 * 弹窗按钮点击接口
 *
 * @author D10NG
 * @date on 2019-10-26 15:40
 */
interface OnBtnClick<T : BaseDialog<T>> {
    fun click(d0: T, text: String)
}

/**
 * 简化回调接口
 */
class OnBtnClickListener<T : BaseDialog<T>> : OnBtnClick<T> {

    private lateinit var onClickVal: (dialog: T, text: String) -> Unit

    fun onClick(listener: (dialog: T, text: String) -> Unit) {
        this.onClickVal = listener
    }

    override fun click(d0: T, text: String) {
        this.onClickVal.invoke(d0, text)
    }

}