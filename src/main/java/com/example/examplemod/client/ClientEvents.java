package com.example.examplemod.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
@OnlyIn(Dist.CLIENT)
public class ClientEvents {
    BlockPos pos1 = new BlockPos(1, 1, 1);
    BlockPos pos2 = new BlockPos(10, 10, 10);
    AxisAlignedBB blocks = new AxisAlignedBB(pos2, pos1);


    /**
     Render Bounding box around structure, for debugging
     But rendering bounding box around each piece would be better
     This only works in third person :|
     This also probably creates so many boxes each time.
     Redoing all this data is not idea.
     */

    // Render BoundingBox in third person, RenderWorldLastEvent has problems rendering in third person,
    // and we are unable to render this in first person.

    @SubscribeEvent
    public void onRenderEntityEvent(RenderLivingEvent event) {
        if (!Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            render(event.getMatrixStack(), event.getPartialRenderTick());
        }
    }


    // Render in first person.
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderWorldEvent(RenderWorldLastEvent event) {
        if (Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            render(event.getMatrixStack(), event.getPartialTicks());
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void render(MatrixStack stack, float ticks) {
        Entity player = Minecraft.getInstance().cameraEntity;

        if (player != null) {
            IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
            IVertexBuilder builder = buffer.getBuffer(RenderType.LINES);

            double x = player.xOld + (player.getX() - player.xOld) * ticks;
            double y = player.yOld + (player.getY() - player.yOld) * ticks;
            double z = player.zOld + (player.getZ() - player.zOld) * ticks;

            // If we're first person subtract our player height, because it offsets.
            if (Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                y += 1.6;
            }

            stack.pushPose();
            stack.translate(-x, -y, -z);
            RenderSystem.enableDepthTest();
            WorldRenderer.renderLineBox(stack, builder, blocks,1.0F, 1.0F, 1.0F, 1.0F);
            stack.popPose();
            buffer.endBatch(RenderType.LINES);
        }
    }
}
