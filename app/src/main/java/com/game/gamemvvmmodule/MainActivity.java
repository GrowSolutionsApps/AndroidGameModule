package com.game.gamemvvmmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.game.gamemodule.StaticData.Data;
import com.game.gamemodule.View.GameListActivity;

public class MainActivity extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameListActivity.class);
                intent.putExtra(Data.isAdmobNativeAdShown, "true");
                intent.putExtra(Data.isAbmobRewardedAdShown, "true");
                intent.putExtra(Data.isAbmobIntAdShown, "true");
                startActivity(intent);
            }
        });

    }
}