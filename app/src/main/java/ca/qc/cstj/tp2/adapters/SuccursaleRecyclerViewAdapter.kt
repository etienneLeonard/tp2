package ca.qc.cstj.tp2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.models.Succursale
import ca.qc.cstj.tp2.succursalesFragmentDirections
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.viewholder_succursale.view.*
import java.text.NumberFormat
import java.util.*

class SuccursaleRecyclerViewAdapter(var succursales: List<Succursale> = listOf()) : RecyclerView.Adapter<SuccursaleRecyclerViewAdapter.ViewHolder>() {

    private lateinit var circularProgressDrawable: CircularProgressDrawable

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuccursaleRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_succursale, parent, false)

        circularProgressDrawable = CircularProgressDrawable(view.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuccursaleRecyclerViewAdapter.ViewHolder, position: Int) {
        val planet = succursales[position]
        holder.bind(planet)
    }

    override fun getItemCount(): Int = succursales.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val txvSuccursaleName: TextView = view.txvSuccursaleName

        fun bind(succursale: Succursale) {
            txvSuccursaleName.text = succursale.ville

            view.setOnClickListener {
                Toast.makeText(it.context, succursale.ville, Toast.LENGTH_SHORT).show()

                val direction = succursalesFragmentDirections.actionSuccursalesFragmentToDetailsSuccursaleFragment(succursale)

                it.findNavController().navigate(direction)
            }
        }

    }
}