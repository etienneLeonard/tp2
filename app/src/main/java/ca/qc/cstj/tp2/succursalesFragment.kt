package ca.qc.cstj.tp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ca.qc.cstj.tp2.adapters.SuccursaleRecyclerViewAdapter
import ca.qc.cstj.tp2.helpers.RepositoryResult
import ca.qc.cstj.tp2.helpers.TopSpacingItemDecoration
import ca.qc.cstj.tp2.repositories.SuccursaleRepository
import kotlinx.android.synthetic.main.fragment_succursales.*
import kotlinx.coroutines.launch

class succursalesFragment : Fragment() {

    private lateinit var succursaleRecyclerViewAdapter : SuccursaleRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_succursales, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topSpacingItemDecoration = TopSpacingItemDecoration(30)

        succursaleRecyclerViewAdapter = SuccursaleRecyclerViewAdapter()

        rcvSuccursales.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = succursaleRecyclerViewAdapter
            addItemDecoration(topSpacingItemDecoration)
        }

        lifecycleScope.launch {

            when(val result = SuccursaleRepository.getSuccursales()) {
                is RepositoryResult.Success -> {
                    succursaleRecyclerViewAdapter.succursales = result.data
                    rcvSuccursales.adapter!!.notifyDataSetChanged()
                }
                is RepositoryResult.Error -> {
                    Toast.makeText(this@succursalesFragment.context, result.exception.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    companion object {
        fun newInstance(param1: String, param2: String) =
            succursalesFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}