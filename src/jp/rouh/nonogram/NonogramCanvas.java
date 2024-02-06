package jp.rouh.nonogram;

import jp.rouh.util.Square;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NonogramCanvas {
    private final Square<NonogramCell> values;

    public NonogramCanvas(int width, int height) {
        this.values = new Square<>(width, height, NonogramCell.UNKNOWN);
    }

    public int getWidth() {
        return values.getWidth();
    }

    public int getHeight() {
        return values.getHeight();
    }

    public int getSize() {
        return values.getSize();
    }

    public void set(int x, int y, NonogramCell element) {
        values.set(x, y, element);
    }

    public void setRow(int y, List<NonogramCell> elements) {
        values.setRow(y, elements);
    }

    public void setCol(int x, List<NonogramCell> elements) {
        values.setCol(x, elements);
    }

    public NonogramCell get(int x, int y) {
        return values.get(x, y);
    }

    public List<NonogramCell> getRow(int y) {
        return values.getRow(y);
    }

    public List<NonogramCell> getCol(int x) {
        return values.getCol(x);
    }

    public List<List<NonogramCell>> getCols() {
        return values.getCols();
    }

    public List<List<NonogramCell>> getRows() {
        return values.getRows();
    }

    public List<NonogramCell> unfold() {
        return values.unfold();
    }

    public Stream<NonogramCell> stream() {
        return values.stream();
    }

    public boolean hasRange(int x, int y) {
        return values.hasRange(x, y);
    }

    @Override
    public String toString() {
        return toString("◆", "・", "　", "|");
    }

    public String toString(String filled, String blank, String unknown, String separator) {
        return toString(cell -> cell == NonogramCell.FILLED ? filled : cell == NonogramCell.BLANK ? blank : unknown, separator);
    }

    public String toString(Function<NonogramCell, String> mapper, String separator) {
        return getRows().stream()
                .map(row -> row.stream().map(mapper).collect(Collectors.joining(separator)))
                .map(line -> separator + line + separator)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
