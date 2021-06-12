package com.game.gamemodule.Network;


import com.game.gamemodule.Model.GameModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Arti on 8/6/21.
 */
public interface APIInterface {
    @FormUrlEncoded
    @POST("FontKeyboard/getFontGames2.php")
    Call<GameModel> getGames(@Field("identifier") String identifier, @Field("pack_limit") String pack_limit);
}
