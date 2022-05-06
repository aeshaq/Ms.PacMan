package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/*
 An Inventory where picked up powers and eaten ghosts are stored
 */

public class Inventory implements Writable {
    private List<Power> powers;
    private List<Ghost> eatenGhosts;

    // Constructs an inventory
    // EFFECTS: sets powers and eatenGhosts into empty ArrayLists
    public Inventory() {
        powers = new ArrayList<>();
        eatenGhosts = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds p to powers
    public void eatPower(Power p) {
        powers.add(p);
    }

    //
    // MODIFIES: this
    // EFFECTS: removes p from powers
    public void usePower() {
        powers.remove(0);
    }

    public List<Power> getPowers() {
        return powers;
    }

    //
    // MODIFIES: this
    // EFFECTS: adds g to eatenGhosts
    public void eatGhost(Ghost g) {
        eatenGhosts.add(g);
    }

    //
    // MODIFIES: this
    // EFFECTS: removes the first ghost from eatenGhosts
    public void useGhost() {
        eatenGhosts.remove(0);
    }

    public List<Ghost> getGhosts() {
        return eatenGhosts;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("eatenGhosts", ghostsToJson());
        json.put("powers", powersToJson());
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Inventory inventory = (Inventory) o;

        if (powers != null ? !powers.equals(inventory.powers) : inventory.powers != null) {
            return false;
        }
        return eatenGhosts != null ? eatenGhosts.equals(inventory.eatenGhosts) : inventory.eatenGhosts == null;
    }

    //EFFECTS: returns eatenGhosts as a JSONArray
    public JSONArray ghostsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Ghost g : eatenGhosts) {
            jsonArray.put(g.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: returns powers as a JSONArray
    public JSONArray powersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Power p : powers) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }




}
