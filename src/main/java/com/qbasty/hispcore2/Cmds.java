package com.qbasty.hispcore2;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Cmds implements Listener {

    private final Main plugin;

    public Cmds(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent evt) {
        if (evt.getMessage().equalsIgnoreCase("/help")) {
            plugin.getConfig().getList("help").forEach(b -> evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', (String)b)));
            evt.setCancelled(true);
        }
    }
}
