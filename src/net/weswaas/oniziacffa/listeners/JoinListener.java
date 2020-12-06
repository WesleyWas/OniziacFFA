package net.weswaas.oniziacffa.listeners;

import net.weswaas.oniziacffa.OniziacFFA;
import net.weswaas.oniziacffa.data.PlayerData;
import net.weswaas.oniziacffa.data.PlayerDataManager;
import net.weswaas.oniziacffa.kits.kits.BuildUHC;
import net.weswaas.oniziacffa.managers.ScoreboardManager;
import net.weswaas.oniziacffa.managers.SpecManager;
import net.weswaas.oniziacffa.sql.StatsSQL;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class JoinListener implements Listener {

	private SpecManager sm;
	private BuildUHC buhc;
	private StatsSQL sql;
	private PlayerDataManager pdm;
	private ScoreboardManager sbm;
	
	public JoinListener(SpecManager sm, BuildUHC buhc, StatsSQL sql, PlayerDataManager pdm, ScoreboardManager sbm) {

		this.sm = sm;
		this.buhc = buhc;
		this.sql = sql;
		this.pdm = pdm;
		this.sbm = sbm;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		final Player p = e.getPlayer();
		
		sql.connection();
		sql.createAccount(p);
		
		for(Player online : Bukkit.getOnlinePlayers()){
			for(Player specs : sm.specs){
				online.hidePlayer(specs);
			}
		}
		
		EssentialsListener.nodamagePlayers.add(p);

		e.setJoinMessage(null);
		p.setFlying(false);
		p.setGameMode(GameMode.SURVIVAL);
		p.getInventory().clear();
		p.setFoodLevel(20);
		p.setHealth(20);
		p.setSaturation(999999);

		pdm.joinMethod(p.getUniqueId().toString());
		PlayerData data = pdm.getPlayerData(p.getUniqueId().toString());

		int kills = data.getKills();
		int deaths = data.getDeaths();
		int bestks = data.getKs();
		String kdr = data.getKDR();
		int players = Bukkit.getOnlinePlayers().size();
		
		p.sendMessage("§a»§m--------------------------------------------§r§a«");
		p.sendMessage("§a");
		p.sendMessage("§7» §aWelcome on the OniziacNetwork's FFA Arena");
		p.sendMessage("§7» §aPlayers: §7" + players);
		p.sendMessage("§a");
		p.sendMessage("§7» §aKills: §7" + kills);
		p.sendMessage("§7» §aDeaths: §7" + deaths);
		p.sendMessage("§7» §aBest Killstreak: §7" + bestks);
		p.sendMessage("§7» §aKDR: §7" + kdr);
		p.sendMessage("§a");
		p.sendMessage("§a»§m--------------------------------------------§r§a«");
		
		for(PotionEffect pe : p.getActivePotionEffects()){
			p.removePotionEffect(pe.getType());
		}

        DeathListener.scatter(p, getLocation());

		new BukkitRunnable() {
			public void run() {
				buhc.loadKit(buhc.getKit(), p);
				sbm.createSidebar(p);
			}
		}.runTaskLater(OniziacFFA.getInstance(), 1);

	}

	public static Location getLocation(){

		boolean ok = false;
		World arena = Bukkit.getWorld("world");

		while (!ok) {
			Random randX = new Random();
			Random randZ = new Random();

			int x = randX.nextInt(237 - 101) + 101;
			int z = randZ.nextInt(-131 - (-203)) + (-203);

			Block block = arena.getBlockAt(new Location(arena, x, arena.getHighestBlockYAt(x, z) - 1, z));
			Material mat = block.getType();

			if (mat == Material.GRASS || mat == Material.GLASS || mat == Material.DIRT){
				Location loc = new Location(arena, x, arena.getHighestBlockYAt(x, z) + 3, z);
				return loc;
			}

		}

		return null;

	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		
		e.setQuitMessage(null);
		if(sm.specs.contains(e.getPlayer())){
			sm.specs.remove(e.getPlayer());
		}
		
	}

}
