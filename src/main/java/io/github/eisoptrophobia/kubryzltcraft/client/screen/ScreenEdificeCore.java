package io.github.eisoptrophobia.kubryzltcraft.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import io.github.eisoptrophobia.kubryzltcraft.Kubryzltcraft;
import io.github.eisoptrophobia.kubryzltcraft.block.entity.container.ContainerEdificeCore;
import io.github.eisoptrophobia.kubryzltcraft.warfare.edifice.Edifice;
import io.github.eisoptrophobia.kubryzltcraft.warfare.edifice.EdificeUtils;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.CapabilityItemHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScreenEdificeCore extends ContainerScreen<ContainerEdificeCore> {
	
	private final ResourceLocation GUI = new ResourceLocation(Kubryzltcraft.MOD_ID, "textures/gui/container/edifice_core.png");
	private final List<ItemStack> items = new ArrayList<>();
	
	public ScreenEdificeCore(ContainerEdificeCore container, PlayerInventory inventory, ITextComponent title) {
		super(container, inventory, title);
	}
	
	@Override
	protected void init() {
		super.init();
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		renderTooltip(matrixStack, mouseX, mouseY);
		for (int i = 0; i < items.size(); i ++) {
			int itemX = leftPos + 8 + (i % 4) * 18;
			int itemY = height / 2 - 66 + (i / 4) * 18;
			if (mouseX >= itemX && mouseX < itemX + 16 && mouseY >= itemY && mouseY < itemY + 16) {
				renderTooltip(matrixStack, items.get(i), mouseX, mouseY);
				break;
			}
		}
	}
	
	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
		items.clear();
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bind(GUI);
		blit(matrixStack, getGuiLeft(), getGuiTop(), 0, 0, getXSize(), getYSize());
		
		menu.getTileEntity().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
			Edifice edifice = EdificeUtils.getEdificeByBlueprint(handler.getStackInSlot(0).getItem());
			if (edifice != null) {
				EdificeUtils.StatusData missingBlocks = EdificeUtils.getMissingBlocks(menu.getTileEntity().getLevel(), edifice, menu.getTileEntity().getBlockPos(), Rotation.NONE);
				switch (missingBlocks.getValidity()) {
					case INCOMPLETE:
						int column = 0;
						int row = 0;
						for (Map.Entry<Item, List<EdificeUtils.StatusData.BlockData>> item : missingBlocks.getMissingBlocks().entrySet()) {
							ItemStack stack = new ItemStack(item.getKey(), item.getValue().size());
							renderItemStack(stack, 8 + column * 18, 66 - row * 18);
							column ++;
							if (column % 4 == 0) {
								column = 0;
								row ++;
							}
							items.add(stack);
						}
						font.draw(matrixStack, new TranslationTextComponent("kubryzltcraft.gui.edifice_core.incomplete"), leftPos + 98, topPos + 55, Color.RED.getRGB());
						break;
					case OBSTRUCTED:
						font.draw(matrixStack, new TranslationTextComponent("kubryzltcraft.gui.edifice_core.obstructed"), leftPos + 98, topPos + 55, Color.RED.getRGB());
						break;
					case VALID:
						font.draw(matrixStack, new TranslationTextComponent("kubryzltcraft.gui.edifice_core.valid"), leftPos + 98, topPos + 55, Color.GREEN.getRGB());
						break;
				}
				font.draw(matrixStack, "Radar", leftPos + 98, topPos + 33 - font.lineHeight, Color.DARK_GRAY.getRGB());
			}
		});
	}
	
	private void renderItemStack(ItemStack stack, int x, int y) {
		itemRenderer.renderAndDecorateItem(minecraft.player, stack, leftPos + x, height / 2 - y);
		itemRenderer.renderGuiItemDecorations(font, stack, leftPos + x, height / 2 - y, null);
	}
}