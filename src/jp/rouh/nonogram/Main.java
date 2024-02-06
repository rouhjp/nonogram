package jp.rouh.nonogram;

public class Main {
    public static void main(String[] args) {
        var nonogram = new NonogramBuilder()
                .appendRowHint(7, 1, 1, 1, 1, 1)
                .appendRowHint(1, 1, 2, 2)
                .appendRowHint(1, 3, 1, 3)
                .appendRowHint(1, 3, 1, 1, 5, 1)
                .appendRowHint(1, 3, 1, 1, 3, 1)
                .appendRowHint(1, 1, 2, 2, 1)
                .appendRowHint(7, 2, 2, 1)
                .appendRowHint(4, 1, 1)
                .appendRowHint(1, 1, 3, 1, 1, 1, 1)
                .appendRowHint(1, 4, 1, 1, 2)
                .appendRowHint(2, 1, 3, 3, 1)
                .appendRowHint(2, 1, 2, 5)
                .appendRowHint(1, 6, 1, 1, 1)
                .appendRowHint(1, 2, 2, 3, 1, 1)
                .appendRowHint(1, 1, 2, 1)
                .appendRowHint(1, 2, 3, 2)
                .appendRowHint(2, 1, 2, 3, 1, 1, 1)
                .appendColHint(7, 1, 1, 1, 1, 1)
                .appendColHint(1, 1, 1, 1, 2)
                .appendColHint(1, 3, 1, 1, 1)
                .appendColHint(1, 3, 1, 1, 4, 2)
                .appendColHint(1, 3, 1, 1, 2, 1)
                .appendColHint(1, 1, 1, 2, 1, 1)
                .appendColHint(7, 2, 3, 1)
                .appendColHint(3, 2)
                .appendColHint(2, 1, 2, 4, 2)
                .appendColHint(1, 3, 3, 4)
                .appendColHint(1, 2, 2, 2, 2)
                .appendColHint(2, 2, 2, 1)
                .appendColHint(9, 3, 2)
                .appendColHint(5, 3, 1, 1)
                .appendColHint(1, 2, 2, 1, 1)
                .appendColHint(2, 1, 1)
                .appendColHint(1, 2, 7, 1)
                .build();
        var canvas = new NonogramSolver(nonogram).solve();
        System.out.println(canvas.toString("■"," ", "?", " "));
    }
}
