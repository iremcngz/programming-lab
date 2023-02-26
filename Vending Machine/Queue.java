import java.util.ArrayList;

public class Queue {
    int capacity;
    ArrayList<String> tokens;
    ArrayList<String> beforeSortTokens=new ArrayList<String>();

    public  Queue(int numberOfTokens){
        this.capacity=numberOfTokens;
        this.tokens=new ArrayList<String>(capacity);
    }



    public  void enqueue(String token) {
        if (tokens.size() < capacity) {
            //if tokens is empty add the token first index
            if (tokens.size() == 0) {
                tokens.add(token);
            } else {
                for (int i = 0; i < tokens.size(); i++) {
                    //if new token's value is smaller add it leftward
                    if (Integer.parseInt(tokens.get(i).split(" ")[2]) >
                            Integer.parseInt(token.split(" ")[2])) {
                        if (i - 1 >= 0) {
                            for (int k = i; k >= 0; k--) {
                                if (Integer.parseInt(tokens.get(k - 1).split(" ")[2]) !=
                                        Integer.parseInt(token.split(" ")[2])) {
                                    tokens.add(k, token);
                                    break;
                                }
                            }
                        } else if (tokens.size() == 1) {
                            tokens.add(i, token);
                        } else {
                            tokens.add(0, token);
                        }
                        beforeSortTokens.add(token);
                        break;
                        //if new token's value is equal but it's writed before add leftward
                    } else if (Integer.parseInt(tokens.get(i).split(" ")[2]) ==
                            Integer.parseInt(token.split(" ")[2])) {
                        if (i - 1 >= 0) {
                            for (int k = i; k >= 0; k--) {
                                if (Integer.parseInt(tokens.get(k - 1).split(" ")[2]) !=
                                        Integer.parseInt(token.split(" ")[2])) {
                                    tokens.add(k, token);
                                    break;
                                }
                            }
                        } else if (tokens.size() == 1) {

                            tokens.add(0, token);
                        } else {

                            tokens.add(0, token);
                        }
                        break;
                        //if token's value bigger add it rightward
                    } else if ((Integer.parseInt(tokens.get(i).split(" ")[2]) <
                            Integer.parseInt(token.split(" ")[2])))
                        if (i + 1 < tokens.size()) {
                            if (Integer.parseInt(tokens.get(i + 1).split(" ")[2]) >
                                    Integer.parseInt(token.split(" ")[2])) {
                                tokens.add(i + 1, token);
                                break;
                            }
                        } else if (tokens.size() == 1) {
                            tokens.add(i + 1, token);
                            break;
                        } else if (tokens.size() <= i + 1) {
                            tokens.add(token);
                            break;
                        }
                }
            }
        }
        beforeSortTokens.add(token);
    }

    //dequeue the token from token box
    public  void dequeue(String token){
        tokens.remove(token);
    }

}
