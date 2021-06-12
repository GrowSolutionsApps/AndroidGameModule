package com.game.gamemodule.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.game.gamemodule.AdLoder.NativeAdLoader;
import com.game.gamemodule.Model.GameModel;
import com.game.gamemodule.R;
import com.game.gamemodule.StaticData.Data;
import com.game.gamemodule.ViewModel.GameListViewModel;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.util.ArrayList;

/*
    Created by Arti on 11/6/21
*/
public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {
    private final GameListViewModel gameListViewModel;
    Context context;
    ArrayList<GameModel> gameModels;
    LayoutInflater inflater;
    String isNativeAdShown, isAbmobRewardedAdShown, isAbmobIntAdShown;
    ViewGroup f31170n;


    public GameListAdapter(Context context, ArrayList<GameModel> gameModels, String isNativeAdShown, String isAbmobRewardedAdShown, String isAbmobIntAdShown) {
        this.context = context;
        this.gameModels = gameModels;
        this.isNativeAdShown = isNativeAdShown;
        this.isAbmobRewardedAdShown = isAbmobRewardedAdShown;
        this.isAbmobIntAdShown = isAbmobIntAdShown;
        this.inflater = LayoutInflater.from(context);
        gameListViewModel = new ViewModelProvider((FragmentActivity) context).get(GameListViewModel.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        f31170n = parent;
        View view = inflater.inflate(R.layout.item_game, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (Data.checkAdVisible(isNativeAdShown) && gameModels.get(position).getName().equals("AD")) {
            holder.admob_native_main_layout.setVisibility(View.VISIBLE);
            holder.cardview.setVisibility(View.GONE);
            String admob_native = context.getString(R.string.admob_native_id);
            NativeAdLoader.mo32103a(admob_native, holder.f31228Q,
                    (UnifiedNativeAdView) inflater.inflate(R.layout.admob_native1, this.f31170n, false));
        } else {
            setAdapterView(holder, position);
        }
    }

    private void setAdapterView(ViewHolder holder, int position) {
        holder.admob_native_main_layout.setVisibility(View.GONE);
        holder.cardview.setVisibility(View.VISIBLE);
        GameModel gameModel = gameModels.get(position);
        Glide.with(context).load(gameModel.getPreview()).into(holder.image);
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("msg", "cardView== " + gameModel.getIsAd().equalsIgnoreCase("0"));
                Log.w("msg", "cardView== " + gameModels.get(position).getIsLock());
                gameListViewModel.gameClick(context,gameModels,position,isAbmobRewardedAdShown,isAbmobIntAdShown);
            }
        });
    }


    @Override
    public int getItemCount() {
        return gameModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        CardView cardview;
        RelativeLayout admob_native_main_layout;
        public FrameLayout f31228Q;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            cardview = itemView.findViewById(R.id.cardview);
            admob_native_main_layout = itemView.findViewById(R.id.admob_native_main_layout);
            f31228Q = itemView.findViewById(R.id.fl_adplaceholder);
        }
    }
}
