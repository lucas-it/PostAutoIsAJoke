package ch.lucas.bot.postauto.utils.opentransportdata;

/**
 * Statistics about disruptions.
 *
 * @author Lucas-it@github
 */
public class DisruptionStats {
    private int numberOfDelayedTravels;
    private long averageDelayPerBus;
    private long cumulativeLate;

    /**
     * Create a new DisruptionStats with number of delayed travels and the total of delays.
     *
     * @param numberOfDelayedTravels the number of delayed travels
     * @param averageDelayPerBus the average delay per bus in seconds
     * @param cumulativeLate the total of delays in seconds
     */
    public DisruptionStats(int numberOfDelayedTravels, long averageDelayPerBus, long cumulativeLate) {
        this.numberOfDelayedTravels = numberOfDelayedTravels;
        this.cumulativeLate = cumulativeLate;
        this.averageDelayPerBus = averageDelayPerBus;
    }

    /**
     * Get the number of delayed travels.
     * @return number of delayed travels
     */
    public int getNumberOfDelayedTravels() {
        return numberOfDelayedTravels;
    }

    /**
     * Set the number of delayed travels.
     * @param numberOfDelayedTravels the number of delayed travels
     */
    public void setNumberOfDelayedTravels(int numberOfDelayedTravels) {
        this.numberOfDelayedTravels = numberOfDelayedTravels;
    }

    /**
     * Get the total delays of travels.
     * @return the total delays in seconds
     */
    public long getCumulativeLate() {
        return cumulativeLate;
    }

    /**
     * Set the total delays of travels.
     * @param cumulativeLate total delays of travels in seconds
     */
    public void setCumulativeLate(long cumulativeLate) {
        this.cumulativeLate = cumulativeLate;
    }

    /**
     * Get the average delay per bus
     * @return average delay per bus in seconds
     */
    public long getAverageDelayPerBus() {
        return averageDelayPerBus;
    }

    /**
     * Set the average delay per bus.
     * @param averageDelayPerBus average delay per bus in seconds
     */
    public void setAverageDelayPerBus(int averageDelayPerBus) {
        this.averageDelayPerBus = averageDelayPerBus;
    }
}
