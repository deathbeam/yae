package %PACKAGE%.ios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;
import org.yaml.snakeyaml.Yaml;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import non.NonVM;

public class IOSLauncher extends IOSApplication.Delegate {
    @SuppressWarnings("unchecked")
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration cfg = new IOSApplicationConfiguration();
        
        try {
            Map config = (Map<String, Object>)new Yaml().load(new FileInputStream(new File("non/config.yml")));
            return new IOSApplication(new NonVM(config), cfg);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
            return null;
        }
    }

    public static void main(String[] args) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(args, null, IOSLauncher.class);
        pool.close();
    }
}