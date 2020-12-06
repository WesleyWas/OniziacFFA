package net.weswaas.oniziacffa.listeners;
import net.weswaas.oniziacffa.OniziacFFA;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class BlockListener implements Listener {
	
	private ArrayList<Block> blocks = new ArrayList<Block>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(final BlockPlaceEvent e){
		
		if(e.getBlock().getLocation().getBlockY() > 110){
			e.setCancelled(true);
		}
		
		blocks.add(e.getBlock());
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(OniziacFFA.getInstance(), new BukkitRunnable() {
			public void run() {
				e.getBlock().setType(Material.AIR);
				blocks.remove(e.getBlock());
			}
		}, 600);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		if(!blocks.contains(e.getBlock())){
			e.setCancelled(true);
			return;
		}
		
		blocks.remove(e.getBlock());
	}
	
	@EventHandler
	public void onBucket(final PlayerBucketEmptyEvent e){

		final Block block = e.getBlockClicked();
		
		if(e.getBlockClicked().getLocation().getBlockY() > 110){
			e.setCancelled(true);
			return;
		}
		
		new BukkitRunnable() {
			public void run() {

				if(e.getBlockFace() == BlockFace.UP){
					block.getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
				}
				else if(e.getBlockFace() == BlockFace.DOWN){
					block.getLocation().add(0, -1, 0).getBlock().setType(Material.AIR);
				}

				else if(e.getBlockFace() == BlockFace.EAST){
					block.getLocation().add(1, 0, 0).getBlock().setType(Material.AIR);
				}
				else if(e.getBlockFace() == BlockFace.WEST){
					block.getLocation().add(-1, 0, 0).getBlock().setType(Material.AIR);
				}
				else if(e.getBlockFace() == BlockFace.SOUTH){
					block.getLocation().add(0, 0, 1).getBlock().setType(Material.AIR);
				}
				else if(e.getBlockFace() == BlockFace.NORTH){
					block.getLocation().add(0, 0, -1).getBlock().setType(Material.AIR);
				}

			}
		}.runTaskLater(OniziacFFA.getInstance(), 600);
	}

}
