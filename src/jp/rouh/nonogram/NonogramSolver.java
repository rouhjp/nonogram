package jp.rouh.nonogram;

import java.util.*;

public class NonogramSolver {
    private final Nonogram nonogram;
    public NonogramSolver(Nonogram nonogram){
        this.nonogram = nonogram;
    }

    public NonogramCanvas solve(){
        return solve(nonogram);
    }

    private static NonogramCanvas solve(Nonogram nonogram){
        int width = nonogram.getWidth();
        int height = nonogram.getHeight();
        var canvas = new NonogramCanvas(width, height);
        for(int sum = 0;;){
            int sumBefore = sum;
            for (int i = 0; i<width; i++){
                var line = canvas.getCol(i);
                var hint = nonogram.getColHintAt(i);
                var solved = solveLine(line, hint);
                canvas.setCol(i, solved);
            }
            for (int i = 0; i<height; i++){
                var line = canvas.getRow(i);
                var hint = nonogram.getRowHintAt(i);
                canvas.setRow(i, solveLine(line, hint));
            }
            if ((sum = (int)canvas.stream().filter(NonogramCell.UNKNOWN::equals).count())==sumBefore){
                break;
            }
        }
        return canvas;
    }

    private static List<NonogramCell> solveLine(List<NonogramCell> line, int[] hint){
        int blackCount = Arrays.stream(hint).sum();
        int whiteCount = line.size() - blackCount;
        if (blackCount > line.size()){
            throw new IllegalArgumentException();
        }

        var allFilled = new boolean[line.size()];
        var allBlank = new boolean[line.size()];
        Arrays.fill(allFilled, true);
        Arrays.fill(allBlank, true);
        loop: for(int[] spacing = initCombination(whiteCount, hint.length + 1);
                  spacing != null; spacing = nextCombination(spacing)){
            for (int i = 1; i<spacing.length - 1; i++){
                if (spacing[i]==0){
                    continue loop;
                }
            }
            var candidate = new NonogramCell[line.size()];
            for (int i = 0, c = 0; i<spacing.length || i<hint.length; i++){
                if (i < spacing.length){
                    for (int k = 0; k<spacing[i]; k++){
                        if (line.get(c)==NonogramCell.FILLED){
                            continue loop;
                        }
                        candidate[c++] = NonogramCell.BLANK;
                    }
                }
                if (i < hint.length){
                    for (int k = 0; k<hint[i]; k++){
                        if (line.get(c)==NonogramCell.BLANK){
                            continue loop;
                        }
                        candidate[c++] = NonogramCell.FILLED;
                    }
                }
            }

            boolean anyFixed = false;
            for (int i = 0; i<line.size(); i++){
                switch (candidate[i]){
                    case BLANK -> allFilled[i] = false;
                    case FILLED -> allBlank[i] = false;
                }
                anyFixed |= allFilled[i] || allBlank[i];
            }
            if (!anyFixed){
                break;
            }
        }

        var newLine = new ArrayList<NonogramCell>(line.size());
        for (int i = 0; i<line.size(); i++){
            if (line.get(i)!=NonogramCell.UNKNOWN){
                newLine.add(line.get(i));
            }else if (allFilled[i]){
                newLine.add(NonogramCell.FILLED);
            }else if (allBlank[i]){
                newLine.add(NonogramCell.BLANK);
            }else {
                newLine.add(NonogramCell.UNKNOWN);
            }
        }
        return newLine;
    }

    private static int[] initCombination(int sum, int size){
        int[] combination = new int[size];
        combination[0] = sum;
        return combination;
    }

    private static int[] nextCombination(int[] current){
        int[] next = Arrays.copyOf(current, current.length);
        int carryOver = next[next.length - 1];
        next[next.length - 1] = 0;
        int lastNonZeroIndex = -1;
        for (int i = next.length - 1; i>=0; i--){
            if (next[i]!=0){
                lastNonZeroIndex = i;
                break;
            }
        }
        if (lastNonZeroIndex==-1){
            return null;
        }
        next[lastNonZeroIndex]--;
        next[lastNonZeroIndex + 1] += 1 + carryOver;
        return next;
    }
}
