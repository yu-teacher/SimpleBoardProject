package com.example.board.util;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class TimeChange {

    static final int SEC = 60;
    static final int MIN = 60;
    static final int HOUR = 24;
    static final int DAY = 30;
    static final int MONTH = 12;

    public static String dateChangeString(LocalDateTime date) {
        long curTime = System.currentTimeMillis();
        Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
        Date time = Date.from(instant);
        long regTime = time.getTime();
        long diffTime = (curTime - regTime) / 1000;
        String msg = null;
        if (diffTime < SEC) {
            msg = diffTime + "초 전";
        } else if ((diffTime /= SEC) < MIN) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= MIN) < HOUR) {
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= HOUR) < DAY) {
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= DAY) < MONTH) {
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime) + "년 전";
        }
        return msg;
    }
}