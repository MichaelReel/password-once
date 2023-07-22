package com.lowfat.pwdpl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class Events implements Listener {

    public static Set<UUID> unauthorized = new HashSet<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        unauthorized.remove(e.getPlayer().getUniqueId()); // remove if present.
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.isWhitelisted()) {
            player.sendTitle(ChatColor.GREEN + "AUTHORIZED", "", 20, 10, 20);
            return;
        }

        player.sendTitle(ChatColor.RED + "ENTER PASSWORD", "please enter the password to play!", 10, 70, 20);
        player.setGameMode(GameMode.SPECTATOR);
        unauthorized.add(player.getUniqueId());
        PasswordPlugin.FACTORY.withFirstPrompt(new EnterPasswordPrompt()).buildConversation(player).begin();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(PlayerMoveEvent e) {
        if (unauthorized.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommandExecuted(PlayerCommandPreprocessEvent e) {
        if (unauthorized.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
