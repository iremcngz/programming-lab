import java.io.IOException;

public class Zorde extends Teams{
        public String symbol;
        public String pieceType;
        public int row;
        public int columns;
        public boolean active;



        public Zorde (String symbol,String pieceType,int row,int columns,boolean active,int HP,int AP,int maxMove,int counterOfMove){
                super(symbol,pieceType,row,columns,active,HP,AP,maxMove,counterOfMove);
                this.symbol=symbol;
                this.pieceType=pieceType;
                this.row=row;
                this.columns=columns;
                this.active=true;
        }


        public String toString(){
                return this.team;
        }




}
