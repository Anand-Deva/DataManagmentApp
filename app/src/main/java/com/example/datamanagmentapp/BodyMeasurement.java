package com.example.datamanagmentapp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import org.bson.types.ObjectId;

public class BodyMeasurement extends RealmObject {

    @PrimaryKey
    private ObjectId _id;
    private String Gewicht_Kg;
    private String Größe_cm;
    @Required
    private String _partitionKey;
    private String timestamp;

    // Standard getters & setters
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }
    public String getGewicht_Kg() { return Gewicht_Kg; }
    public void setGewicht_Kg(String Gewicht_Kg) { this.Gewicht_Kg = Gewicht_Kg; }
    public String getGröße_cm() { return Größe_cm; }
    public void setGröße_cm(String Größe_cm) { this.Größe_cm = Größe_cm; }
    public String get_partitionKey() { return _partitionKey; }
    public void set_partitionKey(String _partitionKey) { this._partitionKey = _partitionKey; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}

