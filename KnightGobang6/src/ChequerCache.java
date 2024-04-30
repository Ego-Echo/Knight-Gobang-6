import java.util.HashMap;
import java.util.Map;

/**
 * <code>ChequerCache</code> acts as a buffer for data storage in
 * <code>RobotPlayer</code>'s evaluate functions, based on <code>HashMap</code>.
 * <p>
 * <code>ChequerCache</code> is affiliated to <code>RobotPlayer</code>.
 *
 * @author Instant
 * @author Ego-Echo
 * @see RobotPlayer
 * @see PointAndValueAndDeep
 */
public class ChequerCache {
    /**
     * Records all the key-value pairs in <code>ChequerCache</code>.
     */
    private static final Map<Long, PointAndValueAndDeep> cache = new HashMap<>();;

    /**
     * Constructs a new <code>ChequerCache</code>.
     */
    public ChequerCache() {
    }

    /**
     * Returns the pair of point and value in storage without putting if the depth
     * in storage is deeper, otherwise puts and returns <code>null</code>.
     *
     * @param key key of the point and color
     * @param pvd the set of point and value and deep
     */
    public void put(long key, PointAndValueAndDeep pvd) {
        put(key, pvd.pv, pvd.deep);
    }

    /**
     * Returns the pair of point and value in storage without putting if the depth
     * in storage is deeper, otherwise puts and returns <code>null</code>.
     *
     * @param key  key of the point and color
     * @param pv   the set of point and value
     * @param deep depth of the point
     */
    public void put(long key, PointAndValue pv, int deep) {
        put(key, pv.p, pv.value, deep);
    }

    /**
     * Returns the pair of point and value in storage without putting if the depth
     * in storage is deeper, otherwise puts and returns <code>null</code>.
     *
     * @param key   key of the point and color
     * @param p     point
     * @param value value of the point
     * @param deep  depth of the point
     */
    public void put(long key, Point p, int value, int deep) {
        PointAndValueAndDeep cacheValue = cache.get(key);
        if (cacheValue != null && cacheValue.deep < deep) {
            return;
        } else {
            cache.put(key, new PointAndValueAndDeep(p, value, deep));
        }
    }

    /**
     * Returns the pair of point and value in storage if the depth is enough and it
     * exists in storage, otherwise returns <code>null</code>.
     *
     * @param key  key of the point and color
     * @param deep depth of the point
     * @return the pair of point and value
     */
    public PointAndValue get(long key, int deep) {
        PointAndValueAndDeep cacheValue = cache.get(key);
        if (cacheValue != null && cacheValue.deep <= deep) {
            return cacheValue.pv;
        } else {
            return null;
        }
    }
}
