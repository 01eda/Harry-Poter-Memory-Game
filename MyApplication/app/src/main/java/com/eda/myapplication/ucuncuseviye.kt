package com.eda.myapplication

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.Toast
import com.eda.myapplication.databinding.ActivityUcuncuseviyeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.eda.myapplication.R.drawable.*



class ucuncuseviye : AppCompatActivity() {
    lateinit var binding: ActivityUcuncuseviyeBinding
    lateinit var data: FirebaseFirestore
    val resimdizisi= mutableListOf<String>()
    val puandizi= mutableListOf<String>()
    var donenkart=0


    lateinit var zortus:List<ImageButton>
    lateinit var zorseviyekart:List<kart>
    private var zorseviyeSingleSelected: Int?=null   //başlangıçta seçilen kart yok
    var sayac=45
    var runnable:Runnable= kotlinx.coroutines.Runnable { }
    var handler= Handler()
    var puan=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUcuncuseviyeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data= Firebase.firestore


        binding.cik.setOnClickListener{
            intent= Intent(applicationContext,start::class.java)
            startActivity(intent)
        }


        val mediaPlayer = MediaPlayer.create(this, R.raw.prologue)
        val mp = MediaPlayer.create(this, R.raw.congratulations)
        val mp3 = MediaPlayer.create(this, R.raw.shocked)
        val bitis=MediaPlayer.create(this,R.raw.victory)


        if(!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }


        val citiesRef = data.collection("kartbilgisi").document("0A0gQXAW5xnuzoqHUf1l")
        val query = citiesRef.get()
        println(query)


        binding.sesbut.setOnClickListener{
            Toast.makeText(this,"müzik kapandı",Toast.LENGTH_SHORT).show()
            if(mediaPlayer.isPlaying){
                mediaPlayer?.pause()
                // mediaPlayer?.reset()
            }
        }
        binding.textView3.text="SÜRE: ${donenkart}"

        //süre hesaplama kısmı
        sayac=45
        runnable=object :Runnable{
            override fun run() {
                sayac=sayac-1
                binding.surezor.text="SÜRE: ${sayac}"
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


        val zorseviyeresim:MutableList<Int> = mutableListOf(
            harryp, arthur, dumbledore, hagrid, hermione, lily, minerva, ron,
            sirius, peter, lupin,cho, filius, garrick, gilderoy, luna, marcus, myrtle, padma,
            quirinus, rowena, sybill,cedric, ernest,fat, hannah, helga, leanne, newt,nymp, pomona,
            silvanus, ted,andromedajpg, bellatrix, dolores, evan, horaca,leta,
            lucius, malfoy, narcissa, snape, tomrid
        )


        val randomzorseviyeresim=zorseviyeresim.shuffled().take(18)
        val choisezorseviye=(randomzorseviyeresim+randomzorseviyeresim).shuffled()
        //////////////////////////////////////////////////////////////////////////////////////
        val buttonzorseviye= listOf(
            binding.imageButton21,
            binding.imageButton22,
            binding.imageButton23,
            binding.imageButton24,
            binding.imageButton25,
            binding.imageButton26,
            binding.imageButton27,
            binding.imageButton28,
            binding.imageButton29,
            binding.imageButton30,
            binding.imageButton31,
            binding.imageButton32,
            binding.imageButton33,
            binding.imageButton34,
            binding.imageButton35,
            binding.imageButton36,
            binding.imageButton37,
            binding.imageButton38,
            binding.imageButton39,
            binding.imageButton40,
            binding.imageButton41,
            binding.imageButton42,
            binding.imageButton43,
            binding.imageButton44,
            binding.imageButton45,
            binding.imageButton46,
            binding.imageButton47,
            binding.imageButton48,
            binding.imageButton49,
            binding.imageButton50,
            binding.imageButton51,
            binding.imageButton52,
            binding.imageButton53,
            binding.imageButton54,
            binding.imageButton55,
            binding.imageButton56

            )

        ////////////////////////////////////////////////////////////////////////////////////////////////

        zorseviyekart=buttonzorseviye.indices.map { zorseviyeindex ->
            kart(choisezorseviye[zorseviyeindex])
        }
/////////////////////////////////////////////////////////////////////////////////////////////
        buttonzorseviye.forEachIndexed{zorseviyeindex,buttonzorseviye1 ->
            buttonzorseviye1.setOnClickListener{
                // Log.i(TAG,"butona bastin")

                val kartzor=zorseviyekart[zorseviyeindex]
                if (kartzor.isFaceUp) {
                    Toast.makeText(this, "OYUN BİTTİ", Toast.LENGTH_SHORT).show()
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer?.pause()
                        bitis?.start()
                        mediaPlayer?.start()
                    }
                    return@setOnClickListener
                }

                if(zorseviyeSingleSelected==null){  //açık kart yok
                    machzor()
                    zorseviyeSingleSelected=zorseviyeindex //kart seçildi kartın indexsini verdik

                }else{
                    formachzor(zorseviyeSingleSelected!!,zorseviyeindex)
                    zorseviyeSingleSelected=null
                }
                kartzor.isFaceUp=!kartzor.isFaceUp
                zorseviyekart.forEachIndexed{zorseviyeindex,kartzor ->
                    val buttonzorseviye1=buttonzorseviye[zorseviyeindex]
                    if(kartzor.isMatched) {
                        buttonzorseviye1.alpha = 0.1f
                        if (mediaPlayer.isPlaying) {
                            mediaPlayer?.pause()
                            mp?.start()
                            mediaPlayer?.start()

                        }
                    }
                    buttonzorseviye1.setImageResource(if(kartzor.isFaceUp) kartzor.identifier else kartarkasi)


                }

            }
        }
    }

    private fun formachzor(index100:Int,index101:Int) {
        if (zorseviyekart[index100].identifier==zorseviyekart[index101].identifier) {
            Toast.makeText(this, "NE HOŞ EŞLEŞTİ", Toast.LENGTH_SHORT).show()
            zorseviyekart[index100].isMatched = true
            zorseviyekart[index101].isMatched = true
            puan=puan+10
            Toast.makeText(this, "PUAN:${puan}", Toast.LENGTH_SHORT).show()
            return

        }
    }

    private fun machzor() {  //kartları tekrar çevirme
        for (kartzor in zorseviyekart) {
            if (!kartzor.isMatched) {
                kartzor.isFaceUp = false

            }
        }

    }
}


