package com.binartest.challenge8.model.movie

import com.google.gson.annotations.SerializedName

data class GetNowPlayingResponse(
    @SerializedName("results")
    val results:List<GetNowPlayingResponseItem>,
    @SerializedName("total_results")
    val totalData:Int
)