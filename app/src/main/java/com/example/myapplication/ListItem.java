package com.example.myapplication;

public class ListItem {
        private String name;
        private String description;
        private String lng;
        private String lat;
        private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
