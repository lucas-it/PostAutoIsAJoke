package ch.lucas.bot.postauto.utils;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageTest {
    private final Date dateOfReport = new Date();
    private final int nbrOfTravels = 100;
    private final int nbrOfDelayedTravels = 10;
    private final int nbrOfDeletedTravels = 5;
    private final double percentageOfDelayedTravels = 10.0;
    private final double percentageOfDeletedTravels = 5.0;
    private final long cumulatedDelay = 13 * 24 * 60 * 60 + 24 * 60; // 13 days, 21 minutes
    private final long averageDelayPerTrain = 4;
    private final DecimalFormat percentageFormatter = new DecimalFormat("#.##");
    private final Message underTest = new Message(dateOfReport, nbrOfTravels, nbrOfDelayedTravels, nbrOfDeletedTravels, averageDelayPerTrain, cumulatedDelay);

    @Test
    public void testGetDateOfReport() {
        assertEquals(dateOfReport, underTest.getDateOfReport());
    }

    @Test
    public void testGetNbrOfTravels() {
        assertEquals(nbrOfTravels, underTest.getNbrOfTravels());
    }

    @Test
    public void testGetNbrOfDelayedTravels() {
        assertEquals(nbrOfDelayedTravels, underTest.getNbrOfDelayedTravels());
    }

    @Test
    public void testGetNbrOfDeletedTravels() {
        assertEquals(nbrOfDeletedTravels, underTest.getNbrOfDeletedTravels());
    }

    @Test
    public void testGetPercentageOfDelayedTravels() {
        assertEquals(percentageOfDelayedTravels, underTest.getPercentageOfDelayedTravels());
    }

    @Test
    public void testGetPercentageOfDeletedTravels() {
        assertEquals(percentageOfDeletedTravels, underTest.getPercentageOfDeletedTravels());
    }

    @Test
    public void testGetCumulatedDelay() {
        assertEquals(cumulatedDelay, underTest.getCumulatedDelay());
    }

    @Test
    public void testGetAverageDelayPerTrain() {
        assertEquals(averageDelayPerTrain, underTest.getAverageDelayPerBus());
    }

    @Test
    public void testGetFormattedMessage() {
        String underTest = this.underTest.getFormattedMessage(Locale.FRENCH);

        assertTrue(underTest.contains(nbrOfTravels + ""));
        assertTrue(underTest.contains(nbrOfDelayedTravels + ""));
        assertTrue(underTest.contains(nbrOfDeletedTravels + ""));
        assertTrue(underTest.contains(percentageFormatter.format(percentageOfDelayedTravels)));
        assertTrue(underTest.contains(percentageFormatter.format(percentageOfDeletedTravels)));
        assertTrue(underTest.contains(TimeFormatter.convertSecondsToTime(averageDelayPerTrain, Locale.FRENCH)));
        assertTrue(underTest.contains(TimeFormatter.convertSecondsToTime(cumulatedDelay, Locale.FRENCH)));
    }

    @Test
    public void testGetFormattedMessageGerman() {
        String underTest = this.underTest.getFormattedMessage(Locale.GERMAN);

        assertTrue(underTest.contains(nbrOfTravels + ""));
        assertTrue(underTest.contains(nbrOfDelayedTravels + ""));
        assertTrue(underTest.contains(nbrOfDeletedTravels + ""));
        assertTrue(underTest.contains(percentageFormatter.format(percentageOfDelayedTravels)));
        assertTrue(underTest.contains(percentageFormatter.format(percentageOfDeletedTravels)));
        assertTrue(underTest.contains(TimeFormatter.convertSecondsToTime(averageDelayPerTrain, Locale.GERMAN)));
        assertTrue(underTest.contains(TimeFormatter.convertSecondsToTime(cumulatedDelay, Locale.GERMAN)));
    }
}
