import java.io.*;
import java.util.*;

public class Main {
        private String initialsFileName, commandsFileName;
        public String[][] boardArray=null;
        public  ArrayList<Teams> pieces;
        public String winner;
        public Boolean isGameFinish = false;
        public Boolean willContinueD=true;
        public Boolean willContinueO=true;
        public Boolean willContinueE=true;
        public Boolean willContinueT=true;
        public Boolean willContinueH=true;
        public Boolean willContinueG=true;
        BufferedWriter writer;

        Main(String initialsFileName, String commandsFileName) throws java.io.IOException, BoundaryCheckException {
            this.initialsFileName = initialsFileName;
            this.commandsFileName = commandsFileName;
            pieces=getList();
            writer = new BufferedWriter(new FileWriter("output.txt"));
            executeCommands();

        }

        public static void main(String[] args) throws java.io.IOException, BoundaryCheckException {
            String initialsFileName = args.length > 0 ? args[0] : "initials.txt";
            String commandsFileName = args.length > 1 ? args[1] : "commands.txt";
            Main main = new Main(initialsFileName, commandsFileName);

        }


        public void executeCommands() throws java.io.IOException, BoundaryCheckException {

            ArrayList<?> board= (ArrayList<?>) board();
            boardArray= boardArray();

            //writing board
            writeBoard(pieces,sizeOfTable,writer);
            //writing properties
            writeProperty(pieces,writer);
            String a=Arrays.deepToString(boardArray);
            SortedMap<String, String> old=checkProperty(pieces);

            BufferedReader reader;
            String line;
            reader = new BufferedReader(new FileReader(commandsFileName));
            int livingPiece=0;

            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");


                if (!isGameFinish&&(!shouldGamefinish())) {
                    if (words[0].charAt(0) == 'D') {
                        String[] commands = line.substring(3).split(";");
                        try {
                            if (!String.valueOf(commands.length).equals(String.valueOf(Constants.dwarfMaxMove * 2))) {
                                throw new MoveCountCheckException();
                            }
                        }catch(MoveCountCheckException e){
                            writer.write("Error : Move sequence contains wrong number of move steps. Input line ignored.\n\n");
                        }
                        if(!String.valueOf(commands.length).equals(String.valueOf(Constants.dwarfMaxMove * 2)));
                        else {

                            if(willContinueD) {
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[0]),
                                        Integer.parseInt(commands[1]), sizeOfTable, Constants.dwarfMaxMove);

                            }if(willContinueD){
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[2]),
                                        Integer.parseInt(commands[3]), sizeOfTable, Constants.dwarfMaxMove);
                            }

                            checkDie(findPieceFromSymbol(words[0], pieces), pieces);
                            findPieceFromSymbol(words[0], pieces).counterOfMove = 0;

                            if (!Arrays.deepToString(boardArray).equals(a) || (!checkProperty(pieces).equals(old))) {

                                writeBoard(pieces, sizeOfTable, writer);
                                writeProperty(pieces, writer);
                                a = Arrays.deepToString(boardArray);
                                old = checkProperty(pieces);
                            }
                            try {
                                if (!willContinueD) {
                                    throw new BoundaryCheckException();
                                }
                            }catch(BoundaryCheckException e){
                                writer.write("Error : Game board boundaries are exceeded. Input line ignored.\n\n");
                            }
                            willContinueD=true;

                        }

                    } else if (words[0].charAt(0) == 'O') {
                        String[] commands = line.substring(3).split(";");
                        try {
                            if (!String.valueOf(commands.length).equals(String.valueOf(Constants.orkMaxMove * 2))) {
                               throw new MoveCountCheckException();
                            }
                        }catch(MoveCountCheckException e){
                            writer.write("Error : Move sequence contains wrong number of move steps. Input line ignored.\n\n");
                        }
                        if(!String.valueOf(commands.length).equals(String.valueOf(Constants.orkMaxMove * 2))){}
                        else {
                            if(willContinueO) {
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[0]),
                                        Integer.parseInt(commands[1]), sizeOfTable, Constants.orkMaxMove);
                            }

                            checkDie(findPieceFromSymbol(words[0], pieces), pieces);
                            findPieceFromSymbol(words[0], pieces).counterOfMove = 0;
                            if (!Arrays.deepToString(boardArray).equals(a) || (!checkProperty(pieces).equals(old))) {
                                writeBoard(pieces, sizeOfTable, writer);
                                writeProperty(pieces, writer);
                                a = Arrays.deepToString(boardArray);
                            }
                            try {
                                if (!willContinueO) {
                                    throw new BoundaryCheckException();
                                }
                            }catch(BoundaryCheckException e){
                                writer.write("Error : Game board boundaries are exceeded. Input line ignored.\n\n");
                            }
                            willContinueO=true;


                        }

                    } else if ((words[0].charAt(0) == 'E')) {
                        String[] commands = line.substring(3).split(";");
                        try {
                            if (!String.valueOf(commands.length).equals(String.valueOf(Constants.elfMaxMove * 2))) {
                                throw new MoveCountCheckException();
                            }
                        }catch(MoveCountCheckException e){
                            writer.write("Error : Move sequence contains wrong number of move steps. Input line ignored.\n\n");
                        }
                        if(!String.valueOf(commands.length).equals(String.valueOf(Constants.elfMaxMove * 2))){}
                        else {
                            if(willContinueE) {
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[0]),
                                        Integer.parseInt(commands[1]), sizeOfTable, Constants.elfMaxMove);
                            }
                            if(willContinueE) {
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[2]),
                                        Integer.parseInt(commands[3]), sizeOfTable, Constants.elfMaxMove);
                            }if(willContinueE) {
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[4]),
                                        Integer.parseInt(commands[5]), sizeOfTable, Constants.elfMaxMove);
                            }if(willContinueE){
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[6]),
                                        Integer.parseInt(commands[7]), sizeOfTable, Constants.elfMaxMove);
                            }

                            findPieceFromSymbol(words[0], pieces).counterOfMove = 0;
                            checkDie(findPieceFromSymbol(words[0], pieces), pieces);
                            if (!Arrays.deepToString(boardArray).equals(a) || (!checkProperty(pieces).equals(old))) {
                                writeBoard(pieces, sizeOfTable, writer);
                                writeProperty(pieces, writer);
                                a = Arrays.deepToString(boardArray);
                            }
                            try {
                                if (!willContinueE) {
                                    throw new BoundaryCheckException();
                                }
                            }catch(BoundaryCheckException e){
                                writer.write("Error : Game board boundaries are exceeded. Input line ignored.\n\n");
                            }
                            willContinueE=true;


                        }

                    } else if (words[0].charAt(0) == 'T') {
                        //troll
                        String[] commands = line.substring(3).split(";");
                        try {
                            if (!String.valueOf(commands.length).equals(String.valueOf(Constants.trollMaxMove * 2))) {
                                throw new MoveCountCheckException();
                            }
                        }catch(MoveCountCheckException e){
                            writer.write("Error : Move sequence contains wrong number of move steps. Input line ignored.\n\n");
                        }
                        if(!String.valueOf(commands.length).equals(String.valueOf(Constants.trollMaxMove * 2))){}
                        else {
                            if(willContinueT) {
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[0]),
                                        Integer.parseInt(commands[1]), sizeOfTable, Constants.trollMaxMove);
                            }
                            willContinueT=true;
                            checkDie(findPieceFromSymbol(words[0], pieces), pieces);
                            findPieceFromSymbol(words[0], pieces).counterOfMove = 0;
                            if (!Arrays.deepToString(boardArray).equals(a) || (!checkProperty(pieces).equals(old))) {
                                writeBoard(pieces, sizeOfTable, writer);
                                writeProperty(pieces, writer);
                                a = Arrays.deepToString(boardArray);
                            }
                            try {
                                if (!willContinueT) {
                                    throw new BoundaryCheckException();
                                }
                            }catch(BoundaryCheckException e){
                                writer.write("Error : Game board boundaries are exceeded. Input line ignored.\n\n");
                            }
                            willContinueT=true;

                        }

                    } else if (words[0].charAt(0) == 'H') {
                        String[] commands = line.substring(3).split(";");
                        try {
                            if (!String.valueOf(commands.length).equals(String.valueOf(Constants.humanMaxMove * 2))) {
                                throw new MoveCountCheckException();
                            }
                        }catch(MoveCountCheckException e){
                            writer.write("Error : Move sequence contains wrong number of move steps. Input line ignored.\n\n");
                        }
                        if(!String.valueOf(commands.length).equals(String.valueOf(Constants.humanMaxMove * 2))){

                        }
                        else {

                            if(willContinueH) {
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[0]),
                                        Integer.parseInt(commands[1]), sizeOfTable, Constants.humanMaxMove);
                            }
                            if(willContinueH) {
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[2]),
                                        Integer.parseInt(commands[3]), sizeOfTable, Constants.humanMaxMove);
                            }
                            if(willContinueH){
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[4]),
                                        Integer.parseInt(commands[5]), sizeOfTable, Constants.humanMaxMove);
                            }


                            checkDie(findPieceFromSymbol(words[0], pieces), pieces);
                            findPieceFromSymbol(words[0], pieces).counterOfMove = 0;
                            if (!Arrays.deepToString(boardArray).equals(a) || (!checkProperty(pieces).equals(old))) {
                                writeBoard(pieces, sizeOfTable, writer);
                                writeProperty(pieces, writer);
                                a = Arrays.deepToString(boardArray);
                            }

                            try {
                                if (!willContinueH) {
                                   throw new BoundaryCheckException();
                                }
                            }catch(BoundaryCheckException e){
                                writer.write("Error : Game board boundaries are exceeded. Input line ignored.\n\n");
                            }
                            willContinueH=true;

                        }

                    } else if (words[0].charAt(0) == 'G') {
                        String[] commands = line.substring(3).split(";");
                        try {
                            if (!String.valueOf(commands.length).equals(String.valueOf(Constants.goblinMaxMove * 2))) {
                                throw new MoveCountCheckException();
                            }
                        }catch(MoveCountCheckException e){
                            writer.write("Error : Move sequence contains wrong number of move steps. Input line ignored.\n\n");
                        }

                        if(!String.valueOf(commands.length).equals(String.valueOf(Constants.goblinMaxMove * 2))){

                        }
                        else {
                            if(willContinueG) {
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[0]),
                                        Integer.parseInt(commands[1]), sizeOfTable, Constants.goblinMaxMove);

                            }if(willContinueG) {
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[2]),
                                        Integer.parseInt(commands[3]), sizeOfTable, Constants.goblinMaxMove);
                            }if(willContinueG) {
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[4]),
                                        Integer.parseInt(commands[5]), sizeOfTable, Constants.goblinMaxMove);
                            }if(willContinueG){
                                move(findPieceFromSymbol(words[0], pieces), Integer.parseInt(commands[6]),
                                        Integer.parseInt(commands[7]), sizeOfTable, Constants.goblinMaxMove);
                            }
                            willContinueG=true;
                            checkDie(findPieceFromSymbol(words[0], pieces), pieces);
                            findPieceFromSymbol(words[0], pieces).counterOfMove = 0;
                            if (!Arrays.deepToString(boardArray).equals(a) || (!checkProperty(pieces).equals(old))) {
                                writeBoard(pieces, sizeOfTable, writer);
                                writeProperty(pieces, writer);
                                a = Arrays.deepToString(boardArray);
                            }
                            try{
                                if(!willContinueG){
                                    throw new BoundaryCheckException();
                                }
                            }catch(BoundaryCheckException e){
                                writer.write("Error : Game board boundaries are exceeded. Input line ignored.\n\n");
                            }

                            willContinueG=true;

                        }

                    }



                }
            }


            for(Teams piecem:pieces){
                if(piecem.active){
                    livingPiece++;
                }
            }
            if(String.valueOf(livingPiece).equals("1")){
                isGameFinish=true;
                for(Teams piece:pieces){
                    if(piece.active){
                        winner=piece.team;
                    }
                }
            }
            if(isGameFinish){
                writer.write("\nGame Finished\n"+winner+" Wins");
            }



            reader.close();
            writer.close();
        }


    int sizeOfTable;
        public String[][] boardArray() {
            ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>(sizeOfTable);
            String[][] boardArray = new String[sizeOfTable][sizeOfTable];
            for (int row = 0; row < sizeOfTable; row++) {
                for (int col = 0; col < boardArray[row].length; col++) {
                    boardArray[row][col] = "null";
                }
            }
            //////// with toString methods ı used polymorphism!!
            for (Teams piece : pieces) {
                String className = piece.getClass().toString().split(" ")[1];
                if (className.equals("Elf")) {
                    Elf elf = (Elf) piece;
                    if (elf.active) {
                        boardArray[elf.row][elf.columns] = elf.toString();
                    }
                }
                if (className.equals("Ork")) {
                    Ork ork = (Ork) piece;
                    if (ork.active) {

                        boardArray[ork.row][ork.columns] = ork.toString();
                    }
                }
                if (className.equals("Troll")) {
                    Troll troll = (Troll) piece;
                    if (troll.active) {

                        boardArray[troll.row][troll.columns] = troll.toString();
                    }
                }
                if (className.equals("Goblin")) {
                    Goblin g = (Goblin) piece;
                    if (g.active) {

                        boardArray[g.row][g.columns] = g.toString();
                    }
                }
                if (className.equals("Human")) {
                    Human h = (Human) piece;
                    if (h.active) {
                        boardArray[h.row][h.columns] = h.toString();
                    }
                }
                if (className.equals("Dwarf")) {
                    Dwarf d = (Dwarf) piece;
                    if (d.active) {
                        boardArray[d.row][d.columns] = d.toString();
                    }
                }
            }
            return boardArray;
        }

    public ArrayList<Teams> getList() throws IOException {
        // by creating this ArrayList i used polymorphism! there are different type of objects in one ArrayList!!
        BufferedReader initialsTxt = new BufferedReader(new FileReader(initialsFileName));
        ArrayList<Teams> pieces = new ArrayList<>();
        if (initialsTxt.readLine().equals("BOARD")) {
            sizeOfTable = Integer.parseInt(initialsTxt.readLine().split("x")[0]);
        }
        String strLine;
        while ((strLine = initialsTxt.readLine()) != null) {
            String[] line = strLine.split(" ");
            if (line[0].equals("ELF")) {
                Elf elfObj = new Elf(line[1], line[0], Integer.parseInt(line[3]), Integer.parseInt(line[2]), true,
                        Constants.elfHP,Constants.elfAP,Constants.elfMaxMove,0);
                pieces.add(elfObj);
            }
            if (line[0].equals("DWARF")) {
                Dwarf dwarfObj = new Dwarf(line[1], line[0], Integer.parseInt(line[3]), Integer.parseInt(line[2]), true
                ,Constants.dwarfHp,Constants.dwarfAP,Constants.dwarfMaxMove,0);
                pieces.add(dwarfObj);
            }
            if (line[0].equals("HUMAN")) {
                Human humanObj = new Human(line[1], line[0], Integer.parseInt(line[3]), Integer.parseInt(line[2]), true,
                        Constants.humanHP,Constants.humanAP,Constants.humanMaxMove,0);
                pieces.add(humanObj);
            }
            ////////////other team
            if (line[0].equals("GOBLIN")) {
                Goblin goblinObj = new Goblin(line[1], line[0], Integer.parseInt(line[3]), Integer.parseInt(line[2]), true,
                        Constants.goblinHP,Constants.goblinAP,Constants.goblinMaxMove,0);
                pieces.add(goblinObj);
            }
            if (line[0].equals("TROLL")) {
                Troll trollObj = new Troll(line[1], line[0], Integer.parseInt(line[3]), Integer.parseInt(line[2]), true,
                        Constants.trollHP,Constants.trollAP,Constants.trollMaxMove,0);
                pieces.add(trollObj);
            }
            if (line[0].equals("ORK")) {
                Ork orkObj = new Ork(line[1], line[0], Integer.parseInt(line[3]), Integer.parseInt(line[2]), true,
                        Constants.orkHP,Constants.orkAP,Constants.orkMaxMove,0);
                pieces.add(orkObj);
            }
        }
        return pieces;
    }




    public ArrayList<ArrayList<String>> board() {
        ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>(sizeOfTable);

        for (int i = 0; i < sizeOfTable; i++) {
            board.add(new ArrayList<String>(sizeOfTable));
            for (int j = 0; j < sizeOfTable; j++) {
                board.get(i).add("null");
            }
        }
        for (Teams piece : pieces) {
            String className = piece.getClass().toString().split(" ")[1];
            if (className.equals("Elf")) {
                Elf elf = (Elf) piece;
                if (elf.active) {
                    board.get(elf.row).remove(elf.columns);
                    board.get(elf.row).add(elf.columns, elf.symbol);

                }
            }
            if (className.equals("Ork")) {
                Ork ork = (Ork) piece;
                if (ork.active) {
                    board.get(ork.row).remove(ork.columns);
                    board.get(ork.row).add(ork.columns, ork.symbol);

                }
            }
            if (className.equals("Troll")) {
                Troll troll = (Troll) piece;
                if (troll.active) {
                    board.get(troll.row).remove(troll.columns);
                    board.get(troll.row).add(troll.columns, troll.symbol);

                }
            }
            if (className.equals("Goblin")) {
                Goblin g = (Goblin) piece;
                if (g.active) {
                    board.get(g.row).remove(g.columns);
                    board.get(g.row).add(g.columns, g.symbol);
                }
            }
            if (className.equals("Human")) {
                Human h = (Human) piece;
                if (h.active) {
                    board.get(h.row).remove(h.columns);
                    board.get(h.row).add(h.columns, h.symbol);
                }
            }
            if (className.equals("Dwarf")) {
                Dwarf d = (Dwarf) piece;
                if (d.active) {
                    board.get(d.row).remove(d.columns);
                    board.get(d.row).add(d.columns, d.symbol);
                }
            }
        }
        return board;
    }

    public void writeProperty(ArrayList<Teams> pieces,BufferedWriter writer) throws IOException {
        SortedMap<String, String> write = new TreeMap<>();
        //creating rows for properties of character and write the output.txt
        for (Teams piece : pieces) {
            String className = piece.getClass().toString().split(" ")[1];

            if(piece.HP>0&&piece.active) {
                if (className.equals("Elf")) {
                    Elf elf = (Elf) piece;
                    if (elf.active) {
                        write.put(elf.symbol, "\t" + piece.HP + "\t(" + Constants.elfHP + ")\n");
                    }
                }
                if (className.equals("Ork")) {
                    Ork ork = (Ork) piece;
                    if (ork.active) {
                        write.put(ork.symbol, "\t" + piece.HP + "\t(" + Constants.orkHP + ")\n");
                    }
                }
                if (className.equals("Troll")) {
                    Troll troll = (Troll) piece;
                    if (troll.active) {
                        write.put(troll.symbol, "\t" + piece.HP + "\t(" + Constants.trollHP + ")\n");
                    }
                }
                if (className.equals("Goblin")) {
                    Goblin g = (Goblin) piece;
                    if (g.active) {
                        write.put(g.symbol, "\t" + piece.HP + "\t(" + Constants.goblinHP + ")\n");
                    }
                }
                if (className.equals("Human")) {
                    Human h = (Human) piece;
                    if (h.active) {

                        write.put(h.symbol, "\t" + piece.HP + "\t(" + Constants.humanHP + ")\n");
                    }
                }
                if (className.equals("Dwarf")) {
                    Dwarf d = (Dwarf) piece;
                    if (d.active) {
                        write.put(d.symbol, "\t" + piece.HP + "\t(" + Constants.dwarfHp + ")\n");
                    }
                }
            }
        }
        for (Map.Entry mapElement : write.entrySet()) {
            String key = (String)mapElement.getKey();
            // Finding the value
            String value = (String)mapElement.getValue();
            writer.write(key  + value);
        }
        writer.write("\n");
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
            if(piece.active){
                block.get(piece.row).remove(piece.columns);
                block.get(piece.row).add(piece.columns,piece.symbol);
            }

        }
        //all these to write output.txt
        for(int i =0;i<sizeOfTable*2+2;i++) {
            writer.write("*");
        }
        writer.write("\n");
        String listString = "";
        for (ArrayList<String> s : block) {
            String row = "*";
            for (String c : s) {
                row +=c;
            }
            listString+=row+"*\n";
        }
        writer.write(listString);
        for(int i =0;i<sizeOfTable*2+2;i++) {
            writer.write("*");
        }
        writer.write("\n\n");

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

    public String[][] move(Teams character , int columns, int row,int sizeOfTable,int maxMove) throws IOException, BoundaryCheckException {
        //character row=y axis, columns=x axis while move



        if(isValid(character.row+row,character.columns+columns,sizeOfTable)&&character.counterOfMove<=maxMove
                ) {
            if(character.pieceType.equals("ORK")){
                heals(boardArray,pieces,character);
            }
            if(boardArray[character.row+row][character.columns+columns].equals("null")){

                boardArray[character.row+row][character.columns+columns]=character.symbol;
                boardArray[character.row][character.columns]="null";
                character.row+=row;
                character.columns+=columns;
                character.counterOfMove+=1;

                //attack set up
                if(character.pieceType.equals("ORK")){

                    attack(boardArray,pieces,character);
                }if(character.pieceType.equals("HUMAN")){
                    if(String.valueOf(character.counterOfMove).equals(String.valueOf(character.maxMove))) {
                        attack(boardArray, pieces, character);

                    }
                }if(character.pieceType.equals("DWARF")){
                    attack(boardArray,pieces,character);
                }if(character.pieceType.equals("ELF")){
                    if(String.valueOf(character.counterOfMove).equals(String.valueOf(character.maxMove))) {
                       rangedAttack(boardArray,pieces,character);

                    }else{

                        attack(boardArray, pieces, character);
                    }
                }if(character.pieceType.equals("GOBLIN")){

                    attack(boardArray,pieces,character);

                }if(character.pieceType.equals("TROLL")){
                    if(String.valueOf(character.counterOfMove).equals(String.valueOf(character.maxMove))) {

                        attack(boardArray, pieces, character);
                    }
                }

            }

            else if(findPieceFromSymbol(boardArray[character.row+row][character.columns+columns],pieces).team
                    .equals(character.team)){
                //if characters in the same team
                    character.counterOfMove=character.maxMove+10;
            }else if(!findPieceFromSymbol(boardArray[character.row + row][character.columns + columns], pieces)
                    .team.equals(character.team)) {
                // death attack

                    Teams enemy = findPieceFromSymbol(boardArray[character.row + row][character.columns + columns], pieces);

                    //after the first attack;
                    enemy.HP -= character.AP;

                    if (enemy.HP < character.HP) {
                        //character won so decrease the enemys's last HP
                        if(enemy.HP>0){
                            character.HP -= enemy.HP;
                        }
                        // character's new location
                        boardArray[character.row][character.columns]="null";

                        boardArray[enemy.row][enemy.columns]=character.symbol;
                        character.row=enemy.row;
                        character.columns=enemy.columns;
                        //enemy will die
                        kill(enemy);
                        character.counterOfMove=character.maxMove+10;


                    } else if (enemy.HP > character.HP) {
                        if(character.HP>0) {
                            enemy.HP -= character.HP;
                        }
                        //new location
                        boardArray[character.row][character.columns]="null";
                        boardArray[enemy.row][enemy.columns]= enemy.symbol;

                        //character will die
                        kill(character);


                    } else if (String.valueOf(character.HP).equals(String.valueOf(enemy.HP))) {
                        boardArray[character.row][character.columns]="null";
                        boardArray[enemy.row][enemy.columns]="null";
                        kill(character);
                        kill(enemy);
                    }
                }
        }else if(character.counterOfMove<=character.maxMove&&(!shouldGamefinish())){
            // if there is changing will write board
            String a=Arrays.deepToString(boardArray);
            SortedMap<String, String> old=checkProperty(pieces);
            try{
            if(character.counterOfMove==0){
                throw new BoundaryCheckException();
            }
            }catch (BoundaryCheckException Exception){
                writer.write("Error : Game board boundaries are exceeded. Input line ignored.\n\n");
            }



            // ignore the left move steps
            if(character.pieceType.equals("ORK")){
                willContinueO=false;
            }if(character.pieceType.equals("HUMAN")){
               willContinueH=false;
            }if(character.pieceType.equals("DWARF")){
                willContinueD=false;
            }if(character.pieceType.equals("ELF")){
                willContinueE=false;
            }if(character.pieceType.equals("GOBLIN")){
                willContinueG=false;
            }if(character.pieceType.equals("TROLL")){
               willContinueT=false;
            }


        }
       /*   shows every step's on boardArray
       if(character.symbol.equals("T0")){
            System.out.println(character.counterOfMove);
            System.out.println(Arrays.toString(boardArray[0]));
            System.out.println(Arrays.toString(boardArray[1]));
            System.out.println(Arrays.toString(boardArray[2]));
            System.out.println(Arrays.toString(boardArray[3]));
            System.out.println(Arrays.toString(boardArray[4]));
            System.out.println(Arrays.toString(boardArray[5])) ;
            System.out.println(Arrays.toString(boardArray[6]));
            System.out.println(Arrays.toString(boardArray[7]));
            System.out.println(Arrays.toString(boardArray[8]));
            System.out.println(Arrays.toString(boardArray[9])+"\n\n");
        }*/

        return boardArray;
    }

    public static boolean isValid(int row, int column, int boardSize) {
        return row >= 0 && row <= boardSize-1 && column >= 0 && column <= boardSize-1;
    }

    public void heals(String[][] boardArray,ArrayList<Teams> pieces,Teams piece){
        for(int i=piece.row-1;i<=piece.row+1;i++){
            for(int j=piece.columns-1;j<=piece.columns+1;j++){

                if(isValid(i,j,sizeOfTable)) {

                    if (!boardArray[i][j].equals("null")) {


                        if (findPieceFromSymbol(boardArray[i][j], pieces).team.equals("Zorde")) {

                            if(findPieceFromSymbol(boardArray[i][j], pieces).pieceType.equals("ORK")){
                                if (findPieceFromSymbol(boardArray[i][j], pieces).HP + 10 < Constants.orkHP) {
                                    findPieceFromSymbol(boardArray[i][j], pieces).HP += Constants.orkHealPoints;
                                } else {
                                    findPieceFromSymbol(boardArray[i][j], pieces).HP =Constants.orkHP ;
                                }
                            }else if(findPieceFromSymbol(boardArray[i][j], pieces).pieceType.equals("TROLL")){
                                if (findPieceFromSymbol(boardArray[i][j], pieces).HP + 10 < Constants.trollHP) {
                                    findPieceFromSymbol(boardArray[i][j], pieces).HP += Constants.orkHealPoints;
                                } else {
                                    findPieceFromSymbol(boardArray[i][j], pieces).HP =Constants.trollHP ;
                                }
                            }else if(findPieceFromSymbol(boardArray[i][j], pieces).pieceType.equals("GOBLIN")){

                                if (findPieceFromSymbol(boardArray[i][j], pieces).HP + 10 < Constants.goblinHP) {
                                    findPieceFromSymbol(boardArray[i][j], pieces).HP += Constants.orkHealPoints;
                                } else {
                                    findPieceFromSymbol(boardArray[i][j], pieces).HP =Constants.goblinHP ;
                                }
                            }


                        }
                    }
                }
            }
        }
    }

    public void attack(String[][] boardArray,ArrayList<Teams> pieces,Teams piece) throws IOException {

            if(piece.team.equals("Calliance")) {

                for (int i = piece.row - 1; i <= piece.row + 1; i++) {
                    for (int j = piece.columns - 1; j <= piece.columns + 1; j++) {
                        if(isValid(i,j,sizeOfTable)) {
                            if (!boardArray[i][j].equals("null") ) {
                                if(findPieceFromSymbol(boardArray[i][j], pieces).active) {
                                    if (findPieceFromSymbol(boardArray[i][j], pieces).team.equals("Zorde")) {

                                        findPieceFromSymbol(boardArray[i][j], pieces).HP -= piece.AP;
                                        checkDie(findPieceFromSymbol(boardArray[i][j], pieces), pieces);
                                        checkDie(piece, pieces);
                                    }
                                }
                            }
                        }
                    }
                }

            }else if(piece.team.equals("Zorde")){

                for (int i = piece.row - 1; i <= piece.row + 1; i++) {
                    for (int j = piece.columns - 1; j <= piece.columns + 1; j++) {
                        if(isValid(i,j,sizeOfTable)) {

                            if (!boardArray[i][j].equals("null")) {
                                if (findPieceFromSymbol(boardArray[i][j], pieces).team.equals("Calliance")) {

                                    findPieceFromSymbol(boardArray[i][j], pieces).HP -= piece.AP;

                                    checkDie(findPieceFromSymbol(boardArray[i][j], pieces),pieces);
                                    checkDie(piece, pieces);
                                }
                            }
                        }
                    }
                }

            }



    }

    public void rangedAttack(String[][] boardArray, ArrayList<Teams> pieces, Teams piece){
               for (int i = piece.row - 2; i <= piece.row + 2; i++) {
                   for (int j = piece.columns - 2; j <= piece.columns + 2; j++) {
                       if(isValid(i,j,sizeOfTable)) {
                           if (!boardArray[i][j].equals("null")) {

                               if (findPieceFromSymbol(boardArray[i][j], pieces).team.equals("Zorde")) {

                                       findPieceFromSymbol(boardArray[i][j], pieces).HP -= 15;
                                       checkDie(findPieceFromSymbol(boardArray[i][j], pieces),pieces);
                                       checkDie(piece, pieces);

                               }
                           }
                       }
                   }
               }

    }

    public void checkDie(Teams piece,ArrayList<Teams> pieces){

            if(piece.HP<=0){
                piece.active=false;
                boardArray[piece.row][piece.columns]="null";

            }


    }
    public void kill(Teams piece){
        piece.active=false;

    }


    public SortedMap<String, String> checkProperty(ArrayList<Teams> pieces) throws IOException {
        SortedMap<String, String> write = new TreeMap<>();

        for (Teams piece : pieces) {
            String className = piece.getClass().toString().split(" ")[1];

            if (className.equals("Elf")) {
                Elf elf = (Elf) piece;
                if (elf.active) {
                    write.put(elf.symbol, "\t" + piece.HP + "\t(" + Constants.elfHP + ")\n");
                }
            }
            if (className.equals("Ork")) {
                Ork ork = (Ork) piece;
                if (ork.active) {
                    write.put(ork.symbol, "\t" + piece.HP + "\t(" + Constants.orkHP + ")\n");
                }
            }
            if (className.equals("Troll")) {
                Troll troll = (Troll) piece;
                if (troll.active) {
                    write.put(troll.symbol, "\t" + piece.HP + "\t(" + Constants.trollHP + ")\n");
                }
            }
            if (className.equals("Goblin")) {
                Goblin g = (Goblin) piece;
                if (g.active) {
                    write.put(g.symbol, "\t" + piece.HP + "\t(" + Constants.goblinHP + ")\n");
                }
            }
            if (className.equals("Human")) {
                Human h = (Human) piece;
                if (h.active) {

                    write.put(h.symbol, "\t" + piece.HP + "\t(" + Constants.humanHP + ")\n");
                }
            }
            if (className.equals("Dwarf")) {
                Dwarf d = (Dwarf) piece;
                if (d.active) {
                    write.put(d.symbol, "\t" + piece.HP + "\t(" + Constants.dwarfHp + ")\n");
                }
            }
        }
        for (Map.Entry mapElement : write.entrySet()) {
            String key = (String)mapElement.getKey();
            // Finding the value
            String value = (String)mapElement.getValue();

        }

       return write;
    }

    //isGameWillFinish
    public boolean shouldGamefinish(){
            int living =0;
            Boolean result=false;
            for(Teams piecem:pieces){
            if(piecem.active){
                living++;
            }
        }
        if(String.valueOf(living).equals("1")){
            result=true;
        }
        return result;
    }


    //create my exception
    class BoundaryCheckException extends Exception {
        public BoundaryCheckException() {
        }
    }

    class MoveCountCheckException extends Exception {
        public MoveCountCheckException() {

        }
    }



}
