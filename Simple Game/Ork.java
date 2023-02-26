import java.util.ArrayList;

public class Ork extends Zorde{
    public int AP;
    public int HP;
    public int maxMove;
    public String team;
    public int counterOfMove;


    public Ork(String symbol, String pieceType, int row, int columns, boolean active,int HP,int AP,int maxMove,int counterOfMove) {
        super(symbol, pieceType, row, columns, active,HP,AP,maxMove,counterOfMove);
        this.AP=Constants.orkAP;
        this.HP=Constants.orkHP;
        this.maxMove=Constants.orkMaxMove;
        this.team="Zorde";
        this.counterOfMove=0;

    }

    public String toString(){
        return this.symbol;
    }



}
