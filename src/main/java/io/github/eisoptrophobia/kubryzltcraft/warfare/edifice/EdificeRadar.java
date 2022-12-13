package io.github.eisoptrophobia.kubryzltcraft.warfare.edifice;

import io.github.eisoptrophobia.kubryzltcraft.Kubryzltcraft;
import io.github.eisoptrophobia.kubryzltcraft.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;

public class EdificeRadar extends Edifice {
	
	private static Vector3i offset = new Vector3i(-1, 1, -1);
	
	@Override
	public Item getBlueprint() {
		return ModItems.BLUEPRINT_RADAR.get();
	}
	
	@Override
	public CompoundNBT getNBT() {
		return null;
	}
	
	@Override
	public Vector3i getOffset() {
		return offset;
	}
	
	@Override
	public boolean isValid(BlockPos pos, World world) {
		return false;
	}
	
	@Override
	public List<ItemStack> getMissingBlocks(BlockPos pos, World world) {
		return null;
	}
	
	@Override
	public void placeBlock(BlockPos pos, World world, ItemStack item) {
	
	}
}