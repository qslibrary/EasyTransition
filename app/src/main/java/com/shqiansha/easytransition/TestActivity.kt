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
        tvTest.postDelayed({
            tvTest.text=intent.getStringExtra("name")
            ET.releaseLazy(this,tvTest)
        },1000)
        ivTest.setImageResource(R.mipmap.ic_launcher)

    }
}
