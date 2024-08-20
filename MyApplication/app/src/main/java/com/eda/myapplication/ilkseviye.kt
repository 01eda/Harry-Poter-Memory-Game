package com.eda.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import com.eda.myapplication.databinding.ActivityIlkseviyeBinding
import com.eda.myapplication.R.drawable.*
import android.os.Handler
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Runnable
import java.io.FileWriter


private const val TAG ="ilkseviye"
class ilkseviye : AppCompatActivity() {
    lateinit var binding: ActivityIlkseviyeBinding

    lateinit var tus:List<ImageButton>
    lateinit var ilkseviyekart:List<kart>
    private var ilkseviyeSingleSelected: Int?=null   //açık kart olup olmadığını tutan index
    private var art: Int?= arthur


    lateinit var data: FirebaseFirestore
    val ilkseviyeresim= mutableListOf<Int>()
    val resimdizisi= mutableListOf<Bitmap>()

    lateinit var mediaPlayer: MediaPlayer

    var puan=0

    var sayac=45
    var runnable:Runnable= Runnable {  }
    var handler=Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIlkseviyeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data=Firebase.firestore

        val mediaPlayer = MediaPlayer.create(this, R.raw.prologue)
        val mp = MediaPlayer.create(this, R.raw.congratulations)
        val mp3 = MediaPlayer.create(this, R.raw.shocked)
        val bitis=MediaPlayer.create(this,R.raw.victory)


        //val resims= listOf("resim1","resim2","resim3","resim4","resim5","resim6","resim7","resim8","resim9",
        //    "resim10")
        //data.collection("kartbilgisi").whereIn("resim",resims).get().addOnSuccessListener {  }
        //println(resims)
        //veri çekme kısmı
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



        //süre hesaplama kısmı
            sayac=45
            runnable=object :Runnable{
                override fun run() {
                    sayac=sayac-1
                    binding.textView.text="SÜRE: ${sayac}"
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




        binding.calbutton.setOnClickListener{
            Toast.makeText(this,"müzik başladı",Toast.LENGTH_SHORT).show()
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
                // mediaPlayer?.reset()
            }
        }
        binding.oynat.setOnClickListener{
            Toast.makeText(this,"müzik kapandı",Toast.LENGTH_SHORT).show()
            if(mediaPlayer.isPlaying){
                mediaPlayer?.pause()
               // mediaPlayer?.reset()
            }
        }
        binding.oyunubitir.setOnClickListener {
            intent = Intent(applicationContext, start::class.java)
            startActivity(intent)
        }




        val ilkseviyeresim:MutableList<Int> = mutableListOf(
            harryp, arthur, dumbledore, hagrid, hermione, lily, minerva, ron,
            sirius, peter, lupin,cho, filius, garrick, gilderoy, luna, marcus, myrtle, padma,
            quirinus, rowena, sybill,cedric, ernest,fat, hannah, helga, leanne, newt,nymp, pomona,
            silvanus, ted,andromedajpg, bellatrix, dolores, evan, horaca,leta,
            lucius, malfoy, narcissa, snape, tomrid)

        val randomilkseviyeresim=ilkseviyeresim.shuffled().take(2)
        val choiseilkseviye=(randomilkseviyeresim+randomilkseviyeresim).shuffled()
      //////////////////////////////////////////////////////////////////////////////////////
        val buttonilkseviye= listOf(
            binding.imageButton,
            binding.imageButton2,
            binding.imageButton20,
            binding.imageButton3
            )

 ////////////////////////////////////////////////////////////////////////////////////////////////

        ilkseviyekart=buttonilkseviye.indices.map { ilkseviyeindex ->
            kart(choiseilkseviye[ilkseviyeindex])
        }
/////////////////////////////////////////////////////////////////////////////////////////////
       /*
        fun dosyayaz(str:List<kart>){
            var yaz = FileWriter("text.txt")
            yaz.write(str)
            yaz.close()

        }
        dosyayaz(ilkseviyekart)



*/
        ////////////////////////////////////////////////////////////////////////////
        buttonilkseviye.forEachIndexed{ilkseviyeindex,buttonilkseviye1 ->
            buttonilkseviye1.setOnClickListener{
               // Log.i(TAG,"butona bastin")
                val kart5=ilkseviyekart[ilkseviyeindex]
                if (kart5.isFaceUp) {
                    Toast.makeText(this, "OYUN BİTTİ", Toast.LENGTH_SHORT).show()
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer?.pause()
                        bitis?.start()
                        mediaPlayer?.start()
                    }
                    return@setOnClickListener
                }
                if(ilkseviyeSingleSelected==null){
                    mach5()
                    ilkseviyeSingleSelected=ilkseviyeindex


                }else{
                    formach5(ilkseviyeSingleSelected!!,ilkseviyeindex)
                    ilkseviyeSingleSelected=null

                }
                kart5.isFaceUp=!kart5.isFaceUp

                ilkseviyekart.forEachIndexed{ilkseviyeindex,kart5 ->
                    val buttonilkseviye1=buttonilkseviye[ilkseviyeindex]
                    if(kart5.isMatched){
                        buttonilkseviye1.alpha=0.1f
                        if(mediaPlayer.isPlaying){
                            mediaPlayer?.pause()
                            mp?.start()
                            mediaPlayer?.start()
                        }
                    }
                    buttonilkseviye1.setImageResource(if(kart5.isFaceUp) kart5.identifier else kartarkasi)
                }
            }
        }
    }

    private fun formach5(index30:Int,index31:Int) {
        if (ilkseviyekart[index30].identifier==ilkseviyekart[index31].identifier) {
            Toast.makeText(this, "NE HOŞ EŞLEŞTİ", Toast.LENGTH_SHORT).show()
            ilkseviyekart[index30].isMatched = true
            ilkseviyekart[index31].isMatched = true
            puan=puan+10
            Toast.makeText(this, "PUAN:${puan}", Toast.LENGTH_SHORT).show()
            return

        }

    }

    private fun mach5() {
        for(kart5 in ilkseviyekart){
            if(!kart5.isMatched){
                kart5.isFaceUp=false
            }
        }


    }
}