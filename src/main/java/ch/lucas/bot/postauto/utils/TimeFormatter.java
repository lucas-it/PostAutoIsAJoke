package ch.lucas.bot.postauto.utils;

import java.util.Locale;

/**
 * Utility class for the time.
 *
 * @author Lucas-it@github
 */
public class TimeFormatter {
    private TimeFormatter() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Convert seconds to a formatted time.
     * 60 seconds become '1 minute'
     * 1200 seconds become '20 minutes'
     * 7260 secondes become '2 heures, 1 minute'
     * @param seconds the seconds to convert
     * @return the formatted time
     */
    public static String convertSecondsToTime(long seconds, Locale locale) {
        long months = seconds / (30 * 24 * 3600);

        seconds = seconds % (30 * 24 * 3600);
        long days = seconds / (24 * 3600);

        seconds = seconds % (24 * 3600);
        long hours = seconds / 3600;

        seconds %= 3600;
        long minutes = seconds / 60 ;

        // Format result
        StringBuilder result = new StringBuilder();
        boolean setMins = minutes != 0;

        if(months != 0) {
            result.append(months);
            result.append(" ");
            result.append(getMonth(locale, months > 1));
            result.append(days != 0 || hours != 0 ? ", " : "");
            setMins = false;
        }
        if(days != 0) {
            result.append(days);
            result.append(" ");
            result.append(getDay(locale, days > 1));
            result.append(hours != 0 || setMins ? ", " : "");
        }
        if(hours != 0) {
            result.append(hours);
            result.append(" ");
            result.append(getHour(locale, hours > 1));
            result.append(setMins ? ", " : "");
        }
        if(setMins) {
            result.append(minutes);
            result.append(" ");
            result.append(getMinute(locale, minutes > 1));
        }

        return result.toString();
    }

    private static String getMonth(Locale locale, boolean plural) {
        if(locale == Locale.FRENCH) {
            return "mois";
        } else if(locale == Locale.GERMAN) {
            return plural ? "Monate" : "Monat";
        }

        return "";
    }

    private static String getDay(Locale locale, boolean plural) {
        if(locale == Locale.FRENCH) {
            return plural ? "jours" : "jour";
        } else if(locale == Locale.GERMAN) {
            return plural ? "Tage" : "Tag";
        }

        return "";
    }

    private static String getHour(Locale locale, boolean plural) {
        if(locale == Locale.FRENCH) {
            return plural ? "heures" : "heure";
        } else if(locale == Locale.GERMAN) {
            return plural ? "Stunden" : "Stunde";
        }

        return "";
    }

    private static String getMinute(Locale locale, boolean plural) {
        if(locale == Locale.FRENCH) {
            return plural ? "minutes" : "minute";
        } else if(locale == Locale.GERMAN) {
            return plural ? "Minuten" : "Minute";
        }

        return "";
    }
}
