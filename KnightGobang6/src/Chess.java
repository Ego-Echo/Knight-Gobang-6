/**
 * <code>Chess</code> records information of a single chess piece.
 *
 * @author Instant
 * @author Ego-Echo
 * @see Point
 */
public class Chess {
    /**
     * Records the point of the chess piece.
     */
    public final Point p;
    /**
     * Records the color of the chess piece: <code>1</code> for black,
     * <code>2</code> for white.
     * <p>
     * Or records the action of the player: <code>0</code> for retraction,
     * <code>-1</code> for reretraction.
     * <p>
     * For the last chess in a sequence, there are also: <code>-2</code> for white
     * admitting defeat, <code>-1</code> for black admitting defeat,<code>0</code>
     * for an abend, <code>1</code> for black victory, <code>2</code> for white
     * victory, <code>3</code> for draw match.
     */
    public final int color;

    /**
     * Constructs an instantiated chess with information of point and color.
     *
     * @param p     the point of the chess piece
     * @param color the color of the chess piece
     */
    public Chess(Point p, int color) {
        this.p = p;
        this.color = color;
    }

    /**
     * Paraphrases the chess to a string with its point and color.
     * <p>
     * The point part uses the method <code>Point.toString(boolean)</code>.
     *
     * @param flag   the paraphrasing mode for point part: <code>true</code> for
     *               coordination, <code>false</code> for symbol
     * @param isLast the paraphrasing mode for color part :<code>true</code> for the
     *               last chess, which acts as an ending index, <code>false</code>
     *               for the other normal chess
     */
    public String toString(boolean flag, boolean isLast) {
        if (isLast) {
            return Constant.RECTEXT_FINAL[color + 2];
        } else {
            if (p == null) {
                return color == 0 ? "Retraction;" : color == -1 ? "Reretraction;" : "A null point;";
            } else {
                return p.p2Pos().toString(flag) + (color == 1 ? ", Black;" : ", White;");
            }
        }
    }
}
