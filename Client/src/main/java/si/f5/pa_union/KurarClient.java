package si.f5.pa_union;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.GameType;
import si.f5.pa_union.discord.DiscordRPCLink;
import zsawyer.mods.mumblelink.MumbleLinkImpl;
import zsawyer.mods.mumblelink.addons.pa.es.ExtendedPASupport;

public class KurarClient {

    private static KurarClient instance;
    public static boolean isUpdated = false;

    public KurarClient() {
        instance = this;
        new MumbleLinkImpl();
        new ExtendedPASupport();
        DiscordRPCLink.init();
    }

    public static KurarClient getInstance() {
        return instance;
    }

    public GameType getCurrentGameMode() {
        if (Minecraft.getInstance().gameMode != null) {
            return Minecraft.getInstance().gameMode.getPlayerMode();
        }
        return GameType.DEFAULT_MODE;
    }

    public boolean canSeeTrueTabList() {
        return getCurrentGameMode() == GameType.CREATIVE || getCurrentGameMode() == GameType.SPECTATOR;
    }
}
