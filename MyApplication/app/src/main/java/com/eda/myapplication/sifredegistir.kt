package com.eda.myapplication

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.eda.myapplication.databinding.ActivityMainBinding
import com.eda.myapplication.databinding.ActivitySifredegistirBinding
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class sifredegistir : AppCompatActivity() {
    lateinit var binding: ActivitySifredegistirBinding
    private lateinit var firebaseA:FirebaseAuth
    private lateinit var sifre: EditText
    private lateinit var reset:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseA=FirebaseAuth.getInstance()

        binding= ActivitySifredegistirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sifrecikisbutton.setOnClickListener{
            intent= Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }


        sifre= findViewById(R.id.yenisifre)
        reset=findViewById(R.id.yenisifrekaydetbutton)


        binding.yenisifrekaydetbutton.setOnClickListener{
           val  sifre1= sifre.text.toString()
            firebaseA.sendPasswordResetEmail(sifre1).addOnSuccessListener{
                Toast.makeText(this,"maili kontrol ediniz",Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener{
                    Toast.makeText(this,"sifre değişmedi",Toast.LENGTH_SHORT).show()
                }
        }



    }
}