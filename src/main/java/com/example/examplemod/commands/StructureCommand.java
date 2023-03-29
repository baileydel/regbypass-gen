package com.example.examplemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class StructureCommand {
    public static void register(CommandDispatcher<CommandSource> p_198528_0_) {
        LiteralArgumentBuilder<CommandSource> literalargumentbuilder =
                Commands.literal("structure")
                        .requires(source -> source.hasPermission(2))
                        .then(Spawn.register());
        p_198528_0_.register(literalargumentbuilder);
    }


    static class Spawn {
        public static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("spawn")
                    .executes(Spawn::execute);
        }

        private static int execute(CommandContext<CommandSource> context) {
            /*
                        TemplateManager templateManager = context.getSource().getLevel().getStructureManager();
            DynamicRegistries registries = context.getSource().registryAccess();
            ServerWorld world = context.getSource().getLevel();
            ChunkGenerator gen = world.getChunkSource().getGenerator();
            StructureManager sm = world.structureFeatureManager();
            BlockPos pos = new BlockPos(context.getSource().getPosition());
            Biome biome =  world.getBiome(pos);

            IChunk temp = world.getChunk(pos.getX() / 16, pos.getZ() / 16, ChunkStatus.STRUCTURE_STARTS);

            // The only use of this to get the amount of references?
            StructureStart<?> structurestart = sm.getStartForFeature(SectionPos.of(temp.getPos(), 0), ModStructures.HOUSE.get(), temp);
            int i = structurestart != null ? structurestart.getReferences() : 0;

            CStructure.Start structurestart1 = null;

            for (Supplier<StructureFeature<?, ?>> supplier : biome.getGenerationSettings().structures()) {
                if (supplier.get().feature instanceof CStructure) {

                    structurestart1 = (CStructure.Start) ModStructures.HOUSE.get().getStartFactory().create((Structure<NoFeatureConfig>) supplier.get().feature, temp.getPos().x, temp.getPos().z, MutableBoundingBox.getUnknownBox(), i, 198230);
                    structurestart1.generatePieces(registries, gen, templateManager, temp.getPos().x, temp.getPos().z, biome, (NoFeatureConfig) supplier.get().config);

                    if (structurestart1.isValid()) {
                        structurestart1.addReference();

                        temp.setStartForFeature(supplier.get().feature, structurestart1);
                    }
                }
            }

            assert structurestart1 != null;

            try {
                ServerPlayerEntity plauer = context.getSource().getPlayerOrException();
                if (structurestart1.isValid()) {
                    plauer.sendMessage(new StringTextComponent("valid lets go"), plauer.getUUID());
                }
                else {
                    plauer.sendMessage(new StringTextComponent("fuck"), plauer.getUUID());
                }
            }
            catch (Exception e) {
                System.out.println("nooo");
            }

            structurestart1.placeInChunk(world, sm, gen, world.random, new MutableBoundingBox(pos.getX() - 1, pos.getZ() - 1, pos.getX() + 16, pos.getZ() + 16), temp.getPos());

             */
            return 0;
        }
    }
}
