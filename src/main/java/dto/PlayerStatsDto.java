package dto;
// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */

public class PlayerStatsDto {
    PayloadDto payload;
    String country;

    String countryImg;
    int regionPlacement;
    int countryPlacement;

    public int getCountryPlacement() {        return countryPlacement;
    }

    public String getCountryImg() {
        return countryImg;
    }

    public void setCountryImg(String countryImg) {
        this.countryImg = countryImg;
    }

    public void setCountryPlacement(int countryPlacement) {
        this.countryPlacement = countryPlacement;
    }

    public int getRegionPlacement() {
        return regionPlacement;
    }

    public void setRegionPlacement(int regionPlacement) {
        this.regionPlacement = regionPlacement;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public PlayerStatsDto(PayloadDto payload) {
        this.payload = payload;
    }

    public PlayerStatsDto() {

    }

    public PayloadDto getPayload() {
        return payload;
    }

    public void setPayload(PayloadDto payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "PlayerStatsDto{" +
                "payload=" + payload +
                '}';
    }

    public class PayloadDto {
        public String id;
        public String avatar;
        public GamesDto games;
        public String country;
        public String nickname;



        public PayloadDto(String id, String avatar, GamesDto games, String country, String nickname) {
            this.id = id;
            this.avatar = avatar;
            this.games = games;
            this.country = country;
            this.nickname = nickname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public GamesDto getGames() {
            return games;
        }

        public void setGames(GamesDto games) {
            this.games = games;
        }

        @Override
        public String toString() {
            return "PayloadDto{" +
                    "id='" + id + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", games=" + games +
                    '}';
        }
    }
    public class GamesDto {
        public CsgoDto csgo;

        public GamesDto(CsgoDto csgo) {
            this.csgo = csgo;
        }

        public CsgoDto getCsgo() {
            return csgo;
        }

        public void setCsgo(CsgoDto csgo) {
            this.csgo = csgo;
        }

        @Override
        public String toString() {
            return "GamesDto{" +
                    "csgo=" + csgo +
                    '}';
        }
    }
    public class CsgoDto {
        public String faceit_elo;
        public int skill_level;
        public String region;

        public CsgoDto(String faceit_elo, int skill_level, String region) {
            this.faceit_elo = faceit_elo;
            this.skill_level = skill_level;
            this.region = region;
        }

        public String getFaceit_elo() {
            return faceit_elo;
        }

        public void setFaceit_elo(String faceit_elo) {
            this.faceit_elo = faceit_elo;
        }

        public int getSkill_level() {
            return skill_level;
        }

        public void setSkill_level(int skill_level) {
            this.skill_level = skill_level;
        }

        @Override
        public String toString() {
            return "CsgoDto{" +
                    "faceit_elo='" + faceit_elo + '\'' +
                    ", skill_level=" + skill_level +
                    '}';
        }
    }
}
