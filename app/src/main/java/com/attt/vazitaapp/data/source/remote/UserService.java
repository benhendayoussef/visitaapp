package com.attt.vazitaapp.data.source.remote;


import com.attt.vazitaapp.data.requestModel.GetUserInfoResponse;
import com.attt.vazitaapp.data.requestModel.LogoutResponse;
import com.attt.vazitaapp.data.requestModel.SignInRequest;
import com.attt.vazitaapp.data.requestModel.SignInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    /*@POST("api/v1/student/auth/signup")
    Call<SignupResponse> signUp(@Body SignupRequest signupRequest);


    @POST("api/v1/student/auth/verifyCode")
    Call<VerifyCodeResponse> verifyCode(@Body VerifyCodeRequest verifyCodeRequest);*/


    @POST("api/v1/auth/login")
    Call<SignInResponse> login(@Body SignInRequest signInRequest);
    @POST("api/v1/auth/logout")
    Call<LogoutResponse> logout();
    @GET("api/v1/user")
    Call<GetUserInfoResponse> getUserInfo();



}
