package com.example.lebensmittelverwaltung.ui.einkaufsliste.vorratsliste

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.lebensmittelverwaltung.R
import com.example.lebensmittelverwaltung.data.db.entities.ProduktVorrat
import com.example.lebensmittelverwaltung.ui.einkaufsliste.AddDialogListener
import kotlinx.android.synthetic.main.dialog_vorratsliste_produkt_hinzufuegen.*

class VorratProduktHinzufuegenDialog(context: Context, var addDialogListener: AddDialogListener) : AppCompatDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_vorratsliste_produkt_hinzufuegen)

        btnVorratProdukthinzu.setOnClickListener {           //Button um das Produkt hinzuzufügen
            val name = etVorratslisteProduktname.text.toString()    // Inhalte der Textviews werden variablen zugewiesen
            val anzahl = etVorratslisteProduktanzahl.text.toString()
            val datum = etAblaufdatum.text.toString()

            if(name.isEmpty() || anzahl.isEmpty()) {            //Checkt ob der Name und die Anzahl ausgefüllt sind
                Toast.makeText(context, "Information angeben", Toast.LENGTH_SHORT).show() //wenn nicht wird ein Toast aufgerufen
                return@setOnClickListener
            }

            val produkt = ProduktVorrat(name, anzahl.toInt(), datum) //Erstellen eines Produktes mit den zuvor deklarierten Variablen
            addDialogListener.onAddButtonClickedVorrat(produkt)     //Produkt wird dem Listener Übergeben
            dismiss()
        }

        btnNeuerEinkaufAbbrechen.setOnClickListener {   //Button zum schließen des Dialogfensters
            cancel()
        }
    }

}