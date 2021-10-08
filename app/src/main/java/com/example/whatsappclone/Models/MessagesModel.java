package com.example.whatsappclone.Models;

public class MessagesModel {
    String Uid,message;
    Long timestamp;

    public MessagesModel(String uid, String message) {
        Uid = uid;
        this.message = message;
    }

    public MessagesModel() {}

    public MessagesModel(String uid, String message, Long timestamp) {
        Uid = uid;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
