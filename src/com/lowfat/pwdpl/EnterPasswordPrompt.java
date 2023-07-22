package com.lowfat.pwdpl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnterPasswordPrompt implements Prompt {

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return ChatColor.RED + "Say the server password to play!";
    }

    @Override
    public boolean blocksForInput(@NotNull ConversationContext context) {
        return true;
    }

    @Override
    public @Nullable Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
        Conversable conversable = context.getForWhom();
        if (!(conversable instanceof Player)) {
            // ???
            return null;
        }
        Player player = (Player) conversable;
        Bukkit.getConsoleSender().sendMessage(player.toString() + ": wl: " + player.isWhitelisted());
        if (player.isWhitelisted()) {
            authorize_player(player);
            return null;
        }
        if (input.equals(PasswordPlugin.SERVER_PASSWORD)) {
            player.setWhitelisted(true);
            authorize_player(player);
            return null;
        }
        player.sendTitle(ChatColor.RED + "INCORRECT", "please enter the correct password!", 10, 70, 20);
        return new EnterPasswordPrompt();
    }

    private @Nullable void authorize_player(@NotNull Player player) {
        Events.unauthorized.remove(player.getUniqueId());
        player.setGameMode(GameMode.SURVIVAL);
        player.sendTitle(ChatColor.GREEN + "AUTHORIZED", "thank you!", 10, 70, 20);
    }
}
