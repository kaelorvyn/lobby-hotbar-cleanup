# Lobby Hotbar Cleanup

Disables the obsolete `MC-Lobby` dash rod and teleport bow in the lobby.

- Moves the `MC-Lobby` server-selector compass to slot 0 through its existing config.
- Preserves the GadgetsMenu item in slot 4 and the player-hide item in slot 8.
- Removes stale menu/dash/bow items already in inventories and blocks their use; other hotbar slots are untouched.
