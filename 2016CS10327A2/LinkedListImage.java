package col106.a3;

import javafx.util.Pair;
import java.util.ArrayList;
import java.lang.Comparable;

public class Node <Key extends Comparable<Key>, Value>{

    public ArrayList<Pair<Key,Value>> elements ;//= new ArrayList<Pair<Key,Value>>();
    public ArrayList<Node> children ;//= new ArrayList<Node>();
    //no need as can be checked directly by numChildren == 0;
//    public Boolean isLeaf ;//= (this.children.size()==0);//but by this, every new node will be leaf

    public boolean isEmptyNode() {
        return elements.isEmpty();
    }

    public int sizeNode(){//return number of children and they should be b/w b/2 and b inclusive
        return elements.size()+1;//or num keys
    }

    public int heightNode(){

        int max = -1;
        int height = -1;
        for (int i = 0; i< this.children.size() ; i++){
            height = this.children.get(i).heightNode();
            if (height>max){
                max = height;
            }
        }
        return  (1+max);
    }
    public ArrayList<Value> searchNode(Key k , ArrayList<Value> list){//check this function...
        int maxIndex = 0, minIndex = this.elements.size()-1;
        for (int i=0;i<this.elements.size();i++){
            if (elements.get(i).getKey().compareTo(k)>=0){
                if (minIndex>i){ minIndex = i; }
            }
            if (elements.get(i).getKey().compareTo(k)<=0){
                if (maxIndex<i){ maxIndex = i; }
            }
        }

        if (maxIndex==0||minIndex==this.elements.size()-1){
            return list;
        }
        ArrayList<ArrayList<Value>> arrayOfLists = new ArrayList<ArrayList<Value>>(maxIndex-minIndex+2);
        //are the elements of array lists of array lists , automatically initialized
        // do we have to re-initialize each and every list ?
        for (int i=minIndex;i<=maxIndex+1;i++){//check indices as we're going on children
            if (i<this.elements.size()) {
                if (this.elements.get(i).getKey().compareTo(k) == 0) {
                    list.add(this.elements.get(i).getValue());
                }
            }
            //is the below line needed ?
            arrayOfLists.get(i) = new ArrayList<Value>();

            list.addAll(this.children.get(i).searchNode( k , arrayOfLists.get(i)));//but i didn't specify capacity etc.
            //could i have done directly search(k,list) above ?
        }
        return list;
    }

    public StringBuilder toStringNode(){//whoa ... why this error (when i use the function name as toString() ) ?
        StringBuilder s = new StringBuilder();
        s.append("[");
        if (this.children.size()==0){
            for (int i=0; i< this.elements.size();i++){
                    s.append(this.elements.get(i).getKey());
                    s.append("=");
                    s.append(this.elements.get(i).getValue());
                    s.append(", ");
            }
//            remove the last extra comma and space
            s.delete(s.length() - 2 ,s.length()-1);//?? will this work ?

        }
        else {
            for (int i=0; i<= this.children.size();i++){
                s.append(this.children.get(i).toString());//what if the children is null? handled above
                if (i<this.elements.size()){
                    s.append(", ");
                    s.append(this.elements.get(i).getKey());
                    s.append("=");
                    s.append(this.elements.get(i).getValue());
                    s.append(", ");
                }
            }
        }
        s.append("]");
        return s;
    }
    public Node (int num){
        elements = new ArrayList<Pair<Key,Value>>(num-1);//although flexible ..but i am still fixing the intital capacity
        children = new ArrayList<Node>(num);
    }
    public Node (){
        new Node(10);//default capacity of arrayList
    }
//    public insertElement(){}
//    public deleteElement(){}

//    public static void main(String args[]){
//        Node<Integer,Integer> n1 = new Node<Integer,Integer>();
//        System.out.println(n1.isEmpty());
//    }
}

