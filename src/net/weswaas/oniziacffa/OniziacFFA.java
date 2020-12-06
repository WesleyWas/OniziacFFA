package net.weswaas.oniziacffa;

import net.weswaas.oniziacffa.commands.CommandHandler;
import net.weswaas.oniziacffa.data.PlayerDataManager;
import net.weswaas.oniziacffa.kits.KitManager;
import net.weswaas.oniziacffa.kits.kits.BuildUHC;
import net.weswaas.oniziacffa.listeners.ListenersManager;
import net.weswaas.oniziacffa.managers.ScoreboardManager;
import net.weswaas.oniziacffa.managers.SpecManager;
import net.weswaas.oniziacffa.sql.StatsSQL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class OniziacFFA extends JavaPlugin{

    private StatsSQL sql;
    private CommandHandler cmd;
    private SpecManager sm;
    private BuildUHC buhc;
    private ScoreboardManager sbm;
    private KitManager km;
    private PlayerDataManager pdm;

    public HashMap<String, Integer> kills = new HashMap<String, Integer>();
    public HashMap<String, Integer> deaths = new HashMap<String, Integer>();

    public static  OniziacFFA instance;
    public static String PREFIX = "§aOniziacFFA §8» §a";

    @Override
    public void onEnable() {

        instance = this;
        instances();
        recipes();

        new ListenersManager(this, sm, buhc, sql, pdm, sbm).registerListeners();
        cmd.registerCommands(sm);
        sbm.updater();
        km.registerKits();

        Bukkit.getWorld("world").setGameRuleValue("NaturalRegeneration", "false");
        Bukkit.getWorld("world").setTime(0);
        Bukkit.getWorld("world").setGameRuleValue("doDaylightCycle", "false");

        sql.connection();
    }

    @Override
    public void onDisable() {
        sql.deconnection();
    }

    public void instances(){
        sql = new StatsSQL("jdbc:mysql://", "localhost", "oniziac", "weswas", "wqa159", "ffa");
        cmd = new CommandHandler(this);
        buhc = new BuildUHC();
        sm = new SpecManager(buhc);
        pdm = new PlayerDataManager(sql);
        sbm = new ScoreboardManager(pdm);
        km = new KitManager();
    }

    public static OniziacFFA getInstance(){
        return instance;
    }

    @SuppressWarnings("deprecation")
    public void recipes(){
        ItemStack item = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("Golden Head");
        item.setItemMeta(itemMeta);
        @SuppressWarnings("unused")
        ItemStack crane = new ItemStack(Material.SKULL_ITEM, 1,(byte)3);

        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("GGG","GHG","GGG");
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('H', Material.SKULL_ITEM,(byte)3);

        Bukkit.getServer().addRecipe(recipe);
    }

    public StatsSQL getStatsSQL(){
        return this.sql;
    }

    public PlayerDataManager getPlayerDataManager(){
        return this.pdm;
    }

}
