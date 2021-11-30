package com.example.lebensmittelverwaltung.ui.einkaufsliste

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.lebensmittelverwaltung.data.db.entities.Einkaeufe
import com.example.lebensmittelverwaltung.data.db.entities.Produkt
import com.example.lebensmittelverwaltung.data.db.entities.ProduktVorrat
import com.example.lebensmittelverwaltung.data.reporsitories.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class ViewModel (
    private val repository: Repository
        ) : ViewModel() {

        fun upsert(produkt : Produkt) = CoroutineScope(Dispatchers.Main).launch {
            repository.upsert(produkt)
        }

        fun delete(produkt: Produkt) = CoroutineScope(Dispatchers.Main).launch {
            repository.delete(produkt)
        }

        fun getAllProdukte() = repository.getAllProdukte()

        fun deleteAll(list : List<Produkt>) = CoroutineScope(Dispatchers.Main).launch {
            repository.deleteAllProdukte(list)
        }

         fun upsertVorrat(produktVorrat: ProduktVorrat) = CoroutineScope(Dispatchers.Main).launch {
             repository.upsertVorrat(produktVorrat)
         }

        fun deleteVorrat(produktVorrat: ProduktVorrat) = CoroutineScope(Dispatchers.Main).launch {
            repository.deleteVorrat(produktVorrat)
         }

        fun deleteAllVorrat(list : List<ProduktVorrat>) = CoroutineScope(Dispatchers.Main).launch {
            repository.deleteAllVorrat(list)
        }

        fun getAllProdukteVorratsliste() = repository.getAllProdukteVorratsliste()


        fun upsertEinkauf(einkauf: Einkaeufe) = CoroutineScope(Dispatchers.Main).launch {
            repository.upsertEinkauf(einkauf)
        }

        fun deleteEinkauf(einkauf: Einkaeufe) = CoroutineScope(Dispatchers.Main).launch {
            repository.deleteEinkauf(einkauf)
        }

        fun getAllEinkaeufe() = repository.getAllEinkaeufe()


        fun produktGekauft(produkt: Produkt) { // Die Funktion markiert das Produkt als gekauft
            produkt.gekauft = true
        }

        fun gekauftesProduktMarkieren(produkt: Produkt, gekauft : Boolean) {
            if(gekauft) {
                produkt.name = produkt.name +"(Gekauft)" // Die Funktion macht das gekaufte Produkt für den User sichtbar
            }

        }


    @RequiresApi(Build.VERSION_CODES.O)
        fun markierenProduktExpiringSoon (list: List<ProduktVorrat>) {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")   //erstellen eines formatters
        val dateTime = LocalDate.now()                                        //aktuelles Datum wird einer Variablen zugewiesen

            for (produkt in list){
                val ablaufdatum = LocalDate.parse(produkt.datum, formatter)     //Produktdatum wird aus dem Pattern "dd.MM.yyyy" zu "yyyy-MM-DD"
                val daysToExpire = ChronoUnit.DAYS.between(dateTime, ablaufdatum)   //Tage zwischen Ablaufdatum und Heute wird berechnet

                if(daysToExpire < 5){
                    produkt.expiringSoon = true //Wenn die Tage bis zum ablaufen kleiner als 5 sind, wird das Produkt markiert
                }

            }
        }

        fun produktExpiringSoon(list: List<ProduktVorrat>) : Boolean {
            for (produkt in list) {
                if(produkt.expiringSoon!!) {    //Wenn eines der Produkte der Vorratsliste bald abbläuft, wird true zurückgegeben
                    return true
                }
            }
            return false
        }

        fun gesamtpreis(list: List<Produkt>) : Double{
            var gesamtPreis = 0.00

            for(produkt in list){   //berechnen des Gesamtpreises aller Produkte aus der Einkaufsliste
                if(produkt.gekauft!!){
                    gesamtPreis += (produkt.preis!! * produkt.anzahl)
                }
            }
            return gesamtPreis
        }




}