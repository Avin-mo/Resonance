package persistence;

import org.json.JSONObject;

// CITATION: parts of this code was taken / inspired from CPSC 210 JsonSerializationDemo
public interface Writable {
    // return this as a JSON object
    JSONObject toJson();
}
