package si.f5.pa_union.reconnect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;

public class Reconnect {
    public static String serverIP;
    public static Screen lastScreen;
    public static boolean isLan;

    public static void reconnect() {
        ConnectScreen.startConnecting(lastScreen, Minecraft.getInstance(), ServerAddress.parseString(serverIP), new ServerData("Reconnect", serverIP, isLan));
    }
}
