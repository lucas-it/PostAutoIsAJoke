package ch.lucas.bot.postauto.utils.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TweetMaximumLengthExceedExceptionTest {
    @Test
    public void testEmptyConstructor() {
        TweetMaximumLengthExceedException e = new TweetMaximumLengthExceedException();
        Assertions.assertEquals("The Tweet exceed the maximum length of 280 characters.", e.getMessage());
    }

    @Test
    public void testConstructorWithMessage() {
        TweetMaximumLengthExceedException e = new TweetMaximumLengthExceedException("message");
        Assertions.assertEquals("message", e.getMessage());
    }

    @Test
    public void testConstructorWithMessageAndThrowable() {
        TweetMaximumLengthExceedException e = new TweetMaximumLengthExceedException("message", new IllegalArgumentException());
        Assertions.assertEquals("message", e.getMessage());
        Assertions.assertEquals(new IllegalArgumentException().getMessage(), e.getCause().getMessage());
    }
}
