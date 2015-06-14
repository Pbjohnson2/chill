package com.swerve.chill.views.contracts;

import com.swerve.chill.model.remote.Message;
import com.swerve.chill.model.remote.User;

import java.util.List;

public interface ServiceAccessor {
    boolean login(final String username, final String password);
    boolean register(final String username, final String password);

    List<Message> getInbox();
    List<Message> getOutbox();
    List<User> getFriends();

    boolean doesUsernameExist(final String username);
    User addFriend(final String username);
    void setMessageStatus(final Message message, final String status);
    User getCurrentUser();
    void sendMessage(final User friend, final String chillId, final String location, final String date);
}
