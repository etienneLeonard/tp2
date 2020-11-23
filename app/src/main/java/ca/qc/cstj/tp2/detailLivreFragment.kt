package ca.qc.cstj.tp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import ca.qc.cstj.tp2.helpers.RepositoryResult
import ca.qc.cstj.tp2.repositories.LivreRepository
import kotlinx.android.synthetic.main.fragment_detail_livre.*
import kotlinx.coroutines.launch

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch{
            when(val result = LivreRepository.getLivre(args.id)){
                is RepositoryResult.Success -> {
                    (activity as BottomActivity).supportActionBar?.title = result.data.titre
                    // On affiche le titre
                    txvTitre.text = result.data.titre

                    // On affiche l'image

                    txvAuteur.text = result.data.auteur
                    txvIsbn.text = result.data.ISBN
                }
                is RepositoryResult.Error -> {
                    Toast.makeText(this@detailLivreFragment.context, result.exception.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    companion object {
        fun newInstance() = detailLivreFragment().apply {}
    }
}