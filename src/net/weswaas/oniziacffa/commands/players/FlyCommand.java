package net.weswaas.oniziacffa.commands.players;

import net.weswaas.oniziacffa.OniziacFFA;
import net.weswaas.oniziacffa.commands.FFACommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FlyCommand extends FFACommand{

    public FlyCommand() {
        super("fly", "/fly [<player>]");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("ffa.fly")){

                if(p.isFlying()){
                    p.setFlying(false);
                }else{
                    p.setFlying(true);
                }

                p.sendMessage(OniziacFFA.PREFIX + "Your fly mode has been " + (p.isFlying() ? "enabled" : "�cdisabled") + "�a.");
            }else{
                p.sendMessage(OniziacFFA.PREFIX + "§cPlease do not try to fly while playing in the FFA arena.");
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
