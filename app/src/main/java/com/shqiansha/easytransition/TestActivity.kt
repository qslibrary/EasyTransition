package com.shqiansha.easytransition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()
    }

    private fun initView() {
        ivTest.setImageResource(R.mipmap.ic_launcher)
        val id = window.attributes.windowAnimations
//        ivTest.postDelayed({
//            window.setWindowAnimations(id)
//        }, 1000)
//        LogUtil.i("id:${id}")
//        ivTest.scaleX=2f

//        ivTest.scaleX=2f
//        ivTest.postDelayed({
//            ivTest.setImageResource(R.mipmap.ic_launcher)
//            ET.releaseLazy(this,ivTest)
//        },1000)
    }
}
