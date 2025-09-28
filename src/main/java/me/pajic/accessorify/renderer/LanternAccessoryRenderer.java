package me.pajic.accessorify.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
//$ accessory_renderer
import io.wispforest.accessories.api.client.AccessoryRenderer;
//$ simple_accessory_renderer
import io.wispforest.accessories.api.client.SimpleAccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
//? if <= 1.21.1
import net.minecraft.world.entity.LivingEntity;
//? if > 1.21.1 {
/*import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
*///?}
//? if >= 1.21.8 {
/*import io.wispforest.accessories.api.slot.SlotPath;
*///?}

public class LanternAccessoryRenderer implements SimpleAccessoryRenderer {
    //? if <= 1.21.1 {
    @Override
    public <M extends LivingEntity> void render(
            ItemStack stack,
            SlotReference reference,
            PoseStack matrices,
            EntityModel<M> model,
            MultiBufferSource multiBufferSource,
            int light,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        align(stack, reference, model, matrices);
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
                Block.byItem(stack.getItem()).defaultBlockState(),
                matrices, multiBufferSource, light, OverlayTexture.NO_OVERLAY
        );
    }

    @Override
    public <M extends LivingEntity> void align(ItemStack stack, SlotReference reference, EntityModel<M> model, PoseStack matrices) {
        if (model instanceof HumanoidModel<? extends LivingEntity> humanoidModel) {
            Vec3 offset = MultiVersionUtil.hasArmor(reference) ? new Vec3(0.05f, -1.25f, 0.05f) : new Vec3(-0.1f, -1.25f, -0.1f);
            AccessoryRenderer.transformToModelPart(matrices, humanoidModel.body, offset.x, offset.y, offset.z);
        }
    }
    //?}
    //? > 1.21.1 {
    /*@Override
    public <S extends LivingEntityRenderState> void render(
            ItemStack stack,
            //? if < 1.21.8
            SlotReference reference,
            //? if >= 1.21.8
            /^SlotPath reference,^/
            PoseStack matrices,
            EntityModel<S> model,
            S renderState,
            MultiBufferSource multiBufferSource,
            int light,
            float partialTicks
    ) {
        align(stack, reference, model, renderState, matrices);
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
                Block.byItem(stack.getItem()).defaultBlockState(),
                matrices, multiBufferSource, light, OverlayTexture.NO_OVERLAY
        );
    }

    @Override
    public <S extends LivingEntityRenderState> void align(
            ItemStack itemStack,
            //? if < 1.21.8
            SlotReference reference,
            //? if >= 1.21.8
            /^SlotPath reference,^/
            EntityModel<S> entityModel,
            S renderState,
            PoseStack poseStack
    ) {
        if (entityModel instanceof HumanoidModel<? extends HumanoidRenderState> humanoidModel) {
            Vec3 offset = MultiVersionUtil.hasArmor(/^? < 1.21.8 {^/reference/^?} else {^//^renderState^//^?}^/) ? new Vec3(0.05f, -1.25f, 0.05f) : new Vec3(-0.1f, -1.25f, -0.1f);
            AccessoryRenderer.transformToModelPart(poseStack, humanoidModel.body, offset.x, offset.y, offset.z);
        }
    }
    *///?}
}
