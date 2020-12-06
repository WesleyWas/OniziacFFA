package net.weswaas.oniziacffa.data;

import net.weswaas.oniziacffa.sql.StatsSQL;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

/**
 * Created by Weswas on 18/04/2020.
 */
public class PlayerDataManager implements Listener{

    private StatsSQL sql;

    public HashMap<String, PlayerData> playerdata_uuid;

    public PlayerDataManager(StatsSQL sql){
        this.sql = sql;
        playerdata_uuid = new HashMap<>();
    }


    public PlayerData getPlayerData(String uuid){
        return playerdata_uuid.get(uuid);
    }


    public void quitMethod(String uuid){
        PlayerData data = this.playerdata_uuid.get(uuid);
        sql.setKills(uuid, data.getKills());
        sql.setDeaths(uuid, data.getDeaths());
        sql.setKDR(uuid, data.getKDR());
        sql.setKS(uuid, (data.getCurrentKs() > data.getKs() ? data.getCurrentKs() : data.getKs()));
        this.playerdata_uuid.remove(uuid);
    }

    public void joinMethod(String uuid){
        if(this.playerdata_uuid.containsKey(uuid)){
            this.playerdata_uuid.remove(uuid);
        }
        PlayerData data = new PlayerData(sql, uuid);
        this.playerdata_uuid.put(uuid, data);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        String uuid = e.getPlayer().getUniqueId().toString();
        quitMethod(uuid);
    }


}
