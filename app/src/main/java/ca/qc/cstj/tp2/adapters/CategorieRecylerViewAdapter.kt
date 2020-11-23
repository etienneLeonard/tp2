//Auteur : Etienne Desrochers
//Date : 22/11/2020
//But: Adapteur de View Holder
package ca.qc.cstj.tp2.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import ca.qc.cstj.tp2.*
import ca.qc.cstj.tp2.models.Categorie
import ca.qc.cstj.tp2.repositories.LivreRepository


import kotlinx.android.synthetic.main.viewholder_categorie.view.*

//Apadter les categories
class CategorieRecylerViewAdapter (var categories: List<Categorie> = listOf()):RecyclerView.Adapter<CategorieRecylerViewAdapter.ViewHolder>(){


    private lateinit var circularProgressDrawable: CircularProgressDrawable
    // Creation Viewloader
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorieRecylerViewAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_categorie, parent, false)

        circularProgressDrawable = CircularProgressDrawable(view.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        //Retour du viewholder
        return ViewHolder(view)
    }
    //retour le nombre de cate
    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategorieRecylerViewAdapter.ViewHolder, position: Int) {
        val temp = categories[position]
        holder.bind(temp)
    }
    //Permet l'utilisation du view holder
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        //Champ pour l'affichage
        private val txvCategorieName: TextView = view.txvCategorieName
        fun bind(categorie: Categorie)
        {
            //Va chercher la valeur du nom de la categorie
            txvCategorieName.text = categorie.NomCategorie;

            //Si L'utilisateur clique sur une categorie
            view.setOnClickListener {

                //Affiche le nom de la categorie
                Toast.makeText(it.context, categorie.NomCategorie, Toast.LENGTH_SHORT).show()
                //Affiche les livres de la categorie

                val direction = categoriesFragmentDirections.actionCategoriesFragmentToCategorieLivre(categorie.NomCategorie)
                it.findNavController().navigate(direction)
            }
        }
    }
}