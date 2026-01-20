package com.targren.forgeautoshutdown;

import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Logger;

/**
 * Handles hot-reloading of configuration and restarting tasks
 */
public class ConfigReloader
{
    private static final Logger LOGGER = ForgeAutoShutdown.LOGGER;

    /**
     * Reloads the configuration and restarts all tasks
     * @param server The Minecraft server instance
     * @return true if reload was successful
     */
    public static boolean reload(MinecraftServer server)
    {
        try
        {
            // Stop existing tasks
            stopAllTasks();
            
            // Force reload config spec
            LOGGER.info("Reloading configuration from file...");
            Config.SPEC.afterReload();
            
            // Validate new config
            Config.validate();
            
            // Check if anything is enabled
            if (Config.isNothingEnabled())
            {
                LOGGER.warn("No ForgeAutoShutdown features are enabled after reload.");
                return true; // Not an error, just a warning
            }
            
            // Restart tasks based on new config
            if (Config.scheduleEnabled.get())
            {
                LOGGER.info("Restarting schedule task...");
                ShutdownTask.create(server);
            }
            
            if (Config.watchdogEnabled.get())
            {
                LOGGER.info("Restarting watchdog task...");
                WatchdogTask.create(server);
            }
            
            if (Config.idleShutdownEnabled.get())
            {
                LOGGER.info("Restarting idle shutdown task...");
                IdleShutdownTask.create(server);
            }
            
            return true;
        }
        catch (Exception e)
        {
            LOGGER.error("Failed to reload configuration", e);
            return false;
        }
    }

    /**
     * Stops all running tasks
     */
    private static void stopAllTasks()
    {
        LOGGER.info("Stopping all tasks...");
        
        try
        {
            ShutdownTask.stop();
        }
        catch (Exception e)
        {
            LOGGER.warn("Error stopping ShutdownTask", e);
        }
        
        try
        {
            WatchdogTask.stop();
        }
        catch (Exception e)
        {
            LOGGER.warn("Error stopping WatchdogTask", e);
        }
        
        try
        {
            IdleShutdownTask.stop();
        }
        catch (Exception e)
        {
            LOGGER.warn("Error stopping IdleShutdownTask", e);
        }
    }
}
