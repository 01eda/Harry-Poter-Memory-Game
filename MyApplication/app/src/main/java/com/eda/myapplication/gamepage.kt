package com.eda.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.eda.myapplication.R.drawable.*
import android.widget.Button
import android.widget.ImageButton
import android.util.Log
import android.widget.Toast
import com.eda.myapplication.databinding.ActivityGamepageBinding
import com.eda.myapplication.databinding.ActivityKayitolBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.FieldPosition

private const val TAG ="gamepage"
class gamepage : AppCompatActivity() {
    lateinit var buttons:List<ImageButton>
    lateinit var buttons1:List<ImageButton>
    lateinit var buttons2:List<ImageButton>
    lateinit var buttons3:List<ImageButton>

    lateinit var binding: ActivityGamepageBinding
    lateinit var cards:List<kart>
    lateinit var cardk:List<kart>
    lateinit var cardh:List<kart>
    lateinit var cardg:List<kart>

    private var  OfSingleSelectedKart: Int?=null
    private var indexOfSingleSelectedKart: Int?=null
    private var indexOfSingleSelectedKart1: Int?=null
    private var indexOfSingleSelectedKart2: Int?=null

    val resimdizisi= mutableListOf<Bitmap>()
    lateinit var data: FirebaseFirestore


    var sayac=45
    var runnable:Runnable= kotlinx.coroutines.Runnable { }
    var handler= Handler()

    var puan=0





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGamepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mediaPlayer = MediaPlayer.create(this, R.raw.prologue)
        val mp = MediaPlayer.create(this, R.raw.congratulations)
        val mp3 = MediaPlayer.create(this, R.raw.shocked)
        val bitis=MediaPlayer.create(this,R.raw.victory)



        binding.cikisbut.setOnClickListener{
            intent= Intent(applicationContext,start::class.java)
            startActivity(intent)
        }
        binding.seskapamabutton.setOnClickListener{
            if(mediaPlayer!!.isPlaying) {
                mediaPlayer?.pause()
            }

            }
        binding.muzikac.setOnClickListener{
            if (!mediaPlayer.isPlaying) {
                mediaPlayer?.start()
            }


        }

        data= Firebase.firestore

        //süre hesaplama kısmı
        sayac=45
        runnable=object :Runnable{
            override fun run() {
                sayac=sayac-1
                binding.textView4sure.text="SÜRE: ${sayac}"
                handler.postDelayed(runnable,1000)
                if(sayac==0){
                    handler.removeCallbacks(runnable)
                    sayac=45
                    intent = Intent(applicationContext, start::class.java)
                    startActivity(intent)
                }
            }

        }
        handler.post(runnable)


        data.collection("kartbilgisi").addSnapshotListener { snapshot, error ->
            if (error != null){
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_SHORT).show()
            }else{
                if (snapshot != null){
                    if(!snapshot.isEmpty){
                        val documents=snapshot.documents

                        resimdizisi.clear()
                        for(document in documents){

                            val isim=document.get("adi")
                            val evi=document.get("evi")
                            val puan=document.get("puan")
                            val resimler=document.get("resim")
                            //val imageBytes = Base64.decode(resimler, 0)
                            // println(imageBytes)
                            // val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                            println("resim:"+resimler)
                            println("puan:"+puan)
                            println("ev:"+evi)
                            println("isim:"+isim)

                            // var indirilenbilgiler=kartbilgileri(isim,evi,puan,resimler)
                            //resimdizisi.add(image)

                            //val ilkresim:String=resimdizisi.get(1)
                            //println(resimdizisi)
                            //println(resimdizisi)
                            //println(puan)



                        }
                    }
                }
            }
        }


        val images:MutableList<Int> = mutableListOf(
            harryp, arthur, dumbledore, hagrid, hermione, lily, minerva, ron,
            sirius, peter, lupin
        )
        val randomimage = images.shuffled().take(2)
        val choise = (randomimage + randomimage).shuffled()
/////////////////////////////////////////////////////////////////
        val images1:MutableList<Int> = mutableListOf(
            cho, filius, garrick, gilderoy, luna, marcus, myrtle, padma,
            quirinus, rowena, sybill
        )
        val randomimage1 = images1.shuffled().take(2)
        val choise1 = (randomimage1 + randomimage1).shuffled()
////////////////////////////////////////////////////////////////////

        val images2:MutableList<Int> = mutableListOf(
            cedric, ernest,fat, hannah, helga, leanne, newt,nymp, pomona,
            silvanus, ted
        )
        val randomimage2=images2.shuffled().take(2)
        val choise2=(randomimage2+randomimage2).shuffled()
//////////////////////////////////////////////////////////////////////
        val images3:MutableList<Int> = mutableListOf(
            andromedajpg, bellatrix, dolores, evan, horaca,leta,
            lucius, malfoy, narcissa, snape, tomrid
        )

        val randomimage3=images3.shuffled().take(2)
        val choise3 = (randomimage3+randomimage3).shuffled()
//////////////////////////////////////////////////////////////////////

        val buttons = listOf(
            binding.imageButton19,
            binding.imageButton9,
            binding.imageButton6,
            binding.imageButton12,

            )
        val buttons1 = listOf(
            binding.imageButton11,
            binding.imageButton10,
            binding.imageButton7,
            binding.imageButton17
        )
        val buttons2= listOf(
            binding.imageButton18,
            binding.imageButton15,
            binding.imageButton5,
            binding.imageButton4,
        )
        val buttons3= listOf(
            binding.imageButton13,
            binding.imageButton8,
            binding.imageButton14,
            binding.imageButton16,

        )
        //val s = buttons.shuffled()
        //val k = buttons1.shuffled()
/////////////////////////////////////////////////////////////////////
        cards = buttons.indices.map { index9 ->
            kart(choise[index9])
        }
        cardk = buttons1.indices.map { index ->
            kart(choise1[index])
        }
        cardh= buttons2.indices.map{indexh ->
            kart(choise2[indexh])
        }
        cardg = buttons3.indices.map{ indexg ->
            kart(choise3[indexg])
        }
////////////////////////////////////////////////////////////////////
        buttons3.forEachIndexed { indexg, button3 ->
            button3.setOnClickListener {
                Log.i(TAG, "BUTONA BASTIN")
                val kart4 = cardg[indexg]
                if (kart4.isFaceUp) {
                    Toast.makeText(this, "OYUN BİTTİ", Toast.LENGTH_SHORT).show()
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer?.pause()
                        bitis?.start()
                        mediaPlayer?.start()
                    }
                    return@setOnClickListener
                }
                if (indexOfSingleSelectedKart2==null){
                    mach3()
                    indexOfSingleSelectedKart2=indexg

                }else{
                    formach3(indexOfSingleSelectedKart2!!,indexg)
                    indexOfSingleSelectedKart2=null
                }
                kart4.isFaceUp=!kart4.isFaceUp


                cardg.forEachIndexed{indexg, kart4 ->
                    val button3=buttons3[indexg]
                    if(kart4.isMatched){
                        button3.alpha=0.1f
                        if (mediaPlayer.isPlaying) {
                            mediaPlayer?.pause()
                            mp?.start()
                            mediaPlayer?.start()
                        }
                    }
                    button3.setImageResource(if (kart4.isFaceUp) kart4.identifier else kartarkasi)

                }
            }
        }
////////////////////////////////////////////////////////////////////////////
        buttons2.forEachIndexed { indexh, button2 ->
            button2.setOnClickListener {
                Log.i(TAG, "BUTONA BASTIN")
                val kart3 = cardh[indexh]
                if (kart3.isFaceUp){
                    Toast.makeText(this,"OYUN BİTTİ",Toast.LENGTH_SHORT).show()
                    if (mediaPlayer.isPlaying){
                        mediaPlayer?.pause()
                        bitis?.start()
                        mediaPlayer?.start()
                    }
                    return@setOnClickListener

                }
                if (indexOfSingleSelectedKart1==null){
                    mach2()

                    indexOfSingleSelectedKart1=indexh

                }else{
                    formach2(indexOfSingleSelectedKart1!!,indexh)
                    indexOfSingleSelectedKart1=null
                }
                kart3.isFaceUp=!kart3.isFaceUp


                cardh.forEachIndexed{indexh, kart3 ->
                    val button2=buttons2[indexh]
                    if(kart3.isMatched){
                        button2.alpha=0.1f
                        if (mediaPlayer.isPlaying) {
                            mediaPlayer?.pause()
                            mp?.start()
                            mediaPlayer?.start()
                        }
                    }
                    button2.setImageResource(if (kart3.isFaceUp) kart3.identifier else kartarkasi)

                }
            }
        }
//////////////////////////////////////////////////////////////////////////////
        buttons.forEachIndexed { index9, button9 ->
            button9.setOnClickListener {
                Log.i(TAG, "BUTONA BASTIN")
                val kart2 = cards[index9]
                if (kart2.isFaceUp) {
                    Toast.makeText(this, "OYUN BİTTİ", Toast.LENGTH_SHORT).show()
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer?.pause()
                        bitis?.start()
                        mediaPlayer?.start()
                    }
                    return@setOnClickListener
                }
                if (indexOfSingleSelectedKart==null){
                    mach1()

                    indexOfSingleSelectedKart=index9

                }else{
                    formach1(indexOfSingleSelectedKart!!,index9)
                    indexOfSingleSelectedKart=null
                }
                kart2.isFaceUp=!kart2.isFaceUp


                cards.forEachIndexed{index9, kart2 ->
                    val button9=buttons[index9]
                    if(kart2.isMatched){
                        button9.alpha=0.1f
                        if (mediaPlayer.isPlaying) {
                            mediaPlayer?.pause()
                            mp?.start()
                            mediaPlayer?.start()

                        }
                    }
                    button9.setImageResource(if (kart2.isFaceUp) kart2.identifier else kartarkasi)

                }
            }
        }
//////////////////////////////////////////////////////////////////////
        buttons1.forEachIndexed { index, button ->
            button.setOnClickListener {
                Log.i(TAG, "BUTONA BASTIN")
                val kart1 = cardk[index]
                if (kart1.isFaceUp) {
                    Toast.makeText(this, "OYUN BİTTİ", Toast.LENGTH_SHORT).show()
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer?.pause()
                        bitis?.start()
                        mediaPlayer?.start()
                    }
                    return@setOnClickListener
                }
                if (OfSingleSelectedKart==null){
                    mach()
                    OfSingleSelectedKart=index

                }else{
                    formach(OfSingleSelectedKart!!,index)
                    OfSingleSelectedKart=null
                }
                kart1.isFaceUp = !kart1.isFaceUp

                cardk.forEachIndexed{index, kart1 ->
                    val button=buttons1[index]
                    if(kart1.isMatched){
                        button.alpha=0.1f
                        if (mediaPlayer.isPlaying) {
                            mediaPlayer?.pause()
                            mp?.start()
                            mediaPlayer?.start()

                        }

                    }
                    button.setImageResource(if (kart1.isFaceUp) kart1.identifier  else kartarkasi)

                }
            }
        }
    }

    private fun formach3(index15: Int, index16: Int) {
        if(cardg[index15].identifier==cardg[index16].identifier){
            Toast.makeText(this,"tebrikler eşlendi",Toast.LENGTH_SHORT).show()
            cardg[index15].isMatched=true
            cardg[index16].isMatched=true
            puan=puan+10
            Toast.makeText(this, "PUAN:${puan}", Toast.LENGTH_SHORT).show()
            return
        }

    }

    private fun mach3() {
        for (kart4 in cardg){
            if(!kart4.isMatched){
                kart4.isFaceUp=false

            }
        }

    }

    private fun formach2(index15: Int, index16: Int) {
        if(cardh[index15].identifier==cardh[index16].identifier){
            Toast.makeText(this,"tebrikler eşlendi",Toast.LENGTH_SHORT).show()
            cardh[index15].isMatched=true
            cardh[index16].isMatched=true
            puan=puan+10
            Toast.makeText(this, "PUAN:${puan}", Toast.LENGTH_SHORT).show()
            return
        }

    }

    private fun mach2() {
        for (kart3 in cardh){
            if(!kart3.isMatched){
                kart3.isFaceUp=false

            }
        }

    }

    private fun mach1() {
        for (kart2 in cards){
            if(!kart2.isMatched){
                kart2.isFaceUp=false

            }
        }
    }

    private fun mach() {
        for (kart1 in cardk){
            if(!kart1.isMatched){
                kart1.isFaceUp=false

            }
        }
    }

    private fun formach(index15: Int, index16: Int) {
        if(cardk[index15].identifier==cardk[index16].identifier){
            Toast.makeText(this,"tebrikler eşlendi",Toast.LENGTH_SHORT).show()
            cardk[index15].isMatched=true
            cardk[index16].isMatched=true
            puan=puan+10
            Toast.makeText(this, "PUAN:${puan}", Toast.LENGTH_SHORT).show()
            return
        }

    }

    private fun formach1(index15: Int, index16: Int) {
        if(cards[index15].identifier==cards[index16].identifier){
            Toast.makeText(this,"eşlendi",Toast.LENGTH_SHORT).show()
            cards[index15].isMatched=true
            cards[index16].isMatched=true
            puan=puan+10
            Toast.makeText(this, "PUAN:${puan}", Toast.LENGTH_SHORT).show()
            return

        }
    } }







