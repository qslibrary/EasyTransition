package com.shqiansha.easytransition

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.shqiansha.easytransition.utils.UnitUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }

    private fun initView(){
        val build=AlertDialog.Builder(this)
        build.create()
        tvTest.setOnClickListener {
            ET.from(tvTest).to(R.id.tvTest)

            startActivity(Intent(this,TestActivity::class.java))
        }
        ivTest.setOnClickListener {
//            ET.from(ivTest).toLazy(TestActivity::class.java)
            startActivity(Intent(this,TestActivity::class.java))
        }
        tvTest.scaleX
        test()
    }

    private fun test(){
    }
}
