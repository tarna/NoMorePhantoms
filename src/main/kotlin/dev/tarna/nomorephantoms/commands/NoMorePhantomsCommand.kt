package dev.tarna.nomorephantoms.commands

import dev.tarna.nomorephantoms.NoMorePhantoms
import dev.tarna.nomorephantoms.api.utils.color
import dev.tarna.nomorephantoms.api.utils.send
import me.tech.mcchestui.GUI
import me.tech.mcchestui.GUIType
import me.tech.mcchestui.item.guiHeadItem
import me.tech.mcchestui.item.item
import me.tech.mcchestui.utils.gui
import me.tech.mcchestui.utils.openGUI
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class NoMorePhantomsCommand(private val plugin: NoMorePhantoms) : CommandExecutor {
    private val userManager = plugin.userManager

    private val permission = plugin.config.getString("command.permission") ?: "nomorephantoms.command"
    private val permissionMessage = plugin.config.getString("command.permission-message") ?: "<red>You do not have permission to use this command."

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.send("<red>Only players can use this command.")
            return true
        }

        if (!sender.hasPermission(permission)) {
            sender.send(permissionMessage)
            return true
        }

        sender.openGUI(getGUI(sender))

        return true
    }

    private fun getGUI(player: Player): GUI {
        return gui(plugin, "<red><bold>Phantoms".color(), GUIType.Chest(3)) {
            fillBorder {
                item = item(Material.GRAY_STAINED_GLASS_PANE) {
                    name = " ".color()
                }
            }

            val value = userManager.get(player)
            slot(4, 2) {
                if (value) {
                    item = item(Material.LIME_DYE) {
                        name = "<red>Disable Phantoms".color()
                    }
                } else {
                    item = item(Material.RED_DYE) {
                        name = "<green>Enable Phantoms".color()
                    }
                }

                onClick {
                    userManager.toggle(player)
                    player.openGUI(getGUI(player))
                }
            }

            slot(5, 2) {
                item = guiHeadItem {
                    skullOwner = player
                    name = "<blue>${player.name}".color()
                    lore = listOf(
                        if (value) "<green>Enabled" else "<red>Disabled",
                    ).color()
                }
            }

            val globalValues = userManager.values.map { (uuid, value) ->
                plugin.server.getPlayer(uuid) to value
            }.toMap()

            slot(6, 2) {
                item = item(Material.BOOK) {
                    name = "<yellow>Global Info".color()
                    lore = globalValues.map { (player, value) ->
                        "<green>${player?.name} <gray>- ${if (value) "<green>Enabled" else "<red>Disabled"}"
                    }.color()
                }
            }
        }
    }
}