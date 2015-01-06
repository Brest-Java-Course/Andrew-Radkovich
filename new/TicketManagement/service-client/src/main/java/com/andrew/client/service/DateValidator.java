package com.andrew.client.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by andrew on 18.12.14.
 */
public class DateValidator {

    public static boolean isValidDate(String date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(date);
        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }
}
