package com.eda.myapplication

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eda.myapplication.databinding.ActivityKayitolBinding
import com.eda.myapplication.databinding.ActivityMainBinding
import com.eda.myapplication.databinding.ActivityStartBinding
import com.eda.myapplication.databinding.ActivityStartBinding.*

class start : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding
    lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mediaPlayer = MediaPlayer.create(this,R.raw.prologue)



        binding.basitbutton.setOnClickListener{
            intent= Intent(applicationContext,ilkseviye::class.java)
            startActivity(intent)


        }
        binding.ortabutton.setOnClickListener{
            intent= Intent(applicationContext,gamepage::class.java)
            startActivity(intent)
        }
        binding.zorbutton.setOnClickListener{
            intent= Intent(applicationContext,ucuncuseviye::class.java)
            startActivity(intent)
        }
        binding.longout.setOnClickListener{
            intent= Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)


        }

    }
}