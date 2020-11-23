package ca.qc.cstj.tp2

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import ca.qc.cstj.tp2.helpers.RepositoryResult
import ca.qc.cstj.tp2.helpers.Services
import ca.qc.cstj.tp2.repositories.LivreRepository
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_livre.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class detailLivreFragment : Fragment() {



    private val args: detailLivreFragmentArgs by navArgs()


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

                    if (result.data.Img.isNotEmpty())
                        Picasso.get().load(result.data.Img).into(imgImage)
                }
                is RepositoryResult.Error -> {
                    Toast.makeText(this@detailLivreFragment.context, result.exception.message, Toast.LENGTH_LONG).show()
                }
            }

        }
        btnCommentaire.setOnClickListener(View.OnClickListener {
           fun btnCommentaireClick(view: View) {
               val temp = Services.LIVRE_SERVICE2 + args.id
               temp.httpPost().jsonBody("{'name':${tilNom.text},'dateCommentaire':${LocalDateTime.now()},'message:${tilCommentaire.text}','etoile':5}")
            }
        })





    }


    companion object {
        fun newInstance() = detailLivreFragment().apply {}
    }
}