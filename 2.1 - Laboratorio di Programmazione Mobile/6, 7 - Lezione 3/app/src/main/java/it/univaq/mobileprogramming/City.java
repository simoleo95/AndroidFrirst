package it.univaq.mobileprogramming;

/**
 * MobileProgramming2018
 * Created by leonardo on 19/10/2018.
 * <p>
 * BiTE s.r.l.
 * contact info@bitesrl.it
 */
public class City {

    private String name;
    private String region;

    public City(){}

    public City(String name, String region) {
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
