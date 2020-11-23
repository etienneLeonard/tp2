//Auteur : Etienne Desrochers
//Date : 22/11/2020
//But: Adapteur de View Holder
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
import ca.qc.cstj.tp2.categorie_livreFragment
import ca.qc.cstj.tp2.categorie_livreFragmentDirections
import ca.qc.cstj.tp2.models.CategorieLivre
import ca.qc.cstj.tp2.models.Livre
import ca.qc.cstj.tp2.repositories.LivreRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewholder_categorielivre.view.*
// Adapter pour le livre selon un categorie choisie
class CategorieLivreRecyclerViewAdapter(var livre: List<CategorieLivre> = listOf()): RecyclerView.Adapter<CategorieLivreRecyclerViewAdapter.ViewHolder>()
{
    //Constante pour une image abscent
    private var notfound = "https://img.whaleshares.io/wls-img/einstei1/d765e65f432e7e6f0d062616d19364ecdc5631da.png"

    private lateinit var circularProgressDrawable: CircularProgressDrawable
    // Creation Viewloader
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorieLivreRecyclerViewAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_categorielivre, parent, false)

        circularProgressDrawable = CircularProgressDrawable(view.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        //Retour du viewholder
        return ViewHolder(view)
    }

    //Retourne le nombre de d'item
    override fun getItemCount(): Int = livre.size

    override fun onBindViewHolder(holder: CategorieLivreRecyclerViewAdapter.ViewHolder, position: Int) {
        val temp = livre[position]
        holder.bind(temp)
    }

    // Class permetant l'utilisation du view holder
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        //On va chercher les champs pour l'affichage
        private val txvCategorieLivreName: TextView = view.txvCategorieLivreName
        private val txvCategorieLivreAuteur: TextView = view.txvCategorieLivreAuteur
        private val imgCategorieLivre : ImageView = view.imgIconCategorieLivre

        fun bind(livres: CategorieLivre)
        {
            //Place dans les champs les information
            txvCategorieLivreName.text= livres.titre;
            txvCategorieLivreAuteur.text="Par "+livres.auteur

            //Regarde si le livre a une Image
            if(livres.Img.isNotEmpty())
                //Va chercher l'image
                Picasso.get().load(livres.Img).into(imgCategorieLivre)
            else
                //Va chercher l'image par default
                Picasso.get().load(notfound).into(imgCategorieLivre)

            // Lors du click du livre son titre est afficher
            view.setOnClickListener {
                Toast.makeText(it.context, livres.titre, Toast.LENGTH_SHORT).show()
                val direction = categorie_livreFragmentDirections.actionCategorieLivreToDetailLivreFragment(livres.titre)
                it.findNavController().navigate(direction)
            }
        }
    }
}