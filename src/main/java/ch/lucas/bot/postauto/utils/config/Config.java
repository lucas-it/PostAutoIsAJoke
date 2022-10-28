package ch.lucas.bot.postauto.utils.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;

/**
 * This class provides some utility methods to read the json config file.
 *
 * @author Lucas-it@github
 */
public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private final File jsonConfigFile;
    private String openTransportDataApiKey;
    private String twitterConsumerKey;
    private String twitterConsumerSecret;
    private String twitterAccessToken;
    private String twitterAccessTokenSecret;
    private boolean allowTweeting;
    private boolean configIsValid;

    /**
     * Create a new ConfigUtils object.
     *
     * @param jsonConfigFile the path of the json config file
     */
    public Config(File jsonConfigFile) {
        this.jsonConfigFile = jsonConfigFile;

        try {
            JsonElement jsonParser = JsonParser.parseReader(new FileReader(jsonConfigFile));
            if (jsonParser.isJsonObject()) {
                JsonObject jsonConfig = jsonParser.getAsJsonObject();
                if (jsonConfig.has("openTransportDataApiKey") && jsonConfig.has("twitterConsumerKey") && jsonConfig.has("twitterConsumerSecret") && jsonConfig.has("twitterAccessToken") && jsonConfig.has("twitterAccessTokenSecret") && jsonConfig.has("allowTweeting")) {
                    openTransportDataApiKey = jsonConfig.get("openTransportDataApiKey").getAsString();
                    twitterConsumerKey = jsonConfig.get("twitterConsumerKey").getAsString();
                    twitterConsumerSecret = jsonConfig.get("twitterConsumerSecret").getAsString();
                    twitterAccessToken = jsonConfig.get("twitterAccessToken").getAsString();
                    twitterAccessTokenSecret = jsonConfig.get("twitterAccessTokenSecret").getAsString();
                    allowTweeting = jsonConfig.get("allowTweeting").getAsBoolean();
                    configIsValid = true;
                } else {
                    configIsValid = false;
                    LOGGER.error("The config file does not contain all needed parameters. See example.config.json in src/main/resources.");
                }
            } else {
                configIsValid = false;
                LOGGER.error("The config file must be a json object.");
            }
        } catch (Exception e) {
            configIsValid = false;
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Get the OpenTransportData API key.
     *
     * @return the OpenTransportData api key
     */
    public String getOpenTransportDataApiKey() {
        return openTransportDataApiKey;
    }

    /**
     * Get the path of the json config file.
     *
     * @return the full path of json config file
     */
    public File getJsonConfigFile() {
        return jsonConfigFile;
    }

    /**
     * Get the twitter consumer key from config file.
     *
     * @return the twitter consumer key
     */
    public String getTwitterConsumerKey() {
        return twitterConsumerKey;
    }

    /**
     * Get the twitter consumer secret from config file.
     *
     * @return the twitter consumer secret
     */
    public String getTwitterConsumerSecret() {
        return twitterConsumerSecret;
    }

    /**
     * Get the twitter access token from config file.
     *
     * @return the twitter access token
     */
    public String getTwitterAccessToken() {
        return twitterAccessToken;
    }

    /**
     * Get the twitter access token secret from config file.
     *
     * @return the twitter access token secret
     */
    public String getTwitterAccessTokenSecret() {
        return twitterAccessTokenSecret;
    }

    /**
     * Get if the bot is allowed to tweet from config file.
     *
     * @return is allowed to tweet
     */
    public boolean isAllowTweeting() {
        return allowTweeting;
    }

    /**
     * Get if the config file is valid.
     * The file is valid if it's contains all of these parameters:
     * <ul>
     *     <li>openTransportDataApiKey</li>
     *     <li>twitterConsumerKey</li>
     *     <li>twitterConsumerSecret</li>
     *     <li>twitterAccessToken</li>
     *     <li>twitterAccessTokenSecret</li>
     *     <li>allowTweeting</li>
     * </ul>
     *
     * @return the config is valid or not
     */
    public boolean isConfigValid() {
        return configIsValid;
    }
}
