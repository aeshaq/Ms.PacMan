package persistence;

import org.json.JSONObject;

// Citation: Class was modeled after Json Serialization demo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
