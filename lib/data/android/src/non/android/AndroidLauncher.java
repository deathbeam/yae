package non.android;

import android.os.Bundle;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.multidex.MultiDex;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import non.Non;

public class AndroidLauncher extends AndroidApplication {
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        Non non = new Non();
        try {
            String apkName = getContext().getPackageManager().getApplicationInfo(getContext().getPackageName(), 0).sourceDir;
            non.setLoadPath("file:" + apkName + "!/assets");
        } catch (NameNotFoundException e) {}
        initialize(non, config);
    }
    
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this); 
    }
}