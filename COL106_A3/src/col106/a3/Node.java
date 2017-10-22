package col106.a3;

import javafx.util.Pair;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.ArrayList;
import java.lang.Comparable;

public class Node <Key extends Comparable<Key>, Value>{

    public ArrayList<Pair<Key,Value>> elements ;//= new ArrayList<Pair<Key,Value>>();
    public ArrayList<Node<Key,Value>> children ;//= new ArrayList<Node>();
    public int B;
    public Node parent;
    //no need as can be checked directly by numChildren == 0;
//    public Boolean isLeaf ;//= (this.children.size()==0);//but by this, every new node will be leaf

    public boolean isEmptyNode() {
        return elements.isEmpty();
    }

//    public int sizeNode(){//return number of children and they should be b/w b/2 and b inclusive
//    }

    //        return this.children.size();//or num keys
    public int sizeNode(){
        int sum=0;
        sum += this.elements.size();
        for (int i=0;i<this.children.size();i++){
            sum+=this.children.get(i).sizeNode();
        }
        return sum;
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
    public int heightNodeEfficient(){//basically BIG Left
        if (this.children.size()==0)
            return 0;
        else
            return this.children.get(0).heightNodeEfficient()+1;
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

        if ((minIndex>maxIndex) ||  ( (minIndex==0)&&(maxIndex == 0)&&(this.elements.get(0).getKey().compareTo(k)!=0) ) || ( (minIndex==this.elements.size()-1)&&(maxIndex == this.elements.size()-1)&&(this.elements.get(this.elements.size()-1).getKey().compareTo(k)!=0) )  ){
            return list;//is this really good?
        }

//        ArrayList<Value> [] arrayOfLists = new ArrayList<Value>[maxIndex-minIndex+2];
        ArrayList<ArrayList<Value>> arrayOfLists = new ArrayList<ArrayList<Value>>(maxIndex-minIndex+2);
        //are the elements of array lists of array lists , automatically initialized
        // do we have to re-initialize each and every list ?
        for (int i=minIndex;i<=maxIndex+1;i++){//check indices as we're going on children
            if ((i<this.elements.size())&&(i>=0)) {
                if (this.elements.get(i).getKey().compareTo(k) == 0) {
                    list.add(this.elements.get(i).getValue());
                }
            }
            //is the below line needed ?

            if ((i<this.children.size())&&(i>=0)) {
//                arrayOfLists[i] = new ArrayList<Value>();
                arrayOfLists.set(i,new ArrayList<Value>());

                list.addAll(this.children.get(i).searchNode(k, arrayOfLists.get(i)));//but i didn't specify capacity etc.
                //could i have done directly search(k,list) above ?
            }
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

    public Node<Key,Value> copyNode(int leftIndex, int rightIndex){//index of elements array
        Node<Key,Value> n = new Node<Key,Value>(rightIndex-leftIndex+2);//num of children is the size of the node
        n.parent = this.parent;//to prevent hanging node
        for (int i=leftIndex;i<=rightIndex;i++){//adding new elements , not copying refrences
            n.elements.add(new Pair<Key,Value>(this.elements.get(i).getKey(),this.elements.get(i).getValue()));
//            this.chil.get(i)
        }
        for (int i=leftIndex;i<=rightIndex+1;i++){//adjusting parents of children
            this.children.get(i).parent = n;
        }
        //directly copying references of children
        n.children.addAll(this.children.subList(leftIndex,rightIndex+1));
        return n;
    }

    public Node (int num,Node p){
        elements = new ArrayList<Pair<Key,Value>>(num-1);//although flexible ..but i am still fixing the intital capacity
        children = new ArrayList<Node<Key,Value>>(num);
        B = num;
        parent = p;
    }
    public Node (int num){
        this(num,null);
    }
    //        new Node(10);//default capacity of arrayList
    //    }
    //    public Node (){
    public void insertElementNode(Key key,Value val){
        //if you come in here therfore this node is not the root because we have already dealt with the root

        if (this.elements.size()>=B-1) {//leaf or internal but full
            //as we are following the top down approach... scatter as soon as we see a just bursting node
            //try redistribute
//            this.parent will not be null
            Node<Key, Value> p = this.parent;
            int i = 0;
            for (i = 0; i < p.children.size(); i++) {
//                if (p.children.get(i).equals(this)){
                //i want to see if the two addresses are equal
                if (p.children.get(i) == (this)) {
                    break;
                }
            }
            //
            if (i > 0) {

                if (p.children.get(i - 1).elements.size() < B - 1) {
                    //do left shift yo!
                    Pair<Key, Value> up = new Pair<Key, Value>(p.elements.get(i - 1).getKey(), p.elements.get(i - 1).getValue());
                    Pair<Key, Value> right = new Pair<Key, Value>(p.children.get(i).elements.get(0).getKey(), p.children.get(i).elements.get(0).getValue());
                    Node<Key, Value> hChild = p.children.get(i).children.get(0);
                    // . . . .  up  . . .
                    // . . . .  i-1  i . .
                    //. . .  . left right
                    p.children.get(i - 1).elements.add(up);
                    p.children.get(i - 1).children.add(hChild);
                    hChild.parent = p.children.get(i - 1);
                    //left node has grown
                    p.elements.set(i - 1, right);
//                    p is set
                    p.children.get(i).elements.remove(0);
                    p.children.get(i).children.remove(0);
                    //right node is set
                    // now recall insertElement Node on what ?
                    this.insertElementNode(key, val);
                    //is it okay ?
                    return;
                }
            }
            if (i < p.children.size() - 1) {
                if (p.children.get(i + 1).elements.size() < B - 1) {
                    // doing right shift
                    Node<Key, Value> leftN = p.children.get(i);
                    Node<Key, Value> rightN = p.children.get(i + 1);

                    Pair<Key, Value> up = new Pair<Key, Value>(p.elements.get(i).getKey(), p.elements.get(i).getValue());
                    Pair<Key, Value> left = new Pair<Key, Value>(leftN.elements.get(leftN.elements.size() - 1).getKey(), leftN.elements.get(leftN.elements.size() - 1).getValue());
                    Node<Key, Value> hChild = leftN.children.get(leftN.children.size() - 1);//hanging child
                    // . . . .  up  . . .
                    // . . . .  i   i+1 . .
                    //. . .  . left right

                    rightN.elements.add(0, up);
                    rightN.children.add(0, hChild);
                    hChild.parent = rightN;
                    //right node has grown
                    p.elements.set(i, left);
                    //p is set
                    leftN.elements.remove(leftN.elements.size() - 1);
                    leftN.children.remove(leftN.children.size() - 1);
                    //left node ie this is also set
                    //call insert on leftN ie this
                    this.insertElementNode(key, val);
                    return;
                }
            }

            //do split as redistribution didn't work
            int median = (this.elements.size() + 1) / 2;

            p.elements.add(i, new Pair<Key, Value>(this.elements.get(median).getKey(), this.elements.get(median).getValue()));
            Node lChild = this.copyNode(0, median - 1);//therefore parent is same as root ie null
            lChild.parent = p;
            Node rChild = this.copyNode(median + 1, this.elements.size() - 1);
            rChild.parent = p;

            p.children.remove(i);//removing this from the list of children
            p.children.add(i, lChild);//copy elements and child refreneces to a new node to the left
            p.children.add(i + 1, rChild);//same to the right

            //and then where to insert ? yo

            //root is split and brand new, now where (which child )to go to  ?
            if (p.elements.get(i).getKey().compareTo(key) == 0) {
                //insert in the right? child
                p.children.get(i + 1).insertElementNode(key, val);
            } else if (p.elements.get(i).getKey().compareTo(key) > 0) {
                //my k is greater than key//therfore insert in left
                p.children.get(i).insertElementNode(key, val);
            } else {//root.elements.get(0).getKey().compareTo(key)<0
                //insert to the right child
                p.children.get(i + 1).insertElementNode(key, val);
            }

        }

        else{//this is not full

            if (this.children.size()==0){//its a leaf
                int i = 0;
                for (i = 0; i < this.elements.size(); i++) {
                    if (this.elements.get(i).getKey().compareTo(key) >= 0) {
                        break;
                    }
                }
                this.elements.add(i, new Pair<Key, Value>(key, val));//add at that position and shift others to the right
            }
            else {//not full, not leaf
                int i=0,idx1=this.elements.size()-1,idx2=0;
                for (i=0;i<this.elements.size();i++){
                    if (this.elements.get(i).getKey().compareTo(key)>=0){
                        idx1=i;
                        break;
                    }
                }

                for (i=this.elements.size()-1;i>=0;i--){
                    if (this.elements.get(i).getKey().compareTo(key)<=0){
                        idx2 = i;
                        break;
                    }
                }
                //example element array
                // 0 0 0 ... k-1 k-1 k-1   k  k  k  k   k+1 k+1 k+1 ...CASE2
                //...................... idx1 . . .idx2........
                //0 0 0 0 ... k-1  k-1   k+1 ....type ?CASE1
                //................idx2...idx1.....
                //now i can insert between any of the idx1 to idx2 +1 indexed children!!
                if(idx2<idx1){//CASE1
                    this.children.get(idx2+1).insertElementNode(key,val);//inserting in the middle child
                }
                else{//CASE2
//                for ()
                    this.children.get(idx1+1).insertElementNode(key,val);
                }
                //search where to be inserted , call insert node there ,
            }
        }
        return;
    }

//    public deleteElement(){}

//    public static void main(String args[]){
//        Node<Integer,Integer> n1 = new Node<Integer,Integer>();
//        System.out.println(n1.isEmpty());
}
