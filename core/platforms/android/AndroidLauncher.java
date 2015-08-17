package %PACKAGE%.android;

import java.util.Map;
import java.io.InputStream;
import java.io.IOException;
import org.yaml.snakeyaml.Yaml;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import yae.YaeVM;

public class AndroidLauncher extends AndroidApplication {
    @SuppressWarnings("unchecked")
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();

        try {
            Map config = (Map<String, Object>)new Yaml().load(getAssets().open("yae/project.yml"));
            initialize(new YaeVM(config), cfg);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
