# Langton's Ant

This is an implementation of the [Langton's Ant](https://en.wikipedia.org/wiki/Special:Search?search=langton%27s%20ant&go=Go) cellular automaton. It prints the configurable grid's time steps to stdout.

## Installation

1. Install [Kotlin 1.3](https://kotlinlang.org/docs/tutorials/command-line.html).
1. Clone the repository using one of the following methods.
    - SSH: `git clone git@github.com:neelkamath/langtons-ant.git`
    - HTTPS: `git clone https://github.com/neelkamath/langtons-ant.git`

## Usage

1. `cd langtons-ant`
1. Run using `<GRADLE> run --args="<STEPS> <ROWS> <COLUMNS> <ROW> <COLUMN> <DIRECTION>"`. Replace `<GRADLE>` with `gradle.bat` if you're on Windows, and `./gradlew` on others. All the following arguments are optional. `<STEPS>` (`200` by default) are the number of time steps the grid should progress. `<ROWS>` (`11` by default) and `<COLUMNS>` (`11` by default) are the number of rows and columns in the plane. `<ROW>` (`5` by default) and `<COLUMN>` (`5` by default) is the row and column the ant is initially on (the first row/column starts from zero). `DIRECTION` (`LEFT` by default) is the ant's initial direction (one of `UP`, `RIGHT`, `DOWN`, and `LEFT`).

## Testing

`<GRADLE> test`, where `<GRADLE>` is `gradle.bat` on Windows, and `./gradlew` on others.

## License

This project is under the [MIT License](LICENSE).
