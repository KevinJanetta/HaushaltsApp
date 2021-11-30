package com.example.lebensmittelverwaltung.ui.einkaufsliste

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PatternMatcher
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.example.lebensmittelverwaltung.R
import com.example.lebensmittelverwaltung.data.db.EinkaufDatenbank
import com.example.lebensmittelverwaltung.data.db.entities.Produkt
import com.example.lebensmittelverwaltung.data.db.entities.ProduktVorrat
import com.example.lebensmittelverwaltung.data.reporsitories.Repository
import kotlinx.android.synthetic.main.dialog_einkaufsliste_produkt_gekauft.*
import java.text.Format
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern


class EinkaufslisteProduktGekauftDialog (context: Context, var produkt : Produkt?, var addDialogListener: AddDialogListener) : AppCompatDialog(context){



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_einkaufsliste_produkt_gekauft)


        tvProduktgekauftName.text = produkt?.name.toString()    //Textviews werden die Produktinformationen übergeben
        etProduktGekauftAnzahl.hint = produkt?.anzahl.toString()



        btnProduktGekauftOk.setOnClickListener {
            val pattern = Regex("""\d{2}.\d{2}.\d{4}""")    //Pattern für das Datum festlegen - "dd-MM-yyyy"

            val name = tvProduktgekauftName.text.toString()        //Inhalte der Textviews werden variablen zugewiesen
            val anzahl = etProduktGekauftPreis.text.toString()
            val datum = etProduktGekauftDatum.text.toString()
            if(!pattern.matches(datum)){                            //Prüfung, ob das eingegebene Datum dem Pattern entspricht
                Toast.makeText(context, "Datum nicht gültig!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val preis = etProduktGekauftPreis.text.toString().toDouble()


           if(name.isEmpty() || anzahl.isEmpty()){                  //Prüfung ob alle wichtien Felder befüllt sind
                Toast.makeText(context, "Bitte fülle alle Felder aus", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val produkt = Produkt(name, anzahl.toInt(), preis)      //erstellen eines neuen Produktes (An dieser Stelle besteht noch ein Bug, wenn eine Doublezahl dem Textfield zugewiesen wird, wird eine NumberFormatException geworfen)
            val produktVorrat = ProduktVorrat(name, anzahl.toInt(), datum)  //erstellen eines neuen Produktes aus der Vorratsliste
            addDialogListener.onAddButtonClicked(produkt)           //Produkt wird dem Listener übergeben
            addDialogListener.onAddButtonClickedVorrat(produktVorrat)   //Produkt wird dem Listener übergeben
            dismiss()
        }

        btnProduktGekauftAbbrechen.setOnClickListener {
            cancel()
        }



    }

}