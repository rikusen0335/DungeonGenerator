package net.rikusen.dungeongenerator

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class DungeonGenerator : JavaPlugin(), Listener, CommandExecutor {
    private val command: CommandHandler = CommandHandler()
    private val prefix: String = "dg"

    override fun onEnable() {
        logger.info("%sDungeonGenerator is now enabled!".format(ChatColor.GREEN))
        server.pluginManager.registerEvents(this, this)

        command.addCommand(CommandFunctions::getAbout, "dg", "help")
    }

    override fun onDisable() {
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, params: Array<String>): Boolean {
        val cmdName = cmd.name.toLowerCase()
        val result = command.exec(sender, cmdName, *params)
        if (result.message != "") sender.sendMessage(result.message)
        return result.success
    }
}