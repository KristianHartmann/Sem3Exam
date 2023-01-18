package dtos;


public class RentalDto {


    private Integer rentalId;
    private Integer deposit;
    private String endDate;
    private Integer priceAnnual;
    private String startDate;

    private ContactPersonDto contactPersonDto;
    private HouseDto houseDto;

    public RentalDto(Integer rentalId, Integer deposit, String endDate, Integer priceAnnual, String startDate, ContactPersonDto contactPersonDto, HouseDto houseDto) {
        this.rentalId = rentalId;
        this.deposit = deposit;
        this.endDate = endDate;
        this.priceAnnual = priceAnnual;
        this.startDate = startDate;
        this.contactPersonDto = contactPersonDto;
        this.houseDto = houseDto;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPriceAnnual() {
        return priceAnnual;
    }

    public void setPriceAnnual(Integer priceAnnual) {
        this.priceAnnual = priceAnnual;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public ContactPersonDto getContactPersonDto() {
        return contactPersonDto;
    }

    public void setContactPersonDto(ContactPersonDto contactPersonDto) {
        this.contactPersonDto = contactPersonDto;
    }

    public HouseDto getHouseDto() {
        return houseDto;
    }

    public void setHouseDto(HouseDto houseDto) {
        this.houseDto = houseDto;
    }
}
