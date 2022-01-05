package si.f5.pa_union.update;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;

import java.util.List;

public class UpdateList extends ObjectSelectionList<UpdateList.Entry> {
    int size;
    public UpdateList(Minecraft p_94010_, int p_94011_, int p_94012_, int p_94013_, int p_94014_, int p_94015_, List<Component> entries) {
        super(p_94010_, p_94011_, p_94012_, 0, p_94012_ - 32, 2 * p_94010_.font.lineHeight + 8);
        addEntry(new Entry(entries));
        size = entries.size();
    }

    @Override
    public int getMaxScroll() {
        return Math.max(0, Minecraft.getInstance().font.lineHeight * size - (this.y1 - this.y0 - 4));
    }

    @Override
    protected int getScrollbarPosition()
    {
        return this.width - 30;
    }

    @Override
    public int getRowWidth()
    {
        return this.width;
    }

    public static class Entry extends ObjectSelectionList.Entry<UpdateList.Entry>  {
        final List<Component> entry;

        public Entry(List<Component> entry) {
            this.entry = entry;
        }

        @Override
        public void render(PoseStack pStack, int entryIdx, int top, int left, final int entryWidth, final int entryHeight, final int mouseX, final int mouseY, final boolean p_194999_5_, final float partialTicks) {
            Font font = Minecraft.getInstance().font;
            final List<Component> strings = entry;
            int y = top + 2;
            for (int i = 0; i < Math.max(strings.size(), 2); i++) {
                font.draw(pStack, strings.get(i), left + 5, y, 0xFFFFFF);
                y += font.lineHeight;
            }
        }

        @Override
        public Component getNarration() {
            return entry.get(0);
        }
    }
}
