package si.f5.pa_union.update;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class UpdateEntry {
    private final TextComponent title;
    private final List<TextComponent> lines;

    public UpdateEntry(String title) {
        this.title = new TextComponent(title);
        lines = new ArrayList<>();
    }

    public void addLine(String line) {
        lines.add(new TextComponent(line.replace("-", ChatFormatting.BOLD + "・")));
    }

    public TextComponent getTitle() {
        return title;
    }

    public List<TextComponent> getLines() {
        return lines;
    }
}
