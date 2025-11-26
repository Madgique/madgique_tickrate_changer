package com.madgique.tickratechanger.command;

import com.madgique.tickratechanger.TickrateChanger;
import com.madgique.tickratechanger.api.TickrateAPI;
import com.madgique.tickratechanger.config.ClothConfigGUIBuilder;
import com.madgique.tickratechanger.config.TickrateChangerConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;

import static net.minecraft.ChatFormatting.*;

public class TickrateChangerCommands {
  public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
    dispatcher.register(Commands.literal("tickrate")
            .requires(s -> s.getServer() != null && s.getServer().isSingleplayer() || hasChangeTickratePermission(s))
            .then(Commands.literal("help")
                    .executes(context -> showHelp(context.getSource()))
            )
            .then(Commands.literal("setdefault")
                    .then(Commands.argument("ticks", IntegerArgumentType.integer())
                            .executes(context -> setDefaultTickrate(context,  true))
                            .then(Commands.literal("--dontsave")
                                    .executes(context -> setDefaultTickrate(context, false))
                            )
                    )
            )
            .then(Commands.argument("ticks", IntegerArgumentType.integer())
                    .executes(context -> changeTickrate(context))
            )
            .executes(context -> showInfo(context.getSource()))
    );

    // Alias /ticks
    dispatcher.register(Commands.literal("ticks")
            .requires(s -> s.getServer() != null && s.getServer().isSingleplayer() || hasChangeTickratePermission(s))
            .redirect(dispatcher.getRoot().getChild("tickrate"))
            .executes(context -> showInfo(context.getSource()))

    );

    // Alias /tickratechanger
    dispatcher.register(Commands.literal("tickratechanger")
            .requires(s -> s.getServer() != null && s.getServer().isSingleplayer() || hasChangeTickratePermission(s))
            .redirect(dispatcher.getRoot().getChild("tickrate"))
            .executes(context -> showInfo(context.getSource()))
    );

    // Alias /trc
    dispatcher.register(Commands.literal("trc")
            .requires(s -> s.getServer() != null && s.getServer().isSingleplayer() || hasChangeTickratePermission(s))
            .redirect(dispatcher.getRoot().getChild("tickrate"))
            .executes(context -> showInfo(context.getSource()))

    );

    // Alias /settickrate
    dispatcher.register(Commands.literal("settickrate")
            .requires(s -> s.getServer() != null && s.getServer().isSingleplayer() || hasChangeTickratePermission(s))
            .redirect(dispatcher.getRoot().getChild("tickrate"))
            .executes(context -> showInfo(context.getSource()))

    );

  }

  private static boolean hasChangeTickratePermission(CommandSourceStack stack) {
    return stack.hasPermission(2);
  }

  private static int setDefaultTickrate(CommandContext<CommandSourceStack> context, boolean willSave) {
    int tickrate = IntegerArgumentType.getInteger(context, "ticks");
    TickrateAPI.changeDefaultTickrate(tickrate, willSave);
    return Command.SINGLE_SUCCESS;
  }

  private static int changeTickrate(CommandContext<CommandSourceStack> context) {
    int tickrate = IntegerArgumentType.getInteger(context, "ticks");
    MinecraftServer server = context.getSource().getServer();
    TickrateAPI.changeTickrate(tickrate, server);
    return Command.SINGLE_SUCCESS;
  }

  private static int showHelp(CommandSourceStack source) {
    source.sendSuccess(() -> Component.literal("Usage: /tickrate [ticks per second] [help/info]"), false);
    source.sendSuccess(() -> Component.literal("/tickrate help - Shows this help message"), false);
    source.sendSuccess(() -> Component.literal("/tickrate info - Displays the current tickrate"), false);
    return 1;
  }

  private static int showInfo(CommandSourceStack source) {
    float currentClientTickrate = TickrateAPI.getClientTickrate();
    float currentServerTickrate = TickrateAPI.getServerTickrate();
    float defaultTickrate = TickrateChanger.CONFIG.defaultTickrate;
    String SPACE = " ";

    Style currentMessagesStyle = Style.EMPTY.withColor(GREEN);
    Style defautMessagesStyle = Style.EMPTY.withColor(YELLOW);
    Style exampleMessagesStyle = Style.EMPTY.withColor(AQUA);
    Style helpMessagesStyle = Style.EMPTY.withColor(RED);

    MutableComponent currentClientTickrateMessage = Component.literal("Current Client Tickrate:" + SPACE);
    MutableComponent currentServerTickrateMessage = Component.literal("Current Server Tickrate:" + SPACE);
    MutableComponent defaultTickrateMessage = Component.literal("Default Tickrate:" + SPACE);

    MutableComponent example1Message = Component.literal("/tickrate <ticks per second> [all/server/client/ playername ]");
    MutableComponent example2Message = Component.literal("/tickrate setdefault <ticks per second> [--dontsave, --dontupdate]");
    MutableComponent example3Message = Component.literal("/tickrate setmap <ticks per second> [--dontupdate]");


    MutableComponent help1 = Component.literal("Use" + SPACE).setStyle(helpMessagesStyle);
    MutableComponent help2 = Component.literal("/tickrate help" + SPACE).setStyle(Style.EMPTY);
    MutableComponent help3 = Component.literal("for more command info").setStyle(helpMessagesStyle);
    MutableComponent helpInfoMessage = help1.append(help2).append(help3);

    MutableComponent currentClientTickrateValueMessage = Component.literal(currentClientTickrate + SPACE + "ticks per second").setStyle(currentMessagesStyle);
    MutableComponent currentServerTickrateValueMessage = Component.literal(currentServerTickrate + SPACE + "ticks per second").setStyle(currentMessagesStyle);
    MutableComponent defaultTickrateValueMessage = Component.literal(defaultTickrate + SPACE + "ticks per second").setStyle(defautMessagesStyle);

    source.sendSuccess(() -> currentClientTickrateMessage.append(currentClientTickrateValueMessage), false);
    source.sendSuccess(() -> currentServerTickrateMessage.append(currentServerTickrateValueMessage), false);
    source.sendSuccess(() -> defaultTickrateMessage.append(defaultTickrateValueMessage), false);
    source.sendSuccess(() -> Component.literal(SPACE), false);
    source.sendSuccess(() -> example1Message.withStyle(exampleMessagesStyle), false);
    source.sendSuccess(() -> example2Message.withStyle(exampleMessagesStyle), false);
    source.sendSuccess(() -> example3Message.withStyle(exampleMessagesStyle), false);
    source.sendSuccess(() -> Component.literal(SPACE), false);
    source.sendSuccess(() -> helpInfoMessage, false);

    return 1;
  }
}
