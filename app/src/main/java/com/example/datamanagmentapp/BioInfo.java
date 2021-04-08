package com.example.datamanagmentapp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import org.bson.types.ObjectId;

public class BioInfo extends RealmObject {

    @PrimaryKey
    private ObjectId _id;
    @Required
    private String _partitionKey;
    private String age;
    private String gender;
    private String name;

    // Standard getters & setters
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }
    public String get_partitionKey() { return _partitionKey; }
    public void set_partitionKey(String _partitionKey) { this._partitionKey = _partitionKey; }
    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

