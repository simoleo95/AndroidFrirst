package it.univaq.mobileprogramming.model;

/**
 * MobileProgramming2018
 * Created by leonardo on 19/10/2018.
 */
public class City {

    private long id;
    private String name;
    private String region;

    public City(){}

    public City(String name, String region) {
        this.name = name;
        this.region = region;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
