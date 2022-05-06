package persistence;


import model.*;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads PacManGame from JSON data stores it in file
// Citation: Class was modeled after Json Serialization demo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads PacManGame from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PacManGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePacManGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses PacManGame from JSON object and returns it
    private PacManGame parsePacManGame(JSONObject jsonObject) {
        int score = jsonObject.getInt("score");
        Boolean isGameOver = jsonObject.getBoolean("isGameOver");
        PacMan pac = loadPacMan(jsonObject);
        List<Pellet> pellets = loadPellets(jsonObject);
        List<Ghost> ghosts = loadGhosts(jsonObject, "ghosts");
        List<Power> powers = loadPowers(jsonObject, "powerList");
        Inventory inv = loadInventory(jsonObject);
        List<Ghost> caspers = loadGhosts(jsonObject, "caspers");
        loadEventLog(jsonObject);
        PacManGame pmg = new PacManGame(pellets, score, ghosts, pac,
                isGameOver, powers, inv, caspers);
        return pmg;
    }

    private void loadEventLog(JSONObject jsonObject) {
        JSONArray ja = jsonObject.getJSONArray("Event Log");
        EventLog.getInstance().clear();

        for (Object jo: ja) {
            JSONObject todo = (JSONObject) jo;
            Event e = loadEvent(todo);
            EventLog.getInstance().logEvent(e);
        }
    }

    private Event loadEvent(JSONObject jo) {
        String description = jo.getString("description");

        Event e = new Event(description);

        return e;
    }

    // EFFECTS: Returns the Inventory stored in the JSONObject
    private Inventory loadInventory(JSONObject jsonObject) {
        JSONObject jo = jsonObject.getJSONObject("inventory");

        List<Power> powers = loadPowers(jo, "powers");
        List<Ghost> ghosts = loadGhosts(jo, "eatenGhosts");

        Inventory inv = new Inventory();

        for (Power p : powers) {
            inv.eatPower(p);
        }
        for (Ghost g : ghosts) {
            inv.eatGhost(g);
        }
        return inv;
    }

    // EFFECTS: Returns the List<Power> stored in the JSONObject with the key s
    private List<Power> loadPowers(JSONObject jsonObject, String s) {
        List<Power> powers = new ArrayList<>();
        JSONArray ja = jsonObject.getJSONArray(s);

        for (Object jo : ja) {
            JSONObject todo = (JSONObject) jo;
            LevelObject lo = loadLevelObject(todo);
            powers.add(new Power(lo.getPosX(), lo.getPosY(), lo.getSymbol()));
        }
        return powers;
    }

    // EFFECTS: Returns the List<Ghost> stored in the JSONObject with the key s
    private List<Ghost> loadGhosts(JSONObject jsonObject, String s) {
        List<Ghost> ghosts = new ArrayList<>();
        JSONArray ja = jsonObject.getJSONArray(s);

        for (Object jo : ja) {
            JSONObject todo = (JSONObject) jo;
            LevelObject lo = loadLevelObject(todo);
            ghosts.add(new Ghost(lo.getPosX(), lo.getPosY(), lo.getSymbol()));
        }
        return ghosts;
    }

    // EFFECTS: Returns the List<Pellet> stored in the JSONObject
    private List<Pellet> loadPellets(JSONObject jsonObject) {
        List<Pellet> pellets = new ArrayList<>();
        JSONArray ja = jsonObject.getJSONArray("pellets");

        for (Object jo : ja) {
            JSONObject todo = (JSONObject) jo;
            LevelObject lo = loadLevelObject(todo);
            pellets.add(new Pellet(lo.getPosX(), lo.getPosY(), lo.getSymbol()));
        }
        return pellets;
    }

    // helper function for other loaders
    // EFFECTS: Returns the LevelObject stored in the JSONObject
    private LevelObject loadLevelObject(JSONObject jsonObject) {
        int posX = jsonObject.getInt("posX");
        int posY = jsonObject.getInt("posY");
        char symbol = (char) jsonObject.getInt("symbol");

        return new LevelObject(posX, posY, symbol);
    }

    // EFFECTS: Returns the PacMan stored in JSONObject
    private PacMan loadPacMan(JSONObject jo) {
        JSONObject js = jo.getJSONObject("pac");
        int posX = js.getInt("posX");
        int posY = js.getInt("posY");
        char symbol = (char) js.getInt("symbol");

        return new PacMan(posX, posY, symbol);
    }
}
