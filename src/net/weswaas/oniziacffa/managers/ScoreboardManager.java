package net.weswaas.oniziacffa.managers;

import net.weswaas.oniziacffa.OniziacFFA;
import net.weswaas.oniziacffa.data.PlayerData;
import net.weswaas.oniziacffa.data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

/**
 * Created by Weswas on 18/04/2020.
 */
public class ScoreboardManager {

    private PlayerDataManager pdm;

    public ScoreboardManager(PlayerDataManager pdm){
        this.pdm = pdm;
    }

    public HashMap<Scoreboard, Player> boards = new HashMap<>();

    public void updater(){
        new BukkitRunnable() {
            public void run() {
                for(Scoreboard board : boards.keySet()){
                    Player p = boards.get(board);

                    PlayerData data = OniziacFFA.getInstance().getPlayerDataManager().getPlayerData(p.getUniqueId().toString());
                    if(data != null){
                        int kills = data.getKills();
                        int deaths = data.getDeaths();
                        int ks = data.getCurrentKs();
                        int bestks = data.getKs();
                        String kdr = data.getKDR();
                        int players = Bukkit.getOnlinePlayers().size();

                        board.getTeam("score11").setSuffix("§f" + players);
                        board.getTeam("score9").setSuffix("§f" + kills);
                        board.getTeam("score7").setSuffix("§f" + deaths);
                        board.getTeam("score5").setSuffix("§f" + kdr);
                        board.getTeam("score3").setSuffix("§f" + ks);
                        board.getTeam("score1").setSuffix("§f" + bestks);
                    }

                }
            }
        }.runTaskTimer(OniziacFFA.getInstance(), 10, 10);
    }

    public void createSidebar(Player p){

        PlayerData data = pdm.getPlayerData(p.getUniqueId().toString());
        int kills = data.getKills();
        int deaths = data.getDeaths();
        int ks = data.getCurrentKs();
        int bestks = data.getKs();
        String kdr = data.getKDR();
        int players = Bukkit.getOnlinePlayers().size();

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("ffa", "sc");
        Objective health = board.registerNewObjective("heal", "health");
        Objective tab = board.registerNewObjective("healthtab", "health");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.DARK_AQUA + "OniziacFFA");

        health.setDisplaySlot(DisplaySlot.BELOW_NAME);
        health.setDisplayName("§4♥");

        tab.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        tab.setDisplayName("§e");

        Team score1 = board.registerNewTeam("score1");
        score1.setPrefix("§8» ");
        score1.setSuffix("§f" + bestks);
        score1.addEntry(ChatColor.DARK_PURPLE.toString());

        Team score2 = board.registerNewTeam("score2");
        score2.setPrefix("§8» §cBest KS");
        score2.setSuffix("§c:");
        score2.addEntry(ChatColor.RED.toString());

        Team score3 = board.registerNewTeam("score3");
        score3.setPrefix("§8» ");
        score3.setSuffix("§f" + ks);
        score3.addEntry(ChatColor.GOLD.toString());

        Team score4 = board.registerNewTeam("score4");
        score4.setPrefix("§8» §cCurrent KS");
        score4.setSuffix("§c:");
        score4.addEntry(ChatColor.DARK_RED.toString());


        Team score5 = board.registerNewTeam("score5");
        score5.setPrefix("§8» ");
        score5.setSuffix("§f" + kdr);
        score5.addEntry(ChatColor.DARK_BLUE.toString());

        Team score6 = board.registerNewTeam("score6");
        score6.setPrefix("§8» §cKDR");
        score6.setSuffix("§c:");
        score6.addEntry(ChatColor.DARK_AQUA.toString());

        Team score7 = board.registerNewTeam("score7");
        score7.setPrefix("§8» ");
        score7.setSuffix("§f" + deaths);
        score7.addEntry(ChatColor.BOLD.toString());

        Team score8 = board.registerNewTeam("score8");
        score8.setPrefix("§8» §cDeaths");
        score8.setSuffix("§c:");
        score8.addEntry(ChatColor.BLUE.toString());

        Team score9 = board.registerNewTeam("score9");
        score9.setPrefix("§8» ");
        score9.setSuffix("§f" + kills);
        score9.addEntry(ChatColor.BLACK.toString());

        Team score10 = board.registerNewTeam("score10");
        score10.setPrefix("§8» §cKills");
        score10.setSuffix("§c:");
        score10.addEntry(ChatColor.AQUA.toString());

        Team score11 = board.registerNewTeam("score11");
        score11.setPrefix("§8» ");
        score11.setSuffix("§f" + players);
        score11.addEntry(ChatColor.DARK_GREEN.toString());

        Team score12 = board.registerNewTeam("score12");
        score12.setPrefix("§8» §cPlayers");
        score12.setSuffix("§c:");
        score12.addEntry(ChatColor.DARK_GRAY.toString());

        obj.getScore(ChatColor.DARK_GRAY.toString()).setScore(12);
        obj.getScore(ChatColor.DARK_GREEN.toString()).setScore(11);
        obj.getScore(ChatColor.AQUA.toString()).setScore(10);
        obj.getScore(ChatColor.BLACK.toString()).setScore(9);
        obj.getScore(ChatColor.BLUE.toString()).setScore(8);
        obj.getScore(ChatColor.BOLD.toString()).setScore(7);
        obj.getScore(ChatColor.DARK_AQUA.toString()).setScore(6);
        obj.getScore(ChatColor.DARK_BLUE.toString()).setScore(5);
        obj.getScore(ChatColor.DARK_RED.toString()).setScore(4);
        obj.getScore(ChatColor.GOLD.toString()).setScore(3);
        obj.getScore(ChatColor.RED.toString()).setScore(2);
        obj.getScore(ChatColor.DARK_PURPLE.toString()).setScore(1);

        boards.put(board, p);
        p.setScoreboard(board);

    }

}