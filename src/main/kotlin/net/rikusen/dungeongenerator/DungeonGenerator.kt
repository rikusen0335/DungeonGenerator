package net.rikusen.dungeongenerator

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class DungeonGenerator : JavaPlugin(), Listener, CommandExecutor {
    private val prefix: String = "dg"

    override fun onEnable() {
        logger.info("%sDungeonGenerator is now enabled!".format(ChatColor.GREEN))
        logger.info("unko")
        server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, params: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("This command only accepts a player.")
            return true
        }

        if (label == "dg" || label == "dungeongenerator") {
            when (params[0]) {
                "generate" -> CommandFunctions.generateDungeon(sender)
                "help" -> CommandFunctions.getAbout(sender)
            }

            return true
        }

        return false
    }
}