package com.neelkamath.langtonsant

import kotlin.random.Random

typealias Plane = List<MutableList<Cell>>

/** The first row and column are indicated by zero. */
data class Position(var row: Int, var column: Int)

enum class Direction { UP, RIGHT, DOWN, LEFT }

data class Ant(var position: Position, var direction: Direction) {
    /** Changes the [direction] to 90 degrees left of it. */
    fun turnLeft() {
        direction = when (direction) {
            Direction.UP -> Direction.LEFT
            Direction.RIGHT -> Direction.UP
            Direction.DOWN -> Direction.RIGHT
            Direction.LEFT -> Direction.DOWN
        }
    }

    /** Changes the [direction] to 90 degrees right of it. */
    fun turnRight() {
        direction = when (direction) {
            Direction.UP -> Direction.RIGHT
            Direction.RIGHT -> Direction.DOWN
            Direction.DOWN -> Direction.LEFT
            Direction.LEFT -> Direction.UP
        }
    }
}

enum class Cell { WHITE, BLACK }

data class Grid(val plane: Plane, val ant: Ant) {
    init {
        if (plane.isEmpty() || plane[0].isEmpty()) throw Exception("The plane must have at least one row and column")
        if (ant.position.row >= plane.size || ant.position.column >= plane[0].size)
            throw Exception("The ant must be positioned inside the plane")
    }

    /** The [Cell] in front of the [ant] (null if nonexistent). */
    val cellAheadOfAnt
        get() = when (ant.direction) {
            Direction.UP -> if (ant.position.row == 0) null else plane[ant.position.row - 1][ant.position.column]
            Direction.RIGHT ->
                if (ant.position.column == plane[0].size - 1) null else plane[ant.position.row][ant.position.column + 1]
            Direction.DOWN ->
                if (ant.position.row == plane.size - 1) null else plane[ant.position.row + 1][ant.position.column]
            Direction.LEFT -> if (ant.position.column == 0) null else plane[ant.position.row][ant.position.column - 1]
        }

    /** The [Cell] the [ant] is currently on. */
    var antCell
        get() = plane[ant.position.row][ant.position.column]
        set(value) {
            plane[ant.position.row][ant.position.column] = value
        }

    /** Flips the color of the cell the ant is on. */
    fun flipCell() {
        antCell = if (antCell == Cell.WHITE) Cell.BLACK else Cell.WHITE
    }

    /** Moves the [ant] forward one cell. */
    fun moveAntForward() {
        ant.position = when (ant.direction) {
            Direction.UP -> Position(ant.position.row - 1, ant.position.column)
            Direction.RIGHT -> Position(ant.position.row, ant.position.column + 1)
            Direction.DOWN -> Position(ant.position.row + 1, ant.position.column)
            Direction.LEFT -> Position(ant.position.row, ant.position.column - 1)
        }
    }
}

/** Returns a [Plane] having [length] rows and columns and contains only [Cell.WHITE] cells. */
fun createPlane(length: Int) = createPlane(length, length)

/** Returns a [Plane] of size [rows] and [columns] with each cell being [Cell.WHITE]. */
fun createPlane(rows: Int, columns: Int) = List(rows) { MutableList(columns) { Cell.WHITE } }

/**
 * Returns a [Grid] with a [Grid.plane] having [length] rows and columns. The [Grid.plane] will be filled with
 * [Cell.WHITE]. The [Grid.ant] will be placed randomly.
 */
fun createGrid(length: Int) = createGrid(length, length)

/**
 * Returns a [Grid] with a [Grid.plane] of size [rows] and [columns]. The [Grid.plane] will be filled with [Cell.WHITE].
 * The [Grid.ant] will be placed randomly.
 */
fun createGrid(rows: Int, columns: Int) = Grid(
    createPlane(rows, columns),
    Ant(Position(Random.nextInt(rows), Random.nextInt(columns)), Direction.values().random())
)

/** Returns [grid]'s next state, or null if the [Grid.ant] hits a wall (happens when it cannot move forward). */
fun progressGrid(grid: Grid): Grid? = with(grid) {
    if (antCell == Cell.WHITE) ant.turnRight() else ant.turnLeft()
    flipCell()
    if (cellAheadOfAnt == null) return null
    moveAntForward()
    this
}
