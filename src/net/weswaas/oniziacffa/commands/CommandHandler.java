package net.weswaas.oniziacffa.commands;

import net.weswaas.oniziacffa.OniziacFFA;
import net.weswaas.oniziacffa.commands.ffa.SpectateCommand;
import net.weswaas.oniziacffa.commands.players.FlyCommand;
import net.weswaas.oniziacffa.commands.players.FreezeCommand;
import net.weswaas.oniziacffa.commands.players.GamemodeCommand;
import net.weswaas.oniziacffa.managers.SpecManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;

import java.util.ArrayList;
import java.util.List;


public class CommandHandler implements CommandExecutor, TabCompleter{

    private final OniziacFFA plugin;

    public CommandHandler(OniziacFFA plugin) {
        this.plugin = plugin;
    }

    private List<FFACommand> cmds = new ArrayList<FFACommand>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        FFACommand command = getCommand(cmd.getName());

        if (command == null) {
            return true;
        }


        try {
            if (!command.execute(sender, args)) {
                //sender.sendMessage(OniziacFFA.PREFIX + "Usage: " + command.getUsage());
            }
        } catch (Exception ex) {
            sender.sendMessage(ChatColor.RED + ex.getClass().getName() + ": " + ex.getMessage());
            ex.printStackTrace();
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        FFACommand command = getCommand(cmd.getName());

        if (command == null) {
            return null;
        }

        if (!sender.hasPermission(command.getPermission())) {
            return null;
        }

        try {
            List<String> list = command.tabComplete(sender, args);

            if (list == null) {
                return null;
            }

            if (list.isEmpty()) {
                return list;
            }

            List<String> toReturn = new ArrayList<String>();

            if (args[args.length - 1].isEmpty()) {
                for (String type : list) {
                    toReturn.add(type);
                }
            } else {
                for (String type : list) {
                    if (type.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                        toReturn.add(type);
                    }
                }
            }

            return toReturn;
        } catch (Exception ex) {
            sender.sendMessage(ChatColor.RED + ex.getClass().getName() + ": " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    protected FFACommand getCommand(String name) {
        for (FFACommand cmd : cmds) {
            if (cmd.getName().equalsIgnoreCase(name)) {
                return cmd;
            }
        }

        return null;
    }

    public void registerCommands(SpecManager sm) {


        cmds.add(new SpectateCommand(sm));
        cmds.add(new FreezeCommand());
        cmds.add(new GamemodeCommand());
        cmds.add(new FlyCommand());

        for (FFACommand cmd : cmds) {
            PluginCommand pCmd = plugin.getCommand(cmd.getName());

            if (pCmd == null) {
                Bukkit.broadcastMessage(cmd.getName());
                continue;
            }

            pCmd.setExecutor(this);
            pCmd.setTabCompleter(this);
        }
    }

}
