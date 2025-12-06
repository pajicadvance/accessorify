package me.pajic.accessorify;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.accessorify.config.ModConfig;
import me.pajic.accessorify.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? fabric {
import me.pajic.accessorify.platform.fabric.FabricPlatform;
//?} neoforge {
/*import me.pajic.accessorify.platform.neoforge.NeoforgePlatform;
*///?}

@SuppressWarnings("LoggingSimilarMessage")
public class Accessorify {

	public static final String MOD_ID = /*$ mod_id*/ "accessorify";
	public static final String MOD_VERSION = /*$ mod_version*/ "2.4.0";
	public static final String MOD_FRIENDLY_NAME = /*$ mod_name*/ "Accessorify";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ResourceLocation CONFIG_RL = id("config");
	public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);
	private static final Platform PLATFORM = createPlatformInstance();

	public static void onInitialize() {
	}

	public static void onInitializeClient() {
		AccessorifyClient.init();
	}

	public static Platform xplat() {
		return PLATFORM;
	}

	private static Platform createPlatformInstance() {
		//? fabric {
		return new FabricPlatform();
		//?} neoforge {
		/*return new NeoforgePlatform();
		*///?}
	}

	public static ResourceLocation id(String path) {
		//? if 1.20.1
		//return new ResourceLocation(MOD_ID, path);
		//? if > 1.20.1
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	public static void debugLog(String message, Object ... args) {
		if (PLATFORM.isDebug()) LOGGER.info(message, args);
	}
}
