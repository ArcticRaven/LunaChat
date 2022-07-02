package dev.arcticgaming.lunachatmc.EventHandlers;

import dev.arcticgaming.lunachatmc.ChatOperators.ComponentBuilder;
import dev.arcticgaming.lunachatmc.Utility;
import github.scarsz.discordsrv.api.ListenerPriority;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessageReceivedEvent;
import github.scarsz.discordsrv.api.events.DiscordGuildMessageSentEvent;
import github.scarsz.discordsrv.api.events.DiscordReadyEvent;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class DiscordListener implements Listener {

    Server server = Bukkit.getServer();

    private Plugin plugin;
    public DiscordListener() {
        this.plugin = plugin;
    }

    @Subscribe
    public void discordReadyEvent(DiscordReadyEvent event) {
        // Example of using JDA's events
        // We need to wait until DiscordSRV has initialized JDA, thus we're doing this inside DiscordReadyEvent
        DiscordUtil.getJda().addEventListener(new JDAListener(plugin));
    }

    @Subscribe (priority = ListenerPriority.MONITOR)
    public void discordMessageReceived(DiscordGuildMessageReceivedEvent event) {

        server.sendMessage(ComponentBuilder.discordBuilder(event.getAuthor().getName(), Utility.formatResolve(event.getMessage().toString())));
    }

    @Subscribe(priority = ListenerPriority.MONITOR)
    public void aMessageWasSentInADiscordGuildByTheBot(DiscordGuildMessageSentEvent event) {
        // Example of logging a message sent in Minecraft (being sent to Discord)
    }


}
