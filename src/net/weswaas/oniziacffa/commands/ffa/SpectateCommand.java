package net.weswaas.oniziacffa.commands.ffa;

import net.weswaas.oniziacffa.OniziacFFA;
import net.weswaas.oniziacffa.commands.FFACommand;
import net.weswaas.oniziacffa.managers.SpecManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SpectateCommand extends FFACommand{

    private SpecManager sm;

    public SpectateCommand(SpecManager sm) {
        super("spec", "<add | remove> <pseudo>");

        this.sm = sm;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(args.length == 2){
                if(args[0].equalsIgnoreCase("add")){
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target != null && target.isOnline()){

                        if(!sm.specs.contains(target)){
                            sm.setSpec(target);
                        }else{
                            p.sendMessage(OniziacFFA.PREFIX + "§cThis player is already spectator !");
                        }
                        return true;

                    }else{
                        p.sendMessage(OniziacFFA.PREFIX + " §cJoueur introuvable !");
                    }
                }else if(args[0].equalsIgnoreCase("remove")){
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target != null && target.isOnline()){

                        if(sm.specs.contains(target)){
                            sm.removeSpec(target);
                        }else{
                            p.sendMessage(OniziacFFA.PREFIX + "§cThis player is not spectator !");
                        }
                        return true;
                    }
                }


            }

        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }

}
