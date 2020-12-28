package com.dlong.dl10dialog.custom

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.dlong.dialog.BaseDialog
import com.dlong.dl10dialog.R
import com.dlong.dl10dialog.databinding.DialogSyncOfflineMsgBinding

/**
 * 同步离线消息弹窗
 * @Author: D10NG
 * @Time: 2020/12/28 9:55 上午
 */
class SyncOfflineMsgDialog constructor(
    context: Context
): BaseDialog(context) {

    var viewBinding: DialogSyncOfflineMsgBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.dialog_sync_offline_msg, null, false)

    init {
        // 改变宽度
        val params0 = binding.scrollView.layoutParams
        params0.width = LinearLayout.LayoutParams.MATCH_PARENT
        binding.scrollView.layoutParams = params0
        val params = binding.contentLayout.layoutParams
        params.width = LinearLayout.LayoutParams.MATCH_PARENT
        binding.contentLayout.layoutParams = params
        // 设置背景
        binding.scrollView.setBackgroundResource(R.drawable.bg_sync_offline_msg)
        binding.contentLayout.addView(viewBinding.root)
    }

    /**
     * 显示同步
     * @return SyncOfflineMsgDialog
     */
    fun showLoading(): SyncOfflineMsgDialog {
        viewBinding.imgIcon.setImageResource(R.mipmap.icon_offline_loading)
        viewBinding.txtTitle.text = "正在同步离线消息"
        return this
    }

    /**
     * 显示完成
     * @return SyncOfflineMsgDialog
     */
    fun showFinish(): SyncOfflineMsgDialog {
        viewBinding.imgIcon.setImageResource(R.mipmap.icon_offline_complete)
        viewBinding.txtTitle.text = "已成功获取全部离线消息"
        return this
    }
}