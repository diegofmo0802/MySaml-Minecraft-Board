package com.mysaml.mc.board;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mysaml.mc.api.CommandExecutor;

public class FlyCommand implements CommandExecutor {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.getAllowFlight())  {
                player.sendMessage("Modo de vuelo deshabilitado");
                player.setAllowFlight(false);
            }
            else {
                player.sendMessage("Modo de vuelo habilitado");
                player.setAllowFlight(true);
            }
        } else {
            sender.sendMessage("No se puede usar este comando desde consola");
        }
    }
}
