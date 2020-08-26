package com.dlong.dialog.impl

import com.dlong.dialog.PickerDialog

/**
 * @author D10NG
 * @date on 2020/8/26 11:17 AM
 */
interface OnPickItemSelect<T: PickerDialog> {
    fun select(dialog: T, tag: String, position:Int, selectItem: String)
}

class OnPickItemSelectListener<T: PickerDialog>: OnPickItemSelect<T> {

    private lateinit var listener: (dialog: T, tag: String, position: Int, selectItem: String) -> Unit

    fun onSelect(listener: (dialog: T, tag: String, position: Int, selectItem: String) -> Unit) {
        this.listener = listener
    }

    override fun select(dialog: T, tag: String, position: Int, selectItem: String) {
        this.listener.invoke(dialog, tag, position, selectItem)
    }
}