import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * <code>Chessboard</code> paints a chessboard with symbol type coordinate and
 * anchors also painted while extending <code>JPanel</code>.
 *
 * @author Instant
 * @author Ego-Echo
 * @see KnightGobang6
 * @see GoListener
 * @see History
 * @see UI
 */
public class Chessboard extends JPanel {
    /**
     * Records the color of the chessboard.
     */
    private static final Color CHESSBOARD_COLOR = new Color(255, 241, 207);
    /**
     * Records the anchors of the chessboard in 3 difficulties.
     */
    private static final int[][] ANCHOR = { { 4, 10, 16 }, { 5, 11, 18, 25, 31 }, { 6, 12, 18, 25, 32, 38, 44 } };
    /**
     * Records the anchors' size in 3 difficulties.
     */
    private static final int[] ANCHOR_SIZE = { 3, 3, 2 };
    /**
     * Records the graphics of the chessboard.
     */
    private Graphics g;
    /**
     * Records the difficulty <code>RobotPlayer</code> is in: <code>0</code> for
     * normal, <code>1</code> for hard, <code>2</code> for impossible. This variable
     * only works when the match is in PVE or PVP type.
     */
    private static byte dif;
    /**
     * Record the length of the chessboard in current match.
     */
    private static int len;
    /**
     * Record the chess' size of the chessboard in current match.
     */
    private static int css;
    /**
     * Record the grid's size of the chessboard in current match.
     */
    private static int scl;
    /**
     * Records if order is displayed in present.
     */
    private static boolean orderFlag;
    /**
     * Records if field is displayed in present.
     */
    private static boolean fieldFlag;
    /**
     * Points to the current history.
     */
    private static History history;
    /**
     * Points to the current gameboard.
     */
    private static Gameboard gameboard;
    /**
     * Points to the current goListener.
     */
    private static GoListener goListener;

    /**
     * Constructs a new <code>Chessboard</code>.
     */
    public Chessboard() {
    }

    @Override
    public void paint(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        int len = Variable.getLength();
        String alp = Constant.ALPHABET;
        int[] anc = ANCHOR[dif];
        int ancsize = ANCHOR_SIZE[dif];
        int dw = w / (len + 1);
        int dh = h / (len + 1);
        g.setColor(CHESSBOARD_COLOR);
        g.fillRect(0, 0, w, h);
        g.setColor(Color.BLACK);
        g.setFont(Constant.fontCons(1, Font.BOLD, Constant.SCALE[0]));
        for (int i = 1; i <= len; i++) {
            g.drawLine((int) (i * dw), (int) (dh), (int) (i * dw), (int) (len * dh));
            g.drawLine((int) (dw), (int) (i * dh), (int) (len * dw), (int) (i * dh));
            int blank = (i >= 10) ? 4 : 0;
            g.drawString(alp.substring(i - 1, i), (int) (i * dw - 4), (int) (0.5 * dh + 4));
            g.drawString(alp.substring(i - 1, i), (int) (i * dw - 4), (int) ((len + 0.5) * dh + 4));
            g.drawString(Integer.toString(i), (int) (0.5 * dw - 4 - blank), (int) (i * dh + 4));
            g.drawString(Integer.toString(i), (int) ((len + 0.5) * dw - 4 - blank), (int) (i * dh + 4));
        }
        for (int i = 0; i < anc.length; i++) {
            for (int j = 0; j < anc.length; j++) {
                g.fillOval(dw * anc[i] - ancsize, dh * anc[j] - ancsize, 2 * ancsize, 2 * ancsize);
            }
        }
        Chess[] chessSequence = history.getChessSequence();
        if (chessSequence != null) {
            for (int i = 0; i < chessSequence.length; i++) {
                this.g = g;
                Chess chess = chessSequence[i];
                if (chess.p != null) {
                    Point pos = chess.p.p2Pos();
                    boolean color = chess.color == 1 ? true : false;
                    paintPiece(pos, color);
                    if (orderFlag) {
                        paintOrder(pos, i + 1, !color);
                    }
                    if (fieldFlag) {
                        for (Diagram dia : Diagram.values()) {
                            Point pp = chess.p.step(dia, 1);
                            if (pp != null && !gameboard.isFilled(pp)) {
                                paintField(pp.p2Pos());
                            }
                        }
                    }
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Initializes <code>Chessboard</code>'s basic parameters.
     */
    public void initVariable() {
        dif = Variable.difficulty;
        len = Variable.getLength();
        css = Variable.getChessSize();
        scl = Constant.BOARDWIDTH / (len + 1);
        history = KnightGobang6.history;
        gameboard = KnightGobang6.gameboard;
        goListener = KnightGobang6.goListener;
        addMouseListener(goListener);
        orderFlag = gameboard.displayFlags(true);
        fieldFlag = gameboard.displayFlags(false);
    }

    /**
     * Makes an order displaying or hiding.
     * <p>
     * For <code>true</code>, an order displays the order of each move; for
     * <code>false</code>, it hides them.
     *
     * @param flag <code>true</code> to display the move order, <code>false</code>
     *             to hide them
     */
    public void order(boolean flag) {
        orderFlag = flag;
        repaint();
    }

    /**
     * Calls a field in current match.
     * <p>
     * For <code>true</code>, a field displays all the grids whose points are
     * reachable; for <code>false</code>, it hides them.
     *
     * @param flag <code>true</code> to display all the grids whose points are
     *             reachable, <code>false</code> to hide them
     */
    public void field(boolean flag) {
        fieldFlag = flag;
        repaint();
    }

    /**
     * Paints the chess piece onto <code>chessboard</code> with appointed position
     * and color.
     *
     * @param pos   the position where to paint the chess piece
     * @param color the color of the chess piece, <code>true</code> for black,
     *              <code>false</code> for white
     */
    public void paintPiece(Point pos, boolean color) {
        Color clr = color ? Color.BLACK : Color.WHITE;
        g.setColor(clr);
        g.fillOval(scl * pos.x - css, scl * pos.y - css, 2 * css, 2 * css);
    }

    /**
     * Paints the chess piece's order onto <code>chessboard</code> with appointed
     * position and color.
     *
     * @param pos   the position where to paint the chess piece's order
     * @param index the order of the chess piece
     * @param color the color of the chess piece, <code>true</code> for black,
     *              <code>false</code> for white
     */
    public void paintOrder(Point pos, int index, boolean color) {
        Color clr = color ? Color.BLACK : Color.WHITE;
        g.setColor(clr);
        int div = (index >= 100) ? 4 : (index >= 10) ? 2 : 0;
        g.setFont(Constant.fontCons(1, Font.BOLD, Constant.SCALE[0] - div));
        g.drawString(Integer.toString(index), scl * pos.x - 4 - div, scl * pos.y + 4);
    }

    /**
     * Paints the chess piece's field onto <code>chessboard</code> with appointed
     * position.
     *
     * @param pos the position where to paint the chess piece's field
     */
    public void paintField(Point pos) {
        g.setColor(Constant.CHESSFIELDCOLOR);
        g.fillRect(scl * pos.x - css / 2, scl * pos.y - css / 2, css, css);
    }
}
