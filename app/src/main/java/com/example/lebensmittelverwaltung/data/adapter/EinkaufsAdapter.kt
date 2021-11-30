package com.example.lebensmittelverwaltung.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lebensmittelverwaltung.R
import com.example.lebensmittelverwaltung.data.db.entities.Produkt
import com.example.lebensmittelverwaltung.ui.einkaufsliste.ViewModel
import kotlinx.android.synthetic.main.produkt_einkaufsliste.view.*

class EinkaufsAdapter (
    var items : List<Produkt>,
    private val viewModel : ViewModel,
        ) : RecyclerView.Adapter<EinkaufsAdapter.EinkaufsViewHolder>(){

    private var listener: ((item: Produkt) -> Unit)? = null         // Erstellen eines listeners um ein Produkt zu übergeben

    fun setOnItemClickListener(listener: (item: Produkt) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EinkaufsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.produkt_einkaufsliste, parent, false) //Vorher angefertigte Layout übergeben, wie ein Produkt in der Liste aussehen soll
        return EinkaufsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EinkaufsViewHolder, position: Int) {
        val curProdukt = items[position]                                //Produkt identifizieren

        holder.itemView.tvEinkaeufeNameEinkauf.text = curProdukt.name      // Produktinformationen den Textviews zuweisen
        holder.itemView.tvPreisEinkauf.text = "${curProdukt.anzahl}"


        holder.itemView.ivdelete.setOnClickListener {
            viewModel.delete(curProdukt)                                //Beim klick auf den ImageView wird das Produkt gelöscht
        }

        holder.itemView.ivbought.setOnClickListener {
            listener?.invoke(curProdukt)                                //listener um das ausgewählte Produkt der Hauptaktivität zu übergeben

        }


    }


    override fun getItemCount(): Int {
        return items.size               //länge der Liste
    }



    inner class EinkaufsViewHolder (itemview : View) : RecyclerView.ViewHolder(itemview) // Erstellen des Viewholders




}
