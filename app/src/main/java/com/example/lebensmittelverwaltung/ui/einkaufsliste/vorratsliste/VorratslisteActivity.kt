package com.example.lebensmittelverwaltung.ui.einkaufsliste.vorratsliste

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lebensmittelverwaltung.EinkaeufeActivity
import com.example.lebensmittelverwaltung.data.adapter.ProduktVorratAdapter
import com.example.lebensmittelverwaltung.R
import com.example.lebensmittelverwaltung.data.db.EinkaufDatenbank
import com.example.lebensmittelverwaltung.data.db.entities.Produkt
import com.example.lebensmittelverwaltung.data.db.entities.ProduktVorrat
import com.example.lebensmittelverwaltung.data.reporsitories.Repository
import com.example.lebensmittelverwaltung.ui.einkaufsliste.AddDialogListener
import com.example.lebensmittelverwaltung.ui.einkaufsliste.ViewModel
import com.example.lebensmittelverwaltung.ui.einkaufsliste.ViewModelFactory
import com.example.lebensmittelverwaltung.ui.einkaufsliste.EinkaufslisteActivity
import kotlinx.android.synthetic.main.activity_einkaufsliste.*

class VorratslisteActivity : AppCompatActivity(){

    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"
    val NOTIFICATION_ID = 0


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vorratsliste)
        createNotificationChannel()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID) //Die Benachrichtiung mit ihren Eigenschaften, welchen Text usw. sie beinhalten soll
            .setContentTitle("Produkt wird in kürze ablaufen!")
            .setContentText("Ein Produkt wird bald ablaufen!")
            .setSmallIcon(R.drawable.ic_noti)
            .setPriority(NotificationCompat.PRIORITY_HIGH) //Priorität
            .build()

        val notificationManager = NotificationManagerCompat.from(this) //Manager, welcher die Benachritigung abruft


        val database = EinkaufDatenbank(this)        //Erstellen der Datenbank
        val repository = Repository(database)               //Erstellen des Repositorys
        val factory = ViewModelFactory(repository)          //Erstellen der Viewmodelfactory



        val viewModel = ViewModelProviders.of(this, factory).get(ViewModel::class.java)         //Erstllen des Viewmodels


        val adapter = ProduktVorratAdapter(listOf(), viewModel)         //Erstellen des Adapters


        rvEinkaeufeListe.layoutManager = LinearLayoutManager(this)
        rvEinkaeufeListe.adapter = adapter      //adapter zuweisen

        viewModel.getAllProdukteVorratsliste().observe(this, Observer { //LiveDaten aus der Datenbank werden ausgelsen
            adapter.items = it
          viewModel.markierenProduktExpiringSoon(adapter.items) // Produkte die in kürze Ablaufen werden markiert
            adapter.notifyDataSetChanged()
            if(viewModel.produktExpiringSoon(adapter.items)) {
                notificationManager.notify(NOTIFICATION_ID,notification) // Benachrichtigung wird übergeben und aufgerufen, wenn eines der Produkte in kürze abläuft
            }


        })


        btnNeuerEinkauf.setOnClickListener {    //Beim klick auf den Button wird ein Dialogfenster erstellt
            VorratProduktHinzufuegenDialog(this,
                object : AddDialogListener {    //Der Context und der Listener werden übergeben
                    override fun onAddButtonClicked(produkt: Produkt) { // Funktionen müssen implementiert werden, wobei aber nicht alle gebraucht werden
                    }

                    override fun onAddButtonClickedEinkauf(name: String) {
                    }


                    override fun onAddButtonClickedVorrat(produktVorrat: ProduktVorrat) {
                    viewModel.upsertVorrat(produktVorrat)       //Produkt, welches aus dem Dialogfensster mithilfe des Listeners übergeben wird, wird der Vorratsliste hinzugefügt
                }
            }).show()
        }


        btnEinkaufsliste.setOnClickListener {   // Buttton zum starten der EinkaufsActivity
            Intent(this, EinkaufslisteActivity::class.java).also {
                startActivity(it)
            }
        }




        btnEinkaeufe.setOnClickListener {   // Buttton zum starten der EinkaeufeActívity
            Intent(this, EinkaeufeActivity::class.java).also {
                startActivity(it)
            }
        }

    }

    fun createNotificationChannel () {  //Erstellen des Notificationchannels
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {   //wird nur benötigt, wenn man höhere Version als Oreo benutzt
            val channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME, //Erstellen des Channels, mit den zuvor deklarierten Variablen
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.GREEN    //eigenschaften, wie zb. die LED-Farbe sein soll
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager // erstellen des Managers, um den eigentlichen Notificationchannel zu erstellen
            manager.createNotificationChannel(channel)
        }
    }

}