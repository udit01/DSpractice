import java.util.ArrayList;

public class Node {
    public ArrayList<String> list = null;
    public Node(){
        list = new ArrayList<String>();
    }
    public void add(String str){//this method will add and preserve the lex order
        //adds this string in lex order in this node
//        for (int j=0 ; j<list.size();j++){
//            if ()
//        }
        list.add(str);

        //can optimize here for better insertion O(n) if already sorted
//        list.sort(String::compareTo);
    }
    public void printNode(){
//        if (list.size()>1) {//custom adjustment
            System.out.println("Printing node: " + this);
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
//        }
    }
}
