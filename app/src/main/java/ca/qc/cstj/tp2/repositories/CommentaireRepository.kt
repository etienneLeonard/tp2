// But : Programmer le répectoire des commentaires
// Auteur : Gabriel Duquette Godon
// Date : 23 novembre 2020

package ca.qc.cstj.tp2.repositories

import ca.qc.cstj.tp2.helpers.RepositoryResult
import ca.qc.cstj.tp2.helpers.Services
import ca.qc.cstj.tp2.models.Commentaire
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.github.kittinunf.result.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object CommentaireRepository {
    suspend fun getCommentaire(Id:String) : RepositoryResult<List<Commentaire>>
    {
        return withContext(Dispatchers.IO)
        {
            var temp = Services.COMMENTAIRE_SERVICE + Id + "/commentaire"

            val(_,_,result) = temp.httpGet().responseJson()

            when(result)
            {
                is Result.Success -> {
                    RepositoryResult.Success(Json { ignoreUnknownKeys=true }.decodeFromString(result.value.content))
                }
                is Result.Failure -> {
                    RepositoryResult.Error(result.getException())
                }
            }
        }
    }
}