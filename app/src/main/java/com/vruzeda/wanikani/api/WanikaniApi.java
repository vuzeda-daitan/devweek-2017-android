package com.vruzeda.wanikani.api;

import com.vruzeda.wanikani.api.model.BaseResponse;
import com.vruzeda.wanikani.api.model.Kana;
import com.vruzeda.wanikani.api.model.StudyQueue;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WanikaniApi {

    @GET("api/user/{apiToken}/user-information")
    Call<BaseResponse<Void>> userInfomartion(@Path("apiToken") String apiToken);

    @GET("api/user/{apiToken}/study-queue")
    Call<BaseResponse<StudyQueue>> studyQueue(@Path("apiToken") String apiToken);

    @GET("api/user/{apiToken}/radicals")
    Call<BaseResponse<List<Kana>>> getRadicalList(@Path("apiToken") String apiToken);

    @GET("api/user/{apiToken}/kanji")
    Call<BaseResponse<List<Kana>>> getKanjiList(@Path("apiToken") String apiToken);

}
