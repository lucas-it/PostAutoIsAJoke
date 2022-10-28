package ch.lucas.bot.postauto.utils.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {
    private final File configFileOK = new File(getClass().getResource("configValid.json").getPath());
    private final File configFileBadFormat = new File(getClass().getResource("configBadFormat.json").getPath());
    private final File configFileMissingParameters = new File(getClass().getResource("configMissingParameters.json").getPath());
    private final File configFileJsonArray = new File(getClass().getResource("configJsonArray.json").getPath());

    @Test
    public void testConfigOK() {
        Config config = new Config(configFileOK);

        Assertions.assertEquals("1a2b3c", config.getOpenTransportDataApiKey());
        Assertions.assertEquals("123", config.getTwitterConsumerKey());
        Assertions.assertEquals("456", config.getTwitterConsumerSecret());
        Assertions.assertEquals("789", config.getTwitterAccessToken());
        Assertions.assertEquals("abc", config.getTwitterAccessTokenSecret());
        Assertions.assertFalse(config.isAllowTweeting());
        Assertions.assertEquals(configFileOK.getPath(), config.getJsonConfigFile().getPath());
    }

    @Test
    public void testConfigBadFormat() {
        Config underTest = new Config(configFileBadFormat);

        assertFalse(underTest.isConfigValid());
    }

    @Test
    public void testConfigMissingParameters() {
        Config underTest = new Config(configFileMissingParameters);

        assertFalse(underTest.isConfigValid());
    }

    @Test
    public void testConfigJsonArray() {
        Config underTest = new Config(configFileJsonArray);

        assertFalse(underTest.isConfigValid());
    }

    @Test
    public void testConfigIsValid() {
        Config underTest = new Config(configFileOK);

        assertTrue(underTest.isConfigValid());
    }
}
