package com.example.examplemod;

import com.example.examplemod.client.ClientEvents;
import com.example.examplemod.commands.CommandRegistry;
import com.example.examplemod.gen.ModStructures;
import com.example.examplemod.server.ServerEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("examplemod")
public class ExampleMod
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public ExampleMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModStructures.register(bus);

        // Register the setup method for modloading
        bus.addListener(this::setup);

        // Register the doClientStuff method for modloading
        bus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ServerEvents());
        MinecraftForge.EVENT_BUS.register(new CommandRegistry());
        event.enqueueWork(ModStructures::setStructures);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }
}
