package com.chris.dictionmaster.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chris.dictionmaster.databinding.ActivityPurchaseBinding

class PurchaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPurchaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityPurchaseBinding.inflate(layoutInflater)
        setContentView(this.binding.root)
        setupClickListener()
    }

    private fun setupClickListener() {
        binding.btnPurchase.setOnClickListener {
            finish()
        }
    }
}