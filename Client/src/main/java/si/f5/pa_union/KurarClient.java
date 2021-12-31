package si.f5.pa_union;

import si.f5.pa_union.discord.DiscordRPCLink;
import zsawyer.mods.mumblelink.MumbleLinkImpl;
import zsawyer.mods.mumblelink.addons.pa.es.ExtendedPASupport;

public class KurarClient {

    private static KurarClient instance;

    public KurarClient() {
        instance = this;
        new MumbleLinkImpl();
        new ExtendedPASupport();
        DiscordRPCLink.init();
    }

    public static KurarClient getInstance() {
        return instance;
    }
}
