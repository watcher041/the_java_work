
public enum Tile 
{
    // 萬子
    MANZU1(1),
    MANZU2(2),MANZU3(3),MANZU4(4),MANZU5(5),
    MANZU6(6),MANZU7(7),MANZU8(8),MANZU9(9),

    // 筒子
    PINZU1(11),
    PINZU2(12),PINZU3(13),PINZU4(14),PINZU5(15),
    PINZU6(16),PINZU7(17),PINZU8(18),PINZU9(19),

    // 索子
    SOHZU1(21),
    SOHZU2(22),SOHZU3(23),SOHZU4(24),SOHZU5(25),
    SOHZU6(26),SOHZU7(27),SOHZU8(28),SOHZU9(29),

    // 風牌
    TON(31),
    NAN(41),
    SHA(51),
    PEI(61),

    // 三元牌
    HAKU(71),
    HATU(81),
    CYUN(91);

    // 牌の種類の数を表す定数
    static final int ALLTYPE = 10;

    // 牌の画像のサイズを指定
    static final int WIDTH = 30;
    static final int HEIGHT = 40;

    // 牌に対応づけた番号を表す変数
    private final int id;

    // 牌に番号を割り振る
    private Tile(int id) {
        this.id = id;
    }

    // 番号の取得
    public int getId() {
        return id;
    }

    // 牌の種類を取得
    public int getByType(){
        return ( id / 10 );
    }

    // 牌の番号を取得
    public int getByNum(){
        return ( id % 10 );
    }
}
