package si.f5.pa_union.update;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;

public class UpdateScreen extends Screen {
    boolean isLoad;
    static boolean FLAG = true;
    public UpdateScreen() { this(false); }

    public UpdateScreen(boolean isLoading) {
        super(new TextComponent("アップデート内容"));
        isLoad = isLoading;
        if (FLAG) {
            init();
            FLAG = false;
        }
    }

    public void init() {

    }
}
