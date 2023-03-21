
// パッケージをインポート
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.border.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;

// 麻雀卓を作成するためのクラス
public class Table 
{
    // 配牌した枚数の合計を表す変数
    private int dealtNum;
    public int getDealtNum(){ return dealtNum; }

    // 壁牌を表す変数
    private ArrayList<Tile> wallList;
    public Tile getTile(){ return wallList.get(dealtNum++); }

    // ゲーム画面を表示するためのインスタンス
    JFrame gameFrame;
    JPanel tablePanel;
    JLabel fieldLabel;

    // テーブルを回転させるための変数
    int rotateNum = 0;

    // フレームの一辺の長さを指定
    final int LENGTH = 700;

    // コンストラクタ（麻雀卓の作成）
    public Table()
    {
        // フレームを作成する（タイトルバーなどの長さも考慮したサイズにする）
        // setPreferを使うことで、考えたサイズになる
        gameFrame = new JFrame("麻雀ゲーム（役満）");
        gameFrame.setPreferredSize(new Dimension(LENGTH, LENGTH));
        gameFrame.pack();
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 麻雀卓を表示するためのパネルを作成し、フレームに貼り付ける
        rotateNum = 0;
        tablePanel = new JPanel(null){
            // この中を有効にすると、テーブルが回転する
            // @Override
            // public void paintComponent(Graphics g) {
            //     super.paintComponent(g);
            //     Graphics2D g2d = (Graphics2D) g;
            //     int w2 = getWidth() / 2;
            //     int h2 = getHeight() / 2;
            //     g2d.rotate( rotateNum * Math.PI / 2, w2, h2);
            // }
        };  /* 引数がないと、勝手に子要素が配置される */
        tablePanel.setBackground(Color.GREEN);
        gameFrame.add(tablePanel);

        // 場風をパネルに貼り付ける（タイトルバーなどの長さを含むため、余計な項がつく）
        int fieldWidth = LENGTH / 3;
        int fieldHeight = LENGTH / 3;
        Font font = new Font(Font.SERIF,Font.BOLD,50);
        Border border = new BevelBorder(BevelBorder.RAISED);
        fieldLabel = new JLabel();
        fieldLabel.setFont(font);
        fieldLabel.setForeground(Color.BLACK);
        fieldLabel.setBounds(fieldWidth,fieldHeight,fieldWidth,fieldHeight);
        fieldLabel.setHorizontalAlignment(JLabel.CENTER);
        fieldLabel.setBorder(border);
        tablePanel.add(fieldLabel);
    }
  
    // 麻雀卓の方で準備をする
    void preparation()
    {   
        // 配牌を数えるために初期化
        dealtNum = 0;

        // 壁牌を表す変数
        wallList = new ArrayList<Tile>(136);

        // 壁牌を作成する
        for( Tile tileName : Tile.values() ){
            for( int i = 0 ; i < 4 ; i++ ){
                wallList.add(tileName);
            }
        }
        Collections.shuffle(wallList);  /* 牌をシャッフルする */

        // 場風の表示を変更する
        fieldLabel.setText("東一局");
    }
    
}
