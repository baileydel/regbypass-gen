package com.example.examplemod.commands;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommandRegistry {
    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        StructureCommand.register(event.getDispatcher());
    }
}
