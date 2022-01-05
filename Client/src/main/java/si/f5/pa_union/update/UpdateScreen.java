package si.f5.pa_union.update;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class UpdateScreen extends Screen {
    boolean isLoad;
    UpdateList list;

    public UpdateScreen() { this(false); }

    public UpdateScreen(boolean isLoading) {
        super(new TextComponent("アップデート内容"));
        isLoad = isLoading;
    }

    public void init() {
        super.init();
        this.clearWidgets();
        try {
            Path path = Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("update-info.txt")).toURI());
            final List<Component> entries = new ArrayList<>();
            Files.readAllLines(path, StandardCharsets.UTF_8).forEach((s -> {
                if (s.startsWith("#")) {
                    entries.add(new TextComponent(s.replace("#", "")).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.UNDERLINE));
                } else if (s.startsWith("-")){
                    entries.add(new TextComponent(s.replace("-", "・")).withStyle(ChatFormatting.BOLD));
                } else {
                    entries.add(new TextComponent(s));
                }
            }));
            this.list = new UpdateList(this.minecraft, this.width, this.height, 0, this.height, 25, entries);
            this.addWidget(list);
            this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 27, 200, 20, new TextComponent("閉じる"), (p_96827_) -> {
                Minecraft.getInstance().setScreen(null);
            }));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(PoseStack p_96562_, int p_96563_, int p_96564_, float p_96565_) {
        this.renderBackground(p_96562_);
        this.list.render(p_96562_, p_96563_, p_96564_, p_96565_);
        super.render(p_96562_, p_96563_, p_96564_, p_96565_);
    }
}
