package dtos;

public class CityInfoDto {
    private String cityName;
    private Integer zip;

    public CityInfoDto(String cityName, Integer zip) {
        this.cityName = cityName;
        this.zip = zip;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }
}
