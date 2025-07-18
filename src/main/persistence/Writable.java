package persistence;

import org.json.JSONObject;

public interface Writable {
    // CITATION: this code was taken from CPSC 210 JsonSerializationDemo 
    // return this as a JSON object
    JSONObject toJson();
}
