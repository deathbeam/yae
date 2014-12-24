package non.web;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import non.Non;

public class WebLauncher extends GwtApplication {
		
    @Override
    public GwtApplicationConfiguration getConfig () {
        Non.setPlatform("Web");
        return new GwtApplicationConfiguration(800, 600);
    }

    @Override
    public ApplicationListener getApplicationListener () {
        return new Non();
    }
}
