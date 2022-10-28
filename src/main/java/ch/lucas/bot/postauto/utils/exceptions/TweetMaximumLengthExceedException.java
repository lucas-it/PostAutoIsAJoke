package ch.lucas.bot.postauto.utils.exceptions;

/**
 * Exception thrown when the tweet is longer than 280 characters.
 *
 * @author Lucas-it@github
 */
public class TweetMaximumLengthExceedException extends Exception {
    public TweetMaximumLengthExceedException() {
        this("The Tweet exceed the maximum length of 280 characters.", null);
    }

    public TweetMaximumLengthExceedException(String message) {
        this(message, null);
    }

    public TweetMaximumLengthExceedException(String message, Throwable cause) {
        super(message, cause);
    }
}
