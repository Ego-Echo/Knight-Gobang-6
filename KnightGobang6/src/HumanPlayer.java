/**
 * In <strong>Knight Gobang 6</strong>, a <code>HumanPlayer</code> participates
 * in a match and decides how to play in a human's POV.
 * <p>
 * In a single match, there should be set 0 <code>HumanPlayer</code>s (for EVE
 * mode), 1 <code>HumanPlayer</code> (for PVE mode), or 2
 * <code>HumanPlayer</code>s (for PVP mode).
 * <p>
 * <code>HumanPlayer</code> is affiliated to <code>Gameboard</code>, while
 * extending <code>HumanWatcher</code> and implementing <code>Player</code>.
 *
 * @author Instant
 * @author Ego-Echo
 * @see Gameboard
 * @see HumanWatcher
 * @see Player
 */
public class HumanPlayer implements Player {
    /**
     * Records the chess side of <code>HumanPlayer</code>.
     * <p>
     * <code>1</code> is for black, <code>2</code> is for white.
     */
    public final int color;
    /**
     * Records the current point<code>p</code> where <code>HumanPlayer</code>
     * decides
     * to move.
     */
    private Point p;
    /**
     * Points to the current gameboard.
     */
    private final Gameboard gameboard;

    /**
     * Constructs a new <code>HumanPlayer</code> with its chess side
     * <code>color</code>.
     *
     * @param color the chess side of <code>HumanPlayer</code>, <code>1</code> for
     *              black, <code>2</code> for white
     */
    public HumanPlayer(int color) {
        this.color = color;
        this.p = null;
        this.gameboard = KnightGobang6.gameboard;
    }

    /**
     * Notifies <code>HumanPlayer</code> the point <code>p</code> and the color
     * <code>color</code> of each chess piece after it moves.
     * <p>
     * This method does nothing, since relevant functions is achieved in
     * <code>GoListener</code>.
     *
     * @param p     the point of the chess piece
     * @param color the color of the chess piece: <code>1</code> for black,
     *              <code>2</code> for white
     */
    public void notifyMove(Point p, int color) {
    }

    /**
     * Notifies <code>HumanPlayer</code> to retract or to retraction in specific
     * step(s).
     * <p>
     * This method does nothing, since relevant functions is achieved in
     * <code>History</code>.
     *
     * @param step the step(s) for retraction or reretraction
     * @param flag <code>true</code> for retract, <code>false</code> for reretract
     */
    public void notifyRetraction(int step, boolean flag) {
    }

    /**
     * Sets the current point <code>p</code> to a specific point.
     *
     * @param p the point
     */
    public void move(Point p) {
        this.p = p;
    }

    /**
     * Waits for user's mouse action, according to which, returns where to move the
     * chess piece.
     */
    @Override
    public void play() {
        synchronized (this) {
            try {
                while (p == null) {
                    wait(50);
                }
            } catch (InterruptedException e) {
                if (gameboard.isRetractionInterrupted()) {
                    return;
                } else {
                    e.printStackTrace();
                }
            }
        }
        Point currentP = p;
        p = null;
        if (currentP == null) {
            throw new IllegalArgumentException("A null point in HumanPlayer.play()!");
        }
        gameboard.notifyMove(currentP, color == 1 ? true : false);
    }
}
