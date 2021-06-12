package com.game.gamemodule.ViewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.game.gamemodule.AdLoder.DownloadClickIntAdmobAdLoader;
import com.game.gamemodule.AdLoder.RewardedAdLoader;
import com.game.gamemodule.Model.GameModel;
import com.game.gamemodule.Repository.GameRepository;
import com.game.gamemodule.StaticData.Data;
import com.game.gamemodule.StaticData.allURL;
import com.game.gamemodule.View.GameActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

/*
    Created by Arti on 11/6/21
*/
public class GameListViewModel extends ViewModel {
    MutableLiveData<GameModel> data;
    GameRepository gameRepository;

    public GameListViewModel() {
        gameRepository = new GameRepository();
    }

    public MutableLiveData<GameModel> getGames() {
        return this.data;
    }

    public void init(String identifier, String pack_limit) {
        data = gameRepository.getGames(identifier, pack_limit);
    }

    public boolean getmLoding() {
        return gameRepository.mLoading;
    }

    public boolean getisEnded() {
        return gameRepository.isEnded;
    }

    public void setGamePlay(int pos, ArrayList<GameModel> gameModels, Context context) {
        if (Data.isNetWorkAvailable(context)) {
            String id = gameModels.get(pos).getId();
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("id", id);
            client.post(allURL.SetGame_Url, params, new JsonHttpResponseHandler() {
            });
        }
    }

    public void gamePlay(Context context, int position, ArrayList<GameModel> gameModels, String isAbmobIntAdShown) {
        if (context != null) {
            if (isAbmobIntAdShown.equals("true")) {
                if (DownloadClickIntAdmobAdLoader.isAdLoaded(context)) {
                    DownloadClickIntAdmobAdLoader.showAd(context, new DownloadClickIntAdmobAdLoader.adfinish() {
                        @Override
                        public void adfinished() {
                            DownloadClickIntAdmobAdLoader.loadAd(context);
                            if (Data.isNetWorkAvailable(context)) {
                                Intent i = new Intent(context, GameActivity.class);
                                i.putExtra("orientation", gameModels.get(position).getScreenOrientation());
                                i.putExtra("game_link", gameModels.get(position).getLink());
                                context.startActivity(i);
                            } else {
                                Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    if (Data.isNetWorkAvailable(context)) {
                        Intent i = new Intent(context, GameActivity.class);
                        i.putExtra("orientation", gameModels.get(position).getScreenOrientation());
                        i.putExtra("game_link", gameModels.get(position).getLink());
                        context.startActivity(i);
                    } else {
                        Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (Data.isNetWorkAvailable(context)) {
                    Intent i = new Intent(context, GameActivity.class);
                    i.putExtra("orientation", gameModels.get(position).getScreenOrientation());
                    i.putExtra("game_link", gameModels.get(position).getLink());
                    context.startActivity(i);
                } else {
                    Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public void gameClick(Context context, ArrayList<GameModel> gameModels, int position, String isAbmobRewardedAdShown, String isAbmobIntAdShown) {
        if (gameModels.get(position).getIsLock().matches("1") && isAbmobRewardedAdShown.equals("true")) {
            if (RewardedAdLoader.isAdLoaded(context)) {
                RewardedAdLoader.showVideoAd(context, new RewardedAdLoader.mCallBack() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void done(boolean isReworded) {
                        if (isReworded) {
                            gamePlay(context, position, gameModels, isAbmobIntAdShown);
                            setGamePlay(position, gameModels, context);
                        } else {
                            Toast.makeText(context, "To unlock please watch full video ad!", 1).show();
                        }
                    }
                });
            } else {
                if (gameModels.get(position).getIsAd().equalsIgnoreCase("0")) {
                    gamePlay(context, position, gameModels, isAbmobIntAdShown);
                    setGamePlay(position, gameModels, context);
                } else {
                    String url = "";
                    url = gameModels.get(position).getLink();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);
                }
            }
        } else {
            if (gameModels.get(position).getIsAd().equalsIgnoreCase("0")) {
                gamePlay(context, position, gameModels, isAbmobIntAdShown);
                setGamePlay(position, gameModels, context);
            } else {
                String url = "";
                url = gameModels.get(position).getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        }
    }
}
