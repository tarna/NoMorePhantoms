package dev.tarna.nomorephantoms.listeners

import com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent
import dev.tarna.nomorephantoms.NoMorePhantoms
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PhantomSpawnListener(plugin: NoMorePhantoms) : Listener {
    private val userManager = plugin.userManager
    private val mode = plugin.config.getString("mode") ?: "per_player"

    @EventHandler
    fun onPhantomSpawn(event: PhantomPreSpawnEvent) {
        val entity = event.spawningEntity
        if (entity !is Player) return

        if (mode == "per_player") {
            val value = userManager.get(entity)
            event.isCancelled = value
        } else if (mode == "global") {
            event.isCancelled = userManager.shouldGlobalSpawn()
        }
    }
}