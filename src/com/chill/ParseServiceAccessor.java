package com.chill;

import com.chill.model.remote.Message;
import com.chill.model.remote.User;
import com.chill.views.contracts.ServiceAccessor;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ParseServiceAccessor implements ServiceAccessor{
    private final PushNotificationSender mSender;

    public ParseServiceAccessor() {
        mSender = new PushNotificationSender();
    }

    @Override
    public boolean login(final String username, final String password) {
        try {
            final User user = (User) ParseUser.logIn(username, password);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public boolean register(final String username, final String password){
        try {
            final User user = new User(username, password);
            user.signUp();
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public List<Message> getInbox(){
        try {
            return ((User) User.getCurrentUser()).getInbox();
        } catch (ParseException e) {
            return new ArrayList<Message>();
        }
    }

    @Override
    public List<Message> getOutbox(){
        try {
            return ((User) User.getCurrentUser()).getOutbox();
        } catch (ParseException e) {
            return new ArrayList<Message>();
        }
    }

    @Override
    public List<User> getFriends(){
        try {
            return ((User) User.getCurrentUser()).getFriends();
        } catch (ParseException e) {
            return new ArrayList<User>();
        }
    }

    @Override
    public boolean doesUsernameExist(final String username) {
        try {
            return User.doesUsernameExist(username);
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public User addFriend(final String username) {
        try {
            return ((User)User.getCurrentUser()).addFriend(username);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public void setMessageStatus(final Message message, final String status) {
        message.setStatus(status);
        final User from = message.getFrom();
        final User to = message.getTo();
        message.saveInBackground();
        try {
            if (Message.STATUS_ACCEPTED.equals(status)) {
                mSender.push(from, to.getUsername() + " is down to chill.");
            } else {
                mSender.push(message.getFrom(), to.getUsername() + " doesn't want to chill right now.");
            }
        } catch (ParseException e) {
        }
    }

    @Override
    public User getCurrentUser() {
        return (User) User.getCurrentUser();
    }

    @Override
    public void sendMessage(final User friend, final String chillId, final String location, final String date){
        final Message message = new Message((User)User.getCurrentUser(),
                                            friend,
                                            location,
                                            date,
                                            chillId,
                                            Message.STATUS_PENDING
                                            );
        try {
            message.save();
            mSender.push(friend, User.getCurrentUser().getUsername() + " wants to take a chill.");
        } catch (ParseException e) {
        }
    }
}
