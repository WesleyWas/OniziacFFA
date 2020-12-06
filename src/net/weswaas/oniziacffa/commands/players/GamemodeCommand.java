package net.weswaas.oniziacffa.commands.players;

import net.weswaas.oniziacffa.OniziacFFA;
import net.weswaas.oniziacffa.commands.FFACommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GamemodeCommand extends FFACommand{

    public GamemodeCommand() {
        super("gamemode", "/gamemode <0 | 1  2> [<player>]");
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("ffa.gamemode")){
                if(p.hasPermission("ffa.gamemode.modify")){

                    if(args.length == 2){

                        Player target = Bukkit.getPlayer(args[1]);
                        if(target != null && target.isOnline()){
                            target.setGameMode(GameMode.getByValue(Integer.valueOf(args[0])));
                            p.sendMessage(OniziacFFA.PREFIX + "Gamemode " + Integer.valueOf(args[0]) + " has been set to " + target.getName() + ".");
                        }else{
                            p.sendMessage(OniziacFFA.PREFIX + ChatColor.RED + args[1] + " is not online.");
                        }

                    }else if(args.length == 1){

                        p.setGameMode(GameMode.getByValue(Integer.valueOf(args[0])));
                        p.sendMessage(OniziacFFA.PREFIX + "Your gamemode has been changed to gamemode " + Integer.valueOf(args[0]));

                    }else{
                        p.sendMessage(OniziacFFA.PREFIX + "§cInvalid synthax. Please try with /gamemode <0 | 1 | 2> [<player>]");
                    }

                }else{
                    p.sendMessage(OniziacFFA.PREFIX + "§cPlease do not try to change your gamemode while being in the FFA arena. If you want to spec, please do /spec add " + p.getName() + ".");
                }
            }
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        // TODO Auto-generated method stub
        return null;
    }

}
