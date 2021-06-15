package com.coding.aisleassignment.apiservice;

import com.coding.aisleassignment.Constants;
import com.coding.aisleassignment.pojo.GetResponse;
import com.coding.aisleassignment.pojo.Invites;
import com.coding.aisleassignment.pojo.Login;
import com.coding.aisleassignment.pojo.LoginResponse;
import com.coding.aisleassignment.pojo.Token;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiServiceInterface {


    //Header Can be Added  Dynamically also
     @Headers({"Content-Type: application/json",
     "Cookie: __cfduid=df9b865983bd04a5de2cf5017994bbbc71618565720"})
    @POST(Constants.LOGIN)
    Call<LoginResponse> sendPhoneNumberToServer(@Body Login login);

    @Headers({"Content-Type: application/json",
            "Cookie: __cfduid=df9b865983bd04a5de2cf5017994bbbc71618565720"})
    @POST(Constants.VERIFY_OTP)
    Call<Token> verifyOTP(@Body HashMap<String,String> map);

    @Headers({"Cookie: __cfduid=df9b865983bd04a5de2cf5017994bbbc71618565720"})
    @GET(Constants.PROFILE_LIST)
    Call<GetResponse>getResponseFromToken(@Header("Authorization") String authorization);












}
