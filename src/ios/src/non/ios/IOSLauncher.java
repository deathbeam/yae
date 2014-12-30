package non.ios;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import non.Non;

public class IOSLauncher extends IOSApplication.Delegate {
    protected IOSApplication createApplication() {
        Non.setPlatform("IOS");
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        return new IOSApplication(new Non(), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
}
