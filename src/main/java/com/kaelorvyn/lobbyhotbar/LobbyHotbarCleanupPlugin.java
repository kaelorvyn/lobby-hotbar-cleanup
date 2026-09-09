package com.kaelorvyn.lobbyhotbar;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobbyHotbarCleanupPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getScheduler().runTaskTimer(this, this::cleanAll, 20L, 20L);
        getLogger().info("MC-Lobby menu, dash rod, and teleport bow disabled; other hotbar items preserved");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        getServer().getScheduler().runTaskLater(this, () -> clean(event.getPlayer()), 2L);
        getServer().getScheduler().runTaskLater(this, () -> clean(event.getPlayer()), 10L);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onInteract(PlayerInteractEvent event) {
        if (isDisabled(event.getItem())) {
            event.setCancelled(true);
            clean(event.getPlayer());
        }
    }

    private void cleanAll() {
        for (Player player : getServer().getOnlinePlayers()) {
            clean(player);
        }
    }

    private void clean(Player player) {
        boolean changed = false;
        for (int slot = 0; slot < player.getInventory().getSize(); slot++) {
            if (isDisabled(player.getInventory().getItem(slot))) {
                player.getInventory().setItem(slot, null);
                changed = true;
            }
        }
        if (changed) {
            player.updateInventory();
        }
    }

    private boolean isDisabled(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        String displayName = item.getItemMeta().getDisplayName();
        String name = displayName == null ? "" : ChatColor.stripColor(displayName);
        return (item.getType() == Material.COMPASS && name.equalsIgnoreCase("打开菜单"))
                || (item.getType() == Material.BLAZE_ROD && name.equalsIgnoreCase("Dash rod"))
                || (item.getType() == Material.BOW && name.equalsIgnoreCase("Teleport bow"));
    }
}
