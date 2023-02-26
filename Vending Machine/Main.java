import java.io.*;
import java.util.ArrayList;

public class Main {
    private String partsFile,itemsFile,tokensFile,tasksFile,outputFile;
    private ArrayList<Stack> vendingMachine;
    private int numberOfTokens;
    private Queue tokensBox;
    BufferedWriter writer;

    Main(String partsFile,String itemsFile,String tokensFile,String tasksFile,String outputFile) throws Exception {
        this.partsFile=partsFile;
        this.outputFile=outputFile;
        this.itemsFile=itemsFile;
        this.tokensFile=tokensFile;
        this.tasksFile=tasksFile;
        vendingMachine=getVendingMachine();
        numberOfTokens=getNumberOfTokens();
        tokensBox=getTokens();
        writer = new BufferedWriter(new FileWriter("output.txt"));
    }

    //Taking input files as arguments from command line
    public static void main(String[] args) throws Exception {
        String partsFile = args.length > 0 ? args[0] : "parts.txt";
        String itemsFile = args.length > 1 ? args[1] : "items.txt";
        String tokensFile = args.length > 2 ? args[2] : "tokens.txt";
        String tasksFile = args.length > 3 ? args[3] : "tasks.txt";
        String outputFile = args.length > 4 ? args[4] : "output.txt";
        Main main = new Main(partsFile,itemsFile,tokensFile,tasksFile,outputFile);
        main.executeCommands();
        main.writingOutput();
    }




    //get machine from parts txt
    public ArrayList<Stack> getVendingMachine() throws IOException {
        ArrayList<Stack> machine=new ArrayList<Stack>();
        BufferedReader readerParts = new BufferedReader(new FileReader(partsFile));
        String line;
        while ((line = readerParts.readLine()) != null) {
            Stack part =new Stack(line);
            getItems(part,line);
            machine.add(part);
        }
      readerParts.close();
        return machine;

    }

    public void getItems(Stack part,String nameOfStack) throws IOException {
        BufferedReader readerItems = new BufferedReader(new FileReader(itemsFile));
        String lineItems;
        while ((lineItems = readerItems.readLine()) != null) {
            String[] words=lineItems.split(" ");
            if(nameOfStack.equals(words[1])){
                part.push(words[0]);
            }
        }
        readerItems.close();
    }

    public Queue getTokens() throws IOException {
        BufferedReader readerTokens = new BufferedReader(new FileReader(tokensFile));
        String lineTokens;
        Queue queue=new Queue(numberOfTokens);
        while ((lineTokens = readerTokens.readLine()) != null) {
            queue.enqueue(lineTokens);
        }

        readerTokens.close();
        return queue;
    }

    public int getNumberOfTokens() throws IOException {
        int numberOfTokens=0;
        BufferedReader readerTokens = new BufferedReader(new FileReader(tokensFile));
        String lineTokens;
        while ((lineTokens = readerTokens.readLine()) != null) {
            numberOfTokens++;
        }
        readerTokens.close();
        return numberOfTokens;
    }






    public void executeCommands() throws IOException {
        BufferedReader readerTasks = new BufferedReader(new FileReader(tasksFile));
        String lineItems;
        while ((lineItems = readerTasks.readLine()) != null) {
            String[] words=lineItems.split("\t");
            int numberOfBuy=words.length;
            if(words[0].equals("BUY")){
                for(int l=1;l<numberOfBuy;l++) {
                    buy(words[l].split(",")[0], Integer.parseInt(words[l].split(",")[1]));
                }
            }if(words[0].equals("PUT")){
                for(int l=1;l<numberOfBuy;l++) {
                    put(words[l]);
                }

            }
        }
        readerTasks.close();

    }


    public void buy(String item,int numberOfItem) {
        //delete the item from its parts with pop command
        for(Stack stackobj :vendingMachine){
            if(stackobj.getPartName().equals(item)){
                for(int i=0;i<numberOfItem;i++){
                    if(!stackobj.isEmpty()) {
                        stackobj.pop();
                    }
                }
            }
        }

        for(int i=tokensBox.tokens.size()-1;i>=0;i--) {
            if(tokensBox.tokens.get(i).split(" ")[1].equals(item)){

                String [] tokenProperty=tokensBox.tokens.get(i).split(" ");
                //if one token is enough to buy items
                if(Integer.parseInt(tokenProperty[2])>numberOfItem){
                    tokenProperty[2]=String.valueOf(Integer.parseInt(tokenProperty[2])-numberOfItem);
                    if(Integer.parseInt(tokenProperty[2])<=0){
                        tokensBox.dequeue(tokensBox.tokens.get(i));
                    }

                    //update token's property
                    StringBuffer sb = new StringBuffer();
                    for(int k = 0; k < tokenProperty.length; k++) {
                        if(k==0){
                            sb.append(tokenProperty[k]);
                        }else{
                            sb.append(" "+tokenProperty[k]);
                        }
                    }
                    String str = sb.toString();

                    //updating queue : dequeue old token, enqueue updated token
                    tokensBox.dequeue(tokensBox.tokens.get(i));
                    tokensBox.enqueue(str);
                    break;

                    //if one token is enough but we must delete it
                }else if(Integer.parseInt(tokenProperty[2])==numberOfItem){
                    tokensBox.dequeue(tokensBox.tokens.get(i));
                    break;
                }else if(Integer.parseInt(tokenProperty[2])<numberOfItem){
                    numberOfItem-=Integer.parseInt(tokenProperty[2]);
                    tokensBox.dequeue(tokensBox.tokens.get(i));

                }
            }
        }
    }

    public void put(String itemAndSymbolsOfItems){
        String item=itemAndSymbolsOfItems.split(",")[0];
        int numberOfPut=itemAndSymbolsOfItems.split(",").length;
        //for every stack in the machine set the part name and push items
        for(Stack stackobj :vendingMachine){
            if(stackobj.getPartName().equals(item)){
                for(int i=1;i<numberOfPut;i++){
                    stackobj.push(itemAndSymbolsOfItems.split(",")[i]);
                }
            }
        }
    }

    public void writingOutput() throws IOException {
        //write all stack(part) in the vendingMachine to the output file
        for(int l=0;l< vendingMachine.size();l++){
            writer.write(vendingMachine.get(l).getPartName()+":\n");
            if(vendingMachine.get(l).getStack().size()==0){
                writer.write("\n");
            }
            for(int k=vendingMachine.get(l).getStack().size()-1;k>=0;k--){
                writer.write(vendingMachine.get(l).getStack().get(k)+"\n");
            }
            writer.write("---------------\n");
        }


        //write tokens in the token box to the output file
        writer.write("Token Box:\n");
        for(int i=0;i<tokensBox.tokens.size();i++){
            if(i!=tokensBox.capacity-1) {
                writer.write(tokensBox.tokens.get(i) + "\n");
            }else{
                writer.write(tokensBox.tokens.get(i));
            }
        }
        writer.close();
    }

}
