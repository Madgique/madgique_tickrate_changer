package com.madgique.tickratechanger.config;


import com.madgique.tickratechanger.TickrateChanger;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = TickrateChanger.MOD_ID)
public class TickrateChangerConfig implements ConfigData {
  @Comment("Default tickrate. The game will always initialize with this value.")
  public float defaultTickrate = TickrateChanger.DEFAULT_VALUE_DEFAULT_TICKRATE;
  @Comment("Maximum tickrate from servers. Prevents really high tickrate values.")
  public float maxTickrate = TickrateChanger.DEFAULT_VALUE_MAX_TICKRATE;
  @Comment("Minimum tickrate from servers. Prevents really low tickrate values.")
  public float minTickrate = TickrateChanger.DEFAULT_VALUE_MIN_TICKRATE;
  @Comment("Whether it will change the sound speed")
  public boolean changeSound = TickrateChanger.DEFAULT_VALUE_CHANGE_SOUND;
  //    @Comment("Whether it will have special keys for setting the tickrate")
//    public boolean keyBindings = TickrateChanger.KEY;
  @Comment("Whether it will show log messages in the console and the game")
  public boolean showMessages= TickrateChanger.DEFAULT_VALUE_SHOW_MESSAGES;


}
