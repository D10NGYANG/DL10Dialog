package com.dlong.dl10dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dlong.dialog.*
import com.dlong.dl10dialog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    @Synchronized
    fun clickNormalButtonDialog(view: View) {
        ButtonDialog(this).create()
                .setTittle("提示")
                .setMsg("你好帅啊！")
                .setIcon(R.mipmap.icon_notice)
                .addAction("我知道", ButtonStyle.THEME, null)
                .addAction("啊，真的吗？", ButtonStyle.NORMAL, object : OnBtnClick{
                    override fun click(d0: BaseDialog<*>, text: String) {
                        d0.setMsg("真的！")
                        d0.removeAllButtons()
                        d0.startLoad(true, 0, 1)
                        d0.addAction("好的哟", ButtonStyle.THEME, null)
                    }
                })
                .addAction("滚！", ButtonStyle.ERROR, null)
                .show()
    }

    @Synchronized
    fun clickEditDialog(view: View) {
        val tag = "phone"
        EditDialog(this).create()
                .setTittle("提示")
                .setMsg("验证账户")
                .setIcon(R.mipmap.icon_notice)
                .addEdit(tag, "", "请输入手机号码")
                .setInputType(tag, InputType.TYPE_CLASS_PHONE)
                .addAction("确定", ButtonStyle.THEME, object : OnBtnClick{
                    override fun click(d0: BaseDialog<*>, text: String) {
                        val dialog = d0 as EditDialog
                        val input = dialog.getInputText(tag)
                        if (input.length != 11) {
                            dialog.setError(tag, "哪有这个长度的手机号，你这个沙雕")
                        } else {
                            dialog.removeEdit(tag)
                            dialog.setMsg("验证成功！")
                            dialog.removeAction("确定")
                        }
                    }
                })
                .addAction("取消", ButtonStyle.NORMAL, null)
                .show()
    }

    @Synchronized
    fun clickGridDialog(view: View) {
        GridDialog(this).create()
                .setTittle("提示")
                .setMsg("选择图标")
                .setIcon(R.mipmap.icon_notice)
    }

    @Synchronized
    fun clickPickDialog(view: View) {
        val list = listOf("张三", "里斯", "编剧", "鼠标", "手机", "吉萨", "沙雕", "戊午")
        val tag = "person"
        PickerDialog(this).create()
                .setTittle("提示")
                .setMsg("选择联系人")
                .setIcon(R.mipmap.icon_notice)
                .addPickList(tag, list[1], list, "", "")
                .addAction("确定", ButtonStyle.THEME, object : OnBtnClick{
                    override fun click(d0: BaseDialog<*>, text: String) {
                        val dialog = d0 as PickerDialog
                        val select = dialog.getPickOnTag(tag)
                        dialog.dismiss()
                        Toast.makeText(this@MainActivity, select, Toast.LENGTH_SHORT).show()
                    }
                })
                .addAction("取消", ButtonStyle.NORMAL, null)
                .show()
    }

    @Synchronized
    fun clickTimePickDialog(view: View) {
        val hourList = mutableListOf<String>()
        val minuteList = mutableListOf<String>()
        for (i in 0 until 24) {
            hourList.add("$i")
        }
        for (i in 0 until 60) {
            minuteList.add("$i")
        }
        val hTag = "hour"
        val mTag = "minute"
        PickerDialog(this).create()
                .setTittle("提示")
                .setMsg("选择时间")
                .setIcon(R.mipmap.icon_notice)
                .addPickList(hTag, hourList[1], hourList, "", " : ")
                .addPickList(mTag, minuteList[1], minuteList, "", "")
                .addAction("确定", ButtonStyle.THEME, object : OnBtnClick{
                    override fun click(d0: BaseDialog<*>, text: String) {
                        val dialog = d0 as PickerDialog
                        val selectH = dialog.getPickOnTag(hTag)
                        val selectM = dialog.getPickOnTag(mTag)
                        dialog.dismiss()
                        Toast.makeText(this@MainActivity, "$selectH 点 $selectM 分", Toast.LENGTH_SHORT).show()
                    }
                })
                .addAction("取消", ButtonStyle.NORMAL, null)
                .show()
    }
}
