package ca.qc.cstj.tp2

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
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
import androidx.recyclerview.widget.RecyclerView.LayoutManager as layoutManager
import ca.qc.cstj.tp2.helpers.TopSpacingItemDecoration
import ca.qc.cstj.tp2.repositories.CategorieLivreRepository


class categorie_livre : Fragment() {


    private lateinit var categorieLivreRecyclerViewAdapter: CategorieLivreRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categorie_livre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topSpacingItemDecoration = TopSpacingItemDecoration(30)

        categorieLivreRecyclerViewAdapter = CategorieLivreRecyclerViewAdapter()


        rcvCategorieLivre.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = categorieLivreRecyclerViewAdapter
            addItemDecoration(topSpacingItemDecoration)


        }
        lifecycleScope.launch {
            val args: categorie_livreArgs by navArgs()

            when (val result = CategorieLivreRepository.getCategorieLivre(args.NomCategorie)) {
                is RepositoryResult.Success -> {
                    categorieLivreRecyclerViewAdapter.livre = result.data
                    rcvCategorieLivre.adapter!!.notifyDataSetChanged()
                }
                is RepositoryResult.Error -> {
                    Toast.makeText(this@categorie_livre.context, result.exception.message, Toast.LENGTH_LONG).show()
                }

            }

        }


    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            categorie_livre().apply {
                arguments = Bundle().apply {}
            }
    }
}