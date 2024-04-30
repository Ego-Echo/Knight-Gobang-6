/**
 * The major class in <strong>Knight Gobang 6</strong>, which manages the whole
 * code and contains the only code entrance.
 * <p>
 * More details in README.md.
 *
 * @author Instant
 * @author Ego-Echo
 */
public class KnightGobang6 {
    /**
     * Records <code>gameboard</code> in current match or in rest state.
     */
    public static Gameboard gameboard;
    /**
     * Records <code>history</code> in current match or in rest state.
     */
    public static History history;
    /**
     * Records <code>chessboard</code> in current match or in rest state.
     */
    public static Chessboard chessboard;
    /**
     * Records <code>goListener</code> in current <code>chessboard</code>.
     */
    public static GoListener goListener;

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        gameboard = new Gameboard();
        history = new History();
        goListener = new GoListener();
        UI.initVariable();
        chessboard = UI.chessboard;
        history.initVariable();
        goListener.initVariable();
        gameboard.initVariable();
        gameboard.isInited = true;
    }

    /**
     * Starts a new match.
     */
    public static void newMatch() {
        history = new History();
        goListener = new GoListener();
        UI.initVariable();
        chessboard = UI.chessboard;
        history.initVariable();
        goListener.initVariable();
        gameboard.initVariable();
        gameboard.isInited = true;
        System.out.println("New match prepared.");
    }
}
