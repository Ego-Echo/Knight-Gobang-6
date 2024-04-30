/**
 * <code>PointAndValueAndDeep</code> records information of a point and its
 * corresponding value and current depth in a single situation. <strong>This
 * class only permits point mode </strong><code>Point</code> <strong>in the
 * point part.</strong>
 *
 * @author Instant
 * @author Ego-Ego
 * @see Point
 * @see PointAndValue
 */
public class PointAndValueAndDeep {
    /**
     * Records the coordinate of the point and its corresponding value in a specific
     * situation.
     */
    public final PointAndValue pv;
    /**
     * Records the coordinate of the point in a specific situation.
     */
    public final Point p;
    /**
     * Records the corresponding value of the point in a specific situation.
     */
    public final int value;
    /**
     * Records the current depth of the point in a specific situation.
     */
    public final int deep;

    /**
     * Constructs a pair of point and value and its current depth in a specific
     * situation.
     *
     * @param pv   the pair of point and value of the set
     * @param deep the current depth of the point of the set
     */
    public PointAndValueAndDeep(PointAndValue pv, int deep) {
        this.pv = pv;
        this.p = pv.p;
        this.value = pv.value;
        this.deep = deep;
    }

    /**
     * Constructs a set of a point and its corresponding value and current depth in
     * a specific situation.
     *
     * @param p     the coordinate of the point of the set
     * @param value the corresponding value of the point of the set
     * @param deep  the current depth of the point of the set
     */
    public PointAndValueAndDeep(Point p, int value, int deep) {
        this.pv = new PointAndValue(p, value);
        this.p = p;
        this.value = value;
        this.deep = deep;
    }

    /**
     * Constructs a set of a new point and its corresponding value and current depth
     * in a specific situation.
     *
     * @param x     the x coordinate of the point of the set
     * @param y     the y coordinate of the point of the set
     * @param value the corresponding value of the point of the set
     * @param deep  the current depth of the point of the set
     */
    public PointAndValueAndDeep(int x, int y, int value, int deep) {
        this.p = new Point(x, y);
        this.pv = new PointAndValue(p, value);
        this.value = value;
        this.deep = deep;
    }
}
