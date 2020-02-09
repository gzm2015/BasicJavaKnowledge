package java8;

import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 时间测试
 *
 * @author gzm2015
 * @create 2018-05-24-22:12
 */
public class TimeTest {


    Clock clock = Clock.systemDefaultZone();

    @Test
    public void clock() {
        System.out.println(clock.getZone());
        System.out.println(Instant.now(clock).atZone(clock.getZone()));
        Clock clock1 = clock.withZone(ZoneId.of("Asia/Kolkata"));
        System.out.println(Instant.now(clock1).atZone(clock1.getZone()));

        System.out.println(clock.millis());
        System.out.println(Date.from(clock.instant()));
    }

    @Test
    public void localTime() {
        LocalTime time = LocalTime.now();
        System.out.println(time);
        Clock clock1 = clock.withZone(ZoneId.of("Asia/Kolkata"));
        LocalTime time2 = LocalTime.now(clock1);
        System.out.println(time2);

        //plusHours(long hoursToAdd)
        //plusMinutes(long minutesToAdd)
        //plusSeconds(long secondstoAdd)
        long bet = ChronoUnit.HOURS.between(time, time2);
        System.out.println(bet);


        LocalTime late = LocalTime.of(23, 59, 59);
        System.out.println(late);       // 23:59:59

        LocalTime tomo = late.plusHours(1);


        System.out.println(tomo);
    }

}
