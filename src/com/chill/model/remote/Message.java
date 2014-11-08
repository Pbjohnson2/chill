package com.chill.model.remote;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject{
    public static final String STATUS_ACCEPTED = "accepted";
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_DECLINED = "declined";


    public Message (){
    }

    public Message (final User from,
                    final User to,
                    final String location,
                    final String time,
                    final String chillId,
                    final String status) {
        setFrom(from);
        setTo(to);
        setLocation(location);
        setTime(time);
        setChillId(chillId);
        setStatus(status);
    }

    private void setFrom (final User from) {
        put("from", from);
    }

    private void setTo (final User to) {
        put("to", to);
    }

    public void setLocation (final String location) {
        put("location", location);
    }

    public void setTime (final String time) {
        put("time", time);
    }

    public void setChillId(final String chillId) {
        put("chillId", chillId);
    }

    public void setStatus (final String status) {
        put("status", status);
    }

    public User getFrom (){
        return (User) get("from");
    }

    public User getTo (){
        return (User) get("to");
    }

    public String getLocation (){
        return (String) get("location");
    }

    public String getTime (){
        return (String) get("time");
    }

    public String getChillId(){
        return (String) get("chillId");
    }

    public String getStatus (){
        return (String) get("status");
    }
}
