package si.f5.pa_union.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class DiscordRPCLink {
    public static String details = "";
    public static String UserName;
    public static long TimeStamp;

    public static void init() {
        DiscordRPC lib = DiscordRPC.INSTANCE;
        String applicationId = "850964366945288223";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Ready!");
        lib.Discord_Initialize(applicationId, handlers, true, steamId);
        DiscordRichPresence presence = new DiscordRichPresence();
        TimeStamp = System.currentTimeMillis() / 1000;
        presence.startTimestamp = TimeStamp;
        presence.details = "In Menu";
        presence.largeImageText = "Kurar Client";
        presence.state = UserName;
        presence.largeImageKey = "1_17_kurar_logo";
        lib.Discord_UpdatePresence(presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "DiscordRPC").start();
    }

    public static void update() {
        DiscordRPC lib = DiscordRPC.INSTANCE;
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.details = details;
        presence.state = UserName;
        presence.largeImageText = "Kurar Client";
        presence.largeImageKey = "1_17_kurar_logo";
        lib.Discord_UpdatePresence(presence);
    }
}
