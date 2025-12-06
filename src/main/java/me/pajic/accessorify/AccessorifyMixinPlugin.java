package me.pajic.accessorify;

import com.moulberry.mixinconstraints.MixinConstraints;
import com.moulberry.mixinconstraints.mixin.MixinConstraintsBootstrap;
import me.pajic.accessorify.util.CompatFlags;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class AccessorifyMixinPlugin implements IMixinConfigPlugin {

	private String mixinPackage;

    private static final List<String> DISABLE_IF_FORGE = List.of(
            "me.pajic.accessorify.mixin.legacy_elytra.LivingEntityElytraCheckMixin",
            "me.pajic.accessorify.mixin.legacy_elytra.LocalPlayerElytraCheckMixin",
            "me.pajic.accessorify.mixin.legacy_elytra.CapeLayerElytraCheckMixin"
    );

    private static final List<String> DISABLE_IF_FABRIC = List.of(
            "me.pajic.accessorify.mixin.compat.dynlights.DynamicLightsHandlersMixin"
    );

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return checkConstraint(
				//? if > 1.21.1 || neoforge {
				true
				 //?} else if 1.20.1 {
				/*CompatFlags.CONNECTOR_PRESENT ?
						!DISABLE_IF_FORGE.contains(mixinClassName) :
						!DISABLE_IF_FABRIC.contains(mixinClassName)
				*///?} else if 1.21.1 {
				/*!CompatFlags.CONNECTOR_PRESENT || !DISABLE_IF_FORGE.contains(mixinClassName)
				*///?}
				, mixinClassName
		);
	}

	private boolean checkConstraint(boolean b, String mixinClassName) {
		if (this.mixinPackage != null && !mixinClassName.startsWith(this.mixinPackage)) {
			return b;
		}
		return b && MixinConstraints.shouldApplyMixin(mixinClassName);
	}

    @Override
    public void onLoad(String mixinPackage) {
		this.mixinPackage = mixinPackage;
		MixinConstraintsBootstrap.init(mixinPackage);
	}

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public void acceptTargets(Set<String> set, Set<String> set1) {}

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {}

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {}
}
