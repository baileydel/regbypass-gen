package com.example.examplemod.gen;

import com.example.examplemod.CStructure;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Objects;

public class ModStructures {
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, "examplemod");
    public static final RegistryObject<Structure<NoFeatureConfig>> HOUSE = STRUCTURES.register("house", CStructure::new);

    public static void setStructures() {
        setupMap(HOUSE.get(), new StructureSeparationSettings(1020120, 19281, 128129812), true);
    }

    private static <F extends Structure<?>> void setupMap(F structure, StructureSeparationSettings structureSeparationSettings, boolean idk) {
        Structure.STRUCTURES_REGISTRY.put(Objects.requireNonNull(structure.getRegistryName()).toString(), structure);

        if (idk) {
            Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>> builder()
                    .addAll(Structure.NOISE_AFFECTING_FEATURES)
                    .add(structure)
                    .build();
        }

        DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings> builder()
                .putAll(DimensionStructuresSettings.DEFAULTS)
                .put(structure, structureSeparationSettings)
                .build();

        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.forEach(
                (setting) -> {
                    Map<Structure<?>, StructureSeparationSettings> structureMap = setting.structureSettings().structureConfig();
                    structureMap.put(structure, structureSeparationSettings);
                }
        );
    }

    public static void register(IEventBus eventBus) {
        STRUCTURES.register(eventBus);
    }
}
