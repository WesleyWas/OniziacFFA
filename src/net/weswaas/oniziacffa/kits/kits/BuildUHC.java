package net.weswaas.oniziacffa.kits.kits;


import net.weswaas.oniziacffa.OniziacFFA;
import net.weswaas.oniziacffa.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class BuildUHC extends Kit{

    HashMap<Integer, ItemStack> kit = new HashMap<Integer, ItemStack>();

    public BuildUHC() {
        super("BuildUHC", "Basic BuildUHC Kit", new ItemStack(Material.LAVA_BUCKET));

        registerKit();
    }

    public void loadKit(final HashMap<Integer, ItemStack> kit, final Player p){

        new BukkitRunnable() {
            public void run() {
                PlayerInventory inv = p.getInventory();

                inv.setItem(0, kit.get(0));
                inv.setItem(1, kit.get(1));
                inv.setItem(2, kit.get(2));
                inv.setItem(3, kit.get(3));
                inv.setItem(4, kit.get(4));
                inv.setItem(5, kit.get(5));
                inv.setItem(8, kit.get(8));
                inv.setItem(9, kit.get(24));

                inv.setHelmet(kit.get(20));
                inv.setChestplate(kit.get(21));
                inv.setLeggings(kit.get(22));
                inv.setBoots(kit.get(23));

                p.updateInventory();
            }
        }.runTaskLater(OniziacFFA.getInstance(), 2);

    }

    public HashMap<Integer, ItemStack> getKit(){
        return this.kit;

    }

    public void registerKit(){

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta swordM = sword.getItemMeta();
        swordM.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
        swordM.addEnchant(Enchantment.DURABILITY, 3, true);
        sword.setItemMeta(swordM);
        kit.put(0, sword);

        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        kit.put(1, rod);

        ItemStack water = new ItemStack(Material.WATER_BUCKET);
        kit.put(2, water);

        ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE);
        kit.put(3, gapple);

        ItemStack wood = new ItemStack(Material.WOOD, 64);
        kit.put(4, wood);

        ItemStack apples = new ItemStack(Material.APPLE, 16);
        kit.put(5, apples);

        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        ItemMeta helmetM = helmet.getItemMeta();
        helmetM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        helmetM.addEnchant(Enchantment.DURABILITY, 3, true);
        helmet.setItemMeta(helmetM);
        kit.put(20, helmet);

        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta chestplateM = chestplate.getItemMeta();
        chestplateM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        chestplateM.addEnchant(Enchantment.DURABILITY, 3, true);
        chestplate.setItemMeta(chestplateM);
        kit.put(21, chestplate);

        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemMeta leggingsM = leggings.getItemMeta();
        leggingsM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        leggingsM.addEnchant(Enchantment.DURABILITY, 3, true);
        leggings.setItemMeta(leggingsM);
        kit.put(22, leggings);

        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta bootsM = boots.getItemMeta();
        bootsM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        bootsM.addEnchant(Enchantment.DURABILITY, 3, true);
        boots.setItemMeta(bootsM);
        kit.put(23, boots);
    }



}
