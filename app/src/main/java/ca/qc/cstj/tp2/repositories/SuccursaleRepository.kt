package ca.qc.cstj.tp2.repositories

import ca.qc.cstj.tp2.helpers.RepositoryResult
import ca.qc.cstj.tp2.helpers.Services
import ca.qc.cstj.tp2.models.Succursale
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object SuccursaleRepository {

    suspend fun getSuccursales(): RepositoryResult<List<Succursale>> {
        //AJAX Get
        return withContext(Dispatchers.IO) {
            //Dans un autre thread
            val (_, _, result) = Services.SUCCURSALE_SERVICE.httpGet().responseJson()

            when (result) {
                is Result.Success -> {
                    //TODO: Transformer la string en List<Succursale> --> Déserialiser (DéSpécialK)
                    RepositoryResult.Success(Json { ignoreUnknownKeys = true }.decodeFromString(result.value.content))
                }
                is Result.Failure -> {
                    RepositoryResult.Error(result.getException())
                }

            }
        }
    }
}