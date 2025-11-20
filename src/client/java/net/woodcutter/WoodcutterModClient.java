package net.woodcutter;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.woodcutter.config.ModConfig;
import net.woodcutter.hud.CoordsHud;
//import net.woodcutter.hud.RulerHud;

public class WoodcutterModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
		GuiRegistry guiRegistry = AutoConfig.getGuiRegistry(ModConfig.class);
		CoordsHud.register(guiRegistry);
		//RulerHud.register();
	}

	public static ModConfig getConfig() {
		return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
	}
}