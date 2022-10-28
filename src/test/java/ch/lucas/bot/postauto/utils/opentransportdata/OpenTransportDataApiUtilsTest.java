package ch.lucas.bot.postauto.utils.opentransportdata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpenTransportDataApiUtilsTest {
    private OpenTransportDataApiUtils openTransportDataApiUtils = new OpenTransportDataApiUtils(null);

    @Test
    public void testParseDisruptionStatsFromJson() {
        InputStream disruptions = getClass().getResourceAsStream("disruptions.json");
        JsonObject disruptionsJson = JsonParser.parseReader(new InputStreamReader(disruptions)).getAsJsonObject();

        DisruptionStats underTest = openTransportDataApiUtils.parseDisruptionStatsFromJson(disruptionsJson);

        assertEquals(9, underTest.getNumberOfDelayedTravels());
        assertEquals(840777, underTest.getAverageDelayPerBus());
        assertEquals(7567000, underTest.getCumulativeLate());
    }

    @Test
    public void testParseDeletedTravelsFromJson() {
        InputStream deletedTravels = getClass().getResourceAsStream("deletedTravels.json");
        JsonObject deletedTravelsJson = JsonParser.parseReader(new InputStreamReader(deletedTravels)).getAsJsonObject();

        int underTest = openTransportDataApiUtils.parseDeletedTravelsFromJson(deletedTravelsJson);

        assertEquals(8, underTest);
    }

    @Test
    public void testParseTotalTravelsFromJson() {
        InputStream travels = getClass().getResourceAsStream("travels.json");
        JsonObject travelsJson = JsonParser.parseReader(new InputStreamReader(travels)).getAsJsonObject();

        int underTest = openTransportDataApiUtils.parseTotalTravelsFromJson(travelsJson);

        assertEquals(6, underTest);
    }
}
