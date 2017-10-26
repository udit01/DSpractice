import java.util.ArrayList;

public class Node {
    public ArrayList<String> list = null;
    public Node(){
        list = new ArrayList<String>();
    }
    public void add(String str){//this method will add and preserve the lex order
        //adds this string in lex order in this node
        list.add(str);
        //can optimize here for better insertion O(n) if already sorted
        list.sort(String::compareTo);
    }
}
