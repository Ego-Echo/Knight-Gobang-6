/**
 * <code>Variable</code> records all the variables in <strong>Knight Gobang
 * 6</strong>, which can be modified in settings.
 *
 * @author Instant
 * @author Ego-Echo
 * @see Constant
 */
public abstract class Variable {
    /**
     * Records players' type: <code>-1</code> for EVE, <code>0</code> for PVE,
     * <code>1</code> for PVP.
     */
    public static byte players = 0;
    /**
     * Records the chess color <code>HumanPlayer</code> chooses: <code>true</code>
     * for black, <code>false</code> for white. This variable only works when the
     * match is in PVE type.
     */
    public static boolean chess = true;
    /**
     * Records the difficulty <code>RobotPlayer</code> is in: <code>0</code> for
     * normal, <code>1</code> for hard, <code>2</code> for impossible. This variable
     * only works when the match is in PVE or PVP type.
     */
    public static byte difficulty = 0;
    /**
     * Records the volumn level of the music: <code>0.0</code> for the minimum,
     * <code>1.0</code> for maximum.
     */
    private static float music = 0.8f;
    /**
     * Records the volumn level of the sound: <code>0.0</code> for the minimum,
     * <code>1.0</code> for maximum.
     */
    private static float sound = 0.8f;
    /**
     * Records the chessboard file current index, with the maximum length of
     * <code>8</code>.
     */
    public static byte save = 0;

    /**
     * Returns the length of the chessboard in current match.
     *
     * @return length
     */
    public static int getLength() {
        return Constant.LENGTH[difficulty];
    }

    /**
     * Returns the chess' size of the chessboard in current match.
     *
     * @return chess' size
     */
    public static int getChessSize() {
        return Constant.CHESSSIZE[difficulty];
    }

    /**
     * Returns the volumn level of music.
     *
     * @return music volumn
     */
    public static float getMusicVolumn() {
        return music;
    }

    /**
     * Returns the volumn level of sound.
     *
     * @return sound volumn
     */
    public static float getSoundVolumn() {
        return sound;
    }

    /**
     * Recieves the modification from <code>settingsPanel</code> in <code>UI</code>.
     *
     * @param players    <code>-1</code> for EVE, <code>0</code> for PVE,
     *                   <code>1</code> for PVP
     * @param chess      <code>true</code> for black, <code>false</code> for white
     * @param difficulty <code>0</code> for normal, <code>1</code> for hard,
     *                   <code>2</code> for impossible
     */
    public static void modify(byte players, boolean chess, byte difficulty) {
        if (players == -1 || players == 0 || players == 1) {
            Variable.players = players;
        }
        if (chess || !chess) {
            Variable.chess = chess;
        }
        if (difficulty == 0 || difficulty == 1 || difficulty == 2) {
            Variable.difficulty = difficulty;
        }
        System.out.println("Variable modified: " + players + ", " + chess + ", " + difficulty + ".");
    }

    /**
     * Updates the music and sound volumn level.
     *
     * @param music the music or sound volumn from <code>0.0f</code> to
     *              <code>1.0f</code>
     * @param which <code>true</code> for music, <code>false</code> for sound
     */
    public static void musicUpdate(float music, boolean which) {
        if (which) {
            if (music >= 0.0f && music <= 1.0f) {
                Variable.music = music;
            }
        } else {
            if (music >= 0.0f && music <= 1.0f) {
                Variable.sound = music;
            }
        }
    }
}
