package com.qbasty.hispcore2;

import lombok.RequiredArgsConstructor;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BookMeta;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BookBan implements Listener {
    private final Main plugin;

    public void clearBooks(Player player) {
        if (!plugin.getConfig().getBoolean("PreventBookBan"))
            return;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() != null) {
                if (ItemUtil.isBook(item)) {
                    stripPages(item);
                }

                if (ItemUtil.isShulker(item)) {
                    BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
                    ShulkerBox box = (ShulkerBox) meta.getBlockState();

                    for (ItemStack i : box.getInventory().getContents()) {
                        if (i != null && i.getType() != null && ItemUtil.isBook(i)) {
                            stripPages(i);
                        }
                    }

                    box.update();
                    meta.setBlockState(box);
                    item.setItemMeta(meta);
                }
            }
        }
    }

    private void stripPages(ItemStack book) {
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        List<String> pages = new ArrayList<>();

        for (String page : bookMeta.getPages()) {
            if (page.getBytes(StandardCharsets.UTF_8).length <= plugin.getConfig().getInt("MaxByteSize"))
                pages.add(page);
        }

        bookMeta.setPages(pages);

        book.setItemMeta(bookMeta);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        clearBooks(event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        clearBooks(event.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        clearBooks(event.getPlayer());
    }
}
