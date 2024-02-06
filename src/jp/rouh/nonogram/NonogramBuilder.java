package jp.rouh.nonogram;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class NonogramBuilder {
    private final List<int[]> rowHints = new ArrayList<>();
    private final List<int[]> colHints = new ArrayList<>();
    private final int width;
    private final int height;

    public NonogramBuilder(){
        this(0, 0);
    }

    public NonogramBuilder(int width, int height){
        this.width = width;
        this.height = height;
    }

    public NonogramBuilder appendRowHint(int...hint){
        rowHints.add(hint);
        return this;
    }

    public NonogramBuilder appendColHint(int...hint){
        colHints.add(hint);
        return this;
    }

    public Nonogram build() {
        if (width>0 && colHints.size()!=width){
            throw new IllegalStateException("invalid range: col hints");
        }
        if (height>0 && rowHints.size()!=width){
            throw new IllegalStateException("invalid range: row hints");
        }
        return new Nonogram(rowHints, colHints);
    }
}
