package net.weswaas.oniziacffa.commands.players;

import net.weswaas.oniziacffa.OniziacFFA;
import net.weswaas.oniziacffa.commands.FFACommand;
import net.weswaas.oniziacffa.listeners.FreezeListener;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class FreezeCommand extends FFACommand{

    public FreezeCommand() {
        super("freeze", "/freeze <player>");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("ffa.freeze")){
                if(args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null && target.isOnline()){
                        if(FreezeListener.frozen.contains(target)){
                            FreezeListener.frozen.remove(target);
                            p.sendMessage(OniziacFFA.PREFIX + target.getName() + " has been unfrozen.");
                            target.sendMessage(OniziacFFA.PREFIX + "You have been unfrozen.");
                            target.playSound(target.getLocation(), Sound.NOTE_PIANO, 0.8f, 0.8f);
                            p.playSound(target.getLocation(), Sound.NOTE_PIANO, 0.8f, 0.8f);
                            removePotionEffects(target);
                        }else{
                            FreezeListener.frozen.add(target);
                            p.sendMessage(OniziacFFA.PREFIX + target.getName() + " has been frozen.");
                            addPotionEffects(target);
                            target.playSound(target.getLocation(), Sound.NOTE_PIANO, 0.8f, 0.8f);
                            p.playSound(target.getLocation(), Sound.NOTE_PIANO, 0.8f, 0.8f);
                        }
                    }
                }else{
                    p.sendMessage(OniziacFFA.PREFIX + "§cInvalid synthax. Please try with /freeze <player>");
                }
            }
        }

        return false;
    }

    private void addPotionEffects(Player p){
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999999, 127));
        p.setWalkSpeed(0);
    }

    private void removePotionEffects(Player p){
        p.removePotionEffect(PotionEffectType.JUMP);
        p.setWalkSpeed(0.2f);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }

}
