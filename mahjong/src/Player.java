
// パッケージのインポート
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;
import java.awt.GridLayout;

// プレイヤーを作成するためのクラス
public class Player
{
    // 手牌を表す変数
    static private ArrayList<Tile> tileList;
    public ArrayList<Tile> getTileList(){ return tileList; }

    // 手牌の表示に用いるインスタンス
    static JPanel tilePanel;

    // 牌をクリックした時のマウスイベントの初期設定をする
    static MouseClickCheck mouseClass;

    // 初期設定を行う
    public Player(Table table)
    {
        // 手牌を表す変数
        tileList = new ArrayList<Tile>(14);

        // 手牌を表示するためのパネル
        int panelX = ( table.LENGTH / 3 ) - 3 * Tile.WIDTH;
        int panelY = table.LENGTH * 3 / 4;
        tilePanel = new JPanel();
        tilePanel.setLayout( new GridLayout(1,14) );
        tilePanel.setBounds(panelX,panelY,Tile.WIDTH*14,Tile.HEIGHT);
        table.tablePanel.add(tilePanel);
    }

    // プレイヤーの方の準備を行うメソッド
    void preparation(Table table)
    {
        // 手牌を13枚だけ作る
        for(int i = 0 ; i < 13 ; i++ ){
            tileList.add( table.getTile() );
        }

        // 理牌する　
        Collections.sort(tileList);

        // マウスイベントを発生させるための種を用意する
        // thisの部分は後で4人分の情報に置き換える
        mouseClass = new MouseClickCheck(table,this);
    }

    // 牌を取るメソッド
    void takeTile(Table table)
    {
        // 壁牌から牌を取る
        tileList.add( table.getTile() );

        // 牌を表示する
        tileDisplay(table);   
    }

    // 手牌の表示をするメソッド
    static void tileDisplay(Table table)
    {
        // 変数宣言
        JLabel tileLabel;
        ImageIcon tileImage;

        // 予め表示された画像を削除する
        tilePanel.removeAll();

        // パネルに手牌を貼り付ける
        for( Tile tileName : tileList ){
            tileImage = new ImageIcon( "images/" + tileName.name() + ".gif" );
            tileLabel = new JLabel(tileImage);
            tileLabel.addMouseListener(mouseClass);
            tilePanel.add(tileLabel);
        }

        // 手牌を再描写する
        table.tablePanel.revalidate();
        table.tablePanel.repaint();
    }

    // ツモ上がりできるか判定するメソッド
    boolean winJudge(Table table)
    {
        // 各牌の数を数える
        TreeMap<Tile,Integer> tileMap = new TreeMap<>();
        for( Tile tileName : tileList ){
            if( tileMap.containsKey(tileName) ){
                tileMap.put(tileName ,tileMap.get(tileName)+1 );
            }
            else{
                tileMap.put(tileName,1);
            }
        }

        // 上がれる形かを判定する
        boolean judge = false;
        for( Tile tileName : tileMap.keySet() ){

            // 頭にできるかを確認
            if( tileMap.get(tileName) >= 2 ){

                // 一旦、頭の分だけ抜く
                tileMap.put(tileName ,tileMap.get(tileName) - 2 );

                // 面子ができるか確認する
                if( faceJudge(tileMap) ){
                    tileList.addAll( Arrays.asList(tileName,tileName) );
                    tileDisplay(table);
                    judge = true;
                    break;
                }
                else{
                    tileMap.put(tileName ,tileMap.get(tileName) + 2 );
                }
            }
        }
        return judge;
    }

    // 面子ができるか判定する関数
    static boolean faceJudge( TreeMap<Tile,Integer> tileMap )
    {
        // 面子の形になっているか簡単に確認する
        int tileNum;
        int[] tileCount = new int[Tile.ALLTYPE];
        for( Tile tileName : tileMap.keySet() ){
            tileNum = tileName.getByNum() * tileMap.get(tileName);
            tileCount[tileName.getByType()] += tileNum;
        }
        for( int item : tileCount ){
            if( item % 3 != 0 ) return false;
        }

        // 面子があるか確認するための変数
        TreeMap<Tile,Integer> copyMap = new TreeMap<>(tileMap);
        ArrayList<Tile> resultList = new ArrayList<>(14);
        Iterator<Tile> mapIterator;
        Tile leftTile,centerTile,rightTile;

        // 面子があるか判定を行う
        boolean judge = true;
        while(judge){

            // マップを順に見ていくために必要
            mapIterator = copyMap.keySet().iterator();

            // 左側の牌を取得
            if( copyMap.size() > 0 ){
                leftTile = mapIterator.next();
            }
            else{
                tileList.clear();
                tileList.addAll(resultList);
                break;
            }

            // 左側の枚数ごとに処理を変更する
            switch( copyMap.get(leftTile) ){

                // 左から見て刻子の形なら、それで確定する
                case 3:
                    resultList.addAll( Arrays.asList(leftTile,leftTile,leftTile) );
                
                // 牌が二つ以下あり、どれも順子になっていれば残りが0となり適用される
                case 0:
                    copyMap.remove(leftTile);
                    continue;

                // 牌が二つ以下ある場合
                default:
                    
                    // 右隣二つの牌を取得する
                    centerTile = mapIterator.next();
                    rightTile = mapIterator.next();

                    // 左側の牌が残っているのに、右隣の牌がある場合
                    if( 
                        ( copyMap.get(centerTile) == 0 ) ||
                        ( copyMap.get(rightTile) == 0 )
                    ){
                        judge = false;
                        continue;
                    }

                    // 牌が連続している場合
                    else if(
                        ( centerTile.getId() == leftTile.getId() + 1 ) &&
                        ( centerTile.getId() == rightTile.getId() - 1 )
                    ){
                        // 連続している牌を判定から除外する
                        copyMap.put(leftTile ,copyMap.get(leftTile) - 1  );
                        copyMap.put(centerTile ,copyMap.get(centerTile) - 1 );
                        copyMap.put(rightTile ,copyMap.get(rightTile) - 1 );

                        // 除外した牌を後で出力するリストに格納する
                        resultList.addAll( Arrays.asList(leftTile,centerTile,rightTile) );
                    }

                    // 連続していなかった場合
                    else{
                        judge = false;
                        continue;
                    }
            }

        }
        return judge;
    }

    
}

