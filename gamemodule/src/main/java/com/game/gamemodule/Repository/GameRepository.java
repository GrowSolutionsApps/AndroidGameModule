package com.game.gamemodule.Repository;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.game.gamemodule.Model.GameModel;
import com.game.gamemodule.Network.APIInterface;
import com.game.gamemodule.Network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Arti on 11/6/21.
 */

public class GameRepository {

    private APIInterface apiInterface;
    public boolean mLoading = false;
    public boolean isEnded = false;

    public GameRepository() {
    }

    public MutableLiveData<GameModel> getGames(String identifier, String pack_limit) {
        Log.w("msg", "identifier_game== " + identifier);
        Log.w("msg", "pack_limit_game== " + pack_limit);

        final MutableLiveData<GameModel> data = new MutableLiveData<>();
        mLoading = true;
        apiInterface = ApiClient.getClientAuthentication().create(APIInterface.class);
        Call<GameModel> call = apiInterface.getGames(identifier, pack_limit);
        call.enqueue(new Callback<GameModel>() {
            @Override
            public void onResponse(Call<GameModel> call, Response<GameModel> response) {
                Log.w("msg", "onResponse== " + response.body().getGameList().size());
                mLoading = false;
                if (response.body().getGameList().size() > 0) {
                    isEnded = false;
                } else {
                    isEnded = true;
                    data.setValue(null);
                    return;
                }
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GameModel> call, Throwable t) {
                Log.w("msg", "onFailure== " + t.getMessage());
                data.setValue(null);
            }
        });

        return data;
    }
}
