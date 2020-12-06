package net.weswaas.oniziacffa.kits;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import net.weswaas.oniziacffa.kits.kits.BuildUHC;

public class KitManager {

    public KitManager() {

    }

    public ArrayList<Kit> kits = new ArrayList<Kit>();

    public Kit getKit(String name){
        for(Kit kit : kits){
            if(kit.getName().equalsIgnoreCase(name)){
                return kit;
            }
        }
        return null;
    }

    public List<Kit> getKits(){
        return ImmutableList.copyOf(kits);
    }

    public void registerKits(){

        kits.add(new BuildUHC());
    }

    public boolean contains(String name){
        for(Kit kit : kits){
            if(kit.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

}
