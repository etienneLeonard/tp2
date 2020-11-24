package ca.qc.cstj.tp2

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ListAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ca.qc.cstj.tp2.adapters.CommentaireRecyclerViewAdapter
import ca.qc.cstj.tp2.helpers.RepositoryResult
import ca.qc.cstj.tp2.helpers.Services
import ca.qc.cstj.tp2.helpers.TopSpacingItemDecoration
import ca.qc.cstj.tp2.models.Commentaire
import ca.qc.cstj.tp2.repositories.CommentaireRepository
import ca.qc.cstj.tp2.repositories.LivreRepository
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_livre.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class detailLivreFragment : Fragment() {



    private val args: detailLivreFragmentArgs by navArgs()
    private  lateinit var commentaireRecyclerViewAdapter: CommentaireRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_livre, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        commentaireRecyclerViewAdapter = CommentaireRecyclerViewAdapter()

        val topSpacingItemDecoration = TopSpacingItemDecoration(35)

        rcvCommentaires.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = commentaireRecyclerViewAdapter

            addItemDecoration(topSpacingItemDecoration)
        }

        lifecycleScope.launch{
            when(val result = LivreRepository.getLivre(args.id)){
                is RepositoryResult.Success -> {
                    (activity as BottomActivity).supportActionBar?.title = result.data.titre
                    // On affiche le titre
                    txvTitre.text = result.data.titre

                    // On affiche l'image
                    txvPrix.text = result.data.prix.toString() + "$"
                    txvGenre.text = result.data.categorie

                    txvAuteur.text = result.data.auteur
                    txvIsbn.text = result.data.ISBN

                    when(val result = CommentaireRepository.getCommentaire(args.id))
                    {
                        is RepositoryResult.Success -> {
                            commentaireRecyclerViewAdapter.commentaires = result.data
                            rcvCommentaires.adapter!!.notifyDataSetChanged()
                        }
                        is RepositoryResult.Error -> {
                            Toast.makeText(this@detailLivreFragment.context, result.exception.message, Toast.LENGTH_LONG).show()
                        }
                    }

                    if (result.data.Img.isNotEmpty())
                        Picasso.get().load(result.data.Img).into(imgImage)
                }
                is RepositoryResult.Error -> {
                    Toast.makeText(this@detailLivreFragment.context, result.exception.message, Toast.LENGTH_LONG).show()
                }
            }

        }
        btnCommentaire.setOnClickListener{
            if( tilCommentaire.editableText.toString().isNotEmpty() and tilNom.editableText.toString().isNotEmpty())
            {
                val temp = Services.LIVRE_SERVICE2 + args.id + "/commentaires"
                var e = "{\"name\":\"${tilNom.editableText.toString()}\",\"dateCommentaire\":\"${LocalDateTime.now()}\",\"message\":\"${tilCommentaire.editableText.toString()}\",\"etoile\":${Rating.rating},\"idLivre\":\"${args.id}\"}"
                temp.httpPost().jsonBody(e).response { result -> }

                tilNom.editableText.clear()
                tilCommentaire.editableText.clear()
                Rating.rating =0.0f
                Toast.makeText(this@detailLivreFragment.context, "Envoy Commentaire Réussi", Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(this@detailLivreFragment.context,"Veuiller Remplir le formulaire",Toast.LENGTH_LONG).show()

        }



    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
            return true;
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        fun newInstance() = detailLivreFragment().apply {}
    }
}