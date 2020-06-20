package com.extollit.time;

import com.extollit.temporal.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

public class TimeSystem {
    private static final Logger logger = LogManager.getLogger("Time System");
    private static final int OFFSET = configuredOffset();

    private static int configuredOffset() {
        final String property = System.getProperty(TimeSystem.class.getPackage().getName() + ".offset");
        if (property == null)
            return 0;

        try {
            return Integer.parseInt(property);
        } catch (NumberFormatException e) {
            logger.error("Wrong format for time offset: {}", property);
            return 0;
        }
    }

    public static final long currentTimeMillis() {
        return System.currentTimeMillis() + OFFSET;
    }

    public static final Calendar createCalendar() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, OFFSET);
        return calendar;
    }

    public static final Date addDate(Duration duration) {
        return new Date(currentTimeMillis() + duration.millis());
    }

    public static final Date createDate(long millis) {
        return new Date(millis + OFFSET);
    }
    public static final Date createDate() {
        return new Date(currentTimeMillis());
    }
}
