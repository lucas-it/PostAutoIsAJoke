package ch.lucas.bot.postauto.utils.twitter;

import ch.lucas.bot.postauto.utils.config.Config;
import ch.lucas.bot.postauto.utils.exceptions.TweetMaximumLengthExceedException;
import org.junit.jupiter.api.Test;
import twitter4j.v1.Status;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TwitTest {
    private final Config CONFIG = new Config(new File(getClass().getResource("config.json").getPath()));

    @Test
    public void testConstructor() {
        Twit underTest = new Twit(CONFIG);

        assertNotNull(underTest.getTwitter());
    }

    @Test
    public void testTweetWithTooLongMessage() {
        Twit underTest = new Twit(CONFIG);
        String msg = "abc".repeat(200);

        assertThrows(TweetMaximumLengthExceedException.class, () -> underTest.tweet(msg));
    }

    @Test
    public void testTweetWhenNotAllowed() throws TweetMaximumLengthExceedException {
        Twit underTest = new Twit(CONFIG);
        String msg = "Should not tweet this message.";

        Status result = underTest.tweet(msg);

        // Should not throw an exception.
        assertNull(result);
    }

    @Test
    public void testTweetMultipleMessagesWithTooLongParentMessage() {
        Twit underTest = new Twit(CONFIG);
        String msgTooLong = "abc".repeat(200);
        String msg = "abc";

        assertThrows(TweetMaximumLengthExceedException.class, () -> underTest.tweet(msgTooLong, msg));
    }

    @Test
    public void testTweetMultipleMessagesWithTooLongChildMessage() {
        Twit underTest = new Twit(CONFIG);
        String msgTooLong = "abc".repeat(200);
        String msg = "abc";

        assertThrows(TweetMaximumLengthExceedException.class, () -> underTest.tweet(msg, msg, msgTooLong));
    }

    @Test
    public void testTweetMultipleMessagesWhenNotAllowed() throws TweetMaximumLengthExceedException {
        Twit underTest = new Twit(CONFIG);
        String msg = "Should not tweet this message.";

        underTest.tweet(msg, msg);

        // Should not throw an exception.
    }
}
