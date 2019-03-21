package com.example.homeworkweek6day3;

public class Celebrity {
    private String name;
    private String birthDate;
    private String occupation;
    private String homeTown;
    private String Residence;
    private String picture;


    public Celebrity(String name, String birthDate, String occupation, String homeTown, String residence, String picture) {
        this.name = name;
        this.birthDate = birthDate;
        this.occupation = occupation;
        this.homeTown = homeTown;
        Residence = residence;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getResidence() {
        return Residence;
    }

    public void setResidence(String residence) {
        Residence = residence;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Celebrity{" +
                "name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", occupation='" + occupation + '\'' +
                ", homeTown='" + homeTown + '\'' +
                ", Residence='" + Residence + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
