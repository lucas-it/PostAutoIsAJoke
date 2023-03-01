package ch.lucas.bot.postauto.utils.opentransportdata;

import ch.lucas.bot.postauto.utils.Message;
import ch.lucas.bot.postauto.utils.config.Config;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.StreamSupport;

/**
 * This class provide utility methods to get information from OpenTransportData API.
 *
 * @author Lucas-it@github
 */
public class OpenTransportDataApiUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenTransportDataApiUtils.class);
    private static final String OPEN_TRANSPORT_DATA_API_URL = "https://api.opentransportdata.swiss/ckan-api";
    private static final String RESULT_KEY = "result";
    private static final String RECORDS_KEY = "records";
    private Config config;
    private String resourceId;
    private DisruptionStats disruptionStats;
    private int deletedTravels;
    private int totalTravels;

    /**
     * @param config the configuration object which contain OpenTransportData API key
     */
    public OpenTransportDataApiUtils(Config config) {
        this.config = config;
    }

    /**
     * Get the information about disruptions from the OpenTransportData API about the precedent day.
     *
     * @return a Message object
     * @throws InterruptedException error while connecting to the OpenTransportData API
     */
    public Message getInformationFromAPI() throws InterruptedException, IOException {
        resourceId = getResourceId();

        Thread t1 = new Thread(() -> {
            try {
                LOGGER.info("getInformationFromAPI - Get disruption statistics");
                disruptionStats = getDisruptionStats();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                LOGGER.info("getInformationFromAPI - Get total number of deleted travels");
                deletedTravels = getDeletedTravels();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                LOGGER.info("getInformationFromAPI - Get total number of travels");
                totalTravels = getTotalTravels();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        });

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Waiting for the end of each thread
        t1.join();
        t2.join();
        t3.join();

        LOGGER.info("getInformationFromAPI - Create a new message with all information");
        return new Message(new Date(System.currentTimeMillis() - 86400000), totalTravels, disruptionStats.getNumberOfDelayedTravels(), deletedTravels, disruptionStats.getAverageDelayPerBus(), disruptionStats.getCumulativeLate());
    }

    private String getResourceId() throws IOException {
        LOGGER.info("getResourceId - Initialize connection to OpenTransportData API");
        HttpURLConnection con = createConnectionToAPI("/package_show?id=", "istdaten");

        // Parse JSON
        LOGGER.info("getResourceId - Read API response");
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        LOGGER.info("getResourceId - Parse API response to JSON");
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        String yesterdayFormatted = new SimpleDateFormat("yyyy-MM-dd").format(yesterday.getTime());
        JsonArray resources = JsonParser.parseReader(br).getAsJsonObject().getAsJsonObject(RESULT_KEY).getAsJsonArray("resources");
        JsonObject resource = StreamSupport.stream(resources.spliterator(), true)
                                            .map(JsonElement::getAsJsonObject)
                                            .filter(r -> r.getAsJsonObject().get("identifier").getAsString().contains(yesterdayFormatted))
                                            .findFirst()
                                            .orElse(null);

        if(resource == null) {
            throw new IllegalArgumentException("No resource found!");
        }

        return resource.get("id").getAsString();
    }

    /**
     * Get statistics about disruption. The number of delayed travels and the cumulated delay.
     *
     * @return DisruptionStats
     * @throws IOException error while connecting to the OpenTransportData API
     */
    private DisruptionStats getDisruptionStats() throws IOException {
        // Obtaining late travels
        LOGGER.info("getDisruptionStats - Initialize connection to OpenTransportData API");
        HttpURLConnection con = createConnectionToAPI("SELECT count(*) as \"nbrOfDelayedTravels\", round(avg(date_part('epoch', TO_TIMESTAMP(\"AN_PROGNOSE\", 'DD.MM.YYYY hh24:mi:ss') - TO_TIMESTAMP(\"ANKUNFTSZEIT\", 'DD.MM.YYYY hh24:mi:ss'))::int)) as \"avgDelayPerBus\", sum(date_part('epoch', TO_TIMESTAMP(\"AN_PROGNOSE\", 'DD.MM.YYYY hh24:mi:ss') - TO_TIMESTAMP(\"ANKUNFTSZEIT\", 'DD.MM.YYYY hh24:mi:ss'))) as \"cumulativeLate\" from \"" + resourceId + "\" WHERE \"PRODUKT_ID\" = 'Bus' AND \"BETREIBER_NAME\" = 'PostAuto AG' AND date_part('epoch', TO_TIMESTAMP(\"AN_PROGNOSE\", 'DD.MM.YYYY hh24:mi:ss') - TO_TIMESTAMP(\"ANKUNFTSZEIT\", 'DD.MM.YYYY hh24:mi:ss')) >= 180 AND \"ABFAHRTSZEIT\" IS NULL");

        // Parse JSON
        LOGGER.info("getDisruptionStats - Read API response");
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        LOGGER.info("getDisruptionStats - Parse API response to JSON");
        JsonObject stats = JsonParser.parseReader(br).getAsJsonObject().getAsJsonObject(RESULT_KEY).getAsJsonArray(RECORDS_KEY).get(0).getAsJsonObject();

        return parseDisruptionStatsFromJson(stats);
    }

    /**
     * Parse disruptions stats (number of late travels and cumulative late) from json provided by the API.
     *
     * @param record json object with stats
     * @return parsed disruption stats
     */
    public DisruptionStats parseDisruptionStatsFromJson(JsonObject record) {
        LOGGER.info("parseDisruptionStatsFromJson - Process late travels data");
        long cumulativeLate = record.get("cumulativeLate").getAsLong();
        int averageDelayPerBus = record.get("avgDelayPerBus").getAsInt();
        int numberOfDelayedTravels = record.get("nbrOfDelayedTravels").getAsInt();

        LOGGER.info("parseDisruptionStatsFromJson - Late travels data processed");
        return new DisruptionStats(numberOfDelayedTravels, averageDelayPerBus, cumulativeLate);
    }

    /**
     * Get the number of deleted travels.
     *
     * @return number of deleted travels
     * @throws IOException error while connecting to the OpenTransportData API
     */
    private int getDeletedTravels() throws IOException {
        // Obtaining deleted travels
        LOGGER.info("getDeletedTravels - Initialize connection to OpenTransportData API");
        HttpURLConnection con = createConnectionToAPI("SELECT count(*) as \"nbrOfDeletedTravels\" from \"" + resourceId + "\" WHERE \"PRODUKT_ID\" = 'Bus' AND \"BETREIBER_NAME\" = 'PostAuto AG' AND \"FAELLT_AUS_TF\" = 'true' AND \"ABFAHRTSZEIT\" IS NULL");

        // Parse JSON
        LOGGER.info("getDeletedTravels - Read API response");
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        LOGGER.info("getDeletedTravels - Parse API response to JSON");
        JsonObject deletedTravelsStats = JsonParser.parseReader(br).getAsJsonObject().getAsJsonObject(RESULT_KEY).getAsJsonArray(RECORDS_KEY).get(0).getAsJsonObject();

        return parseDeletedTravelsFromJson(deletedTravelsStats);
    }

    /**
     * Parse number of deleted travels from json provided by the API.
     *
     * @param record json object with stats
     * @return number of deleted travels
     */
    public int parseDeletedTravelsFromJson(JsonObject record) {
        LOGGER.info("parseDeletedTravelsFromJson - Process deleted travels data");
        int nbrOfDeletedTravels = record.get("nbrOfDeletedTravels").getAsInt();

        LOGGER.info("parseDeletedTravelsFromJson - Deleted travels data processed");
        return nbrOfDeletedTravels;
    }

    /**
     * Get total travels of the precedent day.
     * Every time a bus leave a departure station and arrived at the terminus it's one travel.
     *
     * @return the number of travel
     * @throws IOException error while connecting to the OpenTransportData API
     */
    private int getTotalTravels() throws IOException {
        LOGGER.info("getTotalTravels - Initialize connection to OpenTransportData API");
        HttpURLConnection con = createConnectionToAPI("SELECT count(*) as \"nbrOfTravels\" from \"" + resourceId + "\" WHERE \"PRODUKT_ID\" = 'Bus' AND \"BETREIBER_NAME\" = 'PostAuto AG' AND \"ABFAHRTSZEIT\" IS NULL");

        // Parse JSON
        LOGGER.info("getTotalTravels - Read API response");
        BufferedReader allDataJSONReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        LOGGER.info("getTotalTravels - Parse API response to JSON");
        JsonReader jsonReader = new JsonReader(allDataJSONReader);
        JsonObject totalTravelsStats = JsonParser.parseReader(jsonReader).getAsJsonObject().getAsJsonObject(RESULT_KEY).getAsJsonArray(RECORDS_KEY).get(0).getAsJsonObject();

        return parseTotalTravelsFromJson(totalTravelsStats);
    }

    /**
     * Parse number of total travels from json provided by the API.
     *
     * @param record json object with stats
     * @return number of travels
     */
    public int parseTotalTravelsFromJson(JsonObject record) {
        LOGGER.info("parseTotalTravelsFromJson - Process number of travels");
        int nbrOfTravels = record.get("nbrOfTravels").getAsInt();

        LOGGER.info("parseTotalTravelsFromJson - Number of travels processed");
        return nbrOfTravels;
    }

    private HttpURLConnection createConnectionToAPI(String query) throws IOException {
        return createConnectionToAPI("/datastore_search_sql?sql=", query);
    }

    private HttpURLConnection createConnectionToAPI(String endpoint, String query) throws IOException {
        URL url = new URL(OPEN_TRANSPORT_DATA_API_URL + endpoint + URLEncoder.encode(query, StandardCharsets.UTF_8));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", config.getOpenTransportDataApiKey());
        return con;
    }
}
