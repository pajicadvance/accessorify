package me.pajic.accessorify.platform.fabric;

//? fabric {

import me.pajic.accessorify.platform.Platform;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;

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
		//? if > 1.20.1
		return FabricLoader.getInstance().getRawGameVersion();
		//? if 1.20.1
		//return SharedConstants.VERSION_STRING;
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
