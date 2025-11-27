### Added
- `/tickrate <ticks> <player>` command to change tickrate for a specific player
- `--dontupdate` flag for `/tickrate setdefault` command (sets default without updating current tickrate)
- Support for combined flags on setdefault: `--dontsave --dontupdate` (in any order)
- GitHub Actions workflow for automated publishing to Modrinth and CurseForge

### Fixed
- Fixed 2-3 second freeze when changing tickrate (removed problematic `setLastMs` call in TimerMixin)
- Removed incomplete `/tickrate setmap` references from codebase

### Changed
- Improved command system to support per-player tickrate control
- Enhanced setdefault command with more granular control over save and update behavior

### Technical
- Properly configured dependencies: Architectury API, Cloth Config, Fabric API
- Added interface `ITickrateChanger` for timer manipulation
- Improved mixin structure for client tickrate changes