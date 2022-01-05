package si.f5.pa_union.update;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

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
        try {
            Path path = Path.of(Objects.requireNonNull(this.getClass().getClassLoader().getResource("update-info.txt")).getPath());
            List<UpdateEntry> entries = new ArrayList<>();
            AtomicReference<UpdateEntry> entry = new AtomicReference<>();
            Files.readAllLines(path, StandardCharsets.UTF_8).forEach((s -> {
                if (s.startsWith("#") || entry.get() == null) {
                    if (entry.get() != null) {
                        entries.add(entry.get());
                    }
                    entry.set(new UpdateEntry(s.replaceFirst("#", "")));
                } else {
                    entry.get().addLine(s);
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
