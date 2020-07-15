package com.team7.cs401.filestorage.server;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String type;
    protected String status;
    protected String text1;
    protected String text2;
    protected String text3;
    
    // ideas for files and list of users:
    protected String[] textArray;
    protected byte[] file;

    public Message() {
        this.type = "Undefined";
        this.status = "Undefined";
        this.text1 = "Undefined";
    }

    public Message(String type, String status, String text){
        setType(type);
        setStatus(status);
        setText1(text);
    }
    
    public Message(String type, String status, String text, String text2){
        setType(type);
        setStatus(status);
        setText1(text);
        setText2(text2);
    }
    public Message(String type, String status, String text, String text2, String text3){
        setType(type);
        setStatus(status);
        setText1(text);
        setText2(text2);
        setText3(text3);
    }

    private void setType(String type){
    	this.type = type;
    }

    public void setStatus(String status){
    	this.status = status;
    }

    public void setText1(String text){
    	this.text1 = text;
    }
    
    public void setText2(String text){
    	this.text2 = text;
    }
    
    public void setText3(String text){
    	this.text3 = text;
    }

    public String getType(){
    	return type;
    }

    public String getStatus(){
    	return status;
    }

    public String getText1(){
    	return text1;
    }
    
    public String getText2(){
    	return text2;
    }
    
    public String getText3(){
    	return text3;
    }

}

