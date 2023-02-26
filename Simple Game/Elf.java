import java.util.ArrayList;

public class Elf extends Calliance {
    public int HP;
    public int AP;
    public int maxMove;
    public String team;
    public int elfRangedAP;
    public int counterOfMove;



    public Elf(String symbol, String pieceType, int row, int columns, boolean active,int HP,int AP,int maxMove,int counterOfMove) {
        super(symbol, pieceType, row, columns, active,HP,AP,maxMove,counterOfMove);
        this.HP=Constants.elfHP;
        this.AP=Constants.elfAP;
        this.maxMove=Constants.elfMaxMove;
        this.team="Zorde";
        this.elfRangedAP=Constants.elfRangedAP;
        this.counterOfMove=0;

    }

    public String toString(){
        return this.symbol;
    }
}
