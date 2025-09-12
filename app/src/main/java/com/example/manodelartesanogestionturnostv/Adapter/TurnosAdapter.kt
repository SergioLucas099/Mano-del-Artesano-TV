package com.example.manodelartesanogestionturnostv.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.manodelartesanogestionturnostv.Model.TurnosModel
import com.example.manodelartesanogestionturnostv.R

class TurnosAdapter (
    private var turnosList: List<TurnosModel>
) : RecyclerView.Adapter<TurnosAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.turnos_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Lturno = turnosList[position]

        holder.txtNombre.text = Lturno.nombre
        holder.txtTurno.text = Lturno.turno
    }

    override fun getItemCount(): Int {
        return turnosList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
        val txtTurno: TextView = itemView.findViewById(R.id.txtTurno)
    }
}