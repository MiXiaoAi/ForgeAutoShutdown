package com.targren.forgeautoshutdown;

import com.targren.forgeautoshutdown.util.Server;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Monitors server for idle players during specified time period and shuts down after timeout
 */
public class IdleShutdownTask extends TimerTask
{
    private static IdleShutdownTask INSTANCE;
    private static MinecraftServer SERVER;
    private static Logger LOGGER;
    private static Timer TIMER;

    private int idleMinutes = 0;
    private boolean wasEmpty = false;

    public static void create(MinecraftServer server)
    {
        if (INSTANCE != null)
        {
            LOGGER.warn("IdleShutdownTask already exists, stopping old instance");
            stop();
        }

        INSTANCE = new IdleShutdownTask();
        SERVER = server;
        LOGGER = ForgeAutoShutdown.LOGGER;

        TIMER = new Timer("ForgeAutoShutdown idle checker", true); // Set as daemon thread
        int intervalMs = Config.idleCheckInterval.get() * 60 * 1000;
        
        TIMER.schedule(INSTANCE, intervalMs, intervalMs);
        LOGGER.info("Idle shutdown monitor started. Active from {}:{:02d} to {}:{:02d}, timeout: {} minutes",
            Config.idleCheckStartHour.get(),
            Config.idleCheckStartMinute.get(),
            Config.idleCheckEndHour.get(),
            Config.idleCheckEndMinute.get(),
            Config.idleTimeout.get());
    }

    /** Stops the idle shutdown task and cleans up resources */
    public static void stop()
    {
        if (TIMER != null)
        {
            TIMER.cancel();
            TIMER = null;
        }
        
        if (INSTANCE != null)
        {
            INSTANCE.cancel();
            INSTANCE = null;
        }
        
        LOGGER.debug("IdleShutdownTask stopped");
    }

    @Override
    public void run()
    {
        if (!isWithinActiveTime())
        {
            // Reset idle counter when outside active time
            if (idleMinutes > 0)
            {
                LOGGER.debug("Outside active time period, resetting idle counter");
                idleMinutes = 0;
                wasEmpty = false;
            }
            return;
        }

        boolean isEmpty = !Server.hasRealPlayers(SERVER);

        if (isEmpty)
        {
            if (!wasEmpty)
            {
                // Server just became empty
                wasEmpty = true;
                idleMinutes = Config.idleCheckInterval.get();
                LOGGER.info("Server is now empty. Will shutdown after {} minutes of idle time", Config.idleTimeout.get());
            }
            else
            {
                // Server continues to be empty
                idleMinutes += Config.idleCheckInterval.get();
                LOGGER.debug("Server idle for {} minutes (timeout: {})", idleMinutes, Config.idleTimeout.get());

                if (idleMinutes >= Config.idleTimeout.get())
                {
                    LOGGER.info("Server has been idle for {} minutes. Initiating shutdown...", idleMinutes);
                    Server.shutdown(SERVER, Component.literal("Server shutdown due to inactivity"));
                }
            }
        }
        else
        {
            // Server has players
            if (wasEmpty && idleMinutes > 0)
            {
                LOGGER.info("Players detected, resetting idle counter (was idle for {} minutes)", idleMinutes);
            }
            idleMinutes = 0;
            wasEmpty = false;
        }
    }

    private boolean isWithinActiveTime()
    {
        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        int currentMinute = now.get(Calendar.MINUTE);

        int startHour = Config.idleCheckStartHour.get();
        int startMinute = Config.idleCheckStartMinute.get();
        int endHour = Config.idleCheckEndHour.get();
        int endMinute = Config.idleCheckEndMinute.get();

        int currentTime = currentHour * 60 + currentMinute;
        int startTime = startHour * 60 + startMinute;
        int endTime = endHour * 60 + endMinute;

        if (startTime <= endTime)
        {
            // Normal case: start time is before end time (e.g., 08:00 to 23:00)
            return currentTime >= startTime && currentTime <= endTime;
        }
        else
        {
            // Crosses midnight (e.g., 22:00 to 02:00)
            return currentTime >= startTime || currentTime <= endTime;
        }
    }

    private IdleShutdownTask() { }
}
