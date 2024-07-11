package com.mysaml.mc.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.mysaml.mc.api.EventListener;
import com.mysaml.mc.base.Base;
import com.mysaml.mc.base.Config;
import com.mysaml.mc.core.PlaceHolderManager;

public class ScoreBoardListener implements EventListener {
    private Map<Player, Integer> tasks = new HashMap<Player, Integer>();
    public Config config;
    private Boolean enable;
    private Integer delay;
    private String tittle;
    private List<String> content;
    private Plugin Core;
    private PlaceHolderManager holderManager;
    public ScoreBoardListener(Plugin Core) {
        config = new Config("board.yml", this.getClass().getClassLoader());
        this.Core = Core;
        this.holderManager = PlaceHolderManager.getInstance();
        loadVars();
    }
    public void reload() {
        hideAll();
        config.reload();
        loadVars();
        showAll();
    }
    private void loadVars() {
        delay = config.getInt("delay");
        enable = config.getBoolean("enable");
        tittle = config.getString("tittle");
        content = config.getStringList("content");
    }
    public String compileLine(String line, Player player) {
        line = line
            .replace("%player_name%", player.getDisplayName())
            .replace("%player_ping%", Integer.toString(player.getPing()));
        line = Base.Color(line);
        return line;
    }
    public void initScoreBoard(Player player) {
        if (! enable) return;
        final ScoreboardManager sbManager = Bukkit.getScoreboardManager();
        int taskID = Core.getServer().getScheduler().scheduleSyncRepeatingTask(Core, new Runnable() {
            @Override
            public void run() {
                Scoreboard sb = sbManager.getNewScoreboard();
                Objective objetive = sb.registerNewObjective("dci", Criteria.DUMMY, compileLine(tittle, player));
                objetive.setDisplaySlot(DisplaySlot.SIDEBAR);
                objetive.setRenderType(RenderType.HEARTS);
                int backCount = content.size();
                for (String line : content) {
                    objetive.getScore(holderManager.compile(line, player)).setScore(backCount --);
                }
                player.setScoreboard(sb);
            }
        }, 0, delay);
        tasks.put(player, taskID);
    }
    public void finishScoreBoard(Player player) {
        if (tasks.containsKey(player)) {
            Core.getServer().getScheduler().cancelTask(tasks.get(player));
            tasks.remove(player);
            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        }
    }
    public void showAll() {
        for(Player player : Core.getServer().getOnlinePlayers()) {
            finishScoreBoard(player);
            initScoreBoard(player);
        }
    }
    public void hideAll() {
        for(Player player : Core.getServer().getOnlinePlayers()) {
            finishScoreBoard(player);
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        finishScoreBoard(event.getPlayer());
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        initScoreBoard(event.getPlayer());
    }
}
