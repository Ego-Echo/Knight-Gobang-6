import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <code>UI</code> constructs user interface and reacts to user actions in
 * <strong>Knight Gobang 6</strong>, except for those happening on
 * <code>Chessboard</code>.
 * <p>
 * The contained components are:
 * <blockquote>
 *
 * <pre>
 * Components:
 *  mainFrame -> MAIN
 *      mainPane -> -
 *          mainPanel -> M
 *              chessboard -> MC
 *              toolPanel -> MT
 *                  mainLabel -> MTT
 *                  toolButton -> MTB
 *                  declareLabel -> MTD
 *          settingsPanel -> S
 *              settingsLabel -> ST
 *              settingPanel -> SS
 *                  settingLabel -> SSL (playersLabel, chessLabel, difficultyLabel)
 *                  setOptPanel -> SSO
 *                      settingChoice -> SSC (playersCoice, chessChoice, difficultyChoice)
 *              musicPanel -> SM
 *                  musicLabel -> SML (musicLabel, soundLabel)
 *                  musicSlider -> SMS (musicSlider, soundSlider)
 *              okPanel -> SO
 *                  okButton -> SOB (yesButton, noButton)
 *          captionPanel -> C
 *              captionLabel -> CT
 *              contentLabel -> CC
 *              displayLabel -> CD
 *              okPanel -> CO
 *                  okButton -> COB
 *          aboutPanel -> A
 *              aboutLabel -> AT
 *              contentLabel -> AC
 *              okPanel -> AO
 *                  okButton -> AOB
 *          exitPanel -> E
 *              exitLabel -> ET
 *              contentLabel -> EC
 *              saveLabel -> ES
 *              okPanel -> EO
 *                  okButton -> EOB (yesButton, noButton, cancelButton)
 *          warningPanel -> W
 *              warningLabel -> WT
 *              contentLabel -> WC
 *              okPanel -> WO
 *                  okButton -> WOB (yesButton, noButton, cancelButton)
 * </pre>
 *
 * </blockquote>
 *
 * @author Instant
 * @author Ego-Echo
 * @see KnightGobang6
 * @see Gameboard
 * @see Chessboard
 */
public class UI {
    /**
     * Records the pixal chessboard's width and height.
     */
    private static final int BOARDWIDTH = 900;
    /**
     * Records the pixal button's width.
     */
    private static final int BUTTONWIDTH = 200;
    /**
     * Records the pixal interval between a panel, a label or a button. Or records
     * half of the blank edge's width of a panel, achieving it with
     * <code>JPanel.setBorder(BorderFactory(int, int, int, int))</code>.
     */
    private static final int INTERVAL = 5;
    /**
     * Records the pixal SCALEs of the texts.
     * <p>
     * <code>SCALE[0]</code> = <code>12</code>, is for a minimum font;
     * <p>
     * <code>SCALE[1]</code> = <code>16</code>, is for a default font;
     * <p>
     * <code>SCALE[2]</code> = <code>18</code>, is for a bold font;
     * <p>
     * <code>SCALE[3]</code> = <code>24</code>, is for a title font;
     * <p>
     * <code>SCALE[4]</code> = <code>32</code>, is for a strong font;
     * <p>
     * <code>SCALE[5]</code> = <code>36</code>, is for a maximum font.
     */
    private static final int[] SCALE = { 12, 16, 18, 24, 32, 36 };
    /**
     * Records the color of the background in 3 difficulties.
     */
    private static final Color[] BACKGROUND_COLOR = { new Color(139, 163, 199), new Color(216, 199, 181),
            new Color(99, 18, 22) };
    /**
     * Records the color of the middleground in 3 difficulties.
     */
    private static final Color[] MIDDLEGROUND_COLOR = { new Color(26, 40, 71), new Color(73, 45, 34),
            new Color(203, 82, 62) };
    /**
     * Records the color of the foreground in 3 difficulties.
     */
    private static final Color[] FOREGROUND_COLOR = { new Color(247, 246, 245), new Color(247, 246, 245),
            new Color(247, 246, 245) };
    /**
     * Records the dimension of <code>mainFrame</code>.
     * <p>
     * <code>mainFrame</code>
     */
    private static final Dimension MAINSIZE = new Dimension(BOARDWIDTH + BUTTONWIDTH + 6 * INTERVAL,
            BOARDWIDTH + 9 * INTERVAL);
    /**
     * Records the dimension of <code>chessboard</code> in <code>mainPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>mainPanel</code> -
     * <code>chessboard</code>
     */
    private static final Dimension MCSIZE = new Dimension(BOARDWIDTH, BOARDWIDTH);
    /**
     * Records the dimension of <code>toolPanel</code> in <code>mainPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>mainPanel</code> -
     * <code>toolPanel</code>
     */
    private static final Dimension MTSIZE = new Dimension(BUTTONWIDTH + SCALE[1], BOARDWIDTH);
    /**
     * Records the dimension of <code>mainLabel</code> in <code>mainPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>mainPanel</code> -
     * <code>toolPanel</code> - <code>mainLabel</code>
     */
    private static final Dimension MTTSIZE = new Dimension(BUTTONWIDTH, 2 * SCALE[4]);
    /**
     * Records the dimension of <code>toolButton</code> in <code>mainPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>mainPanel</code> -
     * <code>toolPanel</code> - <code>toolButton</code>
     */
    private static final Dimension MTBSIZE = new Dimension(BUTTONWIDTH, 2 * SCALE[1]);
    /**
     * Records the dimension of <code>declareLabel</code> in <code>mainPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>mainPanel</code> -
     * <code>toolPanel</code> - <code>declareLabel</code>
     */
    private static final Dimension MTDSIZE = new Dimension(BUTTONWIDTH, 4 * SCALE[1]);
    /**
     * Records the dimension of <code>settingsPanel</code> in <code>mainPane</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code>
     */
    private static final Dimension SSIZE = new Dimension(2 * BUTTONWIDTH + 6 * SCALE[2] + 4 * INTERVAL,
            2 * SCALE[3] + 10 * SCALE[2] + 2 * SCALE[1] + 12 * INTERVAL);
    /**
     * Records the dimension of <code>settingsLabel</code> in
     * <code>settingsPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code> -
     * <code>settingsLabel</code>
     */
    private static final Dimension STSIZE = new Dimension(2 * BUTTONWIDTH + 6 * SCALE[2] + 2 * INTERVAL,
            2 * SCALE[3]);
    /**
     * Records the dimension of <code>settingPanel</code> in
     * <code>settingsPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code> -
     * <code>settingPanel</code>
     */
    private static final Dimension SSSIZE = new Dimension(2 * BUTTONWIDTH + 6 * SCALE[2] + 2 * INTERVAL,
            6 * SCALE[2] + 2 * INTERVAL);
    /**
     * Records the dimension of <code>settingLabel</code> in
     * <code>settingsPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code> -
     * <code>settingPanel</code> - <code>settingLabel</code>
     */
    private static final Dimension SSLSIZE = new Dimension(BUTTONWIDTH, 2 * SCALE[2]);
    /**
     * Records the dimension of <code>setOptPanel</code> in
     * <code>settingsPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code> -
     * <code>settingPanel</code> - <code>setOptPanel</code>
     */
    private static final Dimension SSOSIZE = new Dimension(BUTTONWIDTH + 4 * SCALE[2], 2 * SCALE[2]);
    /**
     * Records the dimension of <code>musicPanel</code> in
     * <code>settingsPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code> -
     * <code>musicPanel</code>
     */
    private static final Dimension SMSIZE = new Dimension(2 * BUTTONWIDTH + 2 * SCALE[5] + 2 * INTERVAL,
            4 * SCALE[2] + 2 * INTERVAL);
    /**
     * Records the dimension of <code>musicLabel</code> in
     * <code>settingsPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code> -
     * <code>musicPanel</code> - <code>musicLabel</code>
     */
    private static final Dimension SMLSIZE = new Dimension(BUTTONWIDTH, 2 * SCALE[2]);
    /**
     * Records the dimension of <code>musicSlider</code> in
     * <code>settingsPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code> -
     * <code>musicPanel</code> - <code>musicSlider</code>
     */
    private static final Dimension SMSSIZE = new Dimension(BUTTONWIDTH + 2 * SCALE[5], 2 * SCALE[2]);
    /**
     * Records the dimension of <code>okPanel</code> in <code>settingsPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code> -
     * <code>okPanel</code>
     */
    private static final Dimension SOSIZE = new Dimension(2 * BUTTONWIDTH + 2 * INTERVAL, 2 * SCALE[1] + INTERVAL);
    /**
     * Records the dimension of <code>okButton</code> in <code>settingsPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code> -
     * <code>okPanel</code> - <code>okButton</code>
     */
    private static final Dimension SOBSIZE = new Dimension(BUTTONWIDTH, 2 * SCALE[1]);
    /**
     * Records the dimension of <code>captionPanel</code> in <code>mainPane</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>captionPanel</code>
     */
    private static final Dimension CSIZE = new Dimension(2 * BUTTONWIDTH + 12 * SCALE[1] + 4 * INTERVAL,
            2 * SCALE[3] + 4 * SCALE[2] + 2 * SCALE[1] + BUTTONWIDTH + 9 * INTERVAL);
    /**
     * Records the dimension of <code>captionLabel</code> in
     * <code>captionPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>captionPanel</code> -
     * <code>captionLabel</code>
     */
    private static final Dimension CTSIZE = new Dimension(2 * BUTTONWIDTH + 12 * SCALE[1] + 2 * INTERVAL, 2 * SCALE[3]);
    /**
     * Records the dimension of <code>contentLabel</code> in
     * <code>captionPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>captionPanel</code> -
     * <code>contentLabel</code>
     */
    private static final Dimension CCSIZE = new Dimension(2 * BUTTONWIDTH + 12 * SCALE[1] + 2 * INTERVAL,
            4 * SCALE[2] + INTERVAL);
    /**
     * Records the dimension of <code>displayLabel</code> in
     * <code>captionPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>captionPanel</code> -
     * <code>displayLabel</code>
     */
    private static final Dimension CDSIZE = new Dimension(2 * BUTTONWIDTH + 12 * SCALE[1] + 2 * INTERVAL, BUTTONWIDTH);
    /**
     * Records the dimension of <code>okPanel</code> in <code>captionPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>captionPanel</code> -
     * <code>okPanel</code>
     */
    private static final Dimension COSIZE = new Dimension(BUTTONWIDTH + 2 * INTERVAL, 2 * SCALE[1] + INTERVAL);
    /**
     * Records the dimension of <code>okButton</code> in <code>captionPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>captionPanel</code> -
     * <code>okPanel</code> - <code>okButton</code>
     */
    private static final Dimension COBSIZE = new Dimension(BUTTONWIDTH, 2 * SCALE[1]);
    /**
     * Records the dimension of <code>aboutPanel</code> in <code>mainPane</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>aboutPanel</code>
     */
    private static final Dimension ASIZE = new Dimension(2 * BUTTONWIDTH + 2 * INTERVAL,
            2 * SCALE[3] + 8 * SCALE[2] + 2 * SCALE[1] + 9 * INTERVAL);
    /**
     * Records the dimension of <code>aboutLabel</code> in <code>aboutPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>aboutPanel</code> -
     * <code>aboutLabel</code>
     */
    private static final Dimension ATSIZE = new Dimension(2 * BUTTONWIDTH, 2 * SCALE[3]);
    /**
     * Records the dimension of <code>contentLabel</code> in
     * <code>aboutPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>aboutPanel</code> -
     * <code>contentLabel</code>
     */
    private static final Dimension ACSIZE = new Dimension(2 * BUTTONWIDTH, 8 * SCALE[2] + INTERVAL);
    /**
     * Records the dimension of <code>okPanel</code> in <code>aboutPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>aboutPanel</code> -
     * <code>okPanel</code>
     */
    private static final Dimension AOSIZE = new Dimension(BUTTONWIDTH + 2 * INTERVAL, 2 * SCALE[1] + INTERVAL);
    /**
     * Records the dimension of <code>okButton</code> in <code>aboutPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>aboutPanel</code> -
     * <code>okPanel</code> - <code>okButton</code>
     */
    private static final Dimension AOBSIZE = new Dimension(BUTTONWIDTH, 2 * SCALE[1]);
    /**
     * Records the dimension of <code>exitPanel</code> in <code>mainPane</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>exitPanel</code>
     */
    private static final Dimension ESIZE = new Dimension(3 * BUTTONWIDTH + 6 * INTERVAL,
            2 * SCALE[3] + 2 * SCALE[2] + 18 * SCALE[1] + 10 * INTERVAL);
    /**
     * Records the dimension of <code>exitLabel</code> in <code>exitPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>exitPanel</code> -
     * <code>exitLabel</code>
     */
    private static final Dimension ETSIZE = new Dimension(3 * BUTTONWIDTH + 4 * INTERVAL, 2 * SCALE[3]);
    /**
     * Records the dimension of <code>contentLabel</code> in <code>exitPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>exitPanel</code> -
     * <code>contentLabel</code>
     */
    private static final Dimension ECSIZE = new Dimension(3 * BUTTONWIDTH + 4 * INTERVAL, 2 * SCALE[2] + INTERVAL);
    /**
     * Records the dimension of <code>saveLabel</code> in <code>exitPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>exitPanel</code> -
     * <code>saveLabel</code>
     */
    private static final Dimension ESSIZE = new Dimension(3 * BUTTONWIDTH + 4 * INTERVAL, 16 * SCALE[1] + INTERVAL);
    /**
     * Records the dimension of <code>okPanel</code> in <code>exitPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>exitPanel</code> -
     * <code>okPanel</code>
     */
    private static final Dimension EOSIZE = new Dimension(3 * BUTTONWIDTH + 4 * INTERVAL, 2 * SCALE[1] + INTERVAL);
    /**
     * Records the dimension of <code>okButton</code> in <code>exitPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>exitPanel</code> -
     * <code>okPanel</code> - <code>okButton</code>
     */
    private static final Dimension EOBSIZE = new Dimension(BUTTONWIDTH, 2 * SCALE[1]);
    /**
     * Records the dimension of <code>warningPanel</code> in <code>mainPane</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>warningPanel</code>
     */
    private static final Dimension WSIZE = new Dimension(2 * BUTTONWIDTH + 6 * SCALE[2] + 4 * INTERVAL,
            2 * SCALE[3] + 10 * SCALE[2] + 2 * SCALE[1] + 12 * INTERVAL);
    /**
     * Records the dimension of <code>warningLabel</code> in
     * <code>warningPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>warningPanel</code> -
     * <code>warningLabel</code>
     */
    private static final Dimension WTSIZE = new Dimension(2 * BUTTONWIDTH + 4 * SCALE[1] + 2 * INTERVAL, 2 * SCALE[3]);
    /**
     * Records the dimension of <code>contentLabel</code> in
     * <code>warningPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>warningPanel</code> -
     * <code>contentLabel</code>
     */
    private static final Dimension WCSIZE = new Dimension(2 * BUTTONWIDTH + 4 * SCALE[1] + 2 * INTERVAL,
            10 * SCALE[2] + INTERVAL);
    /**
     * Records the dimension of <code>okPanel</code> in <code>warningPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>warningPanel</code> -
     * <code>okPanel</code>
     */
    private static final Dimension WOSIZE = new Dimension(2 * BUTTONWIDTH + 4 * SCALE[1] + 2 * INTERVAL,
            2 * SCALE[1] + INTERVAL);
    /**
     * Records the dimension of <code>okButton</code> in <code>warningPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>warningPanel</code> -
     * <code>okPanel</code> - <code>okButton</code>
     */
    private static final Dimension WOBSIZE = new Dimension((2 * BUTTONWIDTH + SCALE[1]) / 3, 2 * SCALE[1]);
    /**
     * Records players' type of current match.
     */
    private static byte players;
    /**
     * Records chess' type of current match.
     */
    private static boolean chess;
    /**
     * Records difficulty's type of current match.
     */
    private static byte difficulty;
    /**
     * Records players' type of current settings.
     */
    private static byte currentPlayers;
    /**
     * Records chess' type of current settings.
     */
    private static boolean currentChess;
    /**
     * Records difficulty's type of current settings.
     */
    private static byte currentDifficulty;
    /**
     * Records the background color in current UI.
     */
    private static Color bgColor;
    /**
     * Records the middleground color in current UI.
     */
    private static Color mgColor;
    /**
     * Records the foreground color in current UI.
     */
    private static Color fgColor;
    /**
     * The main frame of the user interface.
     * <p>
     * <code>mainFrame</code>
     */
    public static JFrame mainFrame;
    /**
     * The main pane of the user interface, added into <code>mainFrame</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code>
     */
    private static JLayeredPane mainPane;
    /**
     * The main panel of the user interface, added into <code>mainPane</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>mainPanel</code>
     */
    private static JPanel mainPanel;
    /**
     * The chessboard area of the main panel, added into <code>mainPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>mainPanel</code> -
     * <code>chessboard</code>
     */
    public static Chessboard chessboard;
    /**
     * The main panel of the settings interface, added into <code>mainPane</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code>
     */
    private static JPanel settingsPanel;
    /**
     * The main panel of the caption interface, added into <code>mainPane</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>captionPanel</code>
     */
    private static JPanel captionPanel;
    /**
     * The main panel of the about interface, added into <code>mainPane</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>aboutPanel</code>
     */
    private static JPanel aboutPanel;
    /**
     * The main panel of the exit interface, added into <code>mainPane</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>exitPanel</code>
     */
    private static JPanel exitPanel;
    /**
     * The main panel of the warning interface, added into <code>mainPane</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>warningPanel</code>
     */
    private static JPanel warningPanel;
    /**
     * Records the components that are disabled when calling
     * <code>setCompEnabled(Component, boolean)</code>, and provides the list when
     * calling <code>isCompEnabKept(Component, booloean)</code> to ensure each
     * component in a correct enability.
     */
    private static ArrayList<Component> compEnabKeptList;
    /**
     * Records the buttons that are functional relevant to other classes.
     */
    public static ArrayList<JButton> buttonList;
    /**
     * Records the int while each digit indicating if the relevant panel has been
     * initilized or not.
     * <p>
     * The <code>0th</code> digit is for <code>mainPanel</code>, <code>0</code>
     * meaning it never been initialized, <code>1</code> otherwise;
     * <p>
     * The <code>1st</code> digit is for <code>settingsPanel</code>, <code>0</code>
     * meaning it never been initialized, <code>1</code> otherwise;
     * <p>
     * The <code>2nd</code> digit is for <code>captionPanel</code>, <code>0</code>
     * meaning it never been initialized, <code>1</code> otherwise;
     * <p>
     * The <code>3rd</code> digit is for <code>aboutPanel</code>, <code>0</code>
     * meaning it never been initialized, <code>1</code> otherwise;
     * <p>
     * The <code>4th</code> digit is for <code>exitPanel</code>, <code>0</code>
     * meaning it never been initialized, <code>1</code> otherwise;
     * <p>
     * The <code>5th</code> digit is for <code>warningPanel</code>,
     * <code>0</code> meaning it never been initialized, <code>1</code> otherwise.
     */
    private static byte isInited;
    /**
     * Records <code>JPanel</code>s which are currently showed on the interface.
     * <p>
     * The length of <code>stack</code> is constant <code>4</code>.
     */
    private static JPanel[] panelStack;
    /**
     * Points to current history.
     */
    // private static History history;
    /**
     * Points to current gameboard.
     */
    private static Gameboard gameboard;
    /**
     * Points to current goListener.
     */
    private static GoListener goListener;

    /**
     * Initializes <code>UI</code>'s basic parameters.
     */
    public static void initVariable() {
        players = Variable.players;
        chess = Variable.chess;
        difficulty = Variable.difficulty;
        currentPlayers = players;
        currentChess = chess;
        currentDifficulty = difficulty;
        bgColor = BACKGROUND_COLOR[difficulty];
        mgColor = MIDDLEGROUND_COLOR[difficulty];
        fgColor = FOREGROUND_COLOR[difficulty];
        mainFrame = new JFrame();
        mainPane = new JLayeredPane();
        mainFrame.setTitle(Constant.MAINTITLE);
        mainFrame.setSize(MAINSIZE);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(mainPane);
        mainFrame.setVisible(true);
        mainPanel = new JPanel();
        chessboard = new Chessboard();
        chessboard.initVariable();
        settingsPanel = new JPanel();
        captionPanel = new JPanel();
        aboutPanel = new JPanel();
        exitPanel = new JPanel();
        warningPanel = new JPanel();
        compEnabKeptList = new ArrayList<>();
        buttonList = new ArrayList<>();
        isInited = 0;
        panelStack = new JPanel[4];
        // history = KnightGobang6.history;
        gameboard = KnightGobang6.gameboard;
        goListener = KnightGobang6.goListener;
        initUI();
    }

    /**
     * Initializes the user interface. If it is not the first time to initialize, it
     * will update onto current difficulty mode.
     */
    private static void initUI() {
        if ((isInited & 1 << 0) >> 0 == 1) {
            mainPanel.removeAll();
            mainPane.remove(mainPanel);
        } else {
            mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            mainFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    if (panelStack[3] == null) {
                        exitUI(panelStack[frontLayer()]);
                    }
                }
            });
        }
        mainPanel.setName("mainPanel");
        compCons(mainPanel, bgColor);
        chessboard.setName("chessboard");
        compCons(chessboard, mainPanel, null, MCSIZE, BorderLayout.CENTER);
        JPanel toolPanel = new JPanel();
        toolPanel.setName("toolPanel");
        compCons(toolPanel, mainPanel, bgColor, MTSIZE, BorderLayout.EAST);
        JLabel mainLabel = new JLabel(Constant.MTTTEXT);
        mainLabel.setName("mainLabel");
        Font mainFont = Constant.fontCons(0, Font.BOLD, SCALE[3]);
        compCons(mainLabel, toolPanel, fgColor, MTTSIZE, mainFont, BorderLayout.NORTH);
        String[] toolText = Constant.MTBTEXT;
        for (int i = 0; i < toolText.length; i++) {
            JButton toolButton = new JButton(toolText[i]);
            toolButton.setName("toolButton[" + i + "]");
            Font toolFont = Constant.fontCons(0, Font.PLAIN, SCALE[1]);
            compCons(toolButton, toolPanel, mgColor, fgColor, MTBSIZE, toolFont);
            switch (i) {
                case 0: /* start */
                    toolButton.setName("Start");
                    toolButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String[] toolTexts = Constant.MTBTEXT_START;
                            String toolText = toolButton.getText();
                            if (toolText == toolTexts[0]) {
                                gameboard.start(true);
                            } else if (toolText == toolTexts[1]) {
                                gameboard.start(false);
                            }
                        }
                    });
                    buttonList.add(toolButton);
                    break;
                case 1: /* settings */
                    toolButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            settingsUI(mainPanel);
                        }
                    });
                    break;
                case 2: /* caption */
                    toolButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            captionUI(mainPanel);
                        }
                    });
                    break;
                case 3: /* display move order */
                    toolButton.setName("Order");
                    toolButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String[] toolTexts = Constant.MTBTEXT_ORDER;
                            String toolText = toolButton.getText();
                            if (toolText == toolTexts[0]) {
                                gameboard.order(true);
                            } else if (toolText == toolTexts[1]) {
                                gameboard.order(false);
                            }
                        }
                    });
                    toolButton.setEnabled(false);
                    buttonList.add(toolButton);
                    break;
                case 4: /* display reachable field */
                    toolButton.setName("Field");
                    toolButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String[] toolTexts = Constant.MTBTEXT_FIELD;
                            String toolText = toolButton.getText();
                            if (toolText == toolTexts[0]) {
                                gameboard.field(true);
                            } else if (toolText == toolTexts[1]) {
                                gameboard.field(false);
                            }
                        }
                    });
                    toolButton.setEnabled(false);
                    buttonList.add(toolButton);
                    break;
                case 5: /* retract */
                    toolButton.setName("Retract");
                    toolButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            gameboard.retract();
                        }
                    });
                    toolButton.setEnabled(false);
                    buttonList.add(toolButton);
                    break;
                case 6: /* retract retraction */
                    toolButton.setName("Reretract");
                    toolButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            gameboard.reretract();
                        }
                    });
                    toolButton.setEnabled(false);
                    buttonList.add(toolButton);
                    break;
                case 7: /* admit defeat */
                    toolButton.setName("Admit");
                    toolButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            gameboard.admit();
                        }
                    });
                    toolButton.setEnabled(false);
                    buttonList.add(toolButton);
                    break;
                case 8: /* about */
                    toolButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            aboutUI(mainPanel);
                        }
                    });
                    break;
                case 9: /* exit */
                    toolButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            exitUI(mainPanel);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
        JLabel declareLabel = new JLabel(Constant.MTDTEXT);
        declareLabel.setName("declareLabel");
        Font declareFont = Constant.fontCons(0, Font.PLAIN, SCALE[1]);
        compCons(declareLabel, toolPanel, fgColor, MTDSIZE, declareFont, BorderLayout.CENTER);
        toolPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2 * INTERVAL + 2));
        mainPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER, BoxLayout.X_AXIS);
        isInited |= 1 << 0;
        panelStack[0] = mainPanel;
        System.out.println("UI refreshed.");
    }

    /**
     * Displays <code>settingsPanel</code>, while disabling <code>rootPanel</code>.
     * When <code>settingsPanel</code> is closed, <code>rootPanel</code> will be
     * enabled again.
     *
     * @param rootPanel the panel that is becoming covered and is to be disabled
     */
    private static void settingsUI(JPanel rootPanel) {
        if ((isInited & 1 << 1) >> 1 == 1) {
            settingsPanel.removeAll();
        }
        setCompEnabled(rootPanel, false);
        compCons(settingsPanel, bgColor, mgColor, SSIZE);
        settingsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel settingsLabel = new JLabel(Constant.STTEXT);
        Font settingsFont = Constant.fontCons(0, Font.BOLD, SCALE[3]);
        compCons(settingsLabel, settingsPanel, mgColor, STSIZE, settingsFont, null);
        JPanel settingPanel = new JPanel();
        compCons(settingPanel, settingsPanel, bgColor, SSSIZE, null);
        String[] settingText = Constant.SSLTEXT;
        String[][] setOptText = Constant.SSOTEXT;
        for (int i = 0; i < settingText.length; i++) {
            JLabel settingLable = new JLabel(settingText[i]);
            Font settingFont = Constant.fontCons(0, Font.PLAIN, SCALE[2]);
            compCons(settingLable, settingPanel, mgColor, SSLSIZE, settingFont, null);
            JPanel setOptPanel = new JPanel();
            compCons(setOptPanel, settingPanel, bgColor, SSOSIZE, null);
            ButtonGroup settingOption = new ButtonGroup();
            switch (i) {
                case 0: /* players option */
                    String[] playersText = setOptText[i];
                    Dimension playersSize = new Dimension(SSOSIZE.width / playersText.length - SCALE[0],
                            SSOSIZE.height);
                    Font playersFont = Constant.fontCons(0, Font.PLAIN, SCALE[1]);
                    for (int j = 0; j < playersText.length; j++) {
                        JRadioButton settingButton = new JRadioButton(playersText[j], j - 1 == players);
                        compCons(settingButton, setOptPanel, settingOption, bgColor, mgColor, playersSize, playersFont);
                        byte players = (byte) (j - 1);
                        settingButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                currentPlayers = players;
                            }
                        });
                    }
                    break;
                case 1: /* chess option */
                    String[] chessText = setOptText[i];
                    Dimension chessSize = new Dimension(SSOSIZE.width / chessText.length - SCALE[1], SSOSIZE.height);
                    Font chessFont = Constant.fontCons(0, Font.PLAIN, SCALE[1]);
                    for (int j = 0; j < chessText.length; j++) {
                        JRadioButton settingButton = new JRadioButton(chessText[j], j == 0 == chess);
                        compCons(settingButton, setOptPanel, settingOption, bgColor, mgColor, chessSize, chessFont);
                        boolean chess = j == 0;
                        settingButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                currentChess = chess;
                            }
                        });
                    }
                    break;
                case 2: /* difficulty option */
                    String[] difficultyText = setOptText[i];
                    Dimension difficultySize = new Dimension(SSOSIZE.width / difficultyText.length - SCALE[0],
                            SSOSIZE.height);
                    Font difficultyFont = Constant.fontCons(0, Font.PLAIN, SCALE[1]);
                    for (int j = 0; j < difficultyText.length; j++) {
                        JRadioButton settingButton = new JRadioButton(difficultyText[j], j == difficulty);
                        compCons(settingButton, setOptPanel, settingOption, bgColor, mgColor, difficultySize,
                                difficultyFont);
                        byte difficulty = (byte) j;
                        settingButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                currentDifficulty = difficulty;
                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        }
        JPanel musicPanel = new JPanel();
        compCons(musicPanel, settingsPanel, bgColor, SMSIZE, null);
        String[] musicText = Constant.SMLTEXT;
        Font musicFont = Constant.fontCons(0, Font.PLAIN, SCALE[2]);
        JLabel musicLabel = new JLabel(musicText[0]);
        compCons(musicLabel, musicPanel, mgColor, SMLSIZE, musicFont, null);
        JSlider musicSlider = new JSlider(0, 100, (int) (100 * Variable.getMusicVolumn()));
        compCons(musicSlider, musicPanel, bgColor, mgColor, SMSSIZE, musicFont);
        musicSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Variable.musicUpdate(0.01f * musicSlider.getValue(), true);
            }
        });
        Font soundFont = Constant.fontCons(0, Font.PLAIN, SCALE[2]);
        JLabel soundLabel = new JLabel(musicText[1]);
        compCons(soundLabel, musicPanel, mgColor, SMLSIZE, soundFont, null);
        JSlider soundSlider = new JSlider(0, 100, (int) (100 * Variable.getSoundVolumn()));
        compCons(soundSlider, musicPanel, bgColor, mgColor, SMSSIZE, soundFont);
        musicSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Variable.musicUpdate(0.01f * soundSlider.getValue(), false);
            }
        });
        JPanel okPanel = new JPanel();
        compCons(okPanel, settingsPanel, bgColor, SOSIZE, null);
        String[] okText = Constant.SOBTEXT;
        for (int i = 0; i < okText.length; i++) {
            JButton okButton = new JButton(okText[i]);
            Font okFont = Constant.fontCons(0, Font.PLAIN, SCALE[1]);
            compCons(okButton, okPanel, mgColor, fgColor, SOBSIZE, okFont);
            switch (i) {
                case 0: /* yes option */
                    okButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (players == currentPlayers && chess == currentChess && difficulty == currentDifficulty) {
                                mainPane.remove(settingsPanel);
                                setCompEnabled(rootPanel, true);
                                panelStack[1] = null;
                            } else if (gameboard.isInProgress()) {
                                warningUI(settingsPanel);
                            } else {
                                Variable.modify(currentPlayers, currentChess, currentDifficulty);
                                mainFrame.dispose();
                                KnightGobang6.newMatch();
                            }
                        }
                    });
                    break;
                case 1: /* no option */
                    okButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            currentPlayers = players;
                            currentChess = chess;
                            currentDifficulty = difficulty;
                            mainPane.remove(settingsPanel);
                            setCompEnabled(rootPanel, true);
                            panelStack[1] = null;
                        }
                    });
                    break;
                default:
                    break;
            }
        }
        mainPane.add(settingsPanel, JLayeredPane.MODAL_LAYER, BoxLayout.X_AXIS);
        isInited |= 1 << 1;
        panelStack[1] = settingsPanel;
    }

    /**
     * Displays <code>captionPanel</code>, while disabling <code>rootPanel</code>.
     * When <code>captionPanel</code> is closed, <code>rootPanel</code> will be
     * enabled again.
     *
     * @param rootPanel the panel that is becoming covered and is to be disabled
     */
    private static void captionUI(JPanel rootPanel) {
        if ((isInited & 1 << 2) >> 2 == 1) {
            captionPanel.removeAll();
        }
        setCompEnabled(rootPanel, false);
        compCons(captionPanel, bgColor, mgColor, CSIZE);
        captionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel captionLabel = new JLabel(Constant.CTTEXT);
        Font captionFont = Constant.fontCons(0, Font.BOLD, SCALE[3]);
        compCons(captionLabel, captionPanel, mgColor, CTSIZE, captionFont, null);
        JLabel contentLabel = new JLabel(Constant.CCTEXT);
        Font contentFont = Constant.fontCons(0, Font.PLAIN, SCALE[2]);
        compCons(contentLabel, captionPanel, mgColor, CCSIZE, contentFont, null);
        /* The display image should be in a dimension of 602x200. */
        String dispIconPath = System.getProperty("user.dir") + File.separator + "img" + File.separator + "display.jpg";
        JLabel displayLabel = new JLabel(new ImageIcon(dispIconPath));
        compCons(displayLabel, captionPanel, mgColor, CDSIZE, null, null);
        JPanel okPanel = new JPanel();
        compCons(okPanel, captionPanel, bgColor, COSIZE, null);
        JButton okButton = new JButton(Constant.COBTEXT);
        Font okFont = Constant.fontCons(0, Font.PLAIN, SCALE[1]);
        compCons(okButton, okPanel, mgColor, fgColor, COBSIZE, okFont);
        /* ok */
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPane.remove(captionPanel);
                setCompEnabled(rootPanel, true);
                panelStack[1] = null;
            }
        });
        mainPane.add(captionPanel, JLayeredPane.MODAL_LAYER, BoxLayout.X_AXIS);
        isInited |= 1 << 2;
        panelStack[1] = captionPanel;
    }

    /**
     * Displays <code>aboutPanel</code>, while disabling <code>rootPanel</code>.
     * When <code>aboutPanel</code> is closed, <code>rootPanel</code> will be
     * enabled again.
     *
     * @param rootPanel the panel that is becoming covered and is to be disabled
     */
    private static void aboutUI(JPanel rootPanel) {
        if ((isInited & 1 << 3) >> 3 == 1) {
            aboutPanel.removeAll();
        }
        setCompEnabled(rootPanel, false);
        compCons(aboutPanel, bgColor, mgColor, ASIZE);
        aboutPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel aboutLabel = new JLabel(Constant.ATTEXT);
        Font aboutFont = Constant.fontCons(0, Font.BOLD, SCALE[3]);
        compCons(aboutLabel, aboutPanel, mgColor, ATSIZE, aboutFont, null);
        JLabel contentLabel = new JLabel(Constant.ACTEXT);
        Font contentFont = Constant.fontCons(0, Font.PLAIN, SCALE[2]);
        compCons(contentLabel, aboutPanel, mgColor, ACSIZE, contentFont, null);
        JPanel okPanel = new JPanel();
        compCons(okPanel, aboutPanel, bgColor, AOSIZE, null);
        JButton okButton = new JButton(Constant.AOBTEXT);
        Font okFont = Constant.fontCons(0, Font.PLAIN, SCALE[1]);
        compCons(okButton, okPanel, mgColor, fgColor, AOBSIZE, okFont);
        /* ok */
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPane.remove(aboutPanel);
                setCompEnabled(rootPanel, true);
                panelStack[1] = null;
            }
        });
        mainPane.add(aboutPanel, JLayeredPane.MODAL_LAYER, BoxLayout.X_AXIS);
        isInited |= 1 << 3;
        panelStack[1] = aboutPanel;
    }

    /**
     * Displays <code>exitPanel</code>, while disabling <code>rootPanel</code>. When
     * <code>exitPanel</code> is closed, <code>rootPanel</code> will be enabled
     * again.
     *
     * @param rootPanel the panel that is becoming covered and is to be disabled
     */
    private static void exitUI(JPanel rootPanel) {
        if ((isInited & 1 << 4) >> 4 == 1) {
            exitPanel.removeAll();
        }
        setCompEnabled(rootPanel, false);
        compCons(exitPanel, bgColor, mgColor, ESIZE);
        exitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel exitLabel = new JLabel(Constant.ETTEXT);
        Font exitFont = Constant.fontCons(0, Font.BOLD, SCALE[3]);
        compCons(exitLabel, exitPanel, mgColor, ETSIZE, exitFont, null);
        JLabel contentLabel = new JLabel(Constant.ECTEXT);
        Font contentFont = Constant.fontCons(0, Font.PLAIN, SCALE[2]);
        compCons(contentLabel, exitPanel, mgColor, ECSIZE, contentFont, null);
        JLabel saveLabel = new JLabel(); /* add save records here */
        Font saveFont = Constant.fontCons(0, Font.BOLD, SCALE[1]);
        compCons(saveLabel, exitPanel, mgColor, ESSIZE, saveFont, null);
        JPanel okPanel = new JPanel();
        compCons(okPanel, exitPanel, bgColor, EOSIZE, BorderLayout.SOUTH);
        String[] okText = Constant.EOBTEXT;
        for (int i = 0; i < okText.length; i++) {
            JButton okButton = new JButton(okText[i]);
            Font okFont = Constant.fontCons(0, Font.PLAIN, SCALE[1]);
            compCons(okButton, okPanel, mgColor, fgColor, EOBSIZE, okFont);
            switch (i) {
                case 0: /* yes button */
                    okButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            /* save the match */
                            System.exit(0);
                        }
                    });
                    break;
                case 1: /* no button */
                    okButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            /* do not save the match */
                            System.exit(0);
                        }
                    });
                    break;
                case 2: /* cancel button */
                    okButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            mainPane.remove(exitPanel);
                            setCompEnabled(rootPanel, true);
                            panelStack[3] = null;
                            panelStackRepaint();
                        }
                    });
                    break;
                default:
                    break;
            }
        }
        mainPane.add(exitPanel, JLayeredPane.DRAG_LAYER, BoxLayout.X_AXIS);
        isInited |= 1 << 4;
        panelStack[3] = exitPanel;
    }

    /**
     * Displays <code>warningPanel</code>, while disabling <code>rootPanel</code>.
     * When <code>warningPanel</code> is closed, <code>rootPanel</code> will be
     * enabled again.
     *
     * @param rootPanel the panel that is becoming covered and is to be disabled
     */
    private static void warningUI(JPanel rootPanel) {
        if ((isInited & 1 << 5) >> 5 == 1) {
            warningPanel.removeAll();
        }
        compCons(warningPanel, bgColor, mgColor, WSIZE);
        warningPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel warningLabel = new JLabel(Constant.WTTEXT);
        Font warningFont = Constant.fontCons(0, Font.BOLD, SCALE[3]);
        compCons(warningLabel, warningPanel, mgColor, WTSIZE, warningFont, null);
        JLabel contentLabel = new JLabel(Constant.WCTEXT);
        Font contentFont = Constant.fontCons(0, Font.PLAIN, SCALE[2]);
        compCons(contentLabel, warningPanel, mgColor, WCSIZE, contentFont, null);
        JPanel okPanel = new JPanel();
        compCons(okPanel, warningPanel, bgColor, WOSIZE, BorderLayout.SOUTH);
        String[] okText = Constant.WOBTEXT;
        for (int i = 0; i < okText.length; i++) {
            JButton okButton = new JButton(okText[i]);
            Font okFont = Constant.fontCons(0, Font.PLAIN, SCALE[1]);
            compCons(okButton, okPanel, mgColor, fgColor, WOBSIZE, okFont);
            switch (i) {
                case 0: /* yes button */
                    okButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            Variable.modify(currentPlayers, currentChess, currentDifficulty);
                            mainFrame.dispose();
                            KnightGobang6.newMatch();
                        }
                    });
                    break;
                case 1: /* no button */
                    okButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            mainPane.remove(warningPanel);
                            setCompEnabled(rootPanel, true);
                            panelStack[2] = null;
                            mainPane.remove(rootPanel);
                            setCompEnabled(mainPane, true);
                            panelStack[1] = null;
                            currentPlayers = players;
                            currentChess = chess;
                            currentDifficulty = difficulty;
                        }
                    });
                    break;
                case 2: /* cancel button */
                    okButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            mainPane.remove(warningPanel);
                            setCompEnabled(rootPanel, true);
                            panelStack[2] = null;
                            panelStackRepaint();
                        }
                    });
                    break;
                default:
                    break;
            }
        }
        mainPane.add(warningPanel, JLayeredPane.POPUP_LAYER, BoxLayout.X_AXIS);
        isInited |= 1 << 5;
        panelStack[2] = warningPanel;
    }

    /**
     * Constructs a major <code>JPanel</code> with appointed backgroung color and
     * dimension.
     * <p>
     * Attention: this method do not contain an <code>add()</code> step.
     *
     * @param sub the panel to be constructed
     * @param bgc the background color of the panel
     */
    private static void compCons(JPanel sub, Color bgc) {
        sub.setBackground(bgc);
        sub.setBounds(getBounds(mainFrame, null));
    }

    /**
     * Constructs a minor <code>JPanel</code> with appointed backgroung color,
     * lineborder's color and dimension.
     *
     * @param sub the panel to be constructed
     * @param bgc the background color of the panel
     * @param fgc the color of the panel's lineborder
     * @param dim the dimension of the panel
     */
    private static void compCons(JPanel sub, Color bgc, Color fgc, Dimension dim) {
        sub.setBackground(bgc);
        sub.setBorder(new LineBorder(fgc, 2));
        sub.setBounds(getBounds(mainPane, dim));
        mainPane.add(sub, JLayeredPane.MODAL_LAYER);
    }

    /**
     * Constructs an affiliated <code>JPanel</code> with appointed backgroung color,
     * dimension and border layout's type.
     * <p>
     * To construct without a border layout, <code>key</code> should be set to
     * <code>null</code>.
     * <p>
     * This method is available for <code>Chessboard</code> with <code>bgc</code>'s
     * value being <code>null</code>.
     *
     * @param sub the panel to be constructed
     * @param sup the panel to be received
     * @param bgc the background color of the panel
     * @param dim the dimension of the panel
     * @param key the border layout's type of the panel
     */
    private static void compCons(JPanel sub, JPanel sup, Color bgc, Dimension dim, String key) {
        if (!(sub instanceof Chessboard)) {
            sub.setBackground(bgc);
        }
        sub.setPreferredSize(dim);
        if (key == null) {
            sup.add(sub);
        } else {
            sup.add(sub, key);
        }
    }

    /**
     * Constructs a <code>JLabel</code> with appointed text color, dimension, text
     * font and border layout's type.
     * <p>
     * To construct a non-text <code>JLabel</code>, <code>fgc</code> and
     * <code>fnt</code> should be set to <code>null</code>.
     * <p>
     * To construct without a border layout, <code>key</code> should be set to
     * <code>null</code>.
     * <p>
     * For a text-contained <code>JLabel</code>, the text is set to be horizontal
     * and vertical centeral.
     *
     * @param sub the label to be constructed
     * @param sup the panel to be received
     * @param fgc the color of the label's text
     * @param dim the dimension of the label
     * @param fnt the font of the label's text
     * @param key the border layout's type of the label
     */
    private static void compCons(JLabel sub, JPanel sup, Color fgc, Dimension dim, Font fnt, String key) {
        boolean flag = false;
        sub.setPreferredSize(dim);
        if (!(fgc == null)) {
            sub.setForeground(fgc);
            flag = true;
        }
        if (!(fnt == null)) {
            sub.setFont(fnt);
            flag = true;
        }
        if (key == null) {
            sup.add(sub);
        } else {
            sup.add(sub, key);
        }
        if (flag) {
            sub.setHorizontalAlignment(JLabel.CENTER);
            sub.setVerticalAlignment(JLabel.CENTER);
        }
    }

    /**
     * Constructs a <code>JButton</code> with appointed backgroung color, text
     * color, dimension, text font and border layout's type.
     *
     * @param sub the button to be constructed
     * @param sup the panel to be received
     * @param bgc the background color of the button
     * @param fgc the color of the button's text
     * @param dim the dimension of the button
     * @param fnt the font of the button's text
     */
    private static void compCons(JButton sub, JPanel sup, Color bgc, Color fgc, Dimension dim, Font fnt) {
        sub.setPreferredSize(dim);
        sub.setBackground(bgc);
        sub.setForeground(fgc);
        sub.setFont(fnt);
        sup.add(sub);
        sub.setFocusPainted(false);
    }

    /**
     * Constructs a <code>JRadioButton</code> and adds it to appointed
     * <code>ButtonGroup</code>.
     *
     * @param sub the button to be constructed
     * @param sup the panel to be recieved
     * @param grp the button group to be received
     * @param bgc the background color of the button
     * @param fgc the color of the button's text
     * @param dim the dimension of the button
     * @param fnt the font of the button's text
     */
    private static void compCons(JRadioButton sub, JPanel sup, ButtonGroup grp, Color bgc, Color fgc, Dimension dim,
            Font fnt) {
        sub.setPreferredSize(dim);
        sub.setBackground(bgc);
        sub.setForeground(fgc);
        sub.setFont(fnt);
        grp.add(sub);
        sup.add(sub);
        sub.setFocusPainted(false);
        sub.setHorizontalAlignment(JRadioButton.CENTER);
        sub.setVerticalAlignment(JRadioButton.NORTH);
    }

    /**
     * Constructs a <code>JSlider</code>.
     *
     * @param sub the slider to be constructed
     * @param sup the panel to be received
     * @param bgc the background color of the slider
     * @param fgc the foreground color of the slider
     * @param dim the dimension of the slider
     * @param fnt the font of the slider
     */
    private static void compCons(JSlider sub, JPanel sup, Color bgc, Color fgc, Dimension dim, Font fnt) {
        sub.setPreferredSize(dim);
        sub.setBackground(bgc);
        sub.setForeground(fgc);
        sub.setFont(fnt);
        sup.add(sub);
    }

    /**
     * Returns the bounds of the component according to its root component and its
     * dimension.
     *
     * @param root the root component
     * @param dims the component's dimension
     * @return the component's final bounds
     */
    private static Rectangle getBounds(Container root, Dimension dims) {
        if (dims == null) {
            return new Rectangle(root.getWidth(), root.getHeight());
        } else {
            int x = root.getWidth() / 2 - dims.width / 2;
            int y = root.getHeight() / 2 - dims.height / 2;
            return new Rectangle(x, y, dims.width, dims.height);
        }
    }

    /**
     * Sets a component and the affiliated ones in it enabled or disabled, while
     * keeping the ones' enabilities unchanged listed in
     * <code>compEnabKeptList</code>.
     *
     * @param root the root component
     * @param flag the goal enability
     */
    private static void setCompEnabled(Component root, boolean flag) {
        Component[] comp = ((Container) root).getComponents();
        for (Component c : comp) {
            if (c instanceof JPanel) {
                setCompEnabled(c, flag);
            } else {
                c.setEnabled(flag ^ isCompEnabKept(c, flag));
            }
        }
        root.setEnabled(flag ^ isCompEnabKept(root, flag));
        if (root == mainPanel) {
            goListener.setProgress(flag);
        }
    }

    /**
     * Keeps enabilities of specific components that are listed in
     * <code>compEnabKeptList</code>.
     *
     * @param root the checked component
     * @param flag the goal enability
     * @return <code>true</code> if the component's enability should be kept,
     *         <code>false</code> otherwise
     */
    private static boolean isCompEnabKept(Component root, boolean flag) {
        if (root.isEnabled()) {
            return false;
        } else {
            if (!flag) {
                if (!compEnabKeptList.contains(root)) {
                    compEnabKeptList.add(root);
                }
                return false;
            } else {
                if (compEnabKeptList.contains(root)) {
                    compEnabKeptList.remove(root);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    /**
     * Returns current front <code>JPanel</code>'s layer.
     *
     * @return current front <code>JPanel</code>'s layer
     */
    private static int frontLayer() {
        int cps = -1;
        if (panelStack[2] != null) {
            cps = 2;
        } else if (panelStack[1] != null) {
            cps = 1;
        } else if (panelStack[0] != null) {
            cps = 0;
        }
        return cps;
    }

    /**
     * Repaints current showed <code>JPanel</code>s which are listed in
     * <code>panelStack</code>.
     */
    private static void panelStackRepaint() {
        int cps = frontLayer();
        while (cps != -1 && cps != 0) {
            cps--;
            setCompEnabled(panelStack[cps], true);
        }
        while (cps < frontLayer()) {
            panelStack[cps].repaint();
            setCompEnabled(panelStack[cps], false);
            cps++;
        }
        panelStack[cps].repaint();
    }
}
