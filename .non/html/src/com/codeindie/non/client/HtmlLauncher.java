package com.deathbeam.non.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.deathbeam.non.Non;

public class HtmlLauncher extends GwtApplication {
		
    @Override
    public GwtApplicationConfiguration getConfig () {
        Non.setPlatform("Html");
        return new GwtApplicationConfiguration(800, 600);
    }

    @Override
    public ApplicationListener getApplicationListener () {
        return new Non();
    }
}
