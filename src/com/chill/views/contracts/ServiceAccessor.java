package com.chill.views.contracts;

import com.chill.model.remote.Message;
import com.chill.model.remote.User;

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
