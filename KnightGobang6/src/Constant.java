import java.awt.Color;
import java.awt.Font;

/**
 * <code>Constant</code> records all the constants in <strong>Knight Gobang
 * 6</strong>.
 *
 * @author Instant
 * @author Ego-Echo
 */
public abstract class Constant {
    /**
     * Records the length of board grid in 3 difficulties.
     */
    public static final int[] LENGTH = { 19, 35, 49 };
    /**
     * Records the alphabet used as coordinate symbol.
     */
    public static final String ALPHABET = "ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghijkmnopqrstuvwxy";
    /**
     * Records the chess' size of the chessboard in 3 difficulties.
     */
    public static final int[] CHESSSIZE = { 20, 11, 7 };
    /**
     * Records the pixal chessboard's width and height.
     */
    public static final int BOARDWIDTH = 900;
    /**
     * Records the pixal scales of the texts.
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
    public static final int[] SCALE = { 12, 16, 18, 24, 32, 36 };

    /* */
    /* */
    /* ------------ base - color ------------ */
    /* */
    /* */

    /**
     * Records the color of the chess field.
     */
    public static final Color CHESSFIELDCOLOR = new Color(63, 255, 63);

    /* */
    /* */
    /* ------------ color - font ------------ */
    /* */
    /* */

    /**
     * Records the text <code>"等线"</code>, <code>"华文细黑"</code> for font construct.
     */
    public static String[] FONTNAME = { "等线", "华文细黑" };

    /**
     * Construct a <code>Font</code> with specific font name, type and size.
     *
     * @param k the order of the font name in <code>Constant.FONTNAME</code>
     * @param t the type of the font, <code>0</code> for <code>Font.PLAIN</code>,
     *          <code>1</code> for <code>Font.BOLD</code>, <code>2</code> for
     *          <code>Font.ITALIC</code>
     * @param s the size of the font
     * @return the constructed font
     * @see Constant#FONTNAME
     */
    public static Font fontCons(int k, int t, int s) {
        return new Font(FONTNAME[k], t, s);
    }

    /* */
    /* */
    /* ------------ font - string ------------ */
    /* */
    /* */

    /**
     * Records the title of <code>mainFrame</code>.
     * <p>
     * <code>mainFrame</code>
     */
    public static final String MAINTITLE = "骑士六子棋";
    /**
     * Records the content of <code>mainLabel</code> in <code>mainPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>mainPanel</code> -
     * <code>toolPanel</code> - <code>mainLabel</code>
     */
    public static final String MTTTEXT = "<html>骑士六子棋<br/>Knight Gobang 6</html>";
    /**
     * Records each title of <code>toolButton</code> in <code>mainPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>mainPanel</code> -
     * <code>toolPanel</code> - <code>toolButton</code>
     */
    public static final String[] MTBTEXT = { "开始游戏", "游戏设置", "游戏说明", "显示落子顺序", "显示可行区域", "悔棋", "撤销悔棋", "认输", "关于",
            "退出" };
    /**
     * Records the title of <code>toolButton</code> in <code>mainPanel</code>. This
     * botton's name is <code>Start</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>mainPanel</code> -
     * <code>toolPanel</code> - <code>toolButton</code>
     */
    public static final String[] MTBTEXT_START = { "开始游戏", "重新开始" };
    /**
     * Records the title of <code>toolButton</code> in <code>mainPanel</code>. This
     * botton's name is <code>Order</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>mainPanel</code> -
     * <code>toolPanel</code> - <code>toolButton</code>
     */
    public static final String[] MTBTEXT_ORDER = { "显示落子顺序", "隐藏落子顺序" };
    /**
     * Records the title of <code>toolButton</code> in <code>mainPanel</code>. This
     * botton's name is <code>Field</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>mainPanel</code> -
     * <code>toolPanel</code> - <code>toolButton</code>
     */
    public static final String[] MTBTEXT_FIELD = { "显示可行区域", "隐藏可行区域" };
    /**
     * Records the title of <code>toolButton</code> in <code>mainPanel</code>. This
     * botton's name is <code>Retract</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>mainPanel</code> -
     * <code>toolPanel</code> - <code>toolButton</code>
     */
    public static final String[] MTBTEXT_RETRACT = { "悔棋", "悔棋 | 当前剩余：" };
    /**
     * Records the content of <code>declareLabel</code> in <code>mainPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>mainPanel</code> -
     * <code>toolPanel</code> - <code>declareLabel</code>
     */
    public static final String MTDTEXT = "<html>----------------------<br/>Bilibili @墨殇浅辰Instant<br/>GitHub @Ego-Echo<br/>----------------------";
    /**
     * Records the content of <code>settingsLabel</code> in
     * <code>settingsPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code> -
     * <code>settingsLabel</code>
     */
    public static final String STTEXT = "骑士六子棋 - 游戏设置";
    /**
     * Records each title of <code>settingLabel</code> in
     * <code>settingsPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code> -
     * <code>settingPanel</code> - <code>settingLabel</code>
     */
    public static final String[] SSLTEXT = { "请选择玩家类型", "请选择你的执棋", "请选择对局难度" };
    /**
     * Records each title of <code>settingButton</code> in
     * <code>settingsPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code> -
     * <code>settingPanel</code> - <code>settingButton</code>
     */
    public static final String[][] SSOTEXT = { { "机对机", "人对机", "人对人" }, { "黑", "白" }, { "普通", "困难", "地狱" } };
    /**
     * Records each title of <code>musicLabel</code> in <code>settingsPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code> -
     * <code>musicPanel</code> - <code>musicLabel</code>
     */
    public static final String[] SMLTEXT = { "音乐音量", "音效音量" };
    /**
     * Records each title of <code>okButton</code> in <code>settingsPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>settingsPanel</code> -
     * <code>okPanel</code> - <code>okButton</code>
     */
    public static final String[] SOBTEXT = { "确定", "取消" };
    /**
     * Records the content of <code>captionLabel</code> in
     * <code>captionPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>captionPanel</code> -
     * <code>captionLabel</code>
     */
    public static final String CTTEXT = "骑士六子棋 - 游戏说明";
    /**
     * Records the content of <code>contentLabel</code> in
     * <code>captionPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>captionPanel</code> -
     * <code>contentLabel</code>
     */
    public static final String CCTEXT = "<html>游戏规则如下：<br/>1. 六子连线取胜；<br/>2. 从第二子开始，每颗子均需与任一已有落子相隔国际象棋中马的一步。</html>";
    /**
     * Records the content of <code>okButton</code> in <code>captionPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>captionPanel</code> -
     * <code>okPanel</code> - <code>okButton</code>
     */
    public static final String COBTEXT = "确定";
    /**
     * Records the content of <code>aboutLabel</code> in <code>aboutPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>aboutPanel</code> -
     * <code>aboutLabel</code>
     */
    public static final String ATTEXT = "骑士六子棋 - 关于";
    /**
     * Records the content of <code>contentLabel</code> in <code>aboutPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>aboutPanel</code> -
     * <code>contentLabel</code>
     */
    public static final String ACTEXT = "<html><b>游戏制作</b>　Instant | Ego-Echo<br/><b>游戏设计</b>　Instant<br/><b>程序编写</b>　Ego-Echo<br/><b>美术设计</b>　Euphony<br/><b>音效设计</b>　TheY<br/><b>测试人员</b>　Instant | 犹格大泡泡</html>";
    /**
     * Records the content of <code>okButton</code> in <code>aboutPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>aboutPanel</code> -
     * <code>okPanel</code> - <code>okButton</code>
     */
    public static final String AOBTEXT = "确定";
    /**
     * Records the content of <code>exitLabel</code> in <code>exitPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>exitPanel</code> -
     * <code>exitLabel</code>
     */
    public static final String ETTEXT = "骑士六子棋 - 退出";
    /**
     * Records the content of <code>contentLabel</code> in <code>exitPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>exitPanel</code> -
     * <code>contentLabel</code>
     */
    public static final String ECTEXT = "<html>尚未保存当前进度，你确认要退出吗？</html>";
    /**
     * Records the content of <code>okButton</code> in <code>exitPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>exitPanel</code> -
     * <code>okPanel</code> - <code>okButton</code>
     */
    public static final String[] EOBTEXT = { "保存并退出", "直接退出", "取消" };
    /**
     * Records the content of <code>warningLabel</code> in
     * <code>warningPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>warningPanel</code> -
     * <code>warningLabel</code>
     */
    public static final String WTTEXT = "骑士六子棋 - 注意";
    /**
     * Records the content of <code>contentLabel</code> in
     * <code>warningPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>warningPanel</code> -
     * <code>contentLabel</code>
     */
    public static final String WCTEXT = "<html><br/>当前棋局正在进行中，确认修改将中断棋局并<br/>清空棋盘。你确认要继续吗？<br/><br/><br/></html>";
    /**
     * Records the content of <code>okButton</code> in <code>warningPanel</code>.
     * <p>
     * <code>mainFrame</code> - <code>mainPane</code> - <code>warningPanel</code> -
     * <code>okPanel</code> - <code>okButton</code>
     */
    public static final String[] WOBTEXT = { "确认修改", "取消修改", "取消" };
    /**
     * Records the texts of the notification when a match comes to victory, only
     * used in PVE mode.
     */
    public static final String[] VICTEXT = { "恭喜你，你赢了！", "赢了诶，好耶！", "挑战成功，大侠好策略~", "势如破竹，攻无不克！" };
    /**
     * Records the texts of the notification when a match comes to defeated, only
     * used in PVE mode.
     */
    public static final String[] DEFTEXT = { "很抱歉，你输了！", "输了诶，坏耶！", "一着不慎，满盘皆输咯~", "胜败乃兵家常事，大侠请重新来过！" };
    /**
     * Records the texts of the notification when a match comes to draw, only used
     * in PVE mode.
     */
    public static final String[] DRWTEXT = { "还不错，是平局！", "平局诶，巧耶！", "棋逢对手，势均力敌呀~", "稍事休息，下次再加油呀~" };
    /**
     * Records the texts of the notification when a match comes to the end, only
     * used in PVP and EVE mode.
     */
    public static final String[] ENDTEXT = { "白方认输！", "黑方认输！", "意外终止！", "黑方获胜！", "白方获胜！", "双方平局！" };
    /**
     * Records the texts of the notification when a match comes to the end.
     */
    public static final String[] RECTEXT = { "Time: ", "Info: ", "Players: ", "Chess: ", "Difficulty: ", "Moves: ",
            "Final: " };
    /**
     * Records the texts of the final state's notification in <code>History</code>
     * and <code>RobotWatcher</code>.
     */
    public static final String[] RECTEXT_FINAL = { "White admitted defeat.", "Black admitted defeat.", "Abend.",
            "Black gained victory.", "White gained victory.", "Ended as a draw match." };
}
