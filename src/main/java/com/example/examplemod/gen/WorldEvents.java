package com.example.examplemod.gen;


import com.mojang.serialization.Codec;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


@Mod.EventBusSubscriber(modid = "examplemod")
public class WorldEvents {

    @SubscribeEvent
    public static void BiomeLoadingEvent(final BiomeLoadingEvent event) {
        if (event.getName() != null) {
            List<Supplier<StructureFeature<?, ?>>> structures = event.getGeneration().getStructures();
            structures.add(() -> ModStructures.HOUSE.get().configured(IFeatureConfig.NONE));
        }
    }

    @SubscribeEvent
    public static void spacing(final WorldEvent.Load event) {

        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();

            try {
                Method GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));

                if (cgRL != null && cgRL.getNamespace().equals("terraforged")) {
                    return;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());

            tempMap.putIfAbsent(ModStructures.HOUSE.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.HOUSE.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }
}
