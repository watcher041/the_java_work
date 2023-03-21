
// 使用するパッケージを指定
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;

// プログラムの中心となるクラス
class Main
{
    // メイン関数
    public static void main(String args[])
    {
        // テーブルの作成
        Table table = new Table();

        // プレイヤーを作成
        Player player = new Player(table);

        // 壁牌を作成
        table.preparation();

        // プレイヤーの方で準備をする
        player.preparation(table);

        // 親プレイヤーが牌をとる
        player.takeTile(table);

        // これ以降の処理は、マウスイベントによって処理を行う
        // MouseClickCheckクラスを参照
       
    }

}

// マウスがクリックされた際に起動するクラス
class MouseClickCheck implements MouseListener
{
    Table table;
    Player player;

    // マウスイベントを発生させるのに必要なインスタンスを用意する
    MouseClickCheck(Table table,Player player)
    {
        this.table = table;
        this.player = player;
    }

    // クリックされたら、牌を捨てた後にMainクラスにて処理を起動する。
    @Override
    public void mouseClicked(MouseEvent event)
    {
        // 山牌の総数を定数として宣言
        final int dealtNumMax = 136 - 14;

        // 全ての配牌を取った瞬間、流局となる
        if( this.table.getDealtNum() >= dealtNumMax ){

            System.out.println("流局");

            // 手牌をリセットする
            player.getTileList().clear();

            //次のゲームの準備をする

            // 壁牌を作成
            table.preparation();

            // 全てのプレイヤーが準備をする
            player.preparation(table);

            // 親プレイヤーが配牌をとる

        }
        else{

            // ツモ上がりしているか確認する
            if( player.winJudge(table) ) return;

            // イベント情報を辿り、文字列からラベルを識別する番号を取得
            String str = event.getSource().toString();
            str = str.substring( str.indexOf("[,") + 2 );
            str = str.substring( 0 , str.indexOf(",") );

            // クリックしたラベルの番号を取得
            int num = Integer.parseInt(str) / Tile.WIDTH;
            
            // 牌を捨てる　
            player.getTileList().remove(num);

            // 理牌する
            Collections.sort(player.getTileList());

            // 画面を回転させる
            table.rotateNum++;

            // 次のプレイヤーが牌をとる
        }

        // プレイヤーが牌をとる
        player.takeTile(table);
    }

    // マウスイベントを動作させるために、仕方なく入れている
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
}
