# NoMorePhantoms

NoMorePhantoms is a [Paper](https://papermc.io/software/paper) plugin that allows users them selves control if they want phantoms to spawn.

## Modes
- **Per Player**: Each player can control if they want phantoms to spawn for themselves.
- **Global**: The online players can vote if the phantoms should spawn for everyone. If more users have it enabled, the phantoms will spawn.

## Config
```yml
# The mode can be "per_player" or "global"
# per_player will allow players to toggle phantoms for themselves
# global will toggle phantoms for everyone depending on how many players have it enabled
mode: "per_player"

command:
  name: "phantoms"
  permission: "nomorephantoms.command"
  permission-message: "&cYou do not have permission to use this command."
```