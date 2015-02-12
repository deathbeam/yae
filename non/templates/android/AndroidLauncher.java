package %PACKAGE%.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import non.Non;
import non.rhino.RhinoNon;

public class AndroidLauncher extends AndroidApplication {
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Non().setHandler(new RhinoNon()), config);
    }
}