/**
 * In <strong>Knight Gobang 6</strong>, a <code>Player</code> participates in a
 * match and decides how to play.
 * <p>
 * In a single match, there should be set 2 <code>Player</code>s.
 * <p>
 * <code>Player</code> is affiliated to <code>Gameboard</code>.
 *
 * @author Instant
 * @author Ego-Echo
 * @see Gameboard
 */
public interface Player {
    /**
     * Notifies <code>Player</code> the point <code>p</code> and the color
     * <code>color</code> of each chess piece after it moves.
     *
     * @param p     the point of the chess piece
     * @param color the color of the chess piece: <code>1</code> for black,
     *              <code>2</code> for white
     */
    void notifyMove(Point p, int color);

    /**
     * Notifies <code>Player</code> to retract or to retraction in specific step(s).
     *
     * @param step the step(s) for retraction or reretraction
     * @param flag <code>true</code> for retract, <code>false</code> for reretract
     */
    void notifyRetraction(int step, boolean flag);

    /**
     * Notifies <code>Gameboard</code> where to move the chess piece.
     */
    void play();
}
