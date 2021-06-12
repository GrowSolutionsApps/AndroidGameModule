package com.game.gamemodule.View;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;

import com.game.gamemodule.Model.GameModel;
import com.game.gamemodule.R;
import com.game.gamemodule.Receiver.ShortcutReceiver;
import com.game.gamemodule.StaticData.Data;
import com.game.gamemodule.ViewModel.GameListViewModel;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

/*
    Created by Arti on 11/6/21
*/
public class GameListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RelativeLayout NoInternetlayout;
    static RelativeLayout relBottom;
    static ProgressBar progress;
    MaterialRippleLayout refresh_layout_click, back_rel_layout, shortcut_rel_layout;
    GridLayoutManager layoutManager;
    GameListAdapter adapter;
    ArrayList<GameModel> models = new ArrayList<>();
    GameListViewModel gameListViewModel;
    int lastCount = 0;
    String isAdmobNativeAdShown, isAbmobRewardedAdShown, isAbmobIntAdShown;
    public static ArrayList<GameModel> f30316C = new ArrayList<>();
    private int f30318b = 0;
    boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamelist);
        findViewByIds();
        init();
        getDataOnScroll();
        setAllClick();
    }

    private void setAllClick() {
        refresh_layout_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Data.isNetWorkAvailable(GameListActivity.this)) {
                    getGamesFromServer(String.valueOf(lastCount));
                } else {
                    showNetworkLay();
                }
            }
        });
        back_rel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shortcut_rel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createShortcut();
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void createShortcut() {
        if (Build.VERSION.SDK_INT < 26) {
            Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            intent.putExtra("duplicate", false);
            intent.putExtra("android.intent.extra.shortcut.NAME", "Game center");
            intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(this, R.drawable.icon_shortcut_logo));
            Intent intent2 = new Intent("android.intent.action.MAIN");
            intent2.setClass(this, GameListActivity.class);
            intent2.addCategory("android.intent.category.LAUNCHER");
            intent.putExtra("android.intent.extra.shortcut.INTENT", intent2);
            sendBroadcast(intent);
            Toast.makeText(GameListActivity.this, "Game Launcher added successfully.", 1).show();
        } else {
            ShortcutManager shortcutManager = (ShortcutManager) getSystemService("shortcut");
            if (shortcutManager != null && shortcutManager.isRequestPinShortcutSupported()) {
                Intent intent3 = new Intent(this, GameListActivity.class);
                intent3.setAction("android.intent.action.VIEW");
                ShortcutInfo build = new ShortcutInfo.Builder(this, "gamecenter").setIcon(Icon.createWithResource(this, R.drawable.icon_shortcut_logo)).setShortLabel("Game Center").setIntent(intent3).build();
                Intent intent4 = new Intent(this, ShortcutReceiver.class);
                intent4.putExtra("gameid", "111111");
                shortcutManager.requestPinShortcut(build, PendingIntent.getBroadcast(this, 0, intent4, 134217728).getIntentSender());
                Toast.makeText(GameListActivity.this, "Game Launcher added successfully.", 1).show();
            } else {
                Toast.makeText(GameListActivity.this, "Faild to add Game Launcher.", 1).show();

            }
        }
    }

    private void getDataOnScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (Data.checkAdVisible(isAdmobNativeAdShown)) {
                        models.clear();
                        models = new ArrayList<>();
                        f30318b = 0;
                    }
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            int totalItemCount = layoutManager.getItemCount();
                            int lastVisibleItem = 0;
                            lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                            int firstVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                            if ((totalItemCount - 1) <= (lastVisibleItem) && firstVisibleItemPosition >= 0) {
                                if (!gameListViewModel.getmLoding() && !gameListViewModel.getisEnded()) {
                                    getGamesFromServer(String.valueOf(lastCount));
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void init() {
        MobileAds.initialize(GameListActivity.this);
        Data.setContext(GameListActivity.this);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewCacheSize(10);
        try {
            isAdmobNativeAdShown = getIntent().getStringExtra(Data.isAdmobNativeAdShown);
//            isAdmobNativeAdShown = Data.defaultAdmobNativeAdShown;
        } catch (Exception e) {
            isAdmobNativeAdShown = Data.defaultAdmobNativeAdShown;
        }
        try {
            isAbmobRewardedAdShown = getIntent().getStringExtra(Data.isAbmobRewardedAdShown);
//            isAbmobRewardedAdShown = Data.defaultAbmobRewardedAdShown;
        } catch (Exception e) {
            isAbmobRewardedAdShown = Data.defaultAbmobRewardedAdShown;
        }
        try {
            isAbmobIntAdShown = getIntent().getStringExtra(Data.isAbmobIntAdShown);
//            isAbmobIntAdShown = Data.defaultintRewardedAdShown;
        } catch (Exception e) {
            isAbmobIntAdShown = Data.defaultintRewardedAdShown;
        }

        if (Data.checkAdVisible(isAdmobNativeAdShown)) {
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (f30316C != null) {
                        if (f30316C.get(position).getName().equals("AD")) {
                            return 2;
                        } else {
                            return 1;
                        }
                    } else {
                        return 1;
                    }
                }
            });
            adapter = new GameListAdapter(GameListActivity.this, f30316C, isAdmobNativeAdShown, isAbmobRewardedAdShown,isAbmobIntAdShown);
        } else {
            adapter = new GameListAdapter(GameListActivity.this, models, isAdmobNativeAdShown, isAbmobRewardedAdShown,isAbmobIntAdShown);
        }
        recyclerView.setAdapter(adapter);
        recyclerView.post(new Runnable() {
            public void run() {
                recyclerView.scrollToPosition(0);
            }
        });
        gameListViewModel = new ViewModelProvider(this).get(GameListViewModel.class);
        if (Data.isNetWorkAvailable(GameListActivity.this)) {
            getGamesFromServer(String.valueOf(lastCount));
        } else {
            showNetworkLay();
        }
    }

    public void getGamesFromServer(String identifier) {
        hideNetworkLay();
        if (isFirst || NoInternetlayout.getVisibility() == View.VISIBLE) {
            relBottom.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
            isFirst = false;
        } else {
            relBottom.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        }
        gameListViewModel.init(identifier, Data.game_limit);
        gameListViewModel.getGames().observe(this, new Observer<GameModel>() {
            @Override
            public void onChanged(GameModel gameModel) {
                hideProgress();
                try {
                    if (gameModel.getGameList() != null) {
                        lastCount = lastCount + gameModel.getGameList().size();
                        models.addAll(gameModel.getGameList());
                    }
                    Log.w("msg", "ModelSize== " + models.size());
                    if (Data.checkAdVisible(isAdmobNativeAdShown)) {
                        addDummy();
                    } else {
                        if (models.size() > 0) {
                            hideNetworkLay();
                            adapter.notifyItemChanged(models.size() - 1);
                        } else {
                            showNetworkLay();
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    private void findViewByIds() {
        recyclerView = findViewById(R.id.recylerview);
        NoInternetlayout = findViewById(R.id.NoInternetlayout);
        relBottom = findViewById(R.id.relBottom);
        progress = findViewById(R.id.progress);
        refresh_layout_click = findViewById(R.id.refresh_layout_click);
        back_rel_layout = findViewById(R.id.back_rel_layout);
        shortcut_rel_layout = findViewById(R.id.shortcut_rel_layout);
    }

    public void showNetworkLay() {
        NoInternetlayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public void hideNetworkLay() {
        NoInternetlayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public static void hideProgress() {
        relBottom.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
    }

    public void addDummy() {
        int j = 0;
        if (models.size() >= 6) {
            for (int i2 = 1; i2 < models.size(); i2++) {
                try {
                    if (i2 % 6 == 0) {
                        if (models.size() > i2 + j) {
                            models.add(i2 + j, new GameModel("", "AD", "", "", "", "", "", "", ""));
                            j++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        f30318b = models.size();
        mo26820l();
    }

    public void mo26820l() {
        try {
            for (int i4 = 0; i4 < this.f30318b; i4++) {
                Log.w("msg", "i4== " + i4);
                Log.w("msg", "i4== " + i4);

                f30316C.add(models.get(i4));
            }
            if (f30316C.size() <= 0) {
                showNetworkLay();
            }
            new Handler().postDelayed(new C7917f(), 1000);
        } catch (Exception unused) {
        }
    }

    public class C7917f implements Runnable {
        public C7917f() {
        }

        @SuppressLint({"WrongConstant"})
        public void run() {
            if (f30316C.size() != 0) {
                adapter.notifyItemChanged(f30316C.size() - 1);
            }
        }
    }
}