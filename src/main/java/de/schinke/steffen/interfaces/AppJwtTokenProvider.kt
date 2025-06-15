package de.schinke.steffen.interfaces

interface AppJwtTokenProvider {

    fun getJwtToken(): String?
}