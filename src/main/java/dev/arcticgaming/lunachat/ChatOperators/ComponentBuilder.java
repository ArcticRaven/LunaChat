package dev.arcticgaming.lunachat.ChatOperators;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;

public class ComponentBuilder {

    public static TextComponent messageBuilder(Player player, Component message) {

        // Send the message component to be censored
        Component filteredMessage = new MessageProcessor().filterChat(message);

        String prefix = "%vault_prefix%";
        prefix = PlaceholderAPI.setPlaceholders(player, prefix);

        //create message
        TextComponent textComponent = Component.text()
                .content(prefix)
                .append(Component.text(" "))
                .append(player.displayName())
                .clickEvent(ClickEvent.suggestCommand("/msg " + player.getName()))
                .append(Component.text(" » "))
                .append(filteredMessage)
                .build();

        return textComponent;
    }
}
