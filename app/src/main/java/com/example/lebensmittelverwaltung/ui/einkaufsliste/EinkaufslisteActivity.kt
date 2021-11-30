package com.example.lebensmittelverwaltung.ui.einkaufsliste

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lebensmittelverwaltung.*
import com.example.lebensmittelverwaltung.data.adapter.EinkaufsAdapter
import com.example.lebensmittelverwaltung.data.db.EinkaufDatenbank
import com.example.lebensmittelverwaltung.data.db.entities.Einkaeufe
import com.example.lebensmittelverwaltung.data.db.entities.Produkt
import com.example.lebensmittelverwaltung.data.db.entities.ProduktVorrat
import com.example.lebensmittelverwaltung.data.reporsitories.Repository
import com.example.lebensmittelverwaltung.ui.einkaufsliste.vorratsliste.VorratslisteActivity
import kotlinx.android.synthetic.main.activity_einkaufsliste.*
import kotlinx.android.synthetic.main.activity_einkaufsliste.btnEinkaeufe
import kotlinx.android.synthetic.main.activity_einkaufsliste.btnVorratsliste
import kotlinx.android.synthetic.main.activity_einkaufsliste.rvEinkaeufeListe
import kotlinx.android.synthetic.main.activity_einkaufsliste.tvEinkauf


class EinkaufslisteActivity : AppCompatActivity() {


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_einkaufsliste)

        val database = EinkaufDatenbank(this)
        val repository = Repository(database)
        val factory = ViewModelFactory(repository)


        val viewModel = ViewModelProviders.of(this, factory).get(ViewModel::class.java)

        val adapter = EinkaufsAdapter(listOf(), viewModel)

        rvEinkaeufeListe.layoutManager = LinearLayoutManager(this)
        rvEinkaeufeListe.adapter = adapter

        viewModel.getAllProdukte().observe(this, Observer { //LiveDaten werden aus der Datebank gelesen
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        btnProdukthinzufuegen.setOnClickListener { //Beim klick auf den Button wird ein Dialogfenster erstellt

            EinkaufslisteProdukthinzufuegenDialog(this,
                object : AddDialogListener{     //Der Context und der Listener werden übergeben
                    override fun onAddButtonClicked(produkt: Produkt) {
                        viewModel.upsert(produkt)                       //Produkt, welches aus dem Dialogfensster mithilfe des Listeners übergeben wird, wird der Einkaufsliste hinzugefügt
                    }

                    override fun onAddButtonClickedEinkauf(name: String) {
                    }
                                                                            // Funktionen müssen implementiert werden, wobei aber nicht alle gebraucht werden
                    override fun onAddButtonClickedVorrat(produktVorrat: ProduktVorrat) {

                    }
                }).show()

        }


        adapter.setOnItemClickListener {    //Listener aus dem Adapter, damit man weiß auf welches Produkt man geklickt hat
            val produkt1 = it               //Informationen des ausgewählten Produkts werden einer Variablen zugewiesen


            EinkaufslisteProduktGekauftDialog(this, produkt1, object : AddDialogListener{
                override fun onAddButtonClicked(produkt: Produkt) {
                    produkt1.preis = produkt.preis          //Produktpreis aus dem Dialogfenster wird dem Produkt aus dem Adapter zugewiesen
                    viewModel.upsert(produkt1)              //Produkt wird geupdated
                }
                override fun onAddButtonClickedVorrat(produktVorrat: ProduktVorrat) {
                    viewModel.produktGekauft(produkt1)              //Variable "gekauft" des Produktes wird auf "true" gesetzt
                    viewModel.gekauftesProduktMarkieren(produkt1, produkt1.gekauft!!)   //Produkt wird optisch in der Einkaufsliste als gekauft markiert
                    viewModel.upsertVorrat(produktVorrat)       //gekauftes Produkt wird der Vorratsliste hinzugefügt
                }

                override fun onAddButtonClickedEinkauf(name: String) {

                }

            }).show()
        }


        btnVorratsliste.setOnClickListener {    //Button zum starten der VorratslisteActivity
            Intent(this, VorratslisteActivity::class.java).also {
                startActivity(it)

            }
        }

        btnEinkaeufe.setOnClickListener {       //Button zum starten der EinkaeufeActivity
            Intent(this, EinkaeufeActivity::class.java).also {
                startActivity(it)
            }
        }

        btnNeuerEinkauf.setOnClickListener {        //Button zum erstellen eines neuen Einkaufs
            EinkaufslisteNeuerEinkaufDialog(this,
                object : AddDialogListener{
                    override fun onAddButtonClicked(produkt: Produkt) {
                    }

                    override fun onAddButtonClickedEinkauf(name: String) {
                        viewModel.deleteAll(adapter.items)  //alle Produkte werden aus der Einkaufsliste gelöscht
                        tvEinkauf.text = name               // Der Name aus dem Dialogfenster wird dem Einkaufsnamen übergeben
                    }


                    override fun onAddButtonClickedVorrat(produktVorrat: ProduktVorrat) {
                    }
                }).show()
        }

        btnEinkaufabschließen.setOnClickListener {      // Button zum Abschließen des Einkaufs
            val name = tvEinkauf.text.toString()        //Einkaufsname wird übergeben
            val gesamtPreis =  viewModel.gesamtpreis(adapter.items)    //Gesamtpreis der als "gekauft" markierten Produkte
            val einkauf = Einkaeufe(name, gesamtPreis)  //erstellen des Einkaufs mit dem Namen und dem Gesamtpreis


            viewModel.upsertEinkauf(einkauf)            //Einfügen des Einkaufs in die Einkäufe-Liste
            viewModel.deleteAll(adapter.items)          //Da der Einkauf abgeschlossen ist, werden alle Produkte gelöscht, auch die nicht gekauft wurden

        }





    }
}