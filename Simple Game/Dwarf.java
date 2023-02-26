import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Dwarf extends Calliance{
    public int HP;
    public int AP;
    public int maxMove;
    public String team;
    public int counterOfMove;


    public Dwarf(String symbol, String pieceType, int row, int columns, boolean active,int HP,int AP,int maxMove,int counterOfMove) {
        super(symbol, pieceType, row, columns, active,HP,AP,maxMove,counterOfMove);
        this.HP=Constants.dwarfHp;
        this.AP=Constants.dwarfAP;
        this.maxMove=Constants.dwarfMaxMove;
        this.team="Calliance";
        this.counterOfMove=0;

    }


    public String toString(){
        return this.symbol;
    }



}
