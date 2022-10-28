package ch.lucas.bot.postauto.utils;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeFormatterTest {
    // Test minutes
    @Test
    public void testConvertSecondsToTimeWith1MinFR() {
        assertEquals("1 minute", TimeFormatter.convertSecondsToTime(60, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith10MinsFR() {
        assertEquals("10 minutes", TimeFormatter.convertSecondsToTime(600, Locale.FRENCH));
    }

    // Test hours
    @Test
    public void testConvertSecondsToTimeWith1HourFR() {
        assertEquals("1 heure", TimeFormatter.convertSecondsToTime(3600, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith1Hour1MinFR() {
        assertEquals("1 heure, 1 minute", TimeFormatter.convertSecondsToTime(3660, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith1Hour30MinsFR() {
        assertEquals("1 heure, 30 minutes", TimeFormatter.convertSecondsToTime(5400, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith2HoursFR() {
        assertEquals("2 heures", TimeFormatter.convertSecondsToTime(7200, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith2Hours1MinFR() {
        assertEquals("2 heures, 1 minute", TimeFormatter.convertSecondsToTime(7260, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith2Hours6MinsFR() {
        assertEquals("2 heures, 6 minutes", TimeFormatter.convertSecondsToTime(7560, Locale.FRENCH));
    }

    // Test days
    @Test
    public void testConvertSecondsToTimeWith1DayFR() {
        assertEquals("1 jour", TimeFormatter.convertSecondsToTime(86400, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith1Day1MinsFR() {
        assertEquals("1 jour, 1 minute", TimeFormatter.convertSecondsToTime(86460, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith1Day2MinsFR() {
        assertEquals("1 jour, 2 minutes", TimeFormatter.convertSecondsToTime(86520, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith1DayAnd1Hours1MinsFR() {
        assertEquals("1 jour, 1 heure, 1 minute", TimeFormatter.convertSecondsToTime(90060, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith1DayAnd2Hours2MinsFR() {
        assertEquals("1 jour, 2 heures, 2 minutes", TimeFormatter.convertSecondsToTime(93720, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith2DaysFR() {
        assertEquals("2 jours", TimeFormatter.convertSecondsToTime(172800, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith2Days1HourFR() {
        assertEquals("2 jours, 1 heure", TimeFormatter.convertSecondsToTime(176400, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith2Days2HoursFR() {
        assertEquals("2 jours, 2 heures", TimeFormatter.convertSecondsToTime(180000, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith2Days1Hour1MinsFR() {
        assertEquals("2 jours, 1 heure, 1 minute", TimeFormatter.convertSecondsToTime(176460, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith2Days1Hour2MinsFR() {
        assertEquals("2 jours, 1 heure, 2 minutes", TimeFormatter.convertSecondsToTime(176520, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith2Days2Hours1MinsFR() {
        assertEquals("2 jours, 2 heures, 1 minute", TimeFormatter.convertSecondsToTime(180060, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTimeWith2Days2Hours2MinsFR() {
        assertEquals("2 jours, 2 heures, 2 minutes", TimeFormatter.convertSecondsToTime(180120, Locale.FRENCH));
    }

    // Test months
    @Test
    public void testConvertSecondsToTime1MonthFR() {
        assertEquals("1 mois", TimeFormatter.convertSecondsToTime(2592000, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTime1Month1DayFR() {
        assertEquals("1 mois, 1 jour", TimeFormatter.convertSecondsToTime(2678400, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTime1Month1HourFR() {
        assertEquals("1 mois, 1 heure", TimeFormatter.convertSecondsToTime(2595600, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTime1Month1Day1HourFR() {
        assertEquals("1 mois, 1 jour, 1 heure", TimeFormatter.convertSecondsToTime(2682000, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTime2MonthsFR() {
        assertEquals("2 mois", TimeFormatter.convertSecondsToTime(5184000, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTime2Months2DaysFR() {
        assertEquals("2 mois, 2 jours", TimeFormatter.convertSecondsToTime(5356800, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTime2Months2HoursFR() {
        assertEquals("2 mois, 2 heures", TimeFormatter.convertSecondsToTime(5191200, Locale.FRENCH));
    }

    @Test
    public void testConvertSecondsToTime2Months2Days2HoursFR() {
        assertEquals("2 mois, 2 jours, 2 heures", TimeFormatter.convertSecondsToTime(5364000, Locale.FRENCH));
    }

    // DE Language test

    @Test
    public void testConvertSecondsToTimeWith2DaysAnd2Hours2MinsDE() {
        assertEquals("2 Tage, 2 Stunden, 2 Minuten", TimeFormatter.convertSecondsToTime(180120, Locale.GERMAN));
    }

    @Test
    public void testConvertSecondsToTimeWith1DayAnd1Hour1MinDE() {
        assertEquals("1 Tag, 1 Stunde, 1 Minute", TimeFormatter.convertSecondsToTime(90060, Locale.GERMAN));
    }

    @Test
    public void testConvertSecondsToTime2Months2Days2HoursDE() {
        assertEquals("2 Monate, 2 Tage, 2 Stunden", TimeFormatter.convertSecondsToTime(5364000, Locale.GERMAN));
    }

    @Test
    public void testConvertSecondsToTime1Month2Days2HoursDE() {
        assertEquals("1 Monat, 2 Tage, 2 Stunden", TimeFormatter.convertSecondsToTime(2772000, Locale.GERMAN));
    }

    // Unsupported language
    @Test
    public void testConvertSecondsToTimeWith1DayAnd2Hours2MinsUnsupportedLanguage() {
        assertEquals("1 , 2 , 2 ", TimeFormatter.convertSecondsToTime(93720, Locale.CHINESE));
    }

    @Test
    public void testConvertSecondsToTime2Months2Days2HoursUnsupportedLanguage() {
        assertEquals("2 , 2 , 2 ", TimeFormatter.convertSecondsToTime(5364000, Locale.CHINESE));
    }
}
