package de.schinke.steffen.interfaces

interface AppError {

    val type: AppErrorType
    val responseCode: Int?
    val responseMessage: String?
    val innerException: AppError?
}