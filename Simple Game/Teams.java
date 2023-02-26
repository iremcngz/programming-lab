import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

public class Teams {
    public String symbol;
    public String pieceType;
    public int row;
    public int columns;
    public boolean active;
    public String team;
    public int AP;
    public int HP;
    public int maxMove;
    public int counterOfMove;


    public Teams (String symbol,String pieceType,int row,int columns,boolean active,int HP,int AP,int maxMove,int counterOfMove){
        this.symbol=symbol;
        this.pieceType=pieceType;
        this.row=row;
        this.columns=columns;
        this.active=true;
        this.HP=HP;
        this.AP=AP;
        this.maxMove=maxMove;
        this.counterOfMove=counterOfMove;
        switch(symbol.substring(0,1)){
            case"E":
            case"H":
            case"D":
                this.team="Calliance";
                break;
            case"G":
            case"T":
            case"O":
                this.team="Zorde";
                break;
        }
    }


    public static boolean isValid(int row, int column, int boardSize) {
        return row >= 0 && row <= boardSize-1 && column >= 0 && column <= boardSize-1;
    }
    public void writeProperty(ArrayList<Teams> pieces) throws IOException {
        SortedMap<String, String> write = new TreeMap<>();
        for (Object piece : pieces) {
            String className = piece.getClass().toString().split(" ")[1];

            if (className.equals("Elf")) {
                Elf elf = (Elf) piece;
                if (elf.active) {
                    write.put(elf.symbol, "\t" + elf.HP + "\t(" + Constants.elfHP + ")\n");
                }
            }
            if (className.equals("Ork")) {
                Ork ork = (Ork) piece;
                if (ork.active) {
                    write.put(ork.symbol, "\t" + ork.HP + "\t(" + Constants.orkHP + ")\n");
                }
            }
            if (className.equals("Troll")) {
                Troll troll = (Troll) piece;
                if (troll.active) {
                    write.put(troll.symbol, "\t" + troll.HP + "\t(" + Constants.trollHP + ")\n");
                }
            }
            if (className.equals("Goblin")) {
                Goblin g = (Goblin) piece;
                if (g.active) {
                    write.put(g.symbol, "\t" + g.HP + "\t(" + Constants.goblinHP + ")\n");
                }
            }
            if (className.equals("Human")) {
                Human h = (Human) piece;
                if (h.active) {
                    write.put(h.symbol, "\t" + h.HP + "\t(" + Constants.humanHP + ")\n");
                }
            }
            if (className.equals("Dwarf")) {
                Dwarf d = (Dwarf) piece;
                if (d.active) {
                    write.put(d.symbol, "\t" + d.HP + "\t(" + Constants.dwarfHp + ")\n");

                }
            }

        }
    }

    public void writeBoard(ArrayList<Teams> pieces,int sizeOfTable, BufferedWriter writer) throws IOException {
        ArrayList<ArrayList<String>> block = new ArrayList<ArrayList<String>>(sizeOfTable);
        for (int i = 0; i < sizeOfTable; i++) {
            block.add(new ArrayList<String>(sizeOfTable));
            for (int j = 0; j < sizeOfTable; j++){
                block.get(i).add("  ");
            }
        }

        for(Teams piece:pieces){
            String className=piece.getClass().toString().split(" ")[1];
            if(className.equals("Elf")){
                Elf elf=(Elf)piece;
                if(elf.active){
                    block.get(elf.row).remove(elf.columns);
                    block.get(elf.row).add(elf.columns,elf.symbol);
                }
            }if(className.equals("Ork")){
                Ork ork=(Ork)piece;
                if(ork.active){
                    block.get(ork.row).remove(ork.columns);
                    block.get(ork.row).add(ork.columns,ork.symbol);
                }
            }if(className.equals("Troll")){
                Troll troll=(Troll)piece;
                if(troll.active){
                    block.get(troll.row).remove(troll.columns);
                    block.get(troll.row).add(troll.columns,troll.symbol);
                }
            }if(className.equals("Goblin")){
                Goblin g=(Goblin)piece;
                if(g.active){
                    block.get(g.row).remove(g.columns);
                    block.get(g.row).add(g.columns,g.symbol);
                }
            }if(className.equals("Human")){
                Human h=(Human)piece;
                if(h.active){
                    block.get(h.row).remove(h.columns);
                    block.get(h.row).add(h.columns,h.symbol);
                }
            }if(className.equals("Dwarf")){
                Dwarf d=(Dwarf)piece;
                if(d.active){
                    block.get(d.row).remove(d.columns);
                    block.get(d.row).add(d.columns,d.symbol);
                }
            }
        }
        writer.write("**************"+"\n");
        String listString = "";
        for (ArrayList<String> s : block) {
            String row = "*";
            for (String c : s) {
                row +=c;
            }
            listString+=row+"*\n";
        }
        writer.write(listString);
        writer.write("**************\n\n");

    }

    public Teams findPieceFromSymbol(String symbol,ArrayList<Teams> pieces){
        Teams pi=null;
        for(Teams piece:pieces){
            if(piece.symbol.equals(symbol)){
                pi= piece;
            }
        }
        return pi;
    }

    public String toString(){
        return this.pieceType;
    }

}
