# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Minecraft mod called "Madgique's Tickrate Changer" built using the Architectury framework to support multiple mod loaders (Forge and Fabric) for Minecraft 1.20.1.

The mod allows players to change the server/client tickrate through commands, providing control over game speed. This is a resurrection of the original [TickrateChanger](https://github.com/Guichaguri/TickrateChanger) mod.

### Repository
- Repository: https://github.com/Madgique/madgique_tickrate_changer/tree/1.20.1
- This mod serves as the reference template for all future Madgique mods
- Use its `mods.toml` structure as the standard template: https://github.com/Madgique/madgique_tickrate_changer/blob/1.20.1/forge/src/main/resources/META-INF/mods.toml

### Default License
- All Madgique mods use **LGPLv2.1** as the default license
- Set `mod_license = LGPLv2.1` in `mod.info.gradle.properties` for new mods

## Build System & Commands

This project uses Gradle with the Architectury plugin for multi-platform mod development.

### Essential Commands
- `./gradlew build` - Build all modules (common + forge + fabric)
- `./gradlew :forge:build` - Build only the Forge version
- `./gradlew :fabric:build` - Build only the Fabric version
- `./gradlew :common:build` - Build only the common module
- `./gradlew :forge:runClient` - Run Minecraft client with the mod for testing
- `./gradlew clean` - Clean build artifacts

### Development Setup
- Java 17 required (configured via toolchain)
- Uses official Mojang mappings
- Mod version: 1.0 (defined in gradle.properties)
- Target Minecraft: 1.20.1
- Forge version: 47.3.0
- Architectury version: 9.2.14

## Architecture

### Multi-Platform Structure
The project follows Architectury's multi-platform pattern:

- **`common/`** - Platform-agnostic code shared between mod loaders
  - Contains core mod logic, tickrate manipulation, and commands
  - Main class: `TickrateChangerMod`

- **`forge/`** - Forge-specific implementation
  - Platform-specific mod initialization and event handling
  - Contains `TickrateChangerModForge` which bootstraps the common code
  - Includes `mods.toml` for Forge mod metadata

- **`fabric/`** - Fabric-specific implementation
  - Platform-specific mod initialization and event handling
  - Contains `TickrateChangerModFabric` which bootstraps the common code
  - Includes `fabric.mod.json` for Fabric mod metadata

### Dependencies
- Architectury API for cross-platform compatibility
- Multiple mod loaders supported (Forge and Fabric)

## Development Notes

### Code Style
- **IMPORTANT**: Always use proper imports instead of fully-qualified class names in code
- ✅ Good: Use proper import statements at the top of files
- ❌ Bad: Using fully-qualified class names inline

### File Editing
- **CRITICAL**: NEVER use `sed`, `awk`, or similar bash text processing commands for modifying code files
- **ALWAYS** use the Edit tool to modify files one by one
- ✅ Good: Use Edit tool with old_string/new_string parameters
- ❌ Bad: Using `sed -i 's/old/new/g' file.java`
- Reason: Direct file editing ensures proper tracking, error handling, and prevents unintended modifications

### Adding New Features
- Add cross-platform code to `common/src/main/java/com/madgique/tickratechanger/`
- **Forge** platform-specific implementations go in `forge/src/main/java/com/madgique/tickratechanger/forge/`
- **Fabric** platform-specific implementations go in `fabric/src/main/java/com/madgique/tickratechanger/fabric/`
- **CRITICAL**: Never delete Fabric or Forge modules - both are required for multi-platform support
- **IMPORTANT**: Prefer accesswidener for accessing private classes/methods, but reflection can be used when needed

### Access Widener Usage (Modern Approach - Minecraft 1.19+)
- Access wideners modify access levels of classes, methods, and fields in Minecraft's code
- File format: `.accesswidener` extension, start with `accessWidener v2 named`
- Syntax:
  - Classes: `<access> class <className>`
  - Methods: `<access> method <className> <methodName> <methodDesc>`
  - Fields: `<access> field <className> <fieldName> <fieldDesc>`
- Access types: `accessible` (public), `extendable` (subclassing), `mutable` (removes final)

**Configuration for Minecraft 1.19+ (current project - 1.20.1):**
- Place access widener file in `common/src/main/resources/`
- Configure in `common/build.gradle`: `loom { accessWidenerPath = file("src/main/resources/your.accesswidener") }`
- **Fabric**: Reference common access widener + inject it in JAR via `remapJar { injectAccessWidener = true }`
- **Forge**: Use automatic conversion with `forge { convertAccessWideners = true; extraAccessWideners.add ... }`
- **IMPORTANT**: Do NOT create manual `META-INF/accesstransformer.cfg` - let Loom convert it automatically!

**For Minecraft 1.18.2 and earlier (backport notes):**
- The `convertAccessWideners` feature doesn't exist in older Architectury Loom versions
- You MUST create both files manually:
  - `common/src/main/resources/mod.accesswidener` (for Fabric)
  - `forge/src/main/resources/META-INF/accesstransformer.cfg` (for Forge)
- Keep both files in sync manually

**References:**
- Access Widener docs: https://wiki.fabricmc.net/tutorial:accesswideners
- Architectury Loom docs: https://docs.architectury.dev/loom/introduction

### Testing
- Use `./gradlew :forge:runClient` to launch a development instance
- Test configuration changes by modifying the generated config file
- The `forge/run/` directory contains the development environment with logs, saves, and config

### Resource Management
- Forge metadata: `forge/src/resources/META-INF/mods.toml`
- Fabric metadata: `fabric/src/resources/fabric.mod.json`
- Both contain mod display information and dependencies

### Changelog Management
- **IMPORTANT**: The `CHANGELOG.md` file contains ONLY the current version's changes
- When releasing a new version, completely overwrite `CHANGELOG.md` with the new version's changelog
- Do NOT maintain full version history in CHANGELOG.md - GitHub Releases provides that history
- This approach ensures CurseForge and Modrinth display only the current version's changes (not the entire history)
- The GitHub Actions workflow uses `changelog-file: CHANGELOG.md` which will upload only the current version's notes
- Example format:
  ```markdown
  # Changelog

  ## [X.Y.Z] - YYYY-MM-DD

  ### Added
  - Feature 1
  - Feature 2

  ### Changed
  - Change 1
  ```

### Commands & Features
- This mod provides commands to control game tickrate (see [Wiki Commands](https://github.com/Madgique/madgique_tickrate_changer/wiki/Commands))
- Core functionality involves manipulating Minecraft's tick loop timing
- Changes affect both client and server tickrate depending on the command used