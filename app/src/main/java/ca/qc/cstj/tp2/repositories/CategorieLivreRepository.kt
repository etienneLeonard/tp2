package ca.qc.cstj.tp2.repositories

import ca.qc.cstj.tp2.helpers.RepositoryResult
import ca.qc.cstj.tp2.helpers.Services
import ca.qc.cstj.tp2.models.Categorie
import ca.qc.cstj.tp2.models.CategorieLivre
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object CategorieLivreRepository {
    suspend fun getCategorieLivre(NomCategorie:String): RepositoryResult<List<CategorieLivre>> {
        return withContext(Dispatchers.IO)
        {
            //Va chercher les livres qui on la catégorie choisie
            var temp = Services.CATEGORIE_LIVRE_SERVICE+ "?Categorie="+NomCategorie
            val(_,_,result) = temp.httpGet().responseJson()


            when(result)
            {
                is Result.Success ->{
                    //Retour des livres
                    RepositoryResult.Success(Json{ignoreUnknownKeys = true}.decodeFromString(result.value.content))

                }
                is Result.Failure -> {
                    //Retour d'une erreur
                    RepositoryResult.Error(result.getException())
                }
            }
        }
    }
}