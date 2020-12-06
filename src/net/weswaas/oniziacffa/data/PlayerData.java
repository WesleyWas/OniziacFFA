package net.weswaas.oniziacffa.data;

import net.weswaas.oniziacffa.sql.StatsSQL;

/**
 * Created by Weswas on 18/04/2020.
 */
public class PlayerData {

    private StatsSQL sql;

    String uuid;
    int kills;
    int deaths;
    String kdr;
    int ks;

    int currentKS = 0;


    public PlayerData(StatsSQL sql, String uuid){
        this.uuid = uuid;
        this.sql = sql;
        this.kills = sql.getKills(uuid);
        this.deaths = sql.getDeaths(uuid);
        this.kdr = sql.getKDR(uuid);
        this.ks = sql.getKillstreak(uuid);
    }

    public void addKill(){
        this.kills += 1;
        this.currentKS +=1;
        getKDR();
    }

    public void addDeath(){
        this.deaths += 1;
        this.currentKS = 0;
    }

    public Integer getKills(){
        return this.kills;
    }

    public Integer getDeaths(){
        return this.deaths;
    }

    public String getKDR(){
        double kdr = kills / (deaths == 0 ? 1 : deaths);
        double finalKDR = (double) Math.round(kdr * 100) / 100; // 4.248 --> 4.25
        this.kdr = String.valueOf(finalKDR);
        return String.valueOf(finalKDR);
    }

    public Integer getKs(){
        return this.ks;
    }

    public Integer getCurrentKs(){
        return this.currentKS;
    }

}
