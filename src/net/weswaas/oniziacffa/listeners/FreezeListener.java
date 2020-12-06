package net.weswaas.oniziacffa.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.weswaas.oniziacffa.OniziacFFA;

public class FreezeListener implements Listener{
	
	public static ArrayList<Player> frozen = new ArrayList<Player>();
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(frozen.contains(p)){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDamageEntity(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player){
			if(e.getDamager() instanceof Player){
				if(frozen.contains(e.getEntity())){
					e.setCancelled(true);
				}
				if(frozen.contains(e.getDamager())){
					e.setCancelled(true);
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		
		if(frozen.contains(p)){
			for(Player pls : Bukkit.getOnlinePlayers()){
				if(pls.hasPermission("ffa.alert")){
					pls.sendMessage(OniziacFFA.PREFIX + ChatColor.BOLD + ChatColor.RED + p.getName() + " has disconnected while being frozen !");
				}
			}
		}
	}

}
