package model.vo;

public class FullAddress {

    private final String country;
    private final String region;
    private final String city;
    private final StreetAddress streetAddress;

    public FullAddress(String country, String region, String city, StreetAddress streetAddress){
        if (country == null)
            throw new IllegalArgumentException(
                    "Country can't be null"
            );

        if (region == null)
            throw new IllegalArgumentException(
                    "Region can't be null"
            );

        if (city == null)
            throw new IllegalArgumentException(
                    "City can't be null"
            );

        if (streetAddress == null)
            throw new IllegalArgumentException(
                    "Street address can't be null"
            );


        if (country.length() > 64)
            throw new IllegalArgumentException(
                    "country is too big. maximum length is 64"
            );

        if (region.length() > 128)
            throw new IllegalArgumentException(
                    "region is too big. maximum length is 128"
            );

        if (city.length() > 64)
            throw new IllegalArgumentException(
                    "city is too big. maximum length is 64"
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

    @Override
    public String toString() {
        return "fullAddress" + ":" + country + "," + region + "," + city + "," + streetAddress.toString();
    }
}
