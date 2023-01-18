package dtos;

public class TenantDto {
    private Integer id;
    private String name;
    private Integer phone;

    private String job;
    private String user_name;

    public TenantDto(Integer id, String name, Integer phone, String job, String user_name) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.job = job;
        this.user_name = user_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
