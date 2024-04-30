import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * <code>History</code> records the absolute chess piece moves and provides move
 * reference.
 *
 * @author Instant
 * @author Ego-Echo
 * @see KnightGobang6
 * @see Chessboard
 * @see Chess
 */
public class History {
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
     * Records the number of how many moves and retractions have been recorded in
     * current match.
     */
    private int intactStep;
    /**
     * Records the number of how many moves have been recorded in current match. A
     * retraction might affect the number.
     */
    private int step;
    /**
     * Records the chessboard's length of current match.
     */
    private int length;
    /**
     * Records the left times retraction can be permitted in current match.
     */
    private int retractLeft;
    /**
     * Records the intact chess piece moves.
     * <p>
     * For normal chess, its color means:
     * <p>
     * <code>-1</code> for reretraction, <code>0</code> for retraction,
     * <code>1</code> for black, <code>2</code> for white.
     * <p>
     * For the last chess, its color means:
     * <p>
     * <code>-2</code> for white admitting defeat, <code>-1</code> for black
     * admitting defeat,<code>0</code> for an abend, <code>1</code> for black
     * victory, <code>2</code> for white victory, <code>3</code> for draw match.
     */
    private ArrayList<Chess> intactMoveHistory;
    /**
     * Records the chess piece moves.
     * <p>
     * For normal chess, its color means:
     * <p>
     * <code>1</code> for black, <code>2</code> for white.
     * <p>
     * For the last chess, its color means:
     * <p>
     * <code>-2</code> for white admitting defeat, <code>-1</code> for black
     * admitting defeat,<code>0</code> for an abend, <code>1</code> for black
     * victory, <code>2</code> for white victory, <code>3</code> for draw match.
     */
    private Chess[] moveHistory;
    /**
     * Records the steps for each retractions.
     */
    public ArrayList<Integer> retractStepsHistory;
    /**
     * Records the steps for each retractions.
     */
    private int[] retractHistory;
    /**
     * Points to the current chessboard.
     */
    private Chessboard chessboard;
    /**
     * Points to the current gameboard.
     */
    private Gameboard gameboard;
    /**
     * Points to the current goListener.
     */
    // private GoListener goListener;

    /**
     * Construts a new <code>History</code>.
     */
    public History() {
    }

    /**
     * Initializes <code>History</code>'s basic parameters.
     */
    public void initVariable() {
        this.players = Variable.players;
        this.chess = Variable.chess;
        this.difficulty = Variable.difficulty;
        this.intactStep = 0;
        this.step = 0;
        this.length = Variable.getLength();
        this.retractLeft = Gameboard.RETRACT_LEFT[difficulty];
        this.intactMoveHistory = new ArrayList<>();
        this.moveHistory = new Chess[length * length];
        this.retractStepsHistory = new ArrayList<>();
        if (retractLeft >= 0) {
            this.retractHistory = new int[retractLeft];
        } else if (retractLeft == -1) {
            this.retractHistory = new int[100];
        }
        this.chessboard = KnightGobang6.chessboard;
        this.gameboard = KnightGobang6.gameboard;
        // this.goListener = KnightGobang6.goListener;
    }

    /**
     * Notifies <code>History</code> the point <code>p</code> and the move
     * <code>move</code> of each chess piece after its move.
     *
     * @param p    the point of the chess piece
     * @param move the move: <code>-1</code> for reretraction, <code>0</code> for
     *             retraction, <code>1</code> for black move, <code>2</code> for
     *             white move.
     */
    public void notifyMove(Point p, int move) {
        if (move == 1 || move == 2) {
            Chess chess = new Chess(p, move);
            intactMoveHistory.add(chess);
            intactStep++;
            moveHistory[step] = chess;
            step++;
        }
    }

    /**
     * Notifies <code>History</code> of current match's end with <code>index</code>,
     * while showing a notifying <code>JOptionPane</code> to user.
     *
     * @param index current match's end index: <code>-2</code> for white admitting
     *              defeat, <code>-1</code> for black admitting
     *              defeat,<code>0</code> for an abend, <code>1</code> for black
     *              victory, <code>2</code> for white victory, <code>3</code> for
     *              draw match.
     */
    public void notifyEnd(int index) {
        Chess chess = new Chess(null, index);
        intactMoveHistory.add(chess);
        intactStep++;
        moveHistory[step] = chess;
        step++;
        if (players == 0 && index != 0) {
            String message = "";
            int piece = this.chess ? 1 : 2;
            int seed = (int) (4 * Math.random());
            if (index == piece || index + 3 == piece) {
                message = Constant.VICTEXT[seed];
            } else if (index == 3 - piece || index + 3 == 3 - piece) {
                message = Constant.DEFTEXT[seed];
            } else if (index == 3) {
                message = Constant.DRWTEXT[seed];
            }
            JOptionPane.showMessageDialog(null, message);
        } else if (players == -1 || index == 0 || players == 1) {
            JOptionPane.showMessageDialog(null, Constant.ENDTEXT[index + 2]);
        }
    }

    /**
     * Gets real step(s) in current match.
     *
     * @return intact step(s)
     */
    public int getIntactStep() {
        return intactStep;
    }

    /**
     * Gets step(s) in current match.
     *
     * @return step(s)
     */
    public int getCurrentStep() {
        return step;
    }

    /**
     * Returns the step(s) of last retraction.
     *
     * @return step(s) of last retraction
     */
    public int getLastRetractSteps() {
        int currentRetractLeft = gameboard.getCurrentRetractLeft();
        if (currentRetractLeft > 0) {
            return retractHistory[currentRetractLeft-1];
        } else {
            int ans = retractStepsHistory.getLast();
            retractStepsHistory.removeLast();
            return ans;
        }
    }

    /**
     * Returns the chess sequence of current situation on the chessboard.
     *
     * @return the chess sequence
     */
    public Chess[] getChessSequence() {
        if (step <= 0) {
            return null;
        }
        Chess[] chessSequence = new Chess[step];
        for (int i = 0; i < step; i++) {
            chessSequence[i] = moveHistory[i];
        }
        return chessSequence;
    }

    /**
     * Returns the last <code>stepBack</code> moves of the chess pieces. If it can't
     * step back completely, returns <code>null</code>.
     *
     * @param stepBack the step(s) to move back
     * @return the last specific move(s) if reversable, <code>null</code> otherwise
     */
    public Chess[] getLastMoves(int stepBack) {
        Chess[] lastMoves = new Chess[stepBack];
        if (stepBack > step || stepBack <= 0) {
            return null;
        }
        for (int i = 0; i < stepBack; i++) {
            lastMoves[i] = moveHistory[step - i - 1];
        }
        return lastMoves;
    }

    /**
     * Makes a retraction in current match.
     *
     * @param stepBack the step(s) for retraction
     */
    public void retract(int stepBack) {
        for (int i = 0; i < stepBack; i++) {
            step--;
            intactStep++;
            intactMoveHistory.add(new Chess(null, 0));
        }
        int currentRetractLeft = gameboard.getCurrentRetractLeft();
        if (currentRetractLeft > 0) {
            retractHistory[currentRetractLeft - 1] = stepBack;
        } else if (currentRetractLeft == -1) {
            retractStepsHistory.add(stepBack);
        }
        chessboard.repaint();
    }

    /**
     * Returns the last <code>stepFore</code> retracts of the chess pieces. If it
     * can't step fore completely, returns <code>null</code>.
     *
     * @param stepFore the step to reretract back
     * @return the last specific retract(s) if inversable, <code>null</code>
     *         otherwise
     */
    public Chess[] getLastRetracts(int stepFore) {
        Chess[] lastRetracts = new Chess[stepFore];
        if (moveHistory[step + stepFore - 1] == null || stepFore <= 0) {
            return null;
        }
        for (int i = 0; i < stepFore; i++) {
            lastRetracts[i] = moveHistory[step + i];
        }
        return lastRetracts;
    }

    /**
     * Makes a reretraction in current match.
     *
     * @param stepFore the step(s) for reretraction
     */
    public void reretract(int stepFore) {
        for (int i = 0; i < stepFore; i++) {
            intactMoveHistory.add(new Chess(null, -1));
            intactStep++;
            step++;
        }
        chessboard.repaint();
    }

    /**
     * Records the match to a document after it goes ending.
     */
    public void record() {
        /* to be finished */
        if (step <= 8) {
            return;
        }
        LocalDateTime currentTime = LocalDateTime.now();
        String[] rectext = Constant.RECTEXT;
        StringBuilder content = new StringBuilder();
        content.append(rectext[0] + currentTime.toString() + "\n" + rectext[1] + "\n");
        String players = Integer.toString(this.players);
        content.append(rectext[2] + players + "\n");
        String chess = this.chess ? "Black" : "White";
        content.append(rectext[3] + chess + "\n");
        String difficulty = Integer.toString(this.difficulty);
        content.append(rectext[4] + difficulty + "\n\n" + rectext[5] + "\n");
        for (int i = 0; i < step - 1; i++) {
            Chess css = intactMoveHistory.get(i);
            content.append(css.toString(true, false) + "\n");
        }
        content.append("\n" + rectext[6] + intactMoveHistory.get(step - 1).toString(true, true) + "\n");
        String filePath = "../rec/REC_" + currentTime.toString() + ".txt";
        try {
            File recordFile = new File(filePath);
            if (!recordFile.exists()) {
                recordFile.getParentFile().mkdirs();
                recordFile.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(recordFile));
            writer.write(content.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
