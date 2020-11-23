package ca.qc.cstj.tp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_details_succursale.*

class detailsSuccursaleFragment : Fragment() {

    // EL : on va récupérer les arguments du fragment
    private val args: detailsSuccursaleFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_succursale, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //EL : changer le titre dans le barre en haut
        (activity as BottomActivity).supportActionBar?.title = args.succursale!!.ville

        // EL : afficher les informations de la succurale sélectionnée
        txvVilleSuccursale.text = args.succursale!!.ville
        txvAdresseSuccursale.text = args.succursale!!.adresse
        txvInformationSuccursale.text = args.succursale!!.information
        txvTelephoneSuccursale.text = args.succursale!!.telephone
        txvTelecopieurSuccursale.text = args.succursale!!.telecopieur
        txvAppelatif.text = args.succursale!!.appelatif
        txvCodePostalSuccursale.text = args.succursale!!.codePostal
        txvVilleProvinceSuccursale.text = "${args.succursale!!.ville} (${args.succursale!!.province})"

        setHasOptionsMenu(true)
    }

    companion object {
        fun newInstance() =
                detailsSuccursaleFragment().apply {}
    }
}