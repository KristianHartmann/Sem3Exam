package entities;

import dto.PlayerStatsDto;

public class PlayerStats {
    private final String nickname;
    private String faceitId;
    private String img;
    private int elo;
    private int lvl;
    private String region;
    private String country;
    private int regionPlacement;
    private int countryPlacement;
    private String countryImg;

    public PlayerStats(PlayerStatsDto playerStatsDto) {
        this.nickname = playerStatsDto.getPayload().getNickname();
        this.faceitId = playerStatsDto.getPayload().getId();
        this.img = playerStatsDto.getPayload().getAvatar();
        this.elo = Integer.parseInt(playerStatsDto.getPayload().getGames().getCsgo().getFaceit_elo());
        this.lvl = playerStatsDto.getPayload().getGames().getCsgo().getSkill_level();
        this.country = playerStatsDto.getPayload().country;
        this.countryPlacement = playerStatsDto.getCountryPlacement();
        this.region = playerStatsDto.getPayload().getGames().getCsgo().region;
        this.regionPlacement = playerStatsDto.getRegionPlacement();
        this.countryImg = "https://countryflagsapi.com/png/" + playerStatsDto.getPayload().country;
    }

    public String getFaceitId() {
        return faceitId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setFaceitId(String faceitId) {
        this.faceitId = faceitId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    @Override
    public String toString() {
        return "PlayerStats{" +
                "faceitId=" + faceitId +
                ", img='" + img + '\'' +
                ", elo=" + elo +
                ", lvl=" + lvl +
                '}';
    }
}
