package net.weswaas.oniziacffa.listeners;

import net.weswaas.oniziacffa.OniziacFFA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EssentialsListener implements Listener {
	
	public static ArrayList<Player> nodamagePlayers = new ArrayList<Player>();
	//private ArrayList<Player> soundsPlayers = new ArrayList<Player>();
	private BukkitRunnable task;
	
	@EventHandler
	public void onWeather(WeatherChangeEvent e){
		e.setCancelled(true);
		return;
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		e.setCancelled(true);
		return;
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(p.getLocation().getBlockY() > 140){
				e.setCancelled(true);
				return;
			}else if(e.getCause() == DamageCause.FALL){
				if(nodamagePlayers.contains(p)){
					e.setCancelled(true);
					nodamagePlayers.remove(p);
				}
			}
		}
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		if(e.getInventory().getName().contains("Stats")){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntity(EntitySpawnEvent e){
		e.setCancelled(true);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(final PlayerMoveEvent e){
		if(e.getPlayer().getLocation().getBlockY() < 90){
			e.getPlayer().damage(50);
			return;
		}
		
	}
	
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent e){
		Player p = e.getPlayer();
		if(e.getItem().getType().equals(Material.GOLDEN_APPLE) & e.getItem().hasItemMeta()){
			if(e.getItem().getItemMeta().getDisplayName().equals("Golden Head")){
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBowHit(EntityDamageByEntityEvent e){

		if(e.getDamager() instanceof Arrow){
			Arrow arrow = (Arrow) e.getDamager();
			if(arrow.getShooter() instanceof Player){
				if(e.getEntity() instanceof Player){

					if(((Player) arrow.getShooter()).getLocation().getBlockY() > 200){
						e.setCancelled(true);
					}

					final Player shooter = (Player) arrow.getShooter();
					final Player victim = (Player) e.getEntity();

					int distance = Integer.valueOf((int) shooter.getLocation().distance(victim.getLocation()));

					if(distance > 40){
						if(!(victim.getLocation().getBlockY() > 160)){
							Bukkit.broadcastMessage(OniziacFFA.PREFIX + ChatColor.GREEN + shooter.getName() + "§f just realized a longshot of §6" + distance + " §fblocks on " + ChatColor.GREEN + victim.getName());
						}
					}

					this.task = new BukkitRunnable() {
						public void run() {

							Damageable p = (Damageable) victim;
							final double h = p.getHealth();

							if(h == 0){

							}else{

								DecimalFormat df = new DecimalFormat("0.00");
								String finalH = df.format(h);
								shooter.sendMessage(OniziacFFA.PREFIX + victim.getName() + " is now at §3" + finalH + "§4♥.");
							}

						}
					};
					task.runTaskLater(OniziacFFA.getInstance(), 4);
				}
			}
		}
	}
	
}
