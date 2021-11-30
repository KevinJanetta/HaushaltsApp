package com.example.lebensmittelverwaltung.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lebensmittelverwaltung.R
import com.example.lebensmittelverwaltung.data.db.entities.Einkaeufe
import com.example.lebensmittelverwaltung.data.db.entities.Produkt
import com.example.lebensmittelverwaltung.ui.einkaufsliste.ViewModel
import kotlinx.android.synthetic.main.einkauf_einkaeufe.view.*
import kotlinx.android.synthetic.main.einkauf_einkaeufe.view.tvEinkaeufeNameEinkauf
import kotlinx.android.synthetic.main.einkauf_einkaeufe.view.tvPreisEinkauf
import kotlinx.android.synthetic.main.produkt_einkaufsliste.view.*

class EinkaeufeAdapter (
    var einkaeufe : List<Einkaeufe>,
    private val viewModel : ViewModel,
    ) : RecyclerView.Adapter<EinkaeufeAdapter.EinkaeufeViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EinkaeufeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.einkauf_einkaeufe,parent, false)    //Vorher angefertigte Layout übergeben, wie ein Einkauf in der Liste aussehen soll
        return EinkaeufeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EinkaeufeAdapter.EinkaeufeViewHolder, position: Int) {
        val curEinkauf = einkaeufe[position]                                        //Produkt identifizieren

        holder.itemView.tvEinkaeufeNameEinkauf.text = curEinkauf.name               // Produktinformationen den Textviews zuweisen
        holder.itemView.tvPreisEinkauf.text = curEinkauf.gesamtpreis.toString()

        holder.itemView.ivdeleteEinkauf.setOnClickListener {
            viewModel.deleteEinkauf(curEinkauf)                                     //Beim klick auf den ImageView wird der Einkauf gelöscht
        }
    }

    override fun getItemCount(): Int {
        return einkaeufe.size               //länge der Liste
    }




    inner class EinkaeufeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)        // Erstellen des Viewholders


}
