package io.github.eisoptrophobia.kubryzltcraft.warfare.edifice;

import io.github.eisoptrophobia.kubryzltcraft.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.util.math.vector.Vector3i;

public class EdificeRadar extends Edifice {
	
	private static final Vector3i offset = new Vector3i(-1, 1, -1);
	
	@Override
	public Item getBlueprint() {
		return ModItems.BLUEPRINT_RADAR.get();
	}
	
	@Override
	public Vector3i getOffset() {
		return offset;
	}
	
}