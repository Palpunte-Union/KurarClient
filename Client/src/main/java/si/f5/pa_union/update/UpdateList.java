package si.f5.pa_union.update;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.ScrollPanel;

import java.util.List;

public class UpdateList extends ScrollPanel {
    private static final int PADDING = 6;
    List<Component> lines;

    public UpdateList(Minecraft client, int width, int height, int top, int left, List<Component> lines) {
        super(client, width, height, top, left);
        this.lines = lines;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {}

    @Override
    public int getContentHeight()
    {
        int height = 50;
        height += (lines.size() * Minecraft.getInstance().font.lineHeight);
        if (height < this.bottom - this.top - 8)
            height = this.bottom - this.top - 8;
        return height;
    }

    @Override
    protected void drawPanel(PoseStack mStack, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
        for (Component line : lines)
        {
            if (line != null)
            {
                RenderSystem.enableBlend();
                Minecraft.getInstance().font.drawShadow(mStack, line, left + PADDING, relativeY, 0xFFFFFF);
                RenderSystem.disableBlend();
            }
            relativeY += Minecraft.getInstance().font.lineHeight;
        }
    }
}
