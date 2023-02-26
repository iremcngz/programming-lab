import java.util.ArrayList;

public class Human extends Calliance{
    public int HP;
    public int AP;
    public int maxMove;
    public String team;
    public int counterOfMove;

    public Human(String symbol, String pieceType, int row, int columns, boolean active,int HP,int AP,int maxMove,int counterOfMove) {
        super(symbol, pieceType, row, columns, active,HP,AP,maxMove,counterOfMove);
        this.HP=Constants.humanHP;
        this.AP=Constants.humanAP;
        this.maxMove=Constants.humanMaxMove;
        this.team="Calliance";
        this.counterOfMove=0;

    }

    public String toString(){
        return this.symbol;
    }
}
