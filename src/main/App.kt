package com.neelkamath.langtonsant

fun main(args: Array<String>) {
    val steps = args.elementAtOrNull(0)?.toInt() ?: 200
    val rows = args.elementAtOrNull(1)?.toInt() ?: 11
    val columns = args.elementAtOrNull(2)?.toInt() ?: rows
    val row = args.elementAtOrNull(3)?.toInt() ?: 5
    val column = args.elementAtOrNull(4)?.toInt() ?: row
    val direction = Direction.valueOf(args.elementAtOrNull(5) ?: "LEFT")
    var grid = Grid(createPlane(rows, columns), Ant(Position(row, column), direction))
    for (step in 1..steps) {
        println("Time step: $step\n${prettifyGrid(grid)}")
        val newGrid = progressGrid(grid)
        if (newGrid == null) {
            println("The ant has hit a wall and hence no more time steps can be generated.")
            return
        }
        grid = newGrid
    }
}

/**
 * Returns [grid] as a human readable [String].
 *
 * The [String] will look like:
 * ```
 * WE BE BE
 * WU BE WE
 * WE WE BE
 * ```
 * Each row is separated by a new line, and each column a space.
 * Each cell on the grid is indicated by two characters. The first character will be `W` (white cell), or `B` (black
 * cell). The second character will be `E` (empty cell), `U` (has upward facing ant), `R` (has rightward facing ant),
 * `D` (has downward facing ant), or `L` (has a leftward facing ant).
 */
private fun prettifyGrid(grid: Grid) = with(grid) {
    var string = ""
    for ((rowIndex, row) in plane.withIndex()) {
        for ((columnIndex, column) in row.withIndex()) {
            string += column.name[0]
            with(ant) {
                string += if (rowIndex == position.row && columnIndex == position.column) direction.name[0] else "E"
            }
            if (columnIndex != plane[0].size) string += " "
        }
        string += "\n"
    }
    string
}
