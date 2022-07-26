package com.qbasty.hispcore2;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private void register(Listener... list) {
        pluginManager.registerEvents(this, this);
        for (Listener listener : list) {
            pluginManager.registerEvents(listener, this);
        }
    }

    public PluginManager pluginManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        this.saveDefaultConfig();
        pluginManager = getServer().getPluginManager();
        register(
                new ChunkBan(this), new Dupes(this),
                new Cmds(this), new BookBan(this),
                new Exploits(this), new Lag(this)
        );

        ProtocolLib.protocolLibWrapper(this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Integer checkChunk(Material material, Chunk c) {

        int count = 0;
        int cx = c.getX() << 4;
        int cz = c.getZ() << 4;

        for (int x = cx; x < cx + 16; x++) {
            for (int z = cz; z < cz + 16; z++) {
                for (int y = 0; y < 256; y++) {
                    if (c.getBlock(x, y, z).getType() == material) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
