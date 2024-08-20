package com.eda.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.eda.myapplication.databinding.ActivityKayitolBinding
import com.eda.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        //setContentView(R.layout.activity_main)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.uyeolbutton.setOnClickListener{
            intent=Intent(applicationContext,kayitol::class.java)
            startActivity(intent)
        }

        binding.sifredegistirbutton.setOnClickListener{
            intent=Intent(applicationContext,sifredegistir::class.java)
            startActivity(intent)
        }

        binding.girisbutton.setOnClickListener{
            val posta=binding.PersonName.text.toString()
            val sifre=binding.personsifre.text.toString()

            if (posta.isNotEmpty() && sifre.isNotEmpty()){
                auth.signInWithEmailAndPassword(posta,sifre).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent=Intent(this,start::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,"başarısız",Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this,"olmadı",Toast.LENGTH_SHORT).show()
            }
        }

    }

}