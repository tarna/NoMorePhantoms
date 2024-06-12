package dev.tarna.nomorephantoms.api.user

import dev.tarna.nomorephantoms.NoMorePhantoms
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.util.UUID

class UserManager(plugin: NoMorePhantoms) {
    val values = mutableMapOf<UUID, Boolean>()

    var file: File = File(plugin.dataFolder, "data.yml")
    private var config: YamlConfiguration

    init {
        if (!file.exists()) {
            file.createNewFile()
        }

        config = YamlConfiguration.loadConfiguration(file)

        load()
    }

    private fun load() {
        config.getKeys(false).forEach {
            values[UUID.fromString(it)] = config.getBoolean(it)
        }
    }

    fun get(player: Player): Boolean {
        return values[player.uniqueId] ?: false
    }

    fun set(player: Player, value: Boolean) {
        values[player.uniqueId] = value
        save(player)
    }

    fun toggle(player: Player) {
        values[player.uniqueId] = !get(player)
        save(player)
    }

    fun shouldGlobalSpawn(): Boolean {
        return values.values.count { it } < values.values.count { !it }
    }

    fun save(player: Player) {
        config.set(player.uniqueId.toString(), values[player.uniqueId])
        config.save(file)
    }

    fun save() {
        values.forEach { (uuid, value) ->
            config.set(uuid.toString(), value)
        }
        config.save(file)
    }
}