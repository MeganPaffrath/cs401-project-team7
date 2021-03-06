package com.team7.cs401.filestorage.client;

import java.io.File;
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
    }

    public Message(String type, String status, String text){
    	this.type = type;
    	this.status = status;
    	this.text1 = text;
    }
    
    public Message(String type, String status, String text1, String text2){
    	this.type = type;
    	this.status = status;
    	this.text1 = text1;
    	this.text2 = text2;
    }
    public Message(String type, String status, String text1, String text2, String text3){
    	this.type = type;
    	this.status = status;
    	this.text1 = text1;
    	this.text2 = text2;
    	this.text3 = text3;
    }
    public Message(String type, String status, String text1, byte[] file){
    	this.type = type;
    	this.status = status;
    	this.text1 = text1;
    	this.file = file;
    }
    
    public Message(String type, String status, String text1, String[] textArray){
    	this.type = type;
    	this.status = status;
    	this.text1 = text1;
    	this.textArray = textArray;
    }
    
    public Message(String type, String status, String text1, String text2, byte[] file){
    	this.type = type;
    	this.status = status;
    	this.text1 = text1;
    	this.text2 = text2;
    	this.file = file;
    }
    
    public Message(String type, String status, String text1, String text2, String text3, byte[] file){
    	this.type = type;
    	this.status = status;
    	this.text1 = text1;
    	this.text2 = text2;
    	this.text3 = text3;
    	this.file = file;
    }
    
    public Message(String type, String status, String text1, String text2, String[] textArr){
    	this.type = type;
    	this.status = status;
    	this.text1 = text1;
    	this.text2 = text2;
    	this.textArray = textArr;
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
    
    public byte[] getFileBytes() {
    	return file;
    }
    
    public String getTextArray() {
    	String out = "";
    	for (String i : textArray) {
    		out += i + " ";
    	}
    	
    	return out;
    }
    
    public String[] getTextArrayFull() {
    	return textArray;
    }
}


