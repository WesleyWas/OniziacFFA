package net.weswaas.oniziacffa.listeners;

import net.weswaas.oniziacffa.OniziacFFA;
import net.weswaas.oniziacffa.data.PlayerDataManager;
import net.weswaas.oniziacffa.kits.kits.BuildUHC;
import net.weswaas.oniziacffa.sql.StatsSQL;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathListener implements Listener {
	
	private StatsSQL sql;
	private BuildUHC buhc;
	private PlayerDataManager pdm;
	
	ItemStack gold = new ItemStack(Material.GOLD_INGOT, 4);
	ItemStack arrows = new ItemStack(Material.ARROW, 16);
	
	public DeathListener(BuildUHC buhc, PlayerDataManager pdm) {
		
		this.pdm = pdm;
		this.buhc = buhc;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		e.getDrops().clear();
		
		if(e.getEntity() instanceof Player){
			Player died = e.getEntity();
			died.setExp(0);
			pdm.getPlayerData(died.getUniqueId().toString()).addDeath();
			
			if(e.getEntity().getKiller() instanceof Player){
				Player killer = e.getEntity().getKiller();
				
				Damageable d = killer;
				double h = d.getHealth() / 2;

                double finalH = (double) Math.round(h * 100) / 100; // 4.248 --> 4.25
				
				e.setDeathMessage(ChatColor.GREEN + died.getName() + ChatColor.GRAY + " was slain by " + ChatColor.GREEN + killer.getName() + " §7(§a" + finalH + "§4❤§7)");

				pdm.getPlayerData(killer.getUniqueId().toString()).addKill();
				
				killer.getInventory().addItem(getGoldenHead());
				killer.getInventory().addItem(arrows);

				killer.setHealth((killer.getHealth() >= killer.getMaxHealth()) ? killer.getMaxHealth() : killer.getHealth() + 4);
			}else{
				e.setDeathMessage(ChatColor.GREEN + died.getName() + " §7died");
			}
		}
	}
	
	@EventHandler
	public void onRespawn(final PlayerRespawnEvent e){
		buhc.loadKit(buhc.getKit(), e.getPlayer());
		
        scatter(e.getPlayer(), JoinListener.getLocation());
		
		EssentialsListener.nodamagePlayers.add(e.getPlayer());
	}

	public static void scatter(final Player p, final Location loc){
		loc.getChunk().load();
        new BukkitRunnable() {
            public void run() {
                p.teleport(JoinListener.getLocation());
            }
        }.runTaskLater(OniziacFFA.getInstance(), 4);
    }
	
	private ItemStack getGoldenHead(){
		ItemStack goldenHead = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta goldenHeadM = goldenHead.getItemMeta();
		goldenHeadM.setDisplayName("Golden Head");
		goldenHead.setItemMeta(goldenHeadM);
		
		return goldenHead;
	}

}
