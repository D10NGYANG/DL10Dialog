package com.dlong.dl10dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dlong.dialog.*
import com.dlong.dl10dialog.adapter.GridIconAdapter
import com.dlong.dl10dialog.databinding.ActivityMainBinding
import kotlinx.coroutines.*

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
            .addAction("啊，真的吗？", ButtonStyle.NORMAL) {
                onClick { d0, _ ->
                    d0.setMsg("真的！")
                    d0.removeAllButtons()
                    d0.startLoad(true, 0, 1)
                    d0.addAction("好的哟", ButtonStyle.THEME, null)
                }
            }
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
            .addAction("确定", ButtonStyle.THEME) {
                onClick { dialog, _ ->
                    val input = dialog.getInputText(tag)
                    if (input.length != 11) {
                        dialog.setError(tag, "哪有这个长度的手机号，你这个沙雕")
                    } else {
                        dialog.removeEdit(tag)
                        dialog.setMsg("验证成功！")
                        dialog.removeAction("确定")
                    }
                }
            }
            .addAction("取消", ButtonStyle.NORMAL, null)
            .show()
    }

    @Synchronized
    fun clickGridDialog(view: View) {
        val adapter = GridIconAdapter(
            this, listOf(
                R.mipmap.icon_grid_0,
                R.mipmap.icon_grid_1,
                R.mipmap.icon_grid_2,
                R.mipmap.icon_grid_3,
                R.mipmap.icon_grid_4,
                R.mipmap.icon_grid_5,
                R.mipmap.icon_grid_6,
                R.mipmap.icon_grid_7,
                R.mipmap.icon_grid_8,
                R.mipmap.icon_grid_9,
                R.mipmap.icon_grid_10,
                R.mipmap.icon_grid_11,
                R.mipmap.icon_grid_12,
                R.mipmap.icon_grid_13,
                R.mipmap.icon_grid_14,
                R.mipmap.icon_grid_15,
                R.mipmap.icon_grid_16,
                R.mipmap.icon_grid_17
            )
        )
        GridDialog(this).create()
            .setTittle("提示")
            .setMsg("选择图标")
            .setIcon(R.mipmap.icon_notice)
            .setGridList(adapter, 3) {
                onClick { dialog, position ->
                    val icon = adapter.getItem(position) as Int
                    dialog.setIcon(icon)
                }
            }
            .addAction("取消", ButtonStyle.NORMAL, null)
            .show()
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
            .addAction("确定", ButtonStyle.THEME) {
                onClick { dialog, _ ->
                    val select = dialog.getPickOnTag(tag)
                    dialog.dismiss()
                    Toast.makeText(this@MainActivity, select, Toast.LENGTH_SHORT).show()
                }
            }
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
            .addAction("确定", ButtonStyle.THEME) {
                onClick { dialog, _ ->
                    val selectH = dialog.getPickOnTag(hTag)
                    val selectM = dialog.getPickOnTag(mTag)
                    dialog.dismiss()
                    Toast.makeText(this@MainActivity, "$selectH 点 $selectM 分", Toast.LENGTH_SHORT).show()
                }
            }
            .addAction("取消", ButtonStyle.NORMAL, null)
            .show()
    }

    @Synchronized
    fun clickMultiSelectDialog(view: View) {
        MultiSelectDialog(this).create()
            .setTittle("提示")
            .setMsg("请选择你喜欢的手机品牌，可多选")
            .addSelectItem("小米", false)
            .addSelectItem("华为", false)
            .addSelectItem("三星", false)
            .addSelectItem("苹果", false)
            .addSelectItem("中兴", false)
            .addSelectItem("联想", false)
            .addSelectItem("诺基亚", false)
            .addSelectItem("魅族", false)
            .addSelectItem("努比亚", false)
            .addSelectItem("OPPO", false)
            .addSelectItem("vivo", false)
            .addSelectItem("LG", false)
            .addSelectItem("荣耀", false)
            .addSelectItem("红米", false)
            .addSelectItem("锤子", false)
            .addSelectItem("坚果", false)
            .addSelectItem("realme", false)
            .addSelectItem("一加", false)
            .addSelectItem("黑鲨", false)
            .addAction("确定", ButtonStyle.THEME) {
                onClick { dialog, _ ->
                    val map = dialog.getAllItemMap()
                    val builder = StringBuilder("您喜欢的手机品牌是：")
                    for (value in map.keys) {
                        if (map[value] == true) builder.append(value).append("、")
                    }
                    dialog.dismiss()
                    Toast.makeText(this@MainActivity, builder.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            .addAction("没有我喜欢的品牌", ButtonStyle.NORMAL, null)
            .show()
    }

    @Synchronized
    fun clickLoading(view: View) {
        val dialog = JustLoadDialog(this).create().show()
        GlobalScope.launch {
            delay(3000)
            withContext(Dispatchers.Main) {dialog.dismiss()}
        }
    }
}
