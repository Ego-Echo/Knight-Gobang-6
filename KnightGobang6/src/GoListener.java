import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * <code>GoListener</code> reacts for mouse actions which happen on
 * <code>Chessboard</code>, and displays chess pieces (and maybe chess peices'
 * order) on it while extending <code>MouseAdapter</code> and implementing
 * <code>ActionListener</code>.
 *
 * @author Instant
 * @author Ego-Echo
 * @see KnightGobang6
 * @see Gameboard
 * @see Chessboard
 * @see History
 */
public class GoListener extends MouseAdapter implements ActionListener {
    /**
     * Records the graphics of the <code>chessboard</code>.
     */
    private Graphics g;
    /**
     * Records the length of the chessboard in current match.
     */
    private static int len;
    /**
     * Records players' type in current match.
     */
    private static byte ply;
    /**
     * Records the chess color of the chessboard in current step.
     */
    private static boolean chs;
    /**
     * Records the chess' size of the chessboard in current match.
     */
    private static int css;
    /**
     * Records the grid's size of the chessboard in current match.
     */
    private static int scl;
    /**
     * Records if <code>GoListener</code> is in progress.
     */
    private static boolean inProgress;
    /**
     * Records if order is displayed in present.
     */
    private static boolean orderFlag;
    /**
     * Records if field is displayed in present.
     */
    private static boolean fieldFlag;
    /**
     * Points to the current match.
     */
    private static History history;
    /**
     * Points to the current match.
     */
    private static Chessboard chessboard;
    /**
     * Records the current gameboard.
     */
    private static Gameboard gameboard;

    /**
     * Construts a new <code>GoListener</code>.
     */
    public GoListener() {
    }

    /**
     * Initializes <code>GoListener</code>'s basic parameters.
     */
    public void initVariable() {
        len = Variable.getLength();
        ply = Variable.players;
        chs = Variable.chess;
        css = Variable.getChessSize();
        scl = Constant.BOARDWIDTH / (len + 1);
        inProgress = true;
        history = KnightGobang6.history;
        gameboard = KnightGobang6.gameboard;
        chessboard = KnightGobang6.chessboard;
        setGraphics(chessboard.getGraphics());
        orderFlag = gameboard.displayFlags(true);
        fieldFlag = gameboard.displayFlags(false);
    }

    /**
     * Sets the graphics of <code>goListener</code> to a specific one, normally
     * <code>chessboard</code>'s graphics.
     *
     * @param g the appointed graphics
     */
    private void setGraphics(Graphics g) {
        this.g = g;
    }

    /**
     * Sets <code>GoListener</code> in progress or not
     *
     * @param flag <code>true</code> to set in progress, <code>false</code>
     *             otherwise
     */
    public void setProgress(boolean flag) {
        inProgress = flag;
    }

    /**
     * Notifies <code>GoListener</code> to make a move with appointed point and
     * color.
     *
     * @param p     the point of the chess piece
     * @param color the color of the chess piece: <code>1</code> for black,
     *              <code>2</code> for white
     */
    public void notifyMove(Point p, int color) {
        move(p.p2Pos(), color == 1 ? true : false);
    }

    /**
     * Notifies <code>GoListener</code> that a retraction or reretraction has
     * happened.
     */
    public void notifyRetract() {
        if (!gameboard.isInProgress() || ply == 1) {
            chs = !chs;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (inProgress) {
            int i = (int) ((e.getX() + scl / 2) / scl);
            int j = (int) ((e.getY() + scl / 2) / scl);
            Point pos = new Point(i, j);
            if (gameboard.isInProgress()) {
                if (moveable(pos) && gameboard.currentPlayer() instanceof HumanPlayer humanPlayer) {
                    // synchronized (humanPlayer) {
                    humanPlayer.move(pos.pos2P());
                    // humanPlayer.notify();
                    // }
                }
            } else {
                if (moveable(pos)) {
                    boolean clr = chs;
                    gameboard.notifyMove(pos.pos2P(), clr);
                    chs = !chs;
                    System.out.println(pos.toString(false) + (clr ? ", Black;" : ", White;"));
                }
            }
        }
    }

    /**
     * Checks if the position can receive a chess piece or not.
     *
     * @param pos the position under check
     * @return <code>true</code> if the position can receive a chess piece,
     *         <code>false</code> otherwise
     */
    private boolean moveable(Point pos) {
        Point p = pos.pos2P();
        if (!p.checkRange() || gameboard.isFilled(p)) {
            return false;
        } else {
            return gameboard.isKnight(p) || history.getCurrentStep() == 0;
        }
    }

    /**
     * Makes a move of chess piece with appointed position and color
     *
     * @param pos   the position where to move the chess piece
     * @param color the color of the chess piece, <code>true</code> for black,
     *              <code>false</code> for white
     */
    private void move(Point pos, boolean color) {
        /* to be finished */
        paintPiece(pos, color);
        // Point p = pos.pos2P();
        // KnightGobang6.history.notifyMove(p, color ? 1 : 2);
        // KnightGobang6.gameboard.notifyMove(p, color);
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
        chessboard.order(flag);
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
        chessboard.field(flag);
    }

    /**
     * Paints the chess piece onto <code>chessboard</code> with appointed position
     * and color.
     *
     * @param pos   the position where to paint the chess piece
     * @param color the color of the chess piece, <code>true</code> for black,
     *              <code>false</code> for white
     */
    private void paintPiece(Point pos, boolean color) {
        Color clr = color ? Color.BLACK : Color.WHITE;
        g.setColor(clr);
        g.fillOval(scl * pos.x - css, scl * pos.y - css, 2 * css, 2 * css);
        if (orderFlag) {
            int step = history.getCurrentStep();
            paintOrder(pos, step, !color);
        }
        if (fieldFlag) {
            for (Diagram dia : Diagram.values()) {
                Point pp = pos.pos2P().step(dia, 1);
                if (pp != null && moveable(pp.p2Pos())) {
                    paintField(pp.p2Pos());
                }
            }
        }
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
    private void paintOrder(Point pos, int index, boolean color) {
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
    private void paintField(Point pos) {
        g.setColor(Constant.CHESSFIELDCOLOR);
        g.fillRect(scl * pos.x - css / 2, scl * pos.y - css / 2, css, css);
    }
}
