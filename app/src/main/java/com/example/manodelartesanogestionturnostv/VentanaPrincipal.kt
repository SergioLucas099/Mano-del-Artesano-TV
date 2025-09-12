package com.example.manodelartesanogestionturnostv

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.os.Handler
import android.os.Looper
import java.text.SimpleDateFormat
import java.util.*
import android.view.View
import android.widget.VideoView
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manodelartesanogestionturnostv.Adapter.TurnosAdapter
import com.example.manodelartesanogestionturnostv.Model.TurnosModel

class VentanaPrincipal : AppCompatActivity() {

    private lateinit var databaseReference : DatabaseReference
    private lateinit var ListaTurnos : ArrayList<TurnosModel>
    private lateinit var RevTurnos: RecyclerView
    private lateinit var txtHora : TextView
    private lateinit var txtFecha : TextView
    private val handler = Handler(Looper.getMainLooper())
    private val relojRunnable = object : Runnable {
        override fun run() {
            val horaActual  = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
            val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            txtHora.text  = horaActual
            txtFecha.text = fechaActual

            handler.postDelayed(this, 1000) // se ejecuta cada 1 segundo
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_principal)

        // -----Iniciliar widgets XML-----
        //val Contenido = findViewById<TextView>(R.id.Contenido)
        val carruselText  = findViewById<TextView>(R.id.carruselText)
        RevTurnos = findViewById(R.id.RevTurnos)
        txtHora = findViewById(R.id.txtHora)
        txtFecha= findViewById(R.id.txtFecha)
        val videoView = findViewById<VideoView>(R.id.videoView)

        //-----Configuracion Turnos-----
        RevTurnos.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        ListaTurnos = arrayListOf<TurnosModel>()
        RevTurnos.visibility = View.GONE
        FirebaseDatabase.getInstance().reference.child("turnos")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot){
                    ListaTurnos.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(TurnosModel::class.java)
                            ListaTurnos.add(data!!)
                        }
                    }

                    val adapter = TurnosAdapter(ListaTurnos)
                    RevTurnos.adapter = adapter
                    RevTurnos.visibility = View.VISIBLE
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        // -----Configuracion carrusel texto-----
        databaseReference = FirebaseDatabase.getInstance()
            .getReference("carruselTexto")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val texto = snapshot.getValue(String::class.java)
                carruselText.setText(texto)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FIREBASE", "Error: ${error.message}")
            }
        })

        carruselText.setSelected(true)

        // -----Iniciar reloj-----
        handler.post(relojRunnable)

        //Configuracion Video
        databaseReference = FirebaseDatabase.getInstance()
            .getReference("videoActual").child("videoUri")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val videoUri = snapshot.getValue(String::class.java)?.toUri()
                videoView.setVideoURI(videoUri)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FIREBASE", "Error: ${error.message}")
            }
        })

        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            mediaPlayer.setVolume(0f, 0f) // mediaPlayer.setVolume(1f, 1f)
            videoView.start()
        }
    }
}