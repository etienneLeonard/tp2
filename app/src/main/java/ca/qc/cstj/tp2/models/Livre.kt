package ca.qc.cstj.tp2.models

import kotlinx.serialization.Serializable

@Serializable
data class Livre (val categorie:String,var titre:String, val prix:Double, val auteur:String, var ISBN:String, var Img:String) : java.io.Serializable
