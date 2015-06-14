package com.swerve.chill;

import android.app.Application;
import com.swerve.chill.model.remote.Message;
import com.swerve.chill.model.remote.User;
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
