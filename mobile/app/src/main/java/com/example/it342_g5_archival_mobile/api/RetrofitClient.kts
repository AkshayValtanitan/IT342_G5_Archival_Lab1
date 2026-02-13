interface ApiService {

    @POST("api/auth/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<ApiResponse>
