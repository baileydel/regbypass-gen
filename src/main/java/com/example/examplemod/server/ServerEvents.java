package com.example.examplemod.server;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerEvents {
    BlockPos pos1 = new BlockPos(1, 1, 1);
    BlockPos pos2 = new BlockPos(10, 10, 10);
    AxisAlignedBB blocks = new AxisAlignedBB(pos2, pos1);


    @SubscribeEvent
    public void onRegisterCommands(BlockEvent.BreakEvent event) {
        if (!event.getPlayer().isCreative()) {
            BlockPos p = event.getPos();

            if (isWithin(p)) {
                event.setCanceled(true);
                event.getPlayer().sendMessage(new StringTextComponent("cannot break blocks here "), event.getPlayer().getUUID());

            }
        }
    }

    @SubscribeEvent
    public void Explode(ExplosionEvent event) {
        // Radius of explosion
        double r = 5.2;
        BlockPos p = new BlockPos(event.getExplosion().getPosition()).offset(-r, -r, -r);
        BlockPos p2 = new BlockPos(event.getExplosion().getPosition()).offset(r, r, r);
        AxisAlignedBB e = new AxisAlignedBB(p2, p);

        if (e.intersects(blocks)) {
            event.setCanceled(true);
        }
    }



    public boolean isWithin(BlockPos p) {
        boolean inX = p.getX() >= blocks.minX && p.getX() <= blocks.maxX;
        boolean inY = p.getY() >= blocks.minY && p.getY() <= blocks.maxY;
        boolean inZ = p.getZ() >= blocks.minZ && p.getZ() <= blocks.maxZ;

        return inX && inY && inZ;
    }
}
