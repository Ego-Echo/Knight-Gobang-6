/**
 * <code>PointAndValue</code> records information of a point and its
 * corresponding value in a single situation. <strong>This class only permits
 * point mode </strong><code>Point</code> <strong>in the point part.</strong>
 *
 * @author Instant
 * @author Ego-Ego
 * @see Point
 */
public class PointAndValue {
    /**
     * Records the coordinate of the point in a certain situation.
     */
    public final Point p;
    /**
     * Records the value of the point in a certain situation.
     */
    public final int value;

    /**
     * Constructs a set of a point and its corresponding value in a certain
     * situation.
     *
     * @param p     the coordinate of the point of the set
     * @param value the value of the point of the set
     */
    public PointAndValue(Point p, int value) {
        this.p = p;
        this.value = value;
    }

    /**
     * Constructs a set of a new point and its corresponding value in a certain
     * situation.
     *
     * @param x     the x coordinate of the point of the set
     * @param y     the y coordinate of the point of the set
     * @param value the value of the point of the set
     */
    public PointAndValue(int x, int y, int value) {
        this.p = new Point(x, y);
        this.value = value;
    }
}
