package com.madgique.tickratechanger.handlers;

import com.madgique.tickratechanger.command.TickrateChangerCommands;
import com.mojang.brigadier.CommandDispatcher;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public enum TickrateChangerEventHandler {
  INSTANCE;

  public void init() {
    CommandRegistrationEvent.EVENT.register(this::registerCommands);

  }

  private void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher,
                                CommandBuildContext context,
                                Commands.CommandSelection selection) {
    TickrateChangerCommands.register(dispatcher);
  }
}
