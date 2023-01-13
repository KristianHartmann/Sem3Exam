package dtos;

import entities.Cityinfo;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link entities.Cityinfo} entity
 */
public class CityinfoDto implements Serializable {
    private final Integer id;
    private final Integer zip;
    @Size(max = 45)
    private final String cityname;

    public CityinfoDto(Cityinfo cityinfo) {
        this.id = cityinfo.getId();
        this.zip = cityinfo.getZip();
        this.cityname = cityinfo.getCityname();
    }

    public Integer getId() {
        return id;
    }

    public Integer getZip() {
        return zip;
    }

    public String getCityname() {
        return cityname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityinfoDto entity = (CityinfoDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.zip, entity.zip) &&
                Objects.equals(this.cityname, entity.cityname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, zip, cityname);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "zip = " + zip + ", " +
                "cityname = " + cityname + ")";
    }
}