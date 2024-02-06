package jp.rouh.nonogram;

import java.util.List;

public class Nonogram {
    private final List<int[]> rowHints;
    private final List<int[]> colHints;
    public Nonogram(List<int[]> rowHints, List<int[]> colHints){
        this.rowHints = List.copyOf(rowHints);
        this.colHints = List.copyOf(colHints);
    }

    public List<int[]> getRowHints() {
        return rowHints;
    }

    public List<int[]> getColHints() {
        return colHints;
    }

    public int[] getRowHintAt(int r){
        return rowHints.get(r);
    }

    public int[] getColHintAt(int c){
        return colHints.get(c);
    }

    public int getWidth(){
        return this.colHints.size();
    }

    public int getHeight(){
        return this.rowHints.size();
    }
}
