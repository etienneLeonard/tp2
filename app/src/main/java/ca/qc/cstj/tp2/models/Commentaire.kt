package ca.qc.cstj.tp2.models

import kotlinx.serialization.Serializable

@Serializable
data class Commentaire (val name:String,val dateCommentaire:String,val message:String,val etoile:Float,val idLivre:String):java.io.Serializable