package dev.arcticgaming.lunachat.EventHandlers;

import dev.arcticgaming.lunachat.ChatOperators.ComponentBuilder;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class ChatEvent implements Listener {

    static Server server = Bukkit.getServer();

    @EventHandler
    public static void chatManager(AsyncChatEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Component message = event.message();

        event.setCancelled(true);
        server.sendMessage(ComponentBuilder.messageBuilder(player, message));

    }
}
