package it.univaq.mobileprogramming;


public class Location
{
    private String name;
    private String region;
    private String cap;

    public Location(){}

    public Location(String name, String region) {
        this.name = name;
        this.region = region;
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
