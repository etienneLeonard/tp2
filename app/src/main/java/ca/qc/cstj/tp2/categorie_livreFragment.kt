package ca.qc.cstj.tp2

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.fragment_categorie_livre.*
import ca.qc.cstj.tp2.adapters.CategorieLivreRecyclerViewAdapter
import ca.qc.cstj.tp2.helpers.RepositoryResult
import ca.qc.cstj.tp2.helpers.TopSpacingItemDecoration
import ca.qc.cstj.tp2.repositories.CategorieLivreRepository

class categorie_livreFragment : Fragment() {
    //Va chercher le view holder
    private lateinit var categorieLivreRecyclerViewAdapter: CategorieLivreRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_categorie_livre, container, false)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
            return true;
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topSpacingItemDecoration = TopSpacingItemDecoration(30)

        //Affiche le view holder
        categorieLivreRecyclerViewAdapter = CategorieLivreRecyclerViewAdapter()
        rcvCategorieLivre.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = categorieLivreRecyclerViewAdapter
            addItemDecoration(topSpacingItemDecoration)


        }
        lifecycleScope.launch {
            //Ca chercher les parametres
            val args: categorie_livreFragmentArgs by navArgs()

            when (val result = CategorieLivreRepository.getCategorieLivre(args.NomCategorie)) {
                is RepositoryResult.Success -> {
                    //Affiche les livres de la categorie
                    categorieLivreRecyclerViewAdapter.livre = result.data
                    rcvCategorieLivre.adapter!!.notifyDataSetChanged()
                }
                is RepositoryResult.Error -> {
                    Toast.makeText(this@categorie_livreFragment.context, result.exception.message, Toast.LENGTH_LONG).show()
                }

            }

        }


    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            categorie_livreFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}