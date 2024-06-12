package dev.tarna.nomorephantoms

import dev.tarna.nomorephantoms.api.user.UserManager
import dev.tarna.nomorephantoms.commands.NoMorePhantomsCommand
import dev.tarna.nomorephantoms.listeners.PhantomSpawnListener
import org.bstats.bukkit.Metrics
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class NoMorePhantoms : JavaPlugin() {
    lateinit var userManager: UserManager

    override fun onEnable() {
        val now = System.currentTimeMillis()

        saveDefaultConfig()

        val mode = config.getString("mode")
        if (mode == null || mode != "per_player" && mode != "global") {
            logger.warning("Invalid Phantom Spawn Mode: $mode")
            logger.warning("Valid modes are: per_player, global")
            logger.warning("Disabling NoMorePhantoms")
            server.pluginManager.disablePlugin(this)
            return
        }

        userManager = UserManager(this)

        server.pluginManager.registerEvents(PhantomSpawnListener(this), this)

        registerCommand()

        loadMetrics()

        logger.info("NoMorePhantoms has been enabled in ${System.currentTimeMillis() - now}ms!")
    }

    override fun onDisable() {
        val now = System.currentTimeMillis()

        logger.info("NoMorePhantoms has been disabled in ${System.currentTimeMillis() - now}ms!")
    }

    private fun registerCommand() {
        val commandMap = server.commandMap
        val commandName = config.getString("command.name") ?: "phantoms"
        val c = PluginCommand::class.java.getDeclaredConstructor(String::class.java, Plugin::class.java)
        c.isAccessible = true
        val bukkitCommand = c.newInstance(commandName, this)
        bukkitCommand.setDescription("NoMorePhantoms Command")
        bukkitCommand.usage = "/$commandName"
        bukkitCommand.setExecutor(NoMorePhantomsCommand(this))
        commandMap.register(commandName, bukkitCommand)
    }

    private fun loadMetrics() {
        Metrics(this, 22226)
        logger.info("BStats Metrics have been enabled!")
    }
}