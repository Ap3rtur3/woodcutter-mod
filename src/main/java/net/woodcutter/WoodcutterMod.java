package net.woodcutter;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WoodcutterMod implements ModInitializer {
	public static final String MOD_ID = "woodcutter";
	public static final Logger LOGGER = logger("init");

	public static Logger logger(String name) {
		return LoggerFactory.getLogger(String.format("%s/%s", MOD_ID, name));
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Loading woodcutter mod");
		ModItems.initialize();
	}
}