package ca.qc.cstj.tp2.models

import kotlinx.serialization.Serializable

@Serializable
data class CategorieLivre (val categorie:String,val titre:String,val prix:Double,val auteur:String,val sujet:String,val ISBN:String,val __v:Int,val Img:String):java.io.Serializable