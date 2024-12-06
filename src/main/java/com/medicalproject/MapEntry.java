package com.medicalproject;

public class MapEntry {
    private Integer key;
    private String value;

    public MapEntry(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() { return key; }
    public String getValue() { return value; }

    @Override
    public String toString() {
        return value; // This is what will show in the ChoiceBox
    }
}

// Then use it like this:
