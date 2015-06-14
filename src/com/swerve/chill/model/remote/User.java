package com.swerve.chill.model.remote;

import com.parse.*;

import java.util.List;

@ParseClassName("_User")
public class   User extends ParseUser{


    public User(){

    }
    public User(final String username, final String password){
        setUsername(username);
        setPassword(password);
    }

    public String getUsername(){
        return getString("username");
    }

    public List<User> getFriends() throws ParseException {
        final ParseRelation<User> memberRelation = this.getRelation("friends");
        final ParseQuery<User> friendQuery = memberRelation.getQuery();

        friendQuery.orderByDescending("lastName");
        return friendQuery.find();
    }

    public List<Message> getInbox() throws ParseException {
        ParseQuery<Message>  inboxQuery = getDefaultMessageQuery();
        inboxQuery.include("from");
        inboxQuery.include("to");
        inboxQuery.whereEqualTo("to", this);
        inboxQuery.setLimit(15);
        return inboxQuery.find();
    }

    public List<Message> getOutbox() throws ParseException {
        ParseQuery<Message>  outboxQuery = getDefaultMessageQuery();
        outboxQuery.include("to");
        outboxQuery.whereEqualTo("from", this);
        outboxQuery.setLimit(15);
        return outboxQuery.find();
    }

    private ParseQuery<Message> getDefaultMessageQuery() {
        ParseQuery<Message>  messageQuery = ParseQuery.getQuery(Message.class);
        messageQuery.orderByDescending("_created_at");
        return messageQuery;
    }

    public User addFriend(String friend) throws ParseException {
        if (getUsername().equals(friend)) {
            return null;
        }
        ParseQuery<User>  userQuery = ParseQuery.getQuery(User.class);
        userQuery.whereEqualTo("username", friend);
        final List<User> users = userQuery.find();
        if (users == null) {
            return null;
        }
        if (users.size() < 1) {
            return null;
        }
        final User user = userQuery.find().get(0);
        ParseRelation<User> friendRelation = this.getRelation("friends");
        friendRelation.add(user);
        save();
        return user;
    }

    public static final boolean doesUsernameExist(final String username) throws ParseException {
        ParseQuery<User>  userQuery = ParseQuery.getQuery(User.class);
        userQuery.whereEqualTo("username", username);
        List<User> users = userQuery.find();
        if (users == null) {
            return false;
        }
        return users.size() > 0;
    }
}
