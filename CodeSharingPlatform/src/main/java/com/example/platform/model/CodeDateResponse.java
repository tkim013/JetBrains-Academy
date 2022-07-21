package com.example.platform.model;

import com.example.platform.entity.Code;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class CodeDateResponse {

    private final String DATE_FORMATTER= "yyyy/MM/dd HH:mm:ss";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

    private String code;
    private String date;
    private int time;
    private int views;

    public CodeDateResponse() {

    }

    public CodeDateResponse(String code, String date, int time, int views) {
        this.code = code;
        this.date = date;
        this.time = time;
        this.views = views;
    }

    public CodeDateResponse(Code code) {
        this.code = code.getCode();
        this.date = code.getDate().format(formatter);
        this.time = code.getTime() > 0 ? (int) (code.getTime() - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) : 0;
        this.views = code.getViews();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
