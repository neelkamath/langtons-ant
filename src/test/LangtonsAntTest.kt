package com.neelkamath.langtonsant.test

import com.neelkamath.langtonsant.*
import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GridTest {
    @Test
    fun `Instantiating Grid with invalid Plane should throw an exception`() {
        assertFails { Grid(createPlane(0, 1), Ant(Position(0, 0), Direction.UP)) }
    }

    @Test
    fun `Instantiating Grid with an invalid ant position should throw Exception`() {
        assertFails { Grid(createPlane(3, 2), Ant(Position(4, 5), Direction.UP)) }
    }

    @Test
    fun `Nonexistent cells should be null`() = listOf(
        Ant(Position(0, 1), Direction.UP),
        Ant(Position(4, 1), Direction.RIGHT),
        Ant(Position(4, 0), Direction.DOWN),
        Ant(Position(2, 0), Direction.LEFT)
    )
        .map { Grid(createPlane(5, 2), it) }
        .forEach { assertNull(it.cellAheadOfAnt, "The cell ahead of $it doesn't exist") }

    @Test
    fun `Moving an ant forward should move it one cell front`() = mapOf(
        Direction.UP to Position(0, 1),
        Direction.RIGHT to Position(1, 2),
        Direction.DOWN to Position(2, 1),
        Direction.LEFT to Position(1, 0)
    ).forEach { (direction, newPosition) ->
        val oldPosition = Position(1, 1)
        with(Grid(createPlane(3), Ant(oldPosition, direction))) {
            moveAntForward()
            assertEquals(newPosition, ant.position, "Ant should move from $oldPosition to $newPosition")
        }
    }
}

class GridCreatorTest {
    /**
     * Returns a [Grid] with a [Grid.plane] of size [rows] and [columns]. The [Grid.plane] will be filled with
     * [Cell.WHITE]. The [Grid.ant] will be placed randomly.
     */
    private fun createGrid(rows: Int, columns: Int) = Grid(
        createPlane(rows, columns),
        Ant(Position(Random.nextInt(rows), Random.nextInt(columns)), Direction.values().random())
    )

    @Test
    fun `creating a grid should create a grid containing only white cells with a randomly placed ant`() {
        val rows = 3
        val columns = 5
        with(createGrid(rows, columns)) {
            assertEquals(createPlane(rows, columns), plane)
            assertTrue(ant.position.row in 0 until rows)
            assertTrue(ant.position.column in 0 until columns)
        }
    }
}

class GridProgressTest {
    @Test
    fun `progressing the grid when the ant is on a white cell should make it black and move the ant right`() {
        val antRow = 1
        val antColumn = 1
        val grid = Grid(createPlane(3), Ant(Position(antRow, antColumn), Direction.UP))
        with(grid.copy()) {
            plane[antRow][antColumn] = Cell.BLACK
            ant.position.column += 1
            ant.direction = Direction.RIGHT
            assertEquals(this, progressGrid(grid))
        }
    }

    @Test
    fun `progressing the grid when the ant is on a black cell should make it white and move the ant left`() {
        val antRow = 1
        val antColumn = 1
        val plane = createPlane(3)
        plane[antRow][antColumn] = Cell.BLACK
        val grid = Grid(plane, Ant(Position(antRow, antColumn), Direction.UP))
        with(grid.copy()) {
            plane[antRow][antColumn] = Cell.WHITE
            ant.position.column -= 1
            ant.direction = Direction.LEFT
            assertEquals(this, progressGrid(grid))
        }
    }

    @Test
    fun `progressing the grid when the ant is going to hit a wall should give null`() {
        assertNull(progressGrid(Grid(createPlane(3), Ant(Position(2, 2), Direction.UP))))
    }
}
