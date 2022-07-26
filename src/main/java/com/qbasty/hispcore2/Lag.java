package com.qbasty.hispcore2;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class Lag implements Listener {

    private final Main plugin;

    public Lag(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onExpBottle(ExpBottleEvent evt) {

        if (count(evt.getEntity().getLocation().getChunk().getEntities(), EntityType.THROWN_EXP_BOTTLE) > plugin.getConfig().getInt("ExpBottleLimit")) {
            remove(evt.getEntity().getLocation().getChunk().getEntities(), EntityType.THROWN_EXP_BOTTLE);
        }
    }

    @EventHandler
    private void onWitherSpawn(EntitySpawnEvent evt) {
        if (count(evt.getEntity().getLocation().getChunk().getEntities(), EntityType.WITHER) > plugin.getConfig().getInt("WitherLimit")) {
            remove(evt.getEntity().getLocation().getChunk().getEntities(), EntityType.WITHER);
        }
    }

    @EventHandler
    private void onWitherSkull(EntitySpawnEvent evt) {
        if (count(evt.getEntity().getLocation().getChunk().getEntities(), EntityType.WITHER_SKULL) > plugin.getConfig().getInt("WitherSkullLimit")) {
            remove(evt.getEntity().getLocation().getChunk().getEntities(), EntityType.WITHER_SKULL);
        }
    }

    @EventHandler
    public void onEvent(ChunkLoadEvent event) {
        for (Entity e : event.getChunk().getEntities()) {
            if (e instanceof org.bukkit.entity.WitherSkull)
                e.remove();
        }
    }

    @EventHandler
    public void onEvent(ChunkUnloadEvent event) {
        for (Entity e : event.getChunk().getEntities()) {
            if (e instanceof org.bukkit.entity.WitherSkull)
                e.remove();
        }
    }

    @EventHandler
    private void onPlace(BlockPlaceEvent evt) {
        FileConfiguration config = plugin.getConfig();

        if (evt.getBlockPlaced().getType().hasGravity()) {
            if (count(evt.getBlock().getChunk().getEntities(), EntityType.FALLING_BLOCK) > config.getInt("FallingBlockLimit")) {
                evt.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onEntityChange(BlockPhysicsEvent evt) {
        FileConfiguration config = plugin.getConfig();

        if (evt.getChangedType().hasGravity()) {
            if (plugin.getConfig().getBoolean("LimitFallingBlocks")) {
                if (count(evt.getBlock().getChunk().getEntities(), EntityType.FALLING_BLOCK) > config.getInt("FallingBlockLimit")) {
                    evt.setCancelled(true);
                }
            }
        }
    }

    private Integer count(Entity[] e, EntityType type) {
        Integer count = 0;
        for (Entity entity : e) {
            if (entity.getType().equals(type)) {
                count++;
            }
        }
        return count;
    }

    private void remove(Entity[] e, EntityType type) {
        for (Entity entity : e) {
            if (entity.getType().equals(type)) {
                entity.remove();
            }
        }
    }
}
