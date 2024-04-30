/**
 * In <strong>Knight Gobang 6</strong>, a <code>Point</code> records information
 * of a single point or position. When calling methods in this class, the
 * variable should correspond to the method.
 *
 * @author Instant
 * @author Ego-Echo
 * @see Direction
 * @see Diagram
 */
public class Point {
    /**
     * Records the x coordinate of the point or position.
     */
    public final int x;
    /**
     * Records the y coordinate of the point or position.
     */
    public final int y;

    /**
     * Constructs a point or position with x and y coordinate.
     *
     * @param x the x coordinate of the point or position
     * @param y the y coordinate of the point or position
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * <strong>This method is only for point.</strong> Makes a move by an appointed
     * direction from the origin point.
     * <p>
     * If the final point is beyond the chessboard range, it will be set to
     * <code>null</code>.
     *
     * @param dir the direction of the move
     * @param len the distance of the move
     * @return the final point
     */
    public Point move(Direction dir, int len) {
        Point p = new Point(this.x + dir.x * len, this.y + dir.y * len);
        if (!p.checkRange()) {
            return null;
        } else {
            return p;
        }
    }

    /**
     * <strong>This method is only for point.</strong> Makes a step by an appointed
     * diagram from the origin point.
     * <p>
     * If the final point is beyond the chessboard range, it will be set to
     * <code>null</code>.
     *
     * @param dia the diagram of the step
     * @param len the distance of the step
     * @return the final point
     */
    public Point step(Diagram dia, int len) {
        Point p = new Point(this.x + dia.x * len, this.y + dia.y * len);
        if (!p.checkRange()) {
            return null;
        } else {
            return p;
        }
    }

    /**
     * Paraphrases the <strong>point</strong> to a string in coordination mode, or
     * paraphrases the <strong>position</strong> to a string in symbol mode.
     *
     * @param flag the paraphrasing mode: <code>true</code> for coordination,
     *             <code>false</code> for symbol
     * @return the paraphrased string
     */
    public String toString(boolean flag) {
        if (flag) {
            return "(" + x + ", " + y + ")";
        } else {
            return Constant.ALPHABET.substring(x - 1, x) + y;
        }
    }

    /**
     * <strong>This method is only for point.</strong> Changes a point to its
     * corresponding position.
     *
     * @return this position
     */
    public Point p2Pos() {
        return new Point(x + 1, y + 1);
    }

    /**
     * <strong>This method is only for position.</strong> Changes a position to its
     * corresponding point.
     *
     * @return this point
     */
    public Point pos2P() {
        return new Point(x - 1, y - 1);
    }

    /**
     * <strong>This method is only for point.</strong> Checks if the point is in the
     * board range.
     *
     * @return <code>true</code> if the point is in the board range,
     *         <code>false</code> otherwise
     */
    public boolean checkRange() {
        return x >= 0 && x < Variable.getLength() && y >= 0 && y < Variable.getLength();
    }

    /**
     * Returns the hash code of the point.
     */
    @Override
    public int hashCode() {
        return Variable.getLength() * x + y;
    }

    /* ------------------------- ADORABLE DIVIDING LINE ------------------------- */

    /**
     * NOT USED
     *
     * @param p
     * @return
     */
    public boolean nearMidThan(Point p) {
        return Math.max(Math.abs(x - Variable.getLength() / 2), Math.abs(y - Variable.getLength() / 2)) < Math
                .max(Math.abs(p.x - Variable.getLength() / 2), Math.abs(p.y - Variable.getLength() / 2));
    }
}
