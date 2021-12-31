/*
 mod_MumbleLink - Positional Audio Communication for Minecraft with Mumble
 Copyright 2011-2013 zsawyer (http://sourceforge.net/users/zsawyer)

 This file is part of mod_MumbleLink
 (http://sourceforge.net/projects/modmumblelink/).

 mod_MumbleLink is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 mod_MumbleLink is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with mod_MumbleLink.  If not, see <http://www.gnu.org/licenses/>.

 */

package zsawyer.mods.mumblelink;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import zsawyer.mods.mumblelink.api.MumbleLink;
import zsawyer.mods.mumblelink.api.MumbleLinkAPI;
import zsawyer.mods.mumblelink.mumble.ExtendedUpdateData;

/**
 * mod to link with mumble for positional audio
 * <p>
 * this is a forge based implementation (Forge 1.15+)
 *
 * @author zsawyer, 2013-04-09
 */
@OnlyIn(Dist.CLIENT)
public class MumbleLinkImpl extends MumbleLinkBase implements MumbleLink {
    public static Logger LOG = LogManager.getLogger();

    public static MumbleLinkImpl instance;

    private UpdateTicker updateTicker;
    private MumbleLinkAPIImpl api;

    public MumbleLinkImpl() {
        this.preInit();
        instance = this;
    }

    private boolean enabled = true;
    private boolean debug = false;

    @OnlyIn(Dist.CLIENT)
    public void setup() {
        LOG.debug("setup started");
        if (enabled) {
            load();
            LOG.info("loaded and active");
        }
        LOG.trace("setup finished");
    }

    public void preInit() {
        loadConfig();
    }

    private void loadConfig() {
        debug = false;
        enabled = true;
    }

    public void load() {
        super.load();
        initComponents();
        activate();
    }

    private void initComponents() {
        ExtendedUpdateData extendedUpdateData = new ExtendedUpdateData(library, errorHandler);
        mumbleData = extendedUpdateData;
        updateTicker = new UpdateTicker();
        api = new MumbleLinkAPIImpl();
        api.setExtendedUpdateData(extendedUpdateData);
    }

    @Override
    public MumbleLinkAPI getApi() {
        return api;
    }

    @Override
    public void activate() {
        updateTicker.activate();
    }

    @Override
    public void deactivate() {
        updateTicker.deactivate();
    }

    @Override
    public boolean debugging() {
        return debug;
    }

    public static boolean debug() {
        return instance.debug;
    }

    @Override
    public String getName() {
        return "MumbleLink";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    public UpdateTicker getUpdateTicker() {
        return updateTicker;
    }
}
