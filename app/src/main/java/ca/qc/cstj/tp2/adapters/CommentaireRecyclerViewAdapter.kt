package ca.qc.cstj.tp2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import android.widget.TextView

import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.models.Commentaire
import kotlinx.android.synthetic.main.viewholder_commentaire.view.*

class CommentaireRecyclerViewAdapter(var commentaires: List<Commentaire> = listOf()) : RecyclerView.Adapter<CommentaireRecyclerViewAdapter.ViewHolder>() {
    private lateinit var circularProgressDrawable: CircularProgressDrawable

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_commentaire, parent,false)


        //EL : on lance la barre de chargement
        circularProgressDrawable = CircularProgressDrawable(view.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentaireRecyclerViewAdapter.ViewHolder, position: Int) {
        val comment = commentaires[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int = commentaires.size

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        private val txvCommentaireName: TextView = view.txvCommentaireName
        private val txvDateComment : TextView =  view.txvDateComment
        private val txvRating : TextView =  view.txvRating
        private val txvNomAuteur : TextView =  view.txvNomAuteur


        fun bind(commentaire: Commentaire){
            txvCommentaireName.text = commentaire.message
            txvDateComment.text = commentaire.dateCommentaire
            txvNomAuteur.text=commentaire.name
            txvRating.text = commentaire.etoile.toString() +" sur 5"
            view.setOnClickListener{
                Toast.makeText(it.context, commentaire.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}