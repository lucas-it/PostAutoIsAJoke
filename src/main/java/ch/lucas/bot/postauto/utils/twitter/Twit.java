package ch.lucas.bot.postauto.utils.twitter;

import ch.lucas.bot.postauto.utils.config.Config;
import ch.lucas.bot.postauto.utils.exceptions.TweetMaximumLengthExceedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.*;
import twitter4j.v1.Status;
import twitter4j.v1.StatusUpdate;

import java.util.Arrays;

/**
 * This class contains utility methods for posting tweet.
 *
 * @author Lucas-it@github
 */
public class Twit {
    private static final Logger LOGGER = LoggerFactory.getLogger(Twit.class);
    private final Config config;
    private final Twitter twitter;

    /**
     * Create a new Twit object.
     *
     * @param config
     */
    public Twit(Config config) {
        this.config = config;
        this.twitter = Twitter.newBuilder()
                            .oAuthConsumer(config.getTwitterConsumerKey(), config.getTwitterConsumerSecret())
                            .oAuthAccessToken(config.getTwitterAccessToken(), config.getTwitterAccessTokenSecret())
                            .build();
    }

    /**
     * Post a new tweet.
     *
     * @param message the content of the tweet
     * @return the twitter status
     * @throws TweetMaximumLengthExceedException if the tweet is longer than 280 characters.
     */
    public Status tweet(String message) throws TweetMaximumLengthExceedException {
        LOGGER.info("tweet - Check if the message is not too long");
        if (message.length() > 280) {
            throw new TweetMaximumLengthExceedException();
        }

        LOGGER.info("tweet - Check if the bot is allowed to post tweet (config.json)");
        if (config.isAllowTweeting()) {
            try {
                LOGGER.info("tweet - Post the Tweet (update status)");
                return twitter.v1().tweets().updateStatus(message);
            } catch (TwitterException e) {
                LOGGER.error(e.getMessage(), e);
            }
        } else {
            LOGGER.info("tweet - The bot is not allowed to post tweet (change config.json). The tweet is below :");
            LOGGER.info("\n{}", message);
        }

        return null;
    }

    /**
     * Post a new tweet.
     *
     * @param parentMessage the tweet which contain the thread
     * @param messages      the messages of the thread
     * @throws TweetMaximumLengthExceedException if the tweet is longer than 280 characters.
     */
    public void tweet(String parentMessage, String... messages) throws TweetMaximumLengthExceedException {
        LOGGER.info("tweet - Check if the message is not too long");
        if (parentMessage.length() > 280 || Arrays.stream(messages).anyMatch(m -> m.length() > 280)) {
            throw new TweetMaximumLengthExceedException();
        }

        LOGGER.info("tweet - Check if the bot is allowed to post tweet (config.json)");
        if (config.isAllowTweeting()) {
            long inReplyToStatusId;

            try {
                LOGGER.info("tweet - Post the Tweet (update status)");
                inReplyToStatusId = twitter.v1().tweets().updateStatus(parentMessage).getId();
            } catch (TwitterException e) {
                LOGGER.error(e.getMessage(), e);
                return;
            }

            for(String m : messages) {
                StatusUpdate su = StatusUpdate.of(m).inReplyToStatusId(inReplyToStatusId);
                try {
                    LOGGER.info("tweet - Post the a new message under the Thread (update status)");
                    twitter.v1().tweets().updateStatus(su);
                } catch (TwitterException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        } else {
            String tweets = String.join("\n", messages);
            LOGGER.info("tweet - The bot is not allowed to post tweet (change config.json). The tweet is below :");
            LOGGER.info("\n{} \n {}", parentMessage, tweets);
        }
    }

    /**
     * Get the twitter object.
     *
     * @return the twitter object
     */
    public Twitter getTwitter() {
        return twitter;
    }
}
