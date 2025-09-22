package com.example.manodelartesanogestionturnostv.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.manodelartesanogestionturnostv.Model.LlamandoTurnoModel
import com.example.manodelartesanogestionturnostv.R

class LlamandoTurnoAdapter (
    private var turnosList: List<LlamandoTurnoModel>
) : RecyclerView.Adapter<LlamandoTurnoAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.llamando_turno_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Lturno = turnosList[position]

        holder.txtNombre.text = Lturno.NombreAtraccion
        holder.txtTurno.text = Lturno.TurnoAsignado
    }

    override fun getItemCount(): Int {
        return turnosList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombreTurnoLlamado)
        val txtTurno: TextView = itemView.findViewById(R.id.txtTurnoLlamado)
    }
}