package com.dlong.dialog.impl

import com.dlong.dialog.GridDialog

/**
 * @author D10NG
 * @date on 2020/8/26 11:13 AM
 */
interface OnGridItemClick<T: GridDialog> {
    fun click(d: T, p: Int)
}

class OnGridItemClickListener<T: GridDialog>: OnGridItemClick<T> {

    private lateinit var mListener: (dialog: T, position: Int) -> Unit

    fun onClick(listener: (dialog: T, position: Int) -> Unit) {
        this.mListener = listener
    }

    override fun click(d: T, p: Int) {
        this.mListener.invoke(d, p)
    }
}