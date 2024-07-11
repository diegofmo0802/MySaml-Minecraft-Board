package com.mysaml.mc.board;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.mysaml.mc.api.CommandExecutor;
import com.mysaml.mc.base.Base;

public class ScoreBoardCommand implements CommandExecutor {
    ScoreBoardListener scoreListener;
    ScoreBoardCommand(ScoreBoardListener scoreListener) {
        this.scoreListener = scoreListener;
    }
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                switch (args[0]) {
                    case "reload": {
                        scoreListener.reload();
                        sender.sendMessage(Base.Color("&eMySaml&b scoreboard&a config reloaded"));
                        break;
                    }
                    case "hide": {
                        scoreListener.config.set("enable", false);
                        scoreListener.hideAll();
                        sender.sendMessage(Base.Color("&eMySaml&b scoreboard&a scoreboard&3 oculta para todos"));
                        break;
                    }
                    case "show": {
                        scoreListener.config.set("enable", true);
                        scoreListener.showAll();
                        sender.sendMessage(Base.Color("&eMySaml&b scoreboard&a scoreboard&3 mostrada para todos"));
                        break;
                    }
                    default:
                        sender.sendMessage(Base.Color("&eMySaml&c comando desconocido"));
                        break;
                }
                break;
            default:
                sender.sendMessage(Base.Color("&eMySaml v1.0.0"));
                break;
        }
    }
    @Override
    public List<String> onTab(CommandSender sender, String[] args) {
        List<String> result = new ArrayList<String>();
        switch (args.length) {
            case 1:
                result.add("hide");
                result.add("show");
                result.add("reload");
                return result;
            default:
                return result;
        }
    }
}
