package me.pajic.accessorify.platform.fabric;

//? fabric {

import me.pajic.accessorify.platform.Platform;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatform implements Platform {

	@Override
	public boolean isModLoaded(String modId) {
		return FabricLoader.getInstance().isModLoaded(modId);
	}

	@Override
	public ModLoader loader() {
		return ModLoader.FABRIC;
	}

	@Override
	public String mcVersion() {
		return FabricLoader.getInstance().getRawGameVersion();
	}

	@Override
	public String packPath(VersionedPackType versionedPackType) {
		return mcVersion().replace(".", "_") + versionedPackType.getName();
	}

	@Override
	public boolean isDebug() {
		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}
}
//?}
