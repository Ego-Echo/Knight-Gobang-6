import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;

/**
 * <code>Gameboard</code> manages a match. It appoints <code>Watcher</code>s and
 * <code>Player</code>s, and interacts with <code>GoListener</code> and
 * <code>History</code>, which are for PVE and PVP mode.
 *
 * @author Instant
 * @author Ego-Echo
 * @see KnightGobang6
 * @see GoListener
 * @see History
 * @see Player
 * @see UI
 */
public class Gameboard {
    /**
     * Records the times retraction can be permitted in 3 difficulties.
     */
    public static final int[] RETRACT_LEFT = { -1, 8, 3 };
    /**
     * Records players' type of current match.
     */
    private byte players;
    /**
     * Records chess' type of current match.
     */
    private boolean chess;
    /**
     * Records difficulty's type of current match.
     */
    private byte difficulty;
    /**
     * Records the chessboard's length of current match.
     */
    private int length;
    /**
     * Records the situation of each grid of the chessboard.
     * <p>
     * <code>0</code> is blank, <code>1</code> is black, <code>2</code> is white.
     */
    private byte[][] chequer;
    /**
     * Records the buttons that are functional relevant to <code>Gameboard</code>.
     */
    private ArrayList<JButton> buttonList;
    /**
     * Records the start button in current <code>UI</code>.
     */
    private JButton start;
    /**
     * Records the order button in current <code>UI</code>.
     */
    private JButton order;
    /**
     * Records the field button in current <code>UI</code>.
     */
    private JButton field;
    /**
     * Records the retract button in current <code>UI</code>.
     */
    private JButton retract;
    /**
     * Records the reretract button in current <code>UI</code>.
     */
    private JButton reretract;
    /**
     * Records the admit button in current <code>UI</code>.
     */
    private JButton admit;
    /**
     * Records if the order and field are being displayed in current match.
     * <p>
     * The <code>0th</code> digit is for <code>Order</code>, <code>0</code> for
     * hidden, <code>1</code> for displaying;
     * <p>
     * The <code>1st</code> digit is for <code>field</code>, <code>0</code> for
     * hidden, <code>1</code> for displaying.
     */
    private byte displayFlags = 0;
    /**
     * Records the left times retraction can be permitted in current match.
     */
    private int retractLeft;
    /**
     * Records the left times reretraction can be permitted in current match.
     */
    private int reretractLeft;
    /**
     * Records if a match has begun or not:
     * <p>
     * <code>true</code> indicates a match is in progress, <code>false</code>
     * otherwise.
     */
    private boolean inProgress;
    /**
     * Records if other classes have been inited when a new match starts.
     */
    public boolean isInited;
    /**
     * Records <code>Player</code>s in current match.
     * <p>
     * In each match, there is set 2 <code>Player</code>s, with differences in
     * different mode.
     */
    private Player[] player;
    /**
     * Records whose turn it is in present.
     * <p>
     * <code>0</code> for the black side, <code>1</code> for the white side.
     */
    private int whoseTurn;
    /**
     * Records point for the current step.
     */
    private Point currentPoint;
    /**
     * Records the play thread.
     */
    private Thread play;
    /**
     * Records the move thread.
     */
    private Thread move;
    /**
     * Records if the interruption is from a retraction or a reretraction.
     */
    private boolean retractInterrupt;
    /**
     * Points to the current history.
     */
    private History history;
    /**
     * Points to the current chessboard.
     */
    // private Chessboard chessboard;
    /**
     * Points to the current goListener.
     */
    private GoListener goListener;
    /**
     * This lock controls synchronized codes.
     */
    public static final ReentrantLock lock = new ReentrantLock();

    /**
     * Construts a new <code>Gameboard</code>.
     */
    public Gameboard() {
    }

    /**
     * Initializes <code>Gameboard</code>'s basic parameters.
     */
    public void initVariable() {
        this.players = Variable.players;
        this.chess = Variable.chess;
        this.difficulty = Variable.difficulty;
        this.length = Variable.getLength();
        this.chequer = new byte[length][length];
        this.buttonList = UI.buttonList;
        // this.displayFlags = 0;
        this.retractLeft = players == 0 ? RETRACT_LEFT[difficulty] : -1;
        this.reretractLeft = 0;
        this.inProgress = false;
        this.isInited = false;
        this.player = new Player[2];
        this.whoseTurn = 0;
        this.currentPoint = null;
        this.play = null;
        this.move = null;
        this.retractInterrupt = false;
        this.history = KnightGobang6.history;
        // this.chessboard = KnightGobang6.chessboard;
        this.goListener = KnightGobang6.goListener;
        initChequer();
        initButton();
        setPlayers(players);
    }

    /**
     * Initializes the situation of each grid of the chessboard to <code>0</code>,
     * which is blank.
     */
    private void initChequer() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                chequer[i][j] = 0;
            }
        }
    }

    /**
     * Initializes the enabilities of each button listed in <code>buttonList</code>.
     */
    private void initButton() {
        for (JButton button : buttonList) {
            String buttonName = button.getName();
            switch (buttonName) {
                case "Start":
                    start = button;
                    break;
                case "Order":
                    order = button;
                    break;
                case "Field":
                    field = button;
                    break;
                case "Retract":
                    retract = button;
                    break;
                case "Reretract":
                    reretract = button;
                    break;
                case "Admit":
                    admit = button;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Sets the players of current match, according to <code>players</code>,
     * <code>chess</code>, and <code>difficulty</code>.
     *
     * @param players index of players mode
     */
    private void setPlayers(int players) {
        if (players == -1) {
            player[0] = new RobotPlayer(1);
            player[1] = new RobotPlayer(2);
        } else if (players == 0) {
            player[chess ? 0 : 1] = new HumanPlayer(chess ? 1 : 2);
            player[chess ? 1 : 0] = new RobotPlayer(chess ? 2 : 1);
        } else if (players == 1) {
            player[0] = new HumanPlayer(1);
            player[1] = new HumanPlayer(2);
        }
    }

    /**
     * The main method of <code>Gameboard</code>.
     * <p>
     * Manages a match in a single step, collaborating with <code>History</code>,
     * <code>UI</code>, <code>Chessboard</code> and <code>GoListener</code>.
     */
    private void play() {
        if (!inProgress) {
            return;
        }
        synchronized (this) {
            try {
                while (retractInterrupt) {
                    wait(50);
                }
                move = new Thread(() -> {
                    player[whoseTurn].play();
                });
                move.start();
                while (currentPoint == null) {
                    wait(50);
                }
            } catch (InterruptedException e) {
                if (retractInterrupt) {
                    return;
                } else {
                    notifyEnd(0);
                    e.printStackTrace();
                    return;
                }
            }
        }
        Point p = currentPoint;
        if (p == null || !p.checkRange() || chequer[p.x][p.y] != 0 || history.getCurrentStep() != 0 && !isKnight(p)) {
            throw new IllegalArgumentException(p.p2Pos().toString(false) + ", " + chequer[p.x][p.y]);
        }
        chequer[p.x][p.y] = (byte) (whoseTurn + 1);
        player[whoseTurn].notifyMove(p, whoseTurn + 1);
        player[1 - whoseTurn].notifyMove(p, whoseTurn + 1);
        reretractLeft = 0;
        setRetractEnability();
        setReretractEnability();
        history.notifyMove(p, whoseTurn + 1);
        goListener.notifyMove(p, whoseTurn + 1);
        System.out.println((whoseTurn == 0 ? "Black, " : "White, ") + p.p2Pos().toString(false));
        if (checkForWin(p)) {
            notifyEnd(whoseTurn + 1);
            return;
        }
        if (checkFilled()) {
            notifyEnd(3);
            return;
        }
        whoseTurn = 1 - whoseTurn;
        currentPoint = null;
    }

    /**
     * Notifies <code>Gameboard</code> to move a chess piece in color
     * <code>clr</code> at point <code>p</code>.
     * <p>
     * When <code>inProgress</code> is <code>false</code>, it acts as a static
     * chessboard.
     * <p>
     * When <code>inProgress</code> is <code>true</code>, it acts as a buffer
     * method.
     *
     * @param p     the point of the chess
     * @param color the color of the chess, <code>true</code> for black,
     *              <code>false</code> for white
     */
    public void notifyMove(Point p, boolean color) {
        if (inProgress) {
            if (whoseTurn() == color && p != null && chequer[p.x][p.y] == 0
                    && (history.getCurrentStep() == 0 || isKnight(p))) {
                currentPoint = p;
            }
        } else {
            if (p != null && chequer[p.x][p.y] == 0 && (history.getCurrentStep() == 0 || isKnight(p))) {
                chequer[p.x][p.y] = (byte) (color ? 1 : 2);
                reretractLeft = 0;
                setRetractEnability();
                setReretractEnability();
                history.notifyMove(p, color ? 1 : 2);
                goListener.notifyMove(p, color ? 1 : 2);
            }
        }
    }

    /**
     * Notifies <code>Gameboard</code> to retract or to retraction in specific
     * step(s).
     *
     * @param step the step(s) for retraction or reretraction
     * @param flag <code>true</code> for retract, <code>false</code> for reretract
     */
    private void notifyRetraction(int step, boolean flag) {
        retractInterrupt = true;
        move.interrupt();
        if (player[whoseTurn] instanceof RobotPlayer robotPlayer) {
            robotPlayer.clear();
            if (step % 2 == 1) {
                whoseTurn = 1 - whoseTurn;
            }
        }
    }

    /**
     * Notifies <code>Gameboard</code> that <code>RobotPlayer</code> has been
     * cleared.
     */
    public synchronized void notifyCleared() {
        if (players == 0) {
            retractInterrupt = false;
        } else if (players == -1) {
            players = -2;
        } else if (players == -2) {
            players = -1;
            retractInterrupt = false;
        }
    }

    /**
     * Notifies current match's end with <code>index</code> to
     * <code>Watcher</code>(s), <code>Player</code>s and <code>History</code>.
     *
     * @param index <code>-2</code> for white admitting defeat, <code>-1</code> for
     *              black admitting defeat,<code>0</code> for an abend,
     *              <code>1</code> for black victory, <code>2</code> for white
     *              victory, <code>3</code> for draw match.
     */
    public void notifyEnd(int index) {
        history.notifyEnd(index);
        // history.record();
        goListener.setProgress(false);
        inProgress = false;
        setOrderEnability();
        setFieldEnability();
        retract.setEnabled(false);
        reretract.setEnabled(false);
        admit.setEnabled(false);
    }

    /**
     * Checks if the point <code>p</code> has already been placed a chess piece.
     *
     * @param p the point
     * @return <code>true</code> if the point is filled, <code>false</code>
     *         otherwise
     */
    public boolean isFilled(Point p) {
        return p != null && p.checkRange() && chequer[p.x][p.y] > 0;
    }

    /**
     * Checks if the point <code>p</code> is reachable by a knight move.
     *
     * @param p the goal point
     * @return <code>true</code> if the point is reachable, <code>false</code>
     *         otherwise
     */
    public boolean isKnight(Point p) {
        for (Diagram dia : Diagram.values()) {
            Point pp = p.step(dia, 1);
            if (pp != null && chequer[pp.x][pp.y] > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns order's or field's flag.
     *
     * @param flag <code>true</code> for order flag, <code>false</code> for field
     *             flag
     * @return the specific flag's truth or falseness
     */
    public boolean displayFlags(boolean flag) {
        return (displayFlags & 1 << (flag ? 0 : 1)) >> (flag ? 0 : 1) == 1;
    }

    /**
     * Returns if <code>Gameboard</code> is in progress.
     *
     * @return <code>true</code> if in progress, <code>false</code> otherwise
     */
    public boolean isInProgress() {
        return inProgress;
    }

    /**
     * Returns if it is retraction interrupted.
     *
     * @return retractInterrupt
     */
    public boolean isRetractionInterrupted() {
        return retractInterrupt;
    }

    /**
     * Returns the current player in turn.
     *
     * @return the current player
     */
    public Player currentPlayer() {
        return player[whoseTurn];
    }

    /**
     * Returns the current player in turn.
     *
     * @return <code>true</code> for black, <code>false</code> for white
     */
    public boolean whoseTurn() {
        return whoseTurn == 0 ? true : false;
    }

    /**
     * Returns current times of retraction.
     *
     * @return retraction times left
     */
    public int getCurrentRetractLeft() {
        return retractLeft;
    }

    /**
     * Checks if the in-turn <code>player</code> gain victory after making the move
     * <code>p</code>.
     *
     * @param p point of the current move
     * @return <code>true</code> if the in-turn <code>player</code> wins,
     *         <code>false</code> otherwise
     */
    private boolean checkForWin(Point p) {
        int whose = chequer[p.x][p.y];
        for (Direction dir : Direction.get4Directions()) {
            int count = 0;
            for (int i = -5; i <= 5; i++) {
                Point pp = p.move(dir, i);
                if (pp == null || chequer[pp.x][pp.y] != whose) {
                    count = 0;
                } else if (++count == 6) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the chessboard has been fulfilled.
     *
     * @return <code>true</code> if the chessboard has been fulfilled,
     *         <code>false</code> otherwise
     */
    private boolean checkFilled() {
        return history.getCurrentStep() == length * length;
    }

    /**
     * Calls a start in current match.
     * <p>
     * For <code>true</code>, a start begins a match; for <code>false</code>, it
     * ends the current one and begins a new one.
     *
     * @param flag <code>true</code> to begin a match, <code>false</code> to end the
     *             current one and begins a new one
     */
    public void start(boolean flag) {
        play = new Thread(new Runnable() {
            @Override
            public void run() {
                if (!flag) {
                    start.setText(Constant.MTBTEXT_START[0]);
                }
                UI.mainFrame.dispose();
                KnightGobang6.newMatch();
                try {
                    while (!isInited) {
                        wait(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                inProgress = true;
                setOrderEnability();
                setFieldEnability();
                setAdmitEnability();
                goListener.setProgress(true);
                try {
                    while (inProgress) {
                        play();
                        start.setText(Constant.MTBTEXT_START[1]);
                        setAdmitEnability();
                    }
                } catch (Exception e) {
                    notifyEnd(0);
                    e.printStackTrace();
                }
            }
        });
        play.start();
    }

    /**
     * Calls an order in current match.
     * <p>
     * For <code>true</code>, an order displays the order of each move; for
     * <code>false</code>, it hides them.
     *
     * @param flag <code>true</code> to display the move order, <code>false</code>
     *             to hide them
     */
    public void order(boolean flag) {
        goListener.order(flag);
        if (flag) {
            displayFlags |= 1 << 0;
        } else {
            displayFlags &= ~(1 << 0);
        }
        setOrderEnability();
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
        goListener.field(flag);
        if (flag) {
            displayFlags |= 1 << 1;
        } else {
            displayFlags &= ~(1 << 1);
        }
        setFieldEnability();
    }

    /**
     * Calls a retraction in current match.
     * <p>
     * For PVP mode, <code>1</code> retraction causes <code>1</code> step; for PVE
     * mode, <code>1</code> retraction causes <code>2</code> steps.
     * <p>
     * In a match, there is specific quantities of retraction
     * <code>retractLeft</code>; otherwise, there is no limits for retraction, which
     * real value is <code>-1</code>.
     */
    public void retract() {
        if (inProgress) {
            if (retractLeft == -1 || retractLeft > 0) {
                if (players == 0) {
                    if (player[whoseTurn] instanceof HumanPlayer) {
                        retract(2);
                    } else {
                        retract(1);
                    }
                } else if (players == 1) {
                    retract(1);
                }
                if (retractLeft != -1) {
                    retractLeft--;
                }
            }
        } else {
            retract(1);
        }
        setRetractEnability();
        setReretractEnability();
        goListener.notifyRetract();
    }

    /**
     * Calls a retraction in current match.
     * <p>
     * For PVP mode, <code>1</code> retraction causes <code>1</code> step; for PVE
     * mode, <code>1</code> retraction causes <code>2</code> steps.
     *
     * @param step the quantity of steps retraction causes
     */
    private void retract(int step) {
        Chess[] lastMoves = history.getLastMoves(step);
        if (lastMoves != null) {
            for (Chess chess : lastMoves) {
                Point p = chess.p;
                chequer[p.x][p.y] = 0;
            }
        }
        if (inProgress) {
            notifyRetraction(step, true);
            player[0].notifyRetraction(step, true);
            player[1].notifyRetraction(step, true);
        }
        history.retract(step);
        reretractLeft++;
        System.out.println("Retract " + step + (step == 1 ? " move." : " moves."));
    }

    /**
     * Calls a reretraction in current match.
     * <p>
     * For PVP mode, <code>1</code> reretraction causes <code>1</code> step; for PVE
     * mode, <code>1</code> reretraction causes <code>2</code> steps.
     * <p>
     * In a match, the quantity of reretraction depends on retraction times. Once a
     * retraction happens, quantity of reretraction plus <code>1</code>; once a move
     * happens, quantity of reretraction becomes <code>0</code>.
     */
    public void reretract() {
        if (reretractLeft > 0) {
            if (inProgress) {
                if (players == 0) {
                    reretract(history.getLastRetractSteps());
                } else if (players == 1) {
                    reretract(1);
                }
            } else {
                reretract(1);
            }
            setRetractEnability();
            setReretractEnability();
            goListener.notifyRetract();
        }
    }

    /**
     * Calls a reretraction in current match.
     * <p>
     * For PVP mode, <code>1</code> reretraction causes <code>1</code> step; for PVE
     * mode, <code>1</code> reretraction causes <code>2</code> steps.
     *
     * @param step the quantity of steps reretraction causes
     */
    private void reretract(int step) {
        Chess[] lastRetracts = history.getLastRetracts(step);
        if (lastRetracts != null) {
            for (Chess chess : lastRetracts) {
                Point p = chess.p;
                int color = chess.color;
                chequer[p.x][p.y] = (byte) color;
            }
        }
        if (inProgress) {
            notifyRetraction(step, false);
            player[0].notifyRetraction(step, false);
            player[1].notifyRetraction(step, false);
        }
        history.reretract(step);
        reretractLeft--;
        System.out.println("Reretract " + step + (step == 1 ? " move." : " moves."));
    }

    /**
     * Calls an admit in current match to end a match with the presenter's defeat.
     */
    public void admit() {
        if (inProgress) {
            notifyEnd(-whoseTurn - 1);
        }
    }

    /**
     * Sets the <code>Order</code> button to correct enability.
     */
    private void setOrderEnability() {
        Boolean flag = (displayFlags & 1 << 0) >> 0 == 0;
        order.setText(Constant.MTBTEXT_ORDER[flag ? 0 : 1]);
        order.setEnabled(inProgress);
    }

    /**
     * Sets the <code>Field</code> button to correct enability.
     */
    private void setFieldEnability() {
        Boolean flag = (displayFlags & 1 << 1) >> 1 == 0;
        field.setText(Constant.MTBTEXT_FIELD[flag ? 0 : 1]);
        field.setEnabled(inProgress);
    }

    /**
     * Sets the <code>Retract</code> button to correct enability.
     */
    private void setRetractEnability() {
        if (retractLeft >= 0 && inProgress) {
            retract.setText(Constant.MTBTEXT_RETRACT[1] + retractLeft);
        } else {
            retract.setText(Constant.MTBTEXT_RETRACT[0]);
        }
        int stepBack;
        if (players == 1 || !inProgress) {
            stepBack = 1;
        } else {
            stepBack = players == 0 ? 2 : 0;
        }
        Chess[] lastMoves = history.getLastMoves(stepBack);
        retract.setEnabled(lastMoves != null && (retractLeft == -1 || retractLeft > 0));
    }

    /**
     * Sets the <code>Reretract</code> button to correct enability.
     */
    private void setReretractEnability() {
        reretract.setEnabled(reretractLeft > 0);
    }

    /**
     * Sets the <code>Admit</code> button to correct enability.
     */
    private void setAdmitEnability() {
        admit.setEnabled(inProgress && player[whoseTurn] instanceof HumanPlayer);
    }
}
