package com.codeindie.non.android;

import java.io.IOException;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.codeindie.non.Game;
import com.codeindie.non.Utils;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        try {
            initialize(new Game(Utils.configure()), config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
