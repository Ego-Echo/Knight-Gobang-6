/**
 * <code>Direction</code> enumerates all 8 kinds of straight direction.
 *
 * @author Instant
 * @author Ego-Echo
 * @see Point
 * @see Diagram
 */
public enum Direction {
    RIGHT(1, 0), UPRIGHT(1, 1), UP(0, 1), UPLEFT(-1, 1), LEFT(-1, 0), DOWNLEFT(-1, -1), DOWN(0, -1), DOWNRIGHT(1, -1);

    /**
     * Records the x displacement.
     */
    public final int x;
    /**
     * Records the y displacement.
     */
    public final int y;

    /**
     * Constructs a private direction with information of x and y displacement.
     *
     * @param x the x displacement
     * @param y the y displacement
     */
    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 4 directions of all, which is: <code>RIGHT</code>, <code>UPRIGHT</code>,
     * <code>UP</code>, <code>UPLEFT</code>.
     */
    private static Direction[] _4Directions = { RIGHT, UPRIGHT, UP, UPLEFT };

    /**
     * Gets 4 of the directions, which is: <code>RIGHT</code>, <code>UPRIGHT</code>,
     * <code>UP</code>, <code>UPLEFT</code>.
     *
     * @return 4 directions
     */
    public static Direction[] get4Directions() {
        return _4Directions;
    }
}
