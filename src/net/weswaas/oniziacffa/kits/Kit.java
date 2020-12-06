package net.weswaas.oniziacffa.kits;



import org.bukkit.inventory.ItemStack;

public class Kit {

    private String name;
    private String description;
    private ItemStack item;

    public Kit(String name, String description, ItemStack item) {

        this.name = name;
        this.description = description;
        this.item = item;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public ItemStack getItem(){
        return this.item;
    }

}

