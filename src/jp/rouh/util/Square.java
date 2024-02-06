package jp.rouh.util;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 2次元の固定長の要素を保持するクラス
 * @param <E> 要素の型
 */
@SuppressWarnings("unused")
public class Square<E>{
    private final int width;
    private final int height;
    private final Object[][] values;

    /**
     * コンストラクタ
     * 引数で与えられた生成関数をもとに初期化します。
     * 生成関数には、引数として座標(x, y)が与えられます。
     * @param width 幅
     * @param height 高さ
     * @param factory 生成関数
     */
    public Square(int width, int height, BiFunction<Integer, Integer, ? extends E> factory){
        this.width = width;
        this.height = height;
        this.values = new Object[height][width];
        for(int y = 0; y<height; y++){
            for(int x = 0; x<width; x++){
                values[y][x] = factory.apply(x, y);
            }
        }
    }

    /**
     * コンストラクタ
     * 引数で与えられた生成関数をもとに初期化します。
     * @param width 幅
     * @param height 高さ
     * @param factory 生成関数
     */
    public Square(int width, int height, Supplier<? extends E> factory){
        this(width, height, (xIgnored, yIgnored)->factory.get());
    }

    /**
     * コンストラクタ
     * 引数で与えられた初期値をもとに初期化します。
     * @param width 幅
     * @param height 高さ
     * @param defaultValue 初期値
     */
    public Square(int width, int height, E defaultValue){
        this(width, height, ()->defaultValue);
    }

    /**
     * コンストラクタ
     * 引数で与えられた二次元配列をもとに初期化します。
     * @param array 二次元配列
     */
    public Square(E[][] array){
        this(array[0].length, array.length, (x, y)->array[y][x]);
    }

    /**
     * コンストラクタ
     * 初期値としてnullが設定されます。
     * @param width 幅
     * @param height 高さ
     */
    public Square(int width, int height){
        this(width, height, (x, y)->null);
    }

    /**
     * 幅を返します。
     * @return 幅
     */
    public int getWidth(){
        return width;
    }

    /**
     * 高さを返します。
     * @return 高さ
     */
    public int getHeight(){
        return height;
    }

    /**
     * 幅*高さの値を返します。
     * @return 幅*高さ
     */
    public int getSize(){
        return width*height;
    }

    /**
     * 指定した位置に指定した要素を挿入します
     * @param x x座標
     * @param y y座標
     * @param element 保持する要素
     */
    public void set(int x, int y, E element){
        values[y][x] = element;
    }

    public void setRow(int y, List<E> elements){
        if (elements.size()!=width){
            throw new IllegalArgumentException("invalid row length");
        }
        for (int x = 0; x<width; x++){
            values[y][x] = elements.get(x);
        }
    }

    public void setCol(int x, List<E> elements){
        if (elements.size()!=height){
            throw new IllegalArgumentException("invalid col length");
        }
        for (int y = 0; y<height; y++){
            values[y][x] = elements.get(y);
        }
    }

    /**
     * 指定した位置に保持された要素を取得します
     * @param x x座標
     * @param y y座標
     * @return 指定した位置に保持されている要素
     */
    @SuppressWarnings("unchecked")
    public E get(int x, int y){
        requireWithinRange(x, y);
        return (E)values[y][x];
    }

    private E get(int index){
        return get(index%width, index/width);
    }

    /**
     * 指定した列の全ての要素をリストとして取得します
     * @param y y座標
     * @return 列のリスト
     */
    public List<E> getRow(int y){
        return IntStream.range(0, width).mapToObj(x->get(x, y)).collect(toList());
    }

    /**
     * 指定した行のすべての要素をリストとして取得します
     * @param x x座標
     * @return 行のリスト
     */
    public List<E> getCol(int x){
        return IntStream.range(0, height).mapToObj(y->get(x, y)).collect(toList());
    }

    public List<List<E>> getCols(){
        return IntStream.range(0, height).mapToObj(this::getCol).toList();
    }

    public List<List<E>> getRows(){
        return IntStream.range(0, width).mapToObj(this::getRow).toList();
    }

    /**
     * 全ての要素を一次元のリストとして取得します
     * @return 全要素のリスト
     */
    public List<E> unfold(){
        return IntStream.range(0, getSize()).mapToObj(this::get).collect(toList());
    }

    /**
     * 全ての要素を一次元のストリームとして取得します
     * @return 全要素のストリーム
     */
    public Stream<E> stream(){
        return unfold().stream();
    }

    /**
     * 座標(x, y)が範囲内であるか判定します。
     * @param x x座標
     * @param y y座標
     * @return 判定結果
     */
    public boolean hasRange(int x, int y){
        return x>=0 && x<width && y>=0 && y<height;
    }

    private void requireWithinRange(int x, int y){
        if(!hasRange(x, y)){
            throw new IllegalArgumentException("out of bounds ("+x+", "+y+")");
        }
    }
}
