package com.example.datamanagmentapp;

public class CustomBodyMeasurementModel {
    private String Gewicht_Kg;
    private String Größe_cm;
    private String timestamp;

    public CustomBodyMeasurementModel(String gewicht_Kg, String größe_cm, String timestamp) {
        Gewicht_Kg = gewicht_Kg;
        Größe_cm = größe_cm;
        this.timestamp = timestamp;
    }

    public String getGewicht_Kg() {
        return Gewicht_Kg;
    }

    public void setGewicht_Kg(String gewicht_Kg) {
        Gewicht_Kg = gewicht_Kg;
    }

    public String getGröße_cm() {
        return Größe_cm;
    }

    public void setGröße_cm(String größe_cm) {
        Größe_cm = größe_cm;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
