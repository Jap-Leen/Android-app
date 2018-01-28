package com.japkaur.one;

/**
 * Created by jap on 20/1/18.
 */


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("/search/index.xml")
    Call<BooksResponse> getBookInfoByString(@Query("key") String APIKey, @Query("q") String name);
    @GET("/search/index.xml")
    Call<BooksResponse> getBookInfoById(@Query("key") String APIKey, @Query("q") Integer id);
    }

