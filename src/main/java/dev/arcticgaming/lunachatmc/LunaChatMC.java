package dev.arcticgaming.lunachatmc;

import dev.arcticgaming.lunachatmc.ChatOperators.DirectMessages;
import dev.arcticgaming.lunachatmc.EventHandlers.ChatEvent;
import dev.arcticgaming.lunachatmc.EventHandlers.DiscordListener;
import me.clip.placeholderapi.libs.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class LunaChatMC extends JavaPlugin implements @NotNull Listener {

    private BukkitAudiences adventure;

    public static LunaChatMC plugin;
    public static LunaChatMC getPlugin() {
        return plugin;
    }

    public @NonNull BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access adventure when plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.adventure = BukkitAudiences.create(this);
        plugin = this;

        Bukkit.getPluginManager().registerEvents(this, this);
        final PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ChatEvent(), this);
        pm.registerEvents(new DiscordListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player player) {

            //String Builders for Direct Messaging
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < args.length; ) {
                message.append(args[i]).append(" ");
                i++;
            }
            String messageString = message.toString();

            StringBuilder dmMessage = new StringBuilder();
            for (int i = 1; i < args.length; ) {
                dmMessage.append(args[i]).append(" ");
                i++;
            }
            String dmMessageString = dmMessage.toString();

            UUID uuid = player.getUniqueId();

            switch (label) {
                case "message","msg","tell","whisper","pm" :
                    if (!(Bukkit.getPlayer(args[0]) == null)) {
                        DirectMessages.sendDirectMessage(player, (Bukkit.getPlayer(args[0])), dmMessageString);
                    } else {
                        DirectMessages.noDirectMessengers(uuid);
                    }
                    break;

                case "reply","r":
                    if (uuid.toString().equalsIgnoreCase(DirectMessages.getLastMessage(uuid).toString())) {
                        DirectMessages.noDirectMessengers(uuid);
                    } else {
                        DirectMessages.sendDirectMessage(player, Bukkit.getPlayer(DirectMessages.getLastMessage(uuid)), messageString);
                    }
                    break;
                default:
                    player.sendMessage("That command doesn't seem to exist");

            }
        }
        else {
             plugin.getLogger().info("Non-player attempted to run a command, that's just silly uwu");
            }

        return false;
    }
}
