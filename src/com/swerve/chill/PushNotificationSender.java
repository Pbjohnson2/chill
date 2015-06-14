package com.swerve.chill;

import com.swerve.chill.model.remote.User;
import com.parse.ParseException;
import com.parse.ParsePush;

public class PushNotificationSender {

    public void push(final User user, final String message) throws ParseException {
        final ParsePush push = new ParsePush();
        push.setChannel(user.getUsername());
        push.setMessage(message);
        push.send();
    }
}
