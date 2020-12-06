package net.weswaas.oniziacffa.listeners;

import net.weswaas.oniziacffa.OniziacFFA;
import net.weswaas.oniziacffa.data.PlayerDataManager;
import net.weswaas.oniziacffa.kits.kits.BuildUHC;
import net.weswaas.oniziacffa.managers.ScoreboardManager;
import net.weswaas.oniziacffa.managers.SpecManager;
import net.weswaas.oniziacffa.sql.StatsSQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenersManager {
	
	private OniziacFFA plugin;
	private StatsSQL sql;
	private SpecManager sm;
	private BuildUHC buhc;
	private PlayerDataManager pdm;
	private ScoreboardManager sbm;
	
	public ListenersManager(OniziacFFA plugin, SpecManager sm, BuildUHC buhc, StatsSQL sql, PlayerDataManager pdm, ScoreboardManager sbm) {
		
		this.plugin = plugin;
		this.sql = sql;
		this.sm = sm;
		this.buhc = buhc;
		this.pdm = pdm;
		this.sbm = sbm;
	}
	
	public void registerListeners(){
		
		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		pm.registerEvents(new JoinListener(sm, buhc, sql, pdm, sbm), plugin);
		pm.registerEvents(new EssentialsListener(), plugin);
		pm.registerEvents(new DeathListener(buhc, pdm), plugin);
		pm.registerEvents(new BlockListener(), plugin);
		pm.registerEvents(new SpecManager(buhc), plugin);
		pm.registerEvents(pdm, plugin);
		pm.registerEvents(new PingListener(), plugin);
	}

}
