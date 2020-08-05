package net.rikusen.dungeongenerator

import kotlin.random.Random
import java.util.Stack

class MazeGenerator {
    /* Maze Numbers */
    private val PATH  = 0
    private val WALL  = 1

    /* Directions */
    private val UP     = 0
    private val RIGHT  = 1
    private val BOTTOM = 2
    private val LEFT   = 3

    /* Member Variables */
    private val width: Int = 9
    private var maze: Array<Array<Int>> = Array(this.width) {Array(this.width) {this.WALL} }
    private var currentCell: Cell = Cell()
    private var targetCell: Cell = Cell()
    private var startCell: Cell = Cell()
    private var endCell: Cell = Cell()
    private val stack = Stack<Int>()

    fun generate() {
        /* スタート地点を決める */
        val x = (0 until width).filter { it % 2 == 1 }.random()
        val y = (0 until width).filter { it % 2 == 1 }.random()

        startCell = Cell(x, y)

        currentCell = Cell(x, y)
        /* ~スタート地点を決める */

        /* 4方向すべてに道が作れなくなるまで */
        while (true) {
            while(!isNotEnd(currentCell)) {
                if (stack.empty()) {
                    break
                }
                when ((stack.peek() + 2) % 4) {
                    UP     -> currentCell.y += 2
                    RIGHT  -> currentCell.x += 2
                    BOTTOM -> currentCell.y -= 2
                    LEFT   -> currentCell.x -= 2
                }
                stack.pop()
            }

            if (!isNotEnd(currentCell) && stack.empty()) {
                break
            }
            var direction: Int
            var targetCell : Cell
            /* 方向が決まるまでループ */
            do {
                targetCell = currentCell.copy()
                direction = Random.nextInt(4)

                when (direction) {
                    UP     -> targetCell.y += 2
                    RIGHT  -> targetCell.x += 2
                    BOTTOM -> targetCell.y -= 2
                    LEFT   -> targetCell.x -= 2
                }
            } while (!isRemovable(targetCell))

            stack.push(direction)
            removeWall(currentCell, direction)
            currentCell = targetCell.copy()
        }

        println("おわり")
        drawMaze()
    }

    private fun removeWall(currentCell: Cell, direction: Int) {
        val dx: Array<Int> = arrayOf(0, 1, 0, -1)
        val dy: Array<Int> = arrayOf(1, 0, -1, 0)

        val cell1 = Cell(currentCell.x + dx[direction], currentCell.y + dy[direction])
        val cell2 = Cell(currentCell.x + dx[direction]*2, currentCell.y + dy[direction]*2)

        maze[currentCell.x][currentCell.y] = PATH
        maze[cell1.x][cell1.y] = PATH
        maze[cell2.x][cell2.y] = PATH
    }

    private fun isRemovable(targetCell: Cell): Boolean {
        if (targetCell.x >= width || targetCell.y >= width) return false
        if (targetCell.x < 0 || targetCell.y < 0) return false
        if (getValue(targetCell) == 1) return true
        return false
    }

    private fun isNotEnd(currentCell: Cell): Boolean {
        val dx: Array<Int> = arrayOf(0, 2, 0, -2)
        val dy: Array<Int> = arrayOf(2, 0, -2, 0)
        var checker = false

        for (d in 0..3) {
            val toX: Int = currentCell.x + dx[d]
            val toY: Int = currentCell.y + dy[d]

            if (0 > toX || toX >= width || 0 > toY || toY >= width) {
                continue
            }
            val targetCell = Cell(toX, toY)
            if (getValue(targetCell) == 0) {
                continue
            }
            checker = true
        }
        return checker
    }

    fun getValue(cell: Cell): Int = maze[cell.x][cell.y] // TODO ArrayOutOfBoundsExceptionになる可能性がある

    fun drawMaze() {
        for (row in 0..(maze.size-1)) {
            for (col in 0..(maze[row].size-1)) {
                if (row == startCell.x && col == startCell.y) {
                    print(" 0 ")
                } else if (row == endCell.x && col == endCell.y) {
                    print(" 1 ")
                } else if (maze[row][col] == 0) {
                    print(" □ ")
                } else {
                    print(" ■ ")
                }
            }
            println("")
        }
    }
}

data class Cell(var x: Int = 0, var y: Int = 0)