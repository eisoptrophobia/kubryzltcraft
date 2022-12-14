package io.github.eisoptrophobia.kubryzltcraft.client;

import io.github.eisoptrophobia.kubryzltcraft.ConfigCommon;
import io.github.eisoptrophobia.kubryzltcraft.Kubryzltcraft;
import io.github.eisoptrophobia.kubryzltcraft.block.entity.container.ModContainers;
import io.github.eisoptrophobia.kubryzltcraft.client.render.RendererTileEntityEdificeCore;
import io.github.eisoptrophobia.kubryzltcraft.client.render.RendererTileEntityKubryzlt;
import io.github.eisoptrophobia.kubryzltcraft.client.screen.ScreenEdificeCore;
import io.github.eisoptrophobia.kubryzltcraft.warfare.team.KubryzltcraftTeam;
import io.github.eisoptrophobia.kubryzltcraft.warfare.team.KubryzltcraftTeamManager;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = Kubryzltcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
	
	private static final HashMap<String, KubryzltcraftTeam> clientTerritoryData = new HashMap<>();
	private static final HashMap<String, Integer> clientMoraleData = new HashMap<>();
	
	public static void setup(FMLClientSetupEvent event) {
		for (ConfigCommon.TeamsConfig.TeamConfig team : ConfigCommon.getTeams()) {
			KubryzltcraftTeamManager.register(team.id, team.prefix, team.getKubryzltTexture());
		}
		
		RendererTileEntityKubryzlt.register();
		RendererTileEntityEdificeCore.register();
		
		ScreenManager.register(ModContainers.EDIFICE_CORE.get(), ScreenEdificeCore::new);
	}
	
	@SubscribeEvent
	public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
		if (event.getMap().location().equals(AtlasTexture.LOCATION_BLOCKS)) {
			for (KubryzltcraftTeam team : KubryzltcraftTeamManager.getTeams()) {
				event.addSprite(team.getKubryzltTexture());
			}
		}
	}
	
	public static HashMap<String, KubryzltcraftTeam> getClientTerritoryData() {
		return clientTerritoryData;
	}
	
	public static HashMap<String, Integer> getClientMoraleData() {
		return clientMoraleData;
	}
}