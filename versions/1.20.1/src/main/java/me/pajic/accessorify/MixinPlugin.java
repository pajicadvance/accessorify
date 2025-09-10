package me.pajic.accessorify;

import me.pajic.accessorify.util.compat.CompatFlags;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {

    private static final List<String> DISABLE_IF_FORGE = List.of(
            "me.pajic.accessorify.mixin.LivingEntityElytraCheckMixin",
            "me.pajic.accessorify.mixin.LocalPlayerElytraCheckMixin",
            "me.pajic.accessorify.mixin.CapeLayerElytraCheckMixin"
    );

    @Override
    public void onLoad(String s) {}

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return !CompatFlags.CONNECTOR_PRESENT || !DISABLE_IF_FORGE.contains(mixinClassName);
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
