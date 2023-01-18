package dtos;

public class HouseDto {
    private String address;
    private Integer numberOfRooms;

    private CityInfoDto cityInfoDto;

    public HouseDto(String address, Integer numberOfRooms, CityInfoDto cityInfoDto) {
        this.address = address;
        this.numberOfRooms = numberOfRooms;
        this.cityInfoDto = cityInfoDto;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public CityInfoDto getCityInfoDto() {
        return cityInfoDto;
    }

    public void setCityInfoDto(CityInfoDto cityInfoDto) {
        this.cityInfoDto = cityInfoDto;
    }
}
