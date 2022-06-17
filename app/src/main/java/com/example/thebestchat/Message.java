package com.example.thebestchat;

public class Message {
    public String messageText;
    public String messageUser;
    public long messageTime;

    public String getMessageText() {
        return messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }


    public Message() {

    }

    public Message(String messageText, String messageUser, int messageTime) {
        this.messageText=messageText;
        this.messageUser=messageUser;
        this.messageTime=messageTime;

    }
}
