/**
 * <code>Diagram</code> enumerates all 8 kinds of knight move.
 *
 * @author Instant
 * @author Ego-Echo
 * @see Point
 * @see Direction
 */
public enum Diagram {
    ZHEN(2, 1), KUN(1, 2), GEN(-1, 2), KAN(-2, 1), XUN(-2, -1), QIAN(-1, -2), DUI(1, -2), LI(2, -1);

    /**
     * Records the x displacement.
     */
    public final int x;
    /**
     * Records the y displacement.
     */
    public final int y;

    /**
     * Constructs a private diagram with information of x and y displacement.
     *
     * @param x the x displacement
     * @param y the y displacement
     */
    private Diagram(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
