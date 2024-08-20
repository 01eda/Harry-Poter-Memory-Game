package com.eda.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.eda.myapplication.databinding.ActivityKayitolBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase

class kayitol : AppCompatActivity() {
    lateinit var binding: ActivityKayitolBinding
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityKayitolBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // var db= FirebaseDatabase.getInstance().reference
        auth= FirebaseAuth.getInstance()


        binding.geributton.setOnClickListener{
            intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }
        binding.kaydolbutton.setOnClickListener{
            val posta=binding.PostaName.text.toString()
            val sifre=binding.personsifre.text.toString()
            val kullaniciadi=binding.PersonName.text.toString()

            if(posta.isNotEmpty() && sifre.isNotEmpty() ){
                auth.createUserWithEmailAndPassword(posta,sifre).addOnCompleteListener{
                    if (it.isSuccessful){

                        // kullanıcı adı girmek

                        val yenikullanici=auth.currentUser
                        val profileguncelleme = userProfileChangeRequest {
                            displayName = kullaniciadi

                        }
                        yenikullanici!!.updateProfile(profileguncelleme).addOnCompleteListener{task ->
                            if (task.isSuccessful){
                                Toast.makeText(applicationContext,"kullanıcı adı eklend",Toast.LENGTH_SHORT).show()
                            }
                        }

                        val intent= Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this,"başarılı kayıt",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"olmadı",Toast.LENGTH_SHORT).show()

                    }
                }
            }else{
                Toast.makeText(this,"boşlukları doldurun",Toast.LENGTH_SHORT).show()
            }

        }




    }




}