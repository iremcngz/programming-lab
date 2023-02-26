import java.util.ArrayList;

public class Goblin extends Zorde{
    public int HP;
    public int AP;
    public int maxMove;
    public String team;
    public int counterOfMove;

    public Goblin(String symbol, String pieceType, int row, int columns, boolean active,int HP,int AP,int maxMove,int counterOfMove) {
        super(symbol, pieceType, row, columns, active,HP,AP,maxMove,counterOfMove);
        this.HP=Constants.goblinHP;
        this.AP=Constants.goblinAP;
        this.maxMove=Constants.goblinMaxMove;
        this.team="Zorde";
        this.counterOfMove=0;

    }

    public String toString(){
        return this.symbol;
    }
}
