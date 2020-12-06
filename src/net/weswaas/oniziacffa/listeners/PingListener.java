package net.weswaas.oniziacffa.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by Weswas on 18/04/2020.
 */
public class PingListener implements Listener{

    @EventHandler
    public void on(ServerListPingEvent e){

        e.setMotd(String.valueOf(Bukkit.getOnlinePlayers().size()));

    }

}
