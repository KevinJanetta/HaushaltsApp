package com.example.lebensmittelverwaltung.ui.einkaufsliste

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.lebensmittelverwaltung.R
import com.example.lebensmittelverwaltung.data.db.entities.Produkt
import kotlinx.android.synthetic.main.activity_einkaufsliste_produkt.*


class EinkaufslisteProdukthinzufuegenDialog(context: Context, var addDialogListener: AddDialogListener) : AppCompatDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_einkaufsliste_produkthinzufuegen)

        btnEinkaufslisteProdukthinzu.setOnClickListener {
            val name = etEinkaufslisteProduktname.text.toString()       //Inhalte der Textviews werden variablen zugewiesen
            val anzahl = etEinkaufslisteProduktanzahl.text.toString()


            if(name.isEmpty() || anzahl.isEmpty()){                     //Prüfung ob alle wichtien Felder befüllt sind
                Toast.makeText(context, "Bitte fülle alle Felder aus", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val produkt = Produkt(name, anzahl.toInt(), null)       //Neues Produkt wird aus den Informationen der TextViews erstellt
            addDialogListener.onAddButtonClicked(produkt)               //Produkt wird dem Listener übergeben
            dismiss()
        }

        btnEinkaufslisteProduktAbbrechen.setOnClickListener {
            cancel()
        }
    }


}
