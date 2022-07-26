package com.qbasty.hispcore2;

import org.bukkit.World;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Dupes implements Listener {

    private final Main plugin;

    public Dupes(Main plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    private void onEntityDamage(EntityDamageEvent evt) {
        if (plugin.getConfig().getBoolean("PatchEndPortalDupe")) {
            if (evt.getEntity() instanceof LivingEntity) {
                if ((evt.getEntity() instanceof ChestedHorse && ((ChestedHorse) evt.getEntity()).isCarryingChest()) || ((LivingEntity) evt.getEntity()).getCanPickupItems()) {
                    if ((evt.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) || evt.getCause().equals(EntityDamageEvent.DamageCause.FALL))
                            && evt.getEntity().getWorld().getEnvironment().equals(World.Environment.THE_END)
                            && Math.round(evt.getEntity().getLocation().getX()) == 100
                            && Math.round(evt.getEntity().getLocation().getZ()) == 0
                            && (((LivingEntity) evt.getEntity()).getHealth() - evt.getDamage()) <= 0) {
                        evt.getEntity().remove();
                    }
                }
            }
        }
    }
}
