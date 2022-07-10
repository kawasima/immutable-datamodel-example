package example.simple.composability;

/**
 * 当選処理のインタフェース。
 *
 * @author kawasima
 */
public interface Winnable {
    WinningOrder win(String productSerialNo);
}
