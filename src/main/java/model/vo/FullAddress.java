package model.vo;

public class FullAddress {

    private final String country;
    private final String region;
    private final String city;
    private final StreetAddress streetAddress;

    public FullAddress(String country, String region, String city, StreetAddress streetAddress){
        if (country == null)
            throw new NullPointerException(
                    "Country can't be null"
            );

        if (region == null)
            throw new NullPointerException(
                    "Region can't be null"
            );

        if (city == null)
            throw new NullPointerException(
                    "City can't be null"
            );

        if (streetAddress == null)
            throw new NullPointerException(
                    "Street address can't be null"
            );

        this.country = country;
        this.region = region;
        this.city = city;
        this.streetAddress = streetAddress;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public StreetAddress getStreetAddress() {
        return streetAddress;
    }
}
