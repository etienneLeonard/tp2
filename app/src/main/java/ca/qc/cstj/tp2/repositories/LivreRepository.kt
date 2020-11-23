package ca.qc.cstj.tp2.repositories

import ca.qc.cstj.tp2.helpers.RepositoryResult
import ca.qc.cstj.tp2.helpers.Services
import ca.qc.cstj.tp2.models.Livre
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


object LivreRepository {
    suspend fun getLivre(Id:String): RepositoryResult<Livre>{
        return withContext(Dispatchers.IO)
        {
            var temp = Services.LIVRE_SERVICE + Id

            val(_,_,result) = temp.httpGet().responseJson()

            when(result)
            {
                is Result.Success ->{
                    // On retourne le livre.
                    RepositoryResult.Success(Json{ignoreUnknownKeys = true }.decodeFromString(result.value.content))
                }

                is Result.Failure -> {
                    RepositoryResult.Error(result.getException())
                }
            }
        }

    }
}