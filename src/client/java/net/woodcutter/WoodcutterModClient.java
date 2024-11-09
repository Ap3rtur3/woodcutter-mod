package net.woodcutter;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.woodcutter.config.WoodcutterConfig;
import net.woodcutter.hud.CoordsHud;

public class WoodcutterModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		AutoConfig.register(WoodcutterConfig.class, GsonConfigSerializer::new);
		CoordsHud.register(AutoConfig.getGuiRegistry(WoodcutterConfig.class));
	}

	public static WoodcutterConfig getConfig() {
		return AutoConfig.getConfigHolder(WoodcutterConfig.class).getConfig();
	}
}