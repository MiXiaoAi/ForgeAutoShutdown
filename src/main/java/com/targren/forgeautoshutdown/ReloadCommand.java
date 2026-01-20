package com.targren.forgeautoshutdown;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.Logger;

/**
 * Command to reload the mod configuration without restarting the server
 */
public class ReloadCommand
{
    private static final Logger LOGGER = ForgeAutoShutdown.LOGGER;

    /** Registers the `/forgeautoshutdown reload` command */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        dispatcher.register(Commands.literal("forgeautoshutdown")
            .requires(source -> source.hasPermission(3)) // Requires OP level 3
            .then(Commands.literal("reload")
                .executes(context -> {
                    CommandSourceStack source = context.getSource();
                    
                    try
                    {
                        LOGGER.info("Reloading ForgeAutoShutdown configuration...");
                        source.sendSuccess(() -> Component.literal("§e[ForgeAutoShutdown] Reloading configuration..."), true);
                        
                        // Reload configuration
                        boolean success = ConfigReloader.reload(source.getServer());
                        
                        if (success)
                        {
                            source.sendSuccess(() -> Component.literal("§a[ForgeAutoShutdown] Configuration reloaded successfully!"), true);
                            LOGGER.info("Configuration reloaded successfully");
                        }
                        else
                        {
                            source.sendFailure(Component.literal("§c[ForgeAutoShutdown] Failed to reload configuration. Check server logs for details."));
                            LOGGER.error("Failed to reload configuration");
                        }
                        
                        return success ? 1 : 0;
                    }
                    catch (Exception e)
                    {
                        LOGGER.error("Error reloading configuration", e);
                        source.sendFailure(Component.literal("§c[ForgeAutoShutdown] Error: " + e.getMessage()));
                        return 0;
                    }
                })
            )
            .then(Commands.literal("status")
                .executes(context -> {
                    CommandSourceStack source = context.getSource();
                    
                    source.sendSuccess(() -> Component.literal("§6[ForgeAutoShutdown] Current Status:"), false);
                    source.sendSuccess(() -> Component.literal("§7- Schedule: " + (Config.scheduleEnabled.get() ? "§aEnabled" : "§cDisabled")), false);
                    source.sendSuccess(() -> Component.literal("§7- Voting: " + (Config.voteEnabled.get() ? "§aEnabled" : "§cDisabled")), false);
                    source.sendSuccess(() -> Component.literal("§7- Watchdog: " + (Config.watchdogEnabled.get() ? "§aEnabled" : "§cDisabled")), false);
                    source.sendSuccess(() -> Component.literal("§7- Idle Shutdown: " + (Config.idleShutdownEnabled.get() ? "§aEnabled" : "§cDisabled")), false);
                    
                    if (Config.idleShutdownEnabled.get())
                    {
                        source.sendSuccess(() -> Component.literal(String.format("§7  Active: %02d:%02d - %02d:%02d, Timeout: %d min",
                            Config.idleCheckStartHour.get(),
                            Config.idleCheckStartMinute.get(),
                            Config.idleCheckEndHour.get(),
                            Config.idleCheckEndMinute.get(),
                            Config.idleTimeout.get())), false);
                    }
                    
                    return 1;
                })
            )
        );

        LOGGER.debug("`/forgeautoshutdown` command registered");
    }
}
