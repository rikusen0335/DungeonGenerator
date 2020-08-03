package net.rikusen.dungeongenerator

object CommandFunctions {
    fun getAbout(): CommandResult =
            CommandResult(true, "Type dg generate to generate a dungeon")
}