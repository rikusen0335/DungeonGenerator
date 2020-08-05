package net.rikusen.dungeongenerator

import org.bukkit.command.CommandSender

object CommandFunctions {
    fun getAbout(sender: CommandSender) = sender.sendMessage("Type `/dg generate` to generate a dungeon")
    fun generateDungeon(sender: CommandSender) {
        sender.sendMessage("Dungeon has generated!")
    }
}