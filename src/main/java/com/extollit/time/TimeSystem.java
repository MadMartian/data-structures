package com.extollit.time;

import com.extollit.collect.RingBuffer;
import com.extollit.temporal.Duration;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;

public class TimeSystem {
    private static final Logger logger = LoggerFactory.getLogger("Time System");

    private static final TimeSystem INSTANCE;
    private static final int CONFIGURED_OFFSET = configuredOffset();

    private final NTPClientBackground ntp;

    private volatile long offset;

    static {
        TimeSystem instance = null;
        try {
            instance = acquire();
        } catch (IOException e) {
            logger.error("Could not initialize time system, will use configured fallback", e);
        }

        INSTANCE = instance;
    }

    private TimeSystem(int period, String... ntpHosts) {
        this.ntp = new NTPClientBackground(period, ntpHosts);
    }

    private final class NTPClientBackground extends Thread {
        private final RingBuffer<String> hostNames;
        private final NTPUDPClient client = new NTPUDPClient();
        private final int sleep;

        private NTPClientBackground(int period, String... hostNames) {
            super("NTP Sync");
            this.sleep = period * 1000;
            this.hostNames = new RingBuffer<String>(hostNames.length);
            Collections.addAll(this.hostNames, hostNames);
        }

        @Override
        public void run() {
            offset = CONFIGURED_OFFSET;

            if (this.hostNames.isEmpty())
                logger.error("Time system shutting down, no NTP hosts configured");

            try {
                do {
                    for (String hostName : this.hostNames) {
                        try {
                            final InetAddress host = InetAddress.getByName(hostName);
                            final TimeInfo time = this.client.getTime(host);
                            time.computeDetails();
                            final Long offset = time.getOffset();
                            if (offset == null)
                                throw new IllegalArgumentException("Missing time offset");

                            TimeSystem.this.offset = offset;
                        } catch (UnknownHostException e) {
                            logger.warn("Unable to resolve host {}, skipping", hostName);
                        } catch (IOException e) {
                            logger.warn("I/O error from host {}, skipping - {}", hostName, e.getMessage());
                        } catch (IllegalArgumentException e) {
                            logger.error("Unsatisfied condition, illegal argument computing time delta from host {}, skipping - {}", hostName, e.getMessage());
                        }

                        sleep(this.sleep);
                    }
                } while (true);
            } catch (InterruptedException e) {
                logger.warn("Time system shutting down, interrupted");
            }
        }

        public void stopIt() {
            interrupt();
        }
    }

    public static void start() {
        if (INSTANCE != null) {
            logger.info("Time system starting up with hosts {}", INSTANCE.ntp.hostNames);
            INSTANCE.ntp.start();
        } else
            logger.error("Cannot start NTP background worker, failed to initialize");
    }

    public static void stop() {
        if (INSTANCE != null)
            INSTANCE.ntp.stopIt();
    }

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

    private static TimeSystem acquire() throws IOException {
        final Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("ntp.properties"));
        final String template = "%s.ntp.%s";
        final String namespace = TimeSystem.class.getPackage().getName();
        final String[] hostNames = props.getProperty(String.format(template, namespace, "hosts"), "").split("\\s*,\\s*");
        int period = 60;

        try {
            period = Integer.parseInt(props.getProperty(String.format(template, namespace, "period"), "60"));
        } catch (NumberFormatException e) {
            logger.error("Invalid numeric format for period ({}), using default of one minute instead", e.getMessage());
        }
        return new TimeSystem(period, hostNames);
    }

    private static long getOffset() {
        return INSTANCE == null ? CONFIGURED_OFFSET : INSTANCE.offset;
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis() + getOffset();
    }

    public static Calendar createCalendar() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, (int)getOffset());
        return calendar;
    }

    public static Date addDate(Duration duration) {
        return new Date(currentTimeMillis() + duration.millis());
    }

    public static Date createDate(long millis) {
        return new Date(millis + getOffset());
    }
    public static Date createDate() {
        return new Date(currentTimeMillis());
    }
}
