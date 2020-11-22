package ca.qc.cstj.tp2.repositories

import ca.qc.cstj.tp2.helpers.RepositoryResult
import ca.qc.cstj.tp2.helpers.Services
import ca.qc.cstj.tp2.models.Categorie
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object CategorieRepository {
    suspend fun getCategorie(): RepositoryResult<List<Categorie>> {
        return withContext(Dispatchers.IO)
        {
            //va chercher les categories
            val(_,_,result) = Services.CATEGORIE_SERVICE.httpGet().responseJson()

            when(result)
            {
                is Result.Success ->{
                    // Retour des catégories
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