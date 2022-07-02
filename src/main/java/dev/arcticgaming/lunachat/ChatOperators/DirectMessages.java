package dev.arcticgaming.lunachat.ChatOperators;

import dev.arcticgaming.lunachat.Utility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DirectMessages {

    public static Map<UUID, UUID> lastMessage = new HashMap<>();

    public static void setLastMessage(UUID playerUUID, UUID senderUUID){
        lastMessage.put(playerUUID,senderUUID);
    }

    public static UUID getLastMessage(UUID playerUUID) {

        if (lastMessage.containsKey(playerUUID)) {
            return lastMessage.get(playerUUID);
        } else {
            noDirectMessengers(playerUUID);
            return playerUUID;
        }
    }

    public static void noDirectMessengers (UUID uuid) {

        TextComponent denyMessage = Component.text()
                .content("[Server] Sorry, something went wrong with this message")
                .color(TextColor.color(0xff0000))
                .build();

        Bukkit.getPlayer(uuid).sendMessage(denyMessage);

    }

    public static void sendDirectMessage(Player player, Player receiver, String message){

        Component resolvedMessage = Utility.formatResolve(message);
        Component filteredMessage = MessageProcessor.filterChat(resolvedMessage);

        TextComponent directMessage = Component.text()
                .content("[Private] ")
                .color(TextColor.color(0x7d7d7d))
                .append(player.displayName())
                .append(Component.text(" → "))
                .append(receiver.displayName())
                .append(Component.text(" » "))
                .append(filteredMessage)
                .build();

        receiver.sendMessage(directMessage);
        player.sendMessage(directMessage);
        setLastMessage(receiver.getUniqueId(),player.getUniqueId());
    }
}
