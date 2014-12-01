package com.codeindie.non.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.codeindie.non.Non;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Non.setPlatform("Android");
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Non(), config);
    }
}