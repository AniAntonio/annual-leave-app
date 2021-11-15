package com.lhind.util;

import com.lhind.exception.InputException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public static Date today() {
        return Date.from(
                LocalDate.now()
                        .atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
    }

    public static Date beforeToday(int days) {
        return Date.from(
                LocalDate.now()
                        .atStartOfDay().minusDays(days)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
    }

    public static Date addDays(Date startDate, int days) {

        LocalDate localDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        ;
        return Date.from(
                localDate
                        .atStartOfDay().plusDays(days - 1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
    }

    public static Date convertStringDateToLocalDate(String date) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(date);
        } catch (Exception ex) {
            throw new InputException(BadRequest.WRONG_FORMAT);
        }
    }
}
