package com.shqiansha.easytransition

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        tvTest.setOnClickListener {
//            ET.from(tvTest).duration(2000).to(R.id.tvTest)
            ET.from(tvTest).toLazy(TestActivity::class.java)
            val intent = Intent(this, TestActivity::class.java)
            intent.putExtra("name", tvTest.text.toString())
            startActivity(intent)
        }
        ivTest.setOnClickListener {
            ET.from(ivTest).to(R.id.ivTest)
            startActivity(Intent(this, TestActivity::class.java))
        }
        ivTran.setOnClickListener {
//            ivTest.pivotX=0f
//            ivTest.pivotY=0f
            ivTest.scaleX = 1.5f
            ivTest.scaleY = 1.5f
//            ivTest.translationX=100f
//            ivTest.translationY=100f
        }
        ivRestore.setOnClickListener {
            ivTest.translationX = 137.5f
            ivTest.translationY = 137.5f
        }
        ivState.setOnClickListener {
            test()
        }
        test()
    }

    private fun test() {
        val v = ivTest
        val width = v.width
        val height = v.height
        val matrix = v.matrix
        val imageMatrix = v.imageMatrix
        val a = "1"
    }

    private fun test1() {
        ivTest.translationX = 0f
        ivTest.translationY = 0f
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        finishAfterTransition()
    }
}
