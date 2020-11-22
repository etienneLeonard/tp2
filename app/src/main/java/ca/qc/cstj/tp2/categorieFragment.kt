package ca.qc.cstj.tp2

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.fragment_categories.*
import ca.qc.cstj.tp2.adapters.CategorieRecylerViewAdapter
import ca.qc.cstj.tp2.helpers.RepositoryResult
import ca.qc.cstj.tp2.helpers.TopSpacingItemDecoration
import ca.qc.cstj.tp2.repositories.CategorieRepository

class categoriesFragment : Fragment() {

    //Va chercher le viewholder
    private lateinit var categorieRecyvlerViewAdapter: CategorieRecylerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topSpacingItemDecoration = TopSpacingItemDecoration(30)

        //Va chercher le view model et l'affiche
        categorieRecyvlerViewAdapter = CategorieRecylerViewAdapter()
        rcvCategories.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = categorieRecyvlerViewAdapter
            addItemDecoration(topSpacingItemDecoration)
        }

        lifecycleScope.launch {

            when(val result = CategorieRepository.getCategorie()) {
                is RepositoryResult.Success -> {
                    categorieRecyvlerViewAdapter.categories = result.data
                    rcvCategories.adapter!!.notifyDataSetChanged()
                }
                is RepositoryResult.Error -> {

                    Toast.makeText(this@categoriesFragment.context, result.exception.message, Toast.LENGTH_LONG).show()
                }
            }
        }

    }
    companion object {
        fun newInstance(param1: String, param2: String) =
            categoriesFragment().apply {
                arguments = Bundle().apply {}
            }
    }

}