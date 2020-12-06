package net.weswaas.oniziacffa.managers;

import net.weswaas.oniziacffa.OniziacFFA;
import net.weswaas.oniziacffa.kits.kits.BuildUHC;
import net.weswaas.oniziacffa.listeners.EssentialsListener;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class SpecManager implements Listener {
	
	private BuildUHC buhc;
	
	public ArrayList<Player> specs = new ArrayList<Player>();
	
	public SpecManager(BuildUHC buhc) {
		this.buhc = buhc;
	}
	
	@SuppressWarnings("deprecation")
	public void setSpec(Player p){
		
		for(Player online : Bukkit.getOnlinePlayers()){
			online.hidePlayer(p);
		}
		
		p.getInventory().clear();
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		p.setGameMode(GameMode.CREATIVE);
		p.sendMessage(OniziacFFA.PREFIX + " §3You've been added to specs.");
		p.sendMessage(OniziacFFA.PREFIX + " §3You're vanished.");
		p.teleport(new Location(Bukkit.getWorld("world"), 148, 116, -167));
		p.setFlying(true);
		specs.add(p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, 3));
			
	}
	
	@SuppressWarnings("deprecation")
	public void removeSpec(Player p){
		
		for(Player online : Bukkit.getOnlinePlayers()){
			online.showPlayer(p);
		}
		
		buhc.loadKit(buhc.getKit(), p);
		EssentialsListener.nodamagePlayers.add(p);
		p.sendMessage(OniziacFFA.PREFIX + " §3You've been removed from the specs.");
		p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 149, -10.5));
		p.setGameMode(GameMode.SURVIVAL);
		specs.remove(p);
		for(PotionEffect pe : p.getActivePotionEffects()){
			p.removePotionEffect(pe.getType());
		}
		
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		if(specs.contains(e.getPlayer())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e){
		if(specs.contains(e.getPlayer())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		if(specs.contains(e.getPlayer())){
			e.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProjectile(ProjectileLaunchEvent e){
		if(e.getEntity().getShooter() instanceof Player){
			Player p = (Player) e.getEntity().getShooter();
			if(specs.contains(p)){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void pickUpItem(PlayerPickupItemEvent e){
		if(specs.contains(e.getPlayer())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onSpecDamage(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			Player p = (Player) e.getDamager();
			if(specs.contains(p)){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onCraftItem(CraftItemEvent e){
		Player p = (Player) e.getWhoClicked();
		if(specs.contains(p)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(specs.contains(p)){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBucketEmpty(PlayerBucketEmptyEvent e){
		Player p = e.getPlayer();
		if(specs.contains(p)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBucketFill(PlayerBucketFillEvent e){
		Player p = e.getPlayer();
		if(specs.contains(p)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
			Player p = (Player) e.getEntity();
			if(specs.contains(p) || specs.contains(e.getDamager())){
				e.setCancelled(true);
			}
		}
	}

}
