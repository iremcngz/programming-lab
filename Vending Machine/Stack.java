import java.util.ArrayList;

public class Stack<T> {
    private ArrayList<T> stack=new ArrayList<T>();
    int index = 0;
    private String partName=null;

    public Stack(String partName){
        this.partName=partName;
    }

    public void push(T element) {
        stack.add(index,element);
        index++;
    }


    public T pop(){
        return stack.remove(--index);

    }

    public boolean isEmpty() {
        if (index == 0) {
            return true;
        }
        return false;
    }

    public ArrayList<T> getStack(){
        return stack;
    }

    public String getPartName(){
        return partName;
    }


}

