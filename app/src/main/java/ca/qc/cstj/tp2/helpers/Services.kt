package ca.qc.cstj.tp2.helpers

import ca.qc.cstj.tp2.models.Categorie

class Services {
    companion object{
        //lien pour aller récupérer les succursales
        val SUCCURSALE_SERVICE = "http://10.0.2.2:5600/succursales"
        val CATEGORIE_SERVICE ="http://10.0.2.2:5600/categories/"
        val CATEGORIE_LIVRE_SERVICE ="http://10.0.2.2:5600/categories/nom"
        val LIVRE_SERVICE = "http://10.0.2.2:5600/livres/idLivreUnique/"
        val LIVRE_SERVICE2 = "http://10.0.2.2:5600/livres/"
        val COMMENTAIRE_SERVICE  = "http://10.0.2.2:5600/livres/"
    }
}