package dtos;

import java.util.List;

public class TenantsInHouseDto {
    HouseDto houseDto;
    List<TenantDto> tenantDto;

    public TenantsInHouseDto(HouseDto houseDto, List<TenantDto> tenantDto) {
        this.houseDto = houseDto;
        this.tenantDto = tenantDto;
    }

    public HouseDto getHouseDto() {
        return houseDto;
    }

    public void setHouseDto(HouseDto houseDto) {
        this.houseDto = houseDto;
    }

    public List<TenantDto> getTenantDto() {
        return tenantDto;
    }

    public void setTenantDto(List<TenantDto> tenantDto) {
        this.tenantDto = tenantDto;
    }
}
