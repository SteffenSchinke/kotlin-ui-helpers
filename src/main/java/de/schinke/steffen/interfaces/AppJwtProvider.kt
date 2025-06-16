package de.schinke.steffen.interfaces

interface AppJwtProvider {

    fun getJwtToken(): String?
}