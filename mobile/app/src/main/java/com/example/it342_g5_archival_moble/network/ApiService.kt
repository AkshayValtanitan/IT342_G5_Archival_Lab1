package com.example.it342_g5_archival_mobile.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class AuthRequest(
    val username: String,
    val password: String
)

data class ApiResponse(
    val success: Boolean,
    val message: String
)

data class UserData(
    val id: Long,
    val username: String
)

data class MeResponse(
    val success: Boolean,
    val message: String,
    val data: UserData?
)

interface ApiService {

    @POST("/api/auth/register")
    suspend fun register(@Body request: AuthRequest): Response<ApiResponse>

    @POST("/api/auth/login")
    suspend fun login(@Body request: AuthRequest): Response<ApiResponse>

    @POST("/api/auth/logout")
    suspend fun logout(): Response<ApiResponse>

    @GET("/api/user/me")
    suspend fun getMe(): Response<MeResponse>
}