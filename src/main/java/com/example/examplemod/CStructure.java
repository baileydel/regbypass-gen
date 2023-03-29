package com.example.examplemod;


import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nonnull;

public class CStructure extends Structure<NoFeatureConfig> {

    public CStructure() {
        super(NoFeatureConfig.CODEC);
    }

    @Nonnull
    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Nonnull
    @Override
    public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {
        CStructure structure;

        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
            structure = (CStructure) structureIn;
        }

        @Override
        public void generatePieces(@Nonnull DynamicRegistries dynamicRegistries, @Nonnull ChunkGenerator chunkGenerator, @Nonnull TemplateManager templateManager, int chunkX, int chunkZ, @Nonnull Biome biomeIn, @Nonnull NoFeatureConfig config) {
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;

            BlockPos blockPos = new BlockPos(x, 30, z);

            JigsawManager.addPieces(
                dynamicRegistries,
                new VillageConfig(() -> dynamicRegistries.registry(Registry.TEMPLATE_POOL_REGISTRY).get().get(new ResourceLocation("examplemod", "house/start_pool")), 10),
                AbstractVillagePiece::new,
                chunkGenerator,
                    templateManager,
                    blockPos,
                    this.pieces,
                this.random,
                false,
                true
            );

            this.pieces.forEach(piece -> piece.move(0, 1, 0));
            this.pieces.forEach(piece -> piece.getBoundingBox().y0 -= 1);

            this.calculateBoundingBox();

            //TODO when we save a chunk this will be written to it's own file of structure coordinates.

            System.out.println(
                    this.structure.toString() + " at " +
                    this.pieces.get(0).getBoundingBox().x0 + " " +
                    this.pieces.get(0).getBoundingBox().y0 + " " +
                    this.pieces.get(0).getBoundingBox().z0
            );
        }
    }
}
