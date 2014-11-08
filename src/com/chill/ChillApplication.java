package com.chill;

import android.app.Application;
import android.util.Log;
import com.chill.main.MainActivity;
import com.chill.model.remote.Message;
import com.chill.model.remote.User;
import com.parse.*;

public class ChillApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        // Register Model subclasses
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Message.class);

        Parse.initialize(this, "32ACrsiE3UCDYlICgPFW374CcetCTlGD6RwC07MD", "BFKHUzspoCr4UNKCkMVyCVRoqrRUuPtgX2UnRToB");
    }
}
