import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * In <strong>Knight Gobang 6</strong>, a <code>RobotPlayer</code> participates
 * in a match and decides how to play in a robot's POV.
 * <p>
 * In a single match, there should be set 0 <code>RobotPlayer</code>s (for PVP
 * mode), 1 <code>RobotPlayer</code> (for PVE mode), or 2
 * <code>RobotPlayer</code>s (for EVE mode).
 * <p>
 * <code>RobotPlayer</code> is affiliated to <code>Gameboard</code>, while
 * extending <code>RobotWatcher</code> and implementing <code>Player</code>.
 *
 * @author Instant
 * @author Ego-Echo
 * @see Gameboard
 * @see Player
 */
public class RobotPlayer implements Player {
    /**
     * Records the value of equivalent victory when evaluating the board.
     */
    private static final int VICTORY_STANDARD = 800000;
    /**
     * Records the deeps of the robot player will evaluate to for prior and normal
     * points in 3 difficulties.
     */
    private static final int[][] EVALUATE_DEEP = { { 5, 3 }, { 7, 5 }, { 9, 7 } };
    /**
     * Records the maximum of the evaluated points in each depth in 3 difficulties.
     */
    private static final int[] MAX_EVALUATE_POINTS_EACH_DEEP = { 12, 20, 32 };
    /**
     * Records the maximum value of interger, which is <code>2^31-1</code>.
     */
    private static final int MAX_VALUE = Integer.MAX_VALUE;
    /**
     * Records the minimum value of interger, which is <code>-2^31</code>.
     */
    private static final int MIN_VALUE = Integer.MIN_VALUE;
    /**
     * Records the chess side of <code>RobotPlayer</code>.
     * <p>
     * <code>1</code> is for black, <code>2</code> is for white.
     */
    public final int color;
    /**
     * Records the difficulty <code>RobotPlayer</code> is in: <code>0</code> for
     * normal, <code>1</code> for hard, <code>2</code> for impossible.
     */
    private final byte difficulty;
    /**
     * Records the chessboard's length of current match.
     */
    private final int length;
    /**
     * Records the deep of the robot player will evaluate for prior and normal
     * points in current match.
     */
    private final int[] deep;
    /**
     * Records the maximum of evaluated points in current match.
     */
    private final int maxEvalPointCount;
    /**
     * Records the step(s) the current match has passed.
     */
    private int step;
    /**
     * Records the base chess in current evaluation.
     */
    private Chess baseChess;
    /**
     * Records the cache of all situations appeared in the current match.
     */
    private ChequerCache cache;
    /**
     * Records the status of the whole chessboard.
     */
    private ChequerStatus status;
    /**
     * Records the situation of each grid of the chessboard.
     * <p>
     * <code>0</code> is blank, <code>1</code> is black, <code>2</code> is white.
     */
    private byte[][] chequer;
    /**
     * Records the grids those are in situations of reachable blank at present.
     */
    private ArrayList<Point> reachableList;
    /**
     * Records the grids those are in situations of reachable blank in evaluating
     * loop.
     */
    private ArrayList<Point>[] currentReachableList;
    /**
     * Records the chess pieces which are already moved.
     */
    private Chess[] chessSequence;
    /**
     * Records the chess pieces which are moved in trial.
     */
    private Chess[] currentChessSequence;
    /**
     * Records the play thread.
     */
    private Thread play;
    /**
     * Records the points which are in backtracking serching, set chess piece and
     * have not been set back.
     */
    private ArrayList<Point> backTrackRecord;
    /**
     * Records if the interruption is from a retraction or a reretraction.
     */
    private boolean retractInterrupt;
    /**
     * Points to current history.
     */
    private final History history;
    /**
     * Points to current gameboard.
     */
    private final Gameboard gameboard;

    /**
     * Constructs a new <code>RobotPlayer</code> with its chess side
     * <code>color</code>.
     *
     * @param color the chess side of <code>RobotPlayer</code>, <code>1</code> for
     *              black, <code>2</code> for white
     */
    @SuppressWarnings("unchecked")
    public RobotPlayer(int color) {
        this.color = color;
        this.difficulty = Variable.difficulty;
        this.length = Variable.getLength();
        this.deep = EVALUATE_DEEP[difficulty];
        this.maxEvalPointCount = MAX_EVALUATE_POINTS_EACH_DEEP[difficulty];
        this.step = 0;
        this.baseChess = null;
        this.cache = new ChequerCache();
        this.status = new ChequerStatus(length);
        this.chequer = new byte[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                chequer[i][j] = 0;
            }
        }
        this.reachableList = new ArrayList<>();
        this.currentReachableList = new ArrayList[deep[0]];
        for (int i = 0; i < deep[0]; i++) {
            currentReachableList[i] = new ArrayList<>();
        }
        this.chessSequence = new Chess[length * length];
        this.currentChessSequence = new Chess[deep[0]];
        this.backTrackRecord = new ArrayList<>();
        this.retractInterrupt = false;
        this.history = KnightGobang6.history;
        this.gameboard = KnightGobang6.gameboard;
    }

    /**
     * Checks if the point <code>p</code> is reachable via a knight move.
     *
     * @param p the goal point
     * @return <code>true</code> if the point is reachable, <code>false</code>
     *         otherwise
     */
    private boolean isKnight(Point p) {
        for (Diagram dia : Diagram.values()) {
            Point pp = p.step(dia, 1);
            if (pp != null && get(pp) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the point <code>p</code> is nearby via a direct move.
     *
     * @param p the goal point
     * @return <code>true</code> if the point is nearby, <code>false</code>
     *         otherwise
     */
    private boolean isNear(Point p) {
        for (Direction dir : Direction.values()) {
            Point pp = p.move(dir, 1);
            if (pp != null && get(pp) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the point <code>p</code> is nearby via 2 direct moves.
     *
     * @param p the goal point
     * @return <code>true</code> if the point is nearby, <code>false</code>
     *         otherwise
     */
    private boolean isNearby(Point p) {
        for (Direction dir : Direction.values()) {
            Point pp = p.move(dir, 1);
            if (pp != null) {
                if (get(pp) > 0) {
                    return true;
                } else {
                    pp = pp.move(dir, 1);
                    if (pp != null && get(pp) > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Gets all the points which can construct a continuous <code>6</code> via a
     * single move of <code>color</code> in a search.
     *
     * @param color the constructing color side
     * @return the goal points
     */
    private Point[] getCons6(int color) {
        ArrayList<Point> cons6 = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                Point p = new Point(i, j);
                if (get(p) == 0 && isNear(p)) {
                    for (Direction dir : Direction.get4Directions()) {
                        int left = 0;
                        int right = 0;
                        for (int k = -1; k >= -5; k--) {
                            if (get(p.move(dir, k)) == color) {
                                left++;
                            } else {
                                break;
                            }
                        }
                        for (int k = 1; k <= 5; k++) {
                            if (get(p.move(dir, k)) == color) {
                                right++;
                            } else {
                                break;
                            }
                        }
                        if (left + right >= 5) {
                            cons6.add(p);
                        }
                    }
                }
            }
        }
        return cons6.toArray(new Point[cons6.size()]);
    }

    /**
     * Returns if there is a constructed <code>5</code> in a search.
     *
     * @param color the constructing color side
     * @return <code>true</code> if there is a constructed <code>5</code>,
     *         <code>false</code> otherwise
     */
    public boolean isCons5(int color) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                Point p = new Point(i, j);
                if (get(p) == color || get(p) == 0 && isNear(p)) {
                    for (Direction dir : Direction.get4Directions()) {
                        int self = 0;
                        int blank = 0;
                        for (int k = 0; k <= 5; k++) {
                            int kColor = get(p.move(dir, k));
                            if (kColor == color) {
                                self++;
                            } else if (kColor == 0) {
                                blank++;
                            }
                            if (self == 5 && blank == 1) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Gets all the points which can construct two lines of <code>4</code> and
     * <code>5</code> in a search.
     *
     * @param color the constructing color side
     * @return the goal points
     */
    private PointAndValue[] getConsX(int color) {
        ArrayList<PointAndValue> consX = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                Point p = new Point(i, j);
                if (get(p) == 0 && isNearby(p)) {
                    int dist = 0;
                    for (Direction dirX : Direction.get4Directions()) {
                        int left = 0;
                        int right = 0;
                        int blankL = 0;
                        int blankR = 0;
                        for (int k = -1; k >= -5; k--) {
                            Point pp = p.move(dirX, k);
                            if (get(pp) == color) {
                                left++;
                            } else if (get(pp) == 0) {
                                blankL++;
                            } else {
                                break;
                            }
                        }
                        for (int k = 1; k <= 5; k++) {
                            Point pp = p.move(dirX, k);
                            if (get(pp) == color) {
                                right++;
                            } else if (get(pp) == 0) {
                                blankR++;
                            } else {
                                break;
                            }
                        }
                        if (left + right >= 4 && blankL + blankR <= 1) {
                            dist += 5;
                        } else if (left + right >= 3 && blankL + blankR <= 1) {
                            Point pl = p.move(dirX, -left - blankL - 1);
                            Point pr = p.move(dirX, right + blankR + 1);
                            if (get(pl) == 0 && get(pr) == 0) {
                                dist += 3;
                            }
                        }
                        if (dist > 0) {
                            for (Direction dirY : Direction.get4Directions()) {
                                if (dirY == dirX) {
                                    continue;
                                }
                                int up = 0;
                                int down = 0;
                                int blankU = 0;
                                int blankD = 0;
                                for (int k = -1; k >= -5; k--) {
                                    Point pp = p.move(dirX, k);
                                    if (get(pp) == color) {
                                        up++;
                                    } else if (get(pp) == 0) {
                                        blankU++;
                                    } else {
                                        break;
                                    }
                                }
                                for (int k = 1; k <= 5; k++) {
                                    Point pp = p.move(dirX, k);
                                    if (get(pp) == color) {
                                        down++;
                                    } else if (get(pp) == 0) {
                                        blankD++;
                                    } else {
                                        break;
                                    }
                                }
                                if (up + down >= 4 && blankU + blankD <= 1) {
                                    dist += 5;
                                } else if (up + down >= 3 && blankU + blankD <= 1) {
                                    Point pl = p.move(dirX, -up - blankU - 1);
                                    Point pr = p.move(dirX, down + blankD + 1);
                                    if (get(pl) == 0 && get(pr) == 0) {
                                        dist += 3;
                                    }
                                }
                                if (dist >= 6) {
                                    int r = color == this.color ? 0 : 1;
                                    consX.add(new PointAndValue(p, dist - r));
                                }
                            }
                        }
                    }
                }
            }
        }
        return consX.toArray(new PointAndValue[consX.size()]);
    }

    /**
     * Gets the points which are reachable at present.
     *
     * @param deep the depth of current trial move, <code>-1</code> for a final move
     * @return the reachable points
     */
    private ArrayList<Point> getCurrentKnights(int deep) {
        ArrayList<Point> currentKnights = new ArrayList<>();
        ArrayList<Point> currentSequence = new ArrayList<>();
        if (deep >= -1 && deep < this.deep[0]) {
            for (int i = 0; i < step; i++) {
                currentSequence.add(chessSequence[i].p);
            }
            if (deep > -1) {
                for (int i = 0; i <= deep; i++) {
                    currentSequence.add(currentChessSequence[i].p);
                }
            }
            for (Point p : currentSequence) {
                if (p != null && p.checkRange()) {
                    for (Diagram dia : Diagram.values()) {
                        Point pp = p.step(dia, 1);
                        if (get(pp) == 0) {
                            currentKnights.add(pp);
                        }
                    }
                }
            }
            return currentKnights;
        }
        return null;
    }

    /**
     * Gets the chess line based on appointed point <code>p</code>, its current
     * color <code>color</code>, and appointed direction <code>dir</code>.
     * <p>
     * This line records status of each grid: <code>-2</code> for non-edged,
     * <code>-1</code> for out-of-edged, <code>0</code> for blank, <code>1</code>
     * for black, <code>2</code> for white, <code>3</code> for the base point
     * itself.
     *
     * @param p         the base point
     * @param evalColor current evaluated color
     * @param dir       appointed direction
     * @return line of status with chess of each grid
     */
    private ArrayList<Chess> getStatusLine(Point p, int evalColor, Direction dir) {
        Chess[] chessLine = new Chess[13];
        boolean headRecord = false;
        boolean tailRecord = false;
        for (int i = 0; i <= 6; i++) {
            if (i == 0) {
                chessLine[6] = baseChess;
            } else {
                Point pl = p.move(dir, -i);
                if (!headRecord) {
                    if (get(pl) == evalColor || get(pl) == 0) {
                        chessLine[6 - i] = new Chess(pl, get(pl));
                    } else {
                        chessLine[6 - i] = new Chess(pl, get(pl));
                        headRecord = true;
                    }
                }
                Point pr = p.move(dir, i);
                if (!tailRecord) {
                    if (get(pr) == evalColor || get(pr) == 0) {
                        chessLine[6 + i] = new Chess(pr, get(pr));
                    } else {
                        chessLine[6 + i] = new Chess(pr, get(pr));
                        tailRecord = true;
                    }
                }
                if (headRecord && tailRecord) {
                    break;
                }
            }
        }
        ArrayList<Chess> statusLine = new ArrayList<>();
        if (!headRecord) {
            statusLine.add(new Chess(null, -2));
        }
        for (Chess chess : chessLine) {
            if (chess != null) {
                statusLine.add(chess);
            }
        }
        if (!tailRecord) {
            statusLine.add(new Chess(null, -2));
        }
        return statusLine;
    }

    /**
     * Gets the chess line based on appointed chess <code>chess</code>, and
     * appointed direction <code>dir</code>.
     * <p>
     * This line records status of each grid: <code>-2</code> for non-edged,
     * <code>-1</code> for out-of-edged, <code>0</code> for blank, <code>1</code>
     * for black, <code>2</code> for white.
     *
     * @param chess the base chess piece
     * @param dir   appointed direction
     * @return line of status with chess of each grid
     */
    private ArrayList<Chess> getStatusLine(Chess chess, Direction dir) {
        Chess[] chessLine = new Chess[13];
        boolean headRecord = false;
        boolean tailRecord = false;
        for (int i = 0; i <= 6; i++) {
            if (i == 0) {
                chessLine[6] = chess;
            } else {
                Point pl = chess.p.move(dir, -i);
                if (!headRecord) {
                    if (get(pl) == chess.color || get(pl) == 0) {
                        chessLine[6 - i] = new Chess(pl, get(pl));
                    } else {
                        chessLine[6 - i] = new Chess(pl, get(pl));
                        headRecord = true;
                    }
                }
                Point pr = chess.p.move(dir, i);
                if (!tailRecord) {
                    if (get(pr) == chess.color || get(pr) == 0) {
                        chessLine[6 + i] = new Chess(pr, get(pr));
                    } else {
                        chessLine[6 + i] = new Chess(pr, get(pr));
                        tailRecord = true;
                    }
                }
                if (headRecord && tailRecord) {
                    break;
                }
            }
        }
        ArrayList<Chess> statusLine = new ArrayList<>();
        if (!headRecord) {
            statusLine.add(new Chess(null, -2));
        }
        for (Chess c : chessLine) {
            if (c != null) {
                statusLine.add(c);
            }
        }
        if (!tailRecord) {
            statusLine.add(new Chess(null, -2));
        }
        return statusLine;
    }

    /**
     * Gets the suitable quantity of evaluated point according to
     * <code>expectValue</code> and current <code>actualValue</code>.
     *
     * @param expectValue the expected quantity for evaluating
     * @param actualValue the actual quantity for evaluating
     * @return the final quantity for evaluating
     */
    private int getEvaluateCount(int expectValue, int actualValue) {
        return Integer.max(actualValue / 5, Integer.min(expectValue, actualValue));
    }

    /**
     * Clears all the data in current evaluation. Call this method only when the
     * match is PVE or EVE, and it is this player's turn at present.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        retractInterrupt = true;
        play.interrupt();
        // try {
        // while (play.isAlive()) {
        // play.wait(50);
        // }
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        for (Point p : backTrackRecord) {
            chequer[p.x][p.y] = 0;
        }
        currentReachableList = new ArrayList[deep[0]];
        for (int i = 0; i < deep[0]; i++) {
            currentReachableList[i] = new ArrayList<>();
        }
        currentChessSequence = new Chess[deep[0]];
        reachableList = getCurrentKnights(-1);
        gameboard.notifyCleared();
    }

    /**
     * Sets the color of the point <code>p</code> to <code>color</code> for a final
     * move.
     *
     * @param p     the point whose color is to be set
     * @param color the goal color to set
     */
    private void set(Point p, int color) {
        if (p != null && get(p) == 0 && (isKnight(p) || step == 0)) {
            chequer[p.x][p.y] = (byte) color;
            chessSequence[step] = new Chess(p, color);
            step++;
            reachableList = getCurrentKnights(-1);
        }
    }

    /**
     * Gets the color of the point <code>p</code>.
     *
     * @param p the point whose color is to be got
     * @return the goal color to be got, <code>-1</code> for a null point
     */
    private byte get(Point p) {
        return p == null ? -1 : chequer[p.x][p.y];
    }

    /**
     * Puts the color of the point <code>p</code> to <code>color</code> for a trial
     * move with <code>deep</code>.
     *
     * @param p     the point whose color is to be put
     * @param color the goal color to put
     * @param deep  the depth of current trial move
     */
    private void put(Point p, int color, int deep) {
        if (p != null && (isKnight(p) || step == 0)) {
            chequer[p.x][p.y] = (byte) color;
            if (color == 0) {
                currentChessSequence[deep] = null;
                currentReachableList[deep] = null;
            } else if (color == 1 || color == 2) {
                currentChessSequence[deep] = new Chess(p, color);
                currentReachableList[deep] = getCurrentKnights(deep);
            }
        }
    }

    /**
     * Cuts the point <code>p</code> back to a blank situation for a final move.
     *
     * @param p the point which is to be cut
     */
    private void cut(Point p) {
        if (p != null && get(p) > 0) {
            chequer[p.x][p.y] = 0;
            step--;
            chessSequence[step] = null;
            reachableList = getCurrentKnights(-1);
        } else {
            throw new IllegalArgumentException(p.p2Pos().toString(false) + get(p));
        }
    }

    /**
     * Evaluates current half line of the point.
     *
     * @param line the half line to evaluate
     * @return the score for the half line
     */
    private double evaluateHalfLine(int[] line) {
        int edge = -1;
        for (int i = 0; i < line.length; i++) {
            if (line[i] != 0) {
                edge = i;
            }
        }
        double result = 1;
        int lastGrid = 0;
        int grid = 0;
        for (int i = 0; i < line.length; i++) {
            lastGrid = grid;
            grid = line[i];
            if (edge == -1) {
                assert grid == 0;
                result *= 1.06;
            } else {
                if (grid != 0) {
                    result *= 10;
                } else if (i < edge) {
                    if (lastGrid == 0) {
                        result /= 1.25;
                    } else {
                        result /= 1.12;
                    }
                } else {
                    if (lastGrid == 0) {
                        result *= 1.06;
                    } else {
                        result *= 1.12;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Evaluates current line for the board.
     *
     * @param line the line to evaluate
     * @return the score for the line
     */
    private double evaluateLine(int[] line) {
        int leftEdge = -1;
        int rightEdge = -1;
        for (int i = 0; i < line.length; i++) {
            int bit = line[i];
            if (leftEdge == -1 && bit != 0) {
                leftEdge = i;
            }
            if (bit != 0) {
                rightEdge = i;
            }
        }
        double result = 1;
        int lastGrid = 0;
        int grid = 0;
        int cnt = 0;
        for (int i = 0; i < line.length; i++) {
            lastGrid = grid;
            grid = line[i];
            if (leftEdge == -1 || rightEdge == -1) {
                assert grid == 0;
                result *= 1.06;
            } else {
                if (grid != 0) {
                    if (++cnt <= 6) {
                        result *= 10;
                    } else {
                        result *= 1.06;
                    }
                } else if (i > leftEdge && i < rightEdge) {
                    if (lastGrid == 0) {
                        result /= 1.25;
                    } else {
                        result /= 1.12;
                    }
                } else {
                    if (lastGrid == 0) {
                        result *= 1.06;
                    } else {
                        result *= 1.12;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Evaluates current point <code>p</code> of the situation.
     *
     * @param p         the point to evaluate
     * @param selfColor the side of player itself
     * @return the final score of current point
     */
    private int evaluatePoint(Point p, int selfColor) {
        return evaluatePoint(p, selfColor, 1) + evaluatePoint(p, selfColor, 2);
    }

    /**
     * Evaluates current point <code>p</code> of the situation for one side
     * <code>evalColor</code>.
     *
     * @param p         the point to evaluate
     * @param selfColor the side of player itself
     * @param evalColor the side to evaluate
     * @return the score for one side of current point
     */
    private int evaluatePoint(Point p, int selfColor, int evalColor) {
        double result = 0;
        for (Direction dir : Direction.get4Directions()) {
            baseChess = new Chess(p, 3);
            ArrayList<Chess> statusLine = getStatusLine(p, evalColor, dir);
            int length = statusLine.size();
            int head = statusLine.getFirst().color;
            int tail = statusLine.getLast().color;
            int self = statusLine.indexOf(baseChess);
            int blank = 0;
            int[] lineLeft = new int[self];
            int[] lineRight = new int[length - self - 1];
            for (int i = self - 1; i > 0; i--) {
                Chess chess = statusLine.get(i);
                lineLeft[self - i - 1] = chess.color;
                if (chess.color == 0 && !isKnight(chess.p)) {
                    blank++;
                }
            }
            for (int i = self + 1; i < length - 1; i++) {
                Chess chess = statusLine.get(i);
                lineRight[i - self - 1] = chess.color;
                if (chess.color == 0 && !isKnight(chess.p)) {
                    blank++;
                }
            }
            double r = 1;
            if ((head == -1 || head == 3 - evalColor) && (tail == -1 || tail == 3 - evalColor)) {
                if (length < 8) {
                    r *= 0;
                } else {
                    r /= 1.57;
                }
            } else {
                if (head == -1 || head == 3 - evalColor) {
                    r /= 1.25;
                }
                if (tail == -1 || tail == 3 - evalColor) {
                    r /= 1.25;
                }
            }
            for (int i = 0; i < blank; i++) {
                r /= 1.25;
            }
            result += evaluateHalfLine(lineLeft) * evaluateHalfLine(lineRight) * r;
        }
        result *= selfColor == evalColor ? 1 : 0.83;
        return (int) result;
    }

    /**
     * Evaluates current situation of the match.
     *
     * @return the final score of current situation
     */
    private int evaluateBoard() {
        return evaluateBoard(color) - evaluateBoard(3 - color);
    }

    /**
     * Evaluates current situation of the match for one side <code>color</code>.
     *
     * @param color the side to evaluate
     * @return the score for one side of current match
     */
    private int evaluateBoard(int color) {
        double result = 0;
        ArrayList<Chess> currentSequence = new ArrayList<>();
        for (int i = 0; i < step; i++) {
            if (chessSequence[i] != null && chessSequence[i].color == color) {
                currentSequence.add(chessSequence[i]);
            }
        }
        for (int i = 0; i < deep[0]; i++) {
            if (currentChessSequence[i] != null && currentChessSequence[i].color == color) {
                currentSequence.add(currentChessSequence[i]);
            }
        }
        for (Direction dir : Direction.get4Directions()) {
            ArrayList<Chess> chessRecord = new ArrayList<>();
            for (Chess chess : currentSequence) {
                if (chessRecord.contains(chess)) {
                    continue;
                }
                ArrayList<Chess> statusLine = getStatusLine(chess, dir);
                int length = statusLine.size();
                int head = statusLine.getFirst().color;
                int tail = statusLine.getLast().color;
                int[] line = new int[length - 2];
                int blank = 0;
                for (int i = 1; i < length - 1; i++) {
                    Chess c = statusLine.get(i);
                    chessRecord.add(c);
                    line[i - 1] = c.color;
                    if (c.color == 0 && !isKnight(c.p)) {
                        blank++;
                    }
                }
                double r = 1;
                if ((head == -1 || head == 3 - color) && (tail == -1 || tail == 3 - color)) {
                    if (length < 8) {
                        r *= 0.01;
                    }
                    r *= 0.01;
                } else {
                    if (head == -1 || head == 3 - color) {
                        r *= 0.1;
                    }
                    if (tail == -1 || tail == 3 - color) {
                        r *= 0.1;
                    }
                }
                for (int i = 0; i < blank; i++) {
                    r /= 1.25;
                }
                result += evaluateLine(line) * r;
            }
        }
        return (int) result;
    }

    /**
     * Returns all the must-respond points with their values.
     * <p>
     * The point part records the point infomation, the value part records the state
     * infomation:
     * <p>
     * <code>12</code> indicates that the chess of <code>color</code> can
     * construct a continuous <code>6</code> via a single move on the point
     * <code>p</code>.
     * <p>
     * <code>11</code> indicates that the chess of <code>3 - color</code> can
     * construct a continuous <code>6</code> via a single move on the point
     * <code>p</code>.
     * <p>
     * <code>10</code> indicates that the chess of <code>color</code>
     * constructed two crossed <code>5</code>s, and the point <code>p</code> is a
     * way to stop it.
     * <p>
     * <code>9</code> indicates that the chess of <code>3 - color</code>
     * constructed two crossed <code>5</code>s, and the point <code>p</code> is a
     * way to stop it.
     * <p>
     * <code>8</code> indicates that the chess of <code>color</code>
     * constructed crossed <code>4</code> and <code>5</code>, and the point
     * <code>p</code> is a way to stop it.
     * <p>
     * <code>7</code> indicates that the chess of <code>3 - color</code>
     * constructed crossed <code>4</code> and <code>5</code>, and the point
     * <code>p</code> is a way to stop it.
     * <p>
     * <code>6</code> indicates that the chess of <code>color</code>
     * constructed two crossed <code>4</code>s, and the point <code>p</code> is a
     * way to stop it.
     * <p>
     * <code>5</code> indicates that the chess of <code>3 - color</code>
     * constructed two crossed <code>4</code>s, and the point <code>p</code> is a
     * way to stop it.
     *
     * @return array of point and value
     */
    private PointAndValue[] getPriorCalculateList() {
        ArrayList<PointAndValue> priorCalculateList = new ArrayList<>();
        for (Point cons6 : getCons6(color)) {
            priorCalculateList.add(new PointAndValue(cons6, 12));
        }
        for (Point cons6 : getCons6(3 - color)) {
            priorCalculateList.add(new PointAndValue(cons6, 11));
        }
        for (PointAndValue consX : getConsX(color)) {
            priorCalculateList.add(consX);
        }
        for (PointAndValue consX : getConsX(3 - color)) {
            priorCalculateList.add(consX);
        }
        return priorCalculateList.toArray(new PointAndValue[priorCalculateList.size()]);
    }

    /**
     * Returns the step which causes a maximum value.
     *
     * @param leftStep the step(s) to be evaluated
     * @param minValue the value which is passed from other branches
     * @return the step which causes a maximum value, with point and its
     *         corresponding value
     */
    private PointAndValue getMaxEvaluate(int leftStep, int minValue) {
        PointAndValue cacheResult = cache.get(status.getZobrist(), leftStep);
        if (cacheResult != null) {
            return cacheResult;
        }
        int deep = this.deep[1];
        PriorityQueue<PointAndValue> pvQueue = new PriorityQueue<>((pv1, pv2) -> Integer.compare(pv2.value, pv1.value));
        ArrayList<Point> pointList = leftStep == deep ? reachableList : currentReachableList[deep - leftStep - 1];
        for (Point p : pointList) {
            pvQueue.add(new PointAndValue(p, evaluatePoint(p, color)));
        }
        if (leftStep == 1) {
            assert pvQueue.peek() != null;
            Point p = pvQueue.peek().p;
            put(p, color, deep - 1);
            backTrackRecord.add(p);
            status.set(p, color);
            int value = evaluateBoard();
            PointAndValue deepResult = new PointAndValue(p, value);
            cache.put(status.getZobrist(), deepResult, deep - leftStep);
            status.set(p, 0);
            backTrackRecord.remove(p);
            put(p, 0, deep - 1);
            return deepResult;
        }
        Point maxPoint = null;
        int maxValue = MIN_VALUE;
        int i = 0;
        for (PointAndValue pv : pvQueue) {
            if (++i > getEvaluateCount(maxEvalPointCount, pvQueue.size()) && maxPoint != null) {
                break;
            }
            Point p = pv.p;
            put(p, color, deep - leftStep);
            backTrackRecord.add(p);
            status.set(p, color);
            int value = evaluateBoard();
            if (value > VICTORY_STANDARD) {
                PointAndValue winResult = new PointAndValue(p, value);
                cache.put(status.getZobrist(), winResult, deep - leftStep);
                status.set(p, 0);
                backTrackRecord.remove(p);
                put(p, 0, deep - leftStep);
                return winResult;
            }
            PointAndValue nextStepResult = getMinEvaluate(leftStep - 1, maxValue);
            assert nextStepResult != null;
            int nextStepValue = nextStepResult.value;
            if (nextStepValue >= minValue) {
                status.set(p, 0);
                backTrackRecord.remove(p);
                put(p, 0, deep - leftStep);
                PointAndValue iterResult = new PointAndValue(p, nextStepValue);
                return iterResult;
            }
            if (maxPoint == null || maxValue < nextStepValue || maxValue == nextStepValue && p.nearMidThan(maxPoint)) {
                maxValue = nextStepValue;
                maxPoint = p;
            }
            status.set(p, 0);
            backTrackRecord.remove(p);
            put(p, 0, deep - leftStep);
        }
        if (maxPoint == null) {
            return null;
        }
        PointAndValue finalResult = new PointAndValue(maxPoint, maxValue);
        cache.put(status.getZobrist(), finalResult, deep - leftStep);
        return finalResult;
    }

    /**
     * Returns the step which causes a minimum value.
     *
     * @param leftStep the step(s) to be evaluated
     * @param maxValue the value which is passed from other branches
     * @return the step which causes a minimum value, with point and its
     *         corresponding value
     */
    private PointAndValue getMinEvaluate(int leftStep, int maxValue) {
        PointAndValue cacheResult = cache.get(status.getZobrist(), leftStep);
        if (cacheResult != null) {
            return cacheResult;
        }
        int deep = this.deep[1];
        PriorityQueue<PointAndValue> pvQueue = new PriorityQueue<>((pv1, pv2) -> Integer.compare(pv2.value, pv1.value));
        ArrayList<Point> pointList = leftStep == deep ? reachableList : currentReachableList[deep - leftStep - 1];
        for (Point p : pointList) {
            pvQueue.add(new PointAndValue(p, evaluatePoint(p, 3 - color)));
        }
        if (leftStep == 1) {
            assert pvQueue.peek() != null;
            Point p = pvQueue.peek().p;
            put(p, 3 - color, deep - 1);
            backTrackRecord.add(p);
            status.set(p, 3 - color);
            int value = evaluateBoard();
            PointAndValue deepResult = new PointAndValue(p, value);
            cache.put(status.getZobrist(), deepResult, deep - leftStep);
            status.set(p, 0);
            backTrackRecord.remove(p);
            put(p, 0, deep - 1);
            return deepResult;
        }
        Point minPoint = null;
        int minValue = MAX_VALUE;
        int i = 0;
        for (PointAndValue pv : pvQueue) {
            if (++i > getEvaluateCount(maxEvalPointCount, pvQueue.size()) && minPoint != null) {
                break;
            }
            Point p = pv.p;
            put(p, 3 - color, deep - leftStep);
            backTrackRecord.add(p);
            status.set(p, 3 - color);
            int value = evaluateBoard();
            if (value < -VICTORY_STANDARD) {
                PointAndValue winResult = new PointAndValue(p, value);
                cache.put(status.getZobrist(), winResult, deep - leftStep);
                status.set(p, 0);
                backTrackRecord.remove(p);
                put(p, 0, deep - leftStep);
                return winResult;
            }
            PointAndValue nextStepResult = getMaxEvaluate(leftStep - 1, minValue);
            assert nextStepResult != null;
            int nextStepValue = nextStepResult.value;
            if (nextStepValue <= maxValue) {
                status.set(p, 0);
                backTrackRecord.remove(p);
                put(p, 0, deep - leftStep);
                PointAndValue iterResult = new PointAndValue(p, nextStepValue);
                return iterResult;
            }
            if (minPoint == null || minValue > nextStepValue || minValue == nextStepValue && p.nearMidThan(minPoint)) {
                minValue = nextStepValue;
                minPoint = p;
            }
            status.set(p, 0);
            backTrackRecord.remove(p);
            put(p, 0, deep - leftStep);
        }
        if (minPoint == null) {
            return null;
        }
        PointAndValue finalResult = new PointAndValue(minPoint, minValue);
        cache.put(status.getZobrist(), finalResult, deep - leftStep);
        return finalResult;
    }

    /**
     * Returns the step which causes a maximum value.
     *
     * @param leftStep the step(s) to be evaluated
     * @param minValue the value which is passed from other branches
     * @return the step which causes a maximum value, with point and its
     *         corresponding value
     */
    private PointAndValue getMaxCalculate(int leftStep, int minValue) {
        PointAndValue cacheResult = cache.get(status.getZobrist(), leftStep);
        if (cacheResult != null) {
            return cacheResult;
        }
        int deep = this.deep[0];
        PriorityQueue<PointAndValue> pvQueue = new PriorityQueue<>((pv1, pv2) -> Integer.compare(pv2.value, pv1.value));
        if (leftStep == deep) {
            for (PointAndValue pv : getPriorCalculateList()) {
                if (isKnight(pv.p)) {
                    pvQueue.add(new PointAndValue(pv.p, evaluatePoint(pv.p, color)));
                }
            }
        } else {
            for (Point p : currentReachableList[deep - leftStep - 1]) {
                pvQueue.add(new PointAndValue(p, evaluatePoint(p, color)));
            }
        }
        if (leftStep == 1) {
            assert pvQueue.peek() != null;
            Point p = pvQueue.peek().p;
            put(p, color, deep - 1);
            backTrackRecord.add(p);
            status.set(p, color);
            int value = evaluateBoard();
            PointAndValue deepResult = new PointAndValue(p, value);
            cache.put(status.getZobrist(), deepResult, deep - leftStep);
            status.set(p, 0);
            backTrackRecord.remove(p);
            put(p, 0, deep - 1);
            return deepResult;
        }
        Point maxPoint = null;
        int maxValue = MIN_VALUE;
        int i = 0;
        for (PointAndValue pv : pvQueue) {
            if (++i > getEvaluateCount(maxEvalPointCount, pvQueue.size()) && maxPoint != null) {
                break;
            }
            Point p = pv.p;
            put(p, color, deep - leftStep);
            backTrackRecord.add(p);
            status.set(p, color);
            int value = evaluateBoard();
            if (value > VICTORY_STANDARD) {
                PointAndValue winResult = new PointAndValue(p, value);
                cache.put(status.getZobrist(), winResult, deep - leftStep);
                status.set(p, 0);
                backTrackRecord.remove(p);
                put(p, 0, deep - leftStep);
                return winResult;
            }
            PointAndValue nextStepResult = getMinCalculate(leftStep - 1, maxValue);
            assert nextStepResult != null;
            int nextStepValue = nextStepResult.value;
            if (nextStepValue >= minValue) {
                status.set(p, 0);
                backTrackRecord.remove(p);
                put(p, 0, deep - leftStep);
                PointAndValue iterResult = new PointAndValue(p, nextStepValue);
                return iterResult;
            }
            if (maxPoint == null || maxValue < nextStepValue || maxValue == nextStepValue && p.nearMidThan(maxPoint)) {
                maxValue = nextStepValue;
                maxPoint = p;
            }
            status.set(p, 0);
            backTrackRecord.remove(p);
            put(p, 0, deep - leftStep);
        }
        if (maxPoint == null) {
            return null;
        }
        PointAndValue finalResult = new PointAndValue(maxPoint, maxValue);
        cache.put(status.getZobrist(), finalResult, deep - leftStep);
        return finalResult;
    }

    /**
     * Returns the step which causes a minimum value.
     *
     * @param leftStep the step(s) to be evaluated
     * @param maxValue the value which is passed from other branches
     * @return the step which causes a minimum value, with point and its
     *         corresponding value
     */
    private PointAndValue getMinCalculate(int leftStep, int maxValue) {
        PointAndValue cacheResult = cache.get(status.getZobrist(), leftStep);
        if (cacheResult != null) {
            return cacheResult;
        }
        int deep = this.deep[0];
        PriorityQueue<PointAndValue> pvQueue = new PriorityQueue<>((pv1, pv2) -> Integer.compare(pv2.value, pv1.value));
        if (leftStep == deep) {
            for (PointAndValue pv : getPriorCalculateList()) {
                if (isKnight(pv.p)) {
                    pvQueue.add(new PointAndValue(pv.p, evaluatePoint(pv.p, 3 - color)));
                }
            }
        } else {
            for (Point p : currentReachableList[deep - leftStep - 1]) {
                pvQueue.add(new PointAndValue(p, evaluatePoint(p, color)));
            }
        }
        if (leftStep == 1) {
            assert pvQueue.peek() != null;
            Point p = pvQueue.peek().p;
            put(p, 3 - color, deep - 1);
            backTrackRecord.add(p);
            status.set(p, 3 - color);
            int value = evaluateBoard();
            PointAndValue deepResult = new PointAndValue(p, value);
            cache.put(status.getZobrist(), deepResult, deep - leftStep);
            status.set(p, 0);
            backTrackRecord.remove(p);
            put(p, 0, deep - 1);
            return deepResult;
        }
        Point minPoint = null;
        int minValue = MAX_VALUE;
        int i = 0;
        for (PointAndValue pv : pvQueue) {
            if (++i > getEvaluateCount(maxEvalPointCount, pvQueue.size()) && minPoint != null) {
                break;
            }
            Point p = pv.p;
            put(p, 3 - color, deep - leftStep);
            backTrackRecord.add(p);
            status.set(p, 3 - color);
            int value = evaluateBoard();
            if (value < -VICTORY_STANDARD) {
                PointAndValue winResult = new PointAndValue(p, value);
                cache.put(status.getZobrist(), winResult, deep - leftStep);
                status.set(p, 0);
                backTrackRecord.remove(p);
                put(p, 0, deep - leftStep);
                return winResult;
            }
            PointAndValue nextStepResult = getMaxCalculate(leftStep - 1, minValue);
            assert nextStepResult != null;
            int nextStepValue = nextStepResult.value;
            if (nextStepValue <= maxValue) {
                status.set(p, 0);
                backTrackRecord.remove(p);
                put(p, 0, deep - leftStep);
                PointAndValue iterResult = new PointAndValue(p, nextStepValue);
                return iterResult;
            }
            if (minPoint == null || minValue > nextStepValue || minValue == nextStepValue && p.nearMidThan(minPoint)) {
                minValue = nextStepValue;
                minPoint = p;
            }
            status.set(p, 0);
            backTrackRecord.remove(p);
            put(p, 0, deep - leftStep);
        }
        if (minPoint == null) {
            return null;
        }
        PointAndValue finalResult = new PointAndValue(minPoint, minValue);
        cache.put(status.getZobrist(), finalResult, deep - leftStep);
        return finalResult;
    }

    /**
     * Notifies <code>RobotPlayer</code> the point <code>p</code> and the color
     * <code>color</code> of each chess piece after it moves.
     *
     * @param p     the point of the chess piece
     * @param color the color of the chess piece: <code>1</code> for black,
     *              <code>2</code> for white
     */
    @Override
    public void notifyMove(Point p, int color) {
        if (get(p) != 0) {
            throw new IllegalArgumentException(get(p) + p.p2Pos().toString(false));
        }
        set(p, color);
        status.set(p, color);
    }

    /**
     * Notifies <code>RobotPlayer</code> to retract or to retraction in specific
     * step(s).
     *
     * @param step the step(s) for retraction or reretraction
     * @param flag <code>true</code> for retract, <code>false</code> for reretract
     */
    @Override
    public void notifyRetraction(int step, boolean flag) {
        if (flag) {
            Chess[] lastMoves = history.getLastMoves(step);
            if (lastMoves != null) {
                for (Chess chess : lastMoves) {
                    status.set(chess.p, 0);
                    cut(chess.p);
                }
            }
        } else {
            Chess[] lastRetracts = history.getLastRetracts(step);
            if (lastRetracts != null) {
                for (Chess chess : lastRetracts) {
                    set(chess.p, chess.color);
                    status.set(chess);
                }
            }
        }
    }

    @Override
    public void play() {
        play = new Thread(() -> {
            Point p = null;
            if (step == 0) {
                p = new Point(length / 2, length / 2);
            } else if (step == 1) {
                p = chessSequence[0].p.step(Diagram.values()[new Random().nextInt(Diagram.values().length)], 1);
            } else {
                PointAndValue priorityResult = getMaxCalculate(deep[0], MAX_VALUE);
                PointAndValue searchResult = getMaxEvaluate(deep[1], MAX_VALUE);
                PointAndValue result;
                if (priorityResult == null && searchResult == null) {
                    throw new RuntimeException("Calculation timeout.");
                } else if (priorityResult == null) {
                    result = searchResult;
                } else if (searchResult == null) {
                    result = priorityResult;
                } else {
                    result = priorityResult.value >= searchResult.value ? priorityResult : searchResult;
                }
                p = result.p;
            }
            if (p == null) {
                throw new IllegalArgumentException("Calculation timeout.");
            } else {
                gameboard.notifyMove(p, color == 1 ? true : false);
            }
        });
        play.start();
        try {
            play.join();
        } catch (InterruptedException e) {
            if (retractInterrupt) {
                return;
            } else {
                e.printStackTrace();
            }
        }
    }
}
