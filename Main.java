package brickwork;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] sizes = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int rows = sizes[0];
        int cols = sizes[1];

        int[][] layer1 = new int[rows][cols];
        int[][] layer2 = new int[rows][cols];

        createMatrixFromConsole(scanner, layer1, rows);
        createNextLayerInArea(layer1, layer2, rows, cols);
        printMatrix(layer2);
    }

    // create a first layer from input
    private static void createMatrixFromConsole(Scanner scanner, int[][] grid, int rotations) {
        for (int i = 0; i < rotations; i++) {
            grid[i] = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
    }

    /* create second layer with some conditions
    first check if it is possible to put the brick in horizontal position and if it is not,
    then put it in vertical position
    */
    private static void createNextLayerInArea(int[][] matrix1, int[][] matrix2, int rows, int cols) {
        int counter = 1;

        for (int row = 0; row < matrix1.length; row++) {
            for (int col = 0; col < matrix1[row].length; col++) {
                int nextCol = col + 1;
                int nextRow = row + 1;
                //  check if next cell in the next column is in the area, if false => check next cell in the row below
                if (isInTheMatrix(rows, cols, row, nextCol)) {
                    counter = fillHorizontalLayer2Cell(matrix1, matrix2, rows, cols, row, col, counter);
                } else {
                    if (isInTheMatrix(rows, cols, nextRow, col)) {
                        counter = fillVerticalLayer2Cell
                                (matrix1, matrix2, rows, cols, row, col, counter);
                    }
                }
            }
        }
    }

    // if cell is empty, will be different then zero
    private static boolean isEmptyCell(int number) {
        return number == 0;
    }

    // check if the cells coordinate are in matrix area
    private static boolean isInTheMatrix(int maxRow, int maxCol, int currentRow, int currentCol) {
        return ((0 <= currentRow && currentRow < maxRow) && (0 <= currentCol && currentCol < maxCol));
    }

    // fill empty horizontal cell if it possible, or fill possible vertical cell and return updated counter
    private static int fillHorizontalLayer2Cell(int[][] matrix, int[][] matrix2, int rows, int cols,
                                                int currentRow, int currentCol, int counter) {
        int currentCellMatrix1 = matrix[currentRow][currentCol];
        int nextCellMatrix1 = matrix[currentRow][currentCol + 1];
        int currentCellMatrix2 = matrix2[currentRow][currentCol];
        int nextCellMatrix2 = matrix2[currentRow][currentCol + 1];

        if (currentCellMatrix1 != nextCellMatrix1) {
            if (isEmptyCell(currentCellMatrix2) && isEmptyCell(nextCellMatrix2)) {
                matrix2[currentRow][currentCol] = counter;
                matrix2[currentRow][currentCol + 1] = counter;
                counter++;
            }
        } else {
            int nextRow = currentRow + 1;
            if (isInTheMatrix(rows, cols, nextRow, currentCol)) {
                counter = fillVerticalLayer2Cell(matrix, matrix2, rows, cols, currentRow, currentCol, counter);
            }
        }
        return counter;
    }

    // fill possible vertical cell and return updated counter
    private static int fillVerticalLayer2Cell(int[][] matrix, int[][] matrix2, int rows, int cols,
                                              int currentRow, int currentCol, int counter) {
        int nextRow = currentRow + 1;
        int currentCellMatrix2 = matrix2[currentRow][currentCol];
        int nextCellMatrix2 = matrix2[currentRow + 1][currentCol];


        if (isInTheMatrix(rows, cols, nextRow, currentCol)) {
            if (isEmptyCell(currentCellMatrix2) && isEmptyCell(nextCellMatrix2)) {
                matrix2[currentRow][currentCol] = counter;
                matrix2[currentRow + 1][currentCol] = counter;
                counter++;
            }
        }
        return counter;
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }
}
