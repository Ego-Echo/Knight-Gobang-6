import java.util.Random;

/**
 * <code>ChequerStatus</code> acts as a data storage in
 * <code>RobotPlayer</code>'s evaluate functions, based on <code>HashMap</code>.
 * <p>
 * <code>ChequerStatus</code> is affiliated to <code>RobotPlayer</code>.
 *
 * @author Instant
 * @author Ego-Echo
 * @see RobotPlayer
 */
public class ChequerStatus {
    /**
     * Records the hash codes for black and white chess pieces in each grid.
     * <p>
     * The <code>1st</code> dimension indicates the x coordinate of the point;
     * <p>
     * The <code>2nd</code> dimension indicates the y coordinate of the point;
     * <p>
     * The <code>3rd</code> dimension indicates the color of the chess piece,
     * <code>0</code> for black, <code>1</code> for white.
     */
    private static final long[][][] GRID_HASH = new long[Constant.LENGTH[2]][Constant.LENGTH[2]][2];
    /**
     * Initializes the grid hash values.
     */
    static {
        Random random = new Random(202174362880L);
        for (int i = 0; i < Constant.LENGTH[2]; i++) {
            for (int j = 0; j < Constant.LENGTH[2]; j++) {
                for (int k = 0; k < 2; k++) {
                    GRID_HASH[i][j][k] = random.nextLong();
                }
            }
        }
    }
    /**
     * Records the situation of each grid of the chessboard.
     * <p>
     * <code>0</code> is blank, <code>1</code> is black, <code>2</code> is white.
     */
    private byte[][] chequer;
    /**
     * Records the hash code for the whole chessboard.
     */
    private long hash;

    /**
     * Constructs a new <code>ChequerStatus</code> with the chequer length
     * <code>length</code>.
     *
     * @param length the length of chequer
     */
    public ChequerStatus(int length) {
        this.chequer = new byte[length][length];
        this.hash = 0;
    }

    /**
     * Sets the chess onto the chessboard.
     *
     * @param chess the moved chess
     */
    public void set(Chess chess) {
        if (chess != null) {
            set(chess.p, chess.color);
        }
    }

    /**
     * Sets the color of the point <code>p</code> to <code>color</code> for a move.
     *
     * @param p     the point whose color is to be set
     * @param color the goal color to set
     */
    public void set(Point p, int color) {
        if (p != null && p.checkRange() && color >= 0 && color <= 2) {
            setHash(p, color);
            chequer[p.x][p.y] = (byte) color;
        }
    }

    /**
     * Sets <code>hash</code> by a chess piece move.
     *
     * @param p     point of the move
     * @param color color of the move
     */
    private void setHash(Point p, int color) {
        if (p != null && p.checkRange() && color >= 0 && color <= 2) {
            if (color != 0) {
                hash ^= GRID_HASH[p.x][p.y][color - 1];
            } else {
                int revert = chequer[p.x][p.y];
                if (revert != 0) {
                    hash ^= GRID_HASH[p.x][p.y][revert - 1];
                }
            }
        }
    }

    /**
     * Gets <code>hash</code> in <code>ChequerStatus</code>, which records current
     * situation.
     *
     * @return the hash code
     */
    public long getZobrist() {
        return hash;
    }
}
