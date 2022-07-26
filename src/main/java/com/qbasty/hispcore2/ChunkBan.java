package com.qbasty.hispcore2;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class ChunkBan implements Listener {
    private final Main plugin;

    public ChunkBan(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent evt) {

        FileConfiguration config = plugin.getConfig();

        if (config.getBoolean("PreventChunkBan")) {
            Chunk c = evt.getBlock().getChunk();
            Block b = evt.getBlock();

            switch (b.getType()) {
                case ENCHANTMENT_TABLE: {
                    if (plugin.checkChunk(Material.ENCHANTMENT_TABLE, c) > plugin.getConfig().getInt("MaxEnchantmentTablePerChunk")) {
                        evt.setCancelled(true);
                    }
                    break;
                }
                case ENDER_CHEST: {
                    if (plugin.checkChunk(Material.ENDER_CHEST, c) > plugin.getConfig().getInt("MaxEnderchestPerChunk")) {
                        evt.setCancelled(true);
                    }
                    break;
                }
                case HOPPER: {
                    if (plugin.checkChunk(b.getType(), c) > plugin.getConfig().getInt("MaxHopperPerChunk")) {
                        evt.setCancelled(true);
                    }
                    break;
                }
                case WALL_SIGN:
                case SIGN_POST: {
                    if (plugin.checkChunk(b.getType(), c) > plugin.getConfig().getInt("MaxSignPerChunk")) {
                        evt.setCancelled(true);
                    }
                    break;
                }
                case SLIME_BLOCK: {
                    if (plugin.checkChunk(b.getType(), c) > plugin.getConfig().getInt("MaxSlimePerChunk")) {
                        evt.setCancelled(true);
                    }
                    break;
                }
                case BEACON: {
                    if (plugin.checkChunk(b.getType(), c) > plugin.getConfig().getInt("MaxBeaconPerChunk")) {
                        evt.setCancelled(true);
                    }
                    break;
                }
                case PISTON_BASE:
                case PISTON_STICKY_BASE: {
                    if (plugin.checkChunk(b.getType(), c) > config.getInt("MaxPistonPerChunk")) {
                        evt.setCancelled(true);
                    }
                    break;
                }
                case GLOWSTONE: {
                    if ((b.getType().equals(Material.GLOWSTONE)) && plugin.checkChunk(b.getType(), c) > config.getInt("MaxGlowstonePerChunk")) {
                        evt.setCancelled(true);
                    }
                    break;
                }
                default: {
                    if (b.getState() instanceof Container) {
                        if (plugin.checkChunk(b.getType(), c) > config.getInt("MaxContainerPerChunk")) {
                            evt.setCancelled(true);
                        }
                    }
                    break;
                }
            }
        }
    }
}
