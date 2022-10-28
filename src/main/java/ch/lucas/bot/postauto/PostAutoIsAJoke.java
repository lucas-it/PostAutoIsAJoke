package ch.lucas.bot.postauto;

import ch.lucas.bot.postauto.utils.Message;
import ch.lucas.bot.postauto.utils.opentransportdata.OpenTransportDataApiUtils;
import ch.lucas.bot.postauto.utils.config.Config;
import ch.lucas.bot.postauto.utils.exceptions.TweetMaximumLengthExceedException;
import ch.lucas.bot.postauto.utils.twitter.Twit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Locale;

/**
 * Principal class of the project. It runs the process to tweet the information about disruptions.
 *
 * @author Lucas-it@github
 */
public class PostAutoIsAJoke {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostAutoIsAJoke.class);

    public static void main(String[] args) {
        LOGGER.info("Start the program");

        File currentJavaJarFile = new File(PostAutoIsAJoke.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String currentJavaJarFilePath = currentJavaJarFile.getAbsolutePath();
        String currentRootDirectoryPath = currentJavaJarFilePath.replace(currentJavaJarFile.getName(), "");

        LOGGER.info("Loading config file from {}", currentRootDirectoryPath);

        File configFile = new File(currentRootDirectoryPath + "/config.json");
        Config config = null;

        if(configFile.exists()) {
            LOGGER.info("Initialize configuration utilities");
            config = new Config(configFile);
            if(!config.isConfigValid()) {
                LOGGER.error("Config file is not valid. Can't continue.");
                System.exit(-1);
            }
        } else {
            LOGGER.error("The file config.json does not exist at location : {}", currentRootDirectoryPath);
            System.exit(-1);
        }

        LOGGER.info("Initialize Twitter utilities");
        Twit twit = new Twit(config);

        LOGGER.info("Start tweeting procedure");
        long start = System.currentTimeMillis();

        try {
            Message message = new OpenTransportDataApiUtils(config).getInformationFromAPI();
            twit.tweet(message.getFormattedMessage(Locale.FRENCH), message.getFormattedMessage(Locale.GERMAN));
            LOGGER.info("The Tweet has been posted");
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        } catch (TweetMaximumLengthExceedException | IOException e) {
            LOGGER.error(e.getMessage(), e);   
        }

        LOGGER.info("Processing time : {} s", (System.currentTimeMillis() - start) / 1000);
    }
}
