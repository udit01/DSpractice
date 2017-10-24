package col106.a3;

import javafx.util.Pair;

import java.util.ArrayList;
import java.lang.Comparable;
import java.util.Calendar;

public class Node <Key extends Comparable<Key>, Value>{

    public ArrayList<Pair<Key,Value>> elements ;//= new ArrayList<Pair<Key,Value>>();
    public ArrayList<Node<Key,Value>> children ;//= new ArrayList<Node>();
    public static int B;
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

    public boolean isPresent(Key k){//retrun true if present anywhere in this subree

        for (int i=0 ; i<this.elements.size();i++){
            if (this.elements.get(i).getKey().compareTo(k)==0){
                return true;
            }
        }

        if (this.children.size()==0){
            return false;
        }
        else{
            for (int j = 0 ;j < this.children.size();j++){
                if (this.children.get(j).isPresent(k)==true){
                    return true;
                }
            }
        }

        return false;

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

//        if ((minIndex>maxIndex) ||  ( (minIndex==0)&&(maxIndex == 0)&&(this.elements.get(0).getKey().compareTo(k)!=0) ) || ( (minIndex==this.elements.size()-1)&&(maxIndex == this.elements.size()-1)&&(this.elements.get(this.elements.size()-1).getKey().compareTo(k)!=0) )  ){
//            return list;//is this really good?
//        }

        if (this.children.size()!=0) {
            //        ArrayList<Value> [] arrayOfLists = new ArrayList<Value>[maxIndex-minIndex+2];
            ArrayList<ArrayList<Value>> arrayOfLists = new ArrayList<ArrayList<Value>>(this.children.size());
            int t=0;
            //are the elements of array lists of array lists , automatically initialized
            // do we have to re-initialize each and every list ?
            for (int i = minIndex; i <= maxIndex + 1; i++) {//check indices as we're going on children
                if ((i < this.elements.size()) && (i >= 0)) {
                    if (this.elements.get(i).getKey().compareTo(k) == 0) {
                        list.add(this.elements.get(i).getValue());
                    }
                }
                //is the below line needed ?

                if ((i < this.children.size()) && (i >= 0)) {
                    // problem in the below line
                    arrayOfLists.add (new ArrayList<Value>());
//                arrayOfLists[i] = new ArrayList<Value>();
                    list.addAll(this.children.get(i).searchNode(k, arrayOfLists.get(t)));
                    t++;
                    //could i have done directly search(k,list) above ?
                }
            }
        }
        else {
            for (int i = minIndex; i <= maxIndex + 1; i++) {//check indices as we're going on children
                if ((i < this.elements.size()) && (i >= 0)) {
                    if (this.elements.get(i).getKey().compareTo(k) == 0) {
                        list.add(this.elements.get(i).getValue());
                    }
                }
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
            for (int i=0; i< this.children.size();i++){
                s.append(this.children.get(i).toStringNode());//what if the children is null? handled above
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

        if (this.children.size()!=0) {
//            System.out.println("INSIDE COPY NODE LINE 133");
//            System.out.println("leftIndex = " + leftIndex + "ALSO rightIndex = " + rightIndex);
//        System.out.println(this.children.subList(leftIndex,rightIndex+1).toString());
//        System.out.println("LINE133");

            for (int j = leftIndex; j <= rightIndex + 1; j++) {//adjusting parents of children
                this.children.get(j).parent = n;
            }
            n.children.addAll(this.children.subList(leftIndex, rightIndex + 2));
        }
        //directly copying references of children
        return n;
    }

    public Node (int num,Node p){
        elements = new ArrayList<Pair<Key,Value>>(num-1);//although flexible ..but i am still fixing the intital capacity
        children = new ArrayList<Node<Key,Value>>(num);
//        B = b;
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

            //try redistribute
            if (p.children.get(i).children.size()!=0) {//not a leaf to rebalance
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
            }
            else{// a leaf to rebalance
                if (i > 0) {//left shift

                    if (p.children.get(i - 1).elements.size() < B - 1) {
                        //do left shift yo!
                        Pair<Key, Value> up = new Pair<Key, Value>(p.elements.get(i - 1).getKey(), p.elements.get(i - 1).getValue());
                        Pair<Key, Value> right = new Pair<Key, Value>(p.children.get(i).elements.get(0).getKey(), p.children.get(i).elements.get(0).getValue());
//                        Node<Key, Value> hChild = p.children.get(i).children.get(0);
                        // . . . .  up  . . .
                        // . . . .  i-1  i . .
                        //. . .  . left right
                        p.children.get(i - 1).elements.add(up);
//                        p.children.get(i - 1).children.add(hChild);
//                        hChild.parent = p.children.get(i - 1);
                        //left node has grown
                        p.elements.set(i - 1, right);
//                    p is set
                        p.children.get(i).elements.remove(0);
//                        p.children.get(i).children.remove(0);
                        //right node is set
                        // now recall insertElement Node on what ?
                        this.insertElementNode(key, val);
                        //is it okay ?
                        return;
                    }
                }
                if (i < p.children.size() - 1) {//right shift
                    if (p.children.get(i + 1).elements.size() < B - 1) {
                        // doing right shift
                        Node<Key, Value> leftN = p.children.get(i);
                        Node<Key, Value> rightN = p.children.get(i + 1);

                        Pair<Key, Value> up = new Pair<Key, Value>(p.elements.get(i).getKey(), p.elements.get(i).getValue());
                        Pair<Key, Value> left = new Pair<Key, Value>(leftN.elements.get(leftN.elements.size() - 1).getKey(), leftN.elements.get(leftN.elements.size() - 1).getValue());
//                        Node<Key, Value> hChild = leftN.children.get(leftN.children.size() - 1);//hanging child
                        // . . . .  up  . . .
                        // . . . .  i   i+1 . .
                        //. . .  . left right

                        rightN.elements.add(0, up);
//                        rightN.children.add(0, hChild);
//                        hChild.parent = rightN;
                        //right node has grown
                        p.elements.set(i, left);
                        //p is set
                        leftN.elements.remove(leftN.elements.size() - 1);
//                        leftN.children.remove(leftN.children.size() - 1);
                        //left node ie this is also set
                        //call insert on leftN ie this
                        this.insertElementNode(key, val);
                        return;
                    }
                }
            }

            //do split as redistribution didn't work
            int median = (this.elements.size()) / 2;

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
                int minIndexW = this.elements.size();
                int maxIndexW = -1;
                for (int a = 0 ; a<this.elements.size();a++){
                    if (this.elements.get(a).getKey().compareTo(key)==0){
                        if (a>maxIndexW){
                            maxIndexW = a;
                        }
                        if (a<minIndexW){
                            minIndexW = a;
                        }
                    }
                }

                if (minIndexW<=maxIndexW){//atleast one k is present
                    //example element array
                    // k k k K+1 ....
                    // ...... k-1 k k
                    // 0 0 0 ... k-1 k-1 k-1   k  k  k  k   k+1 k+1 k+1 ...CASE2
                    this.children.get(minIndexW+1).insertElementNode(key,val);
                    return;
                }
                // if we have reached here therfore k is not present so we have to find the breaking point

                //example element array
                // k+1 ....
                //............ k-1
                //...................... idx1 . . .idx2........
                //0 0 0 0 ... k-1  k-1   k+1 ....type ?CASE1
                //................idx2...idx1.....
                //now i can insert between any of the idx1 to idx2 +1 indexed children!!

                int i=0,idx1=this.elements.size(),idx2=-1;//initially out of bound indices
                for (i=0;i<this.elements.size();i++){
                    if (this.elements.get(i).getKey().compareTo(key)>0){
                        idx1=i;
                        break;
                    }
                }

                for (i=this.elements.size()-1;i>=0;i--){
                    if (this.elements.get(i).getKey().compareTo(key)<0){
                        idx2 = i;
                        break;
                    }
                }

                if (idx1 == this.elements.size()){
                    //first for loop doesnt run and therefore all elements are < k
                    this.children.get(this.children.size()-1).insertElementNode(key,val);
                    return;
                }
                else if (idx2 == -1){
                    // 2nd for loop doesnt run and therefore all elements are > k
                    this.children.get(0).insertElementNode(key,val);
                    return;
                }
                else{//both loops so .run ..
                    this.children.get(idx2+1).insertElementNode(key,val);
                    return;
                }

//                if(idx2<idx1){//CASE1
//                    this.children.get(idx2+1).insertElementNode(key,val);//inserting in the middle child
//                }
//                else{//CASE2
////                for ()
//                    this.children.get(idx1+1).insertElementNode(key,val);
//                }
                //search where to be inserted , call insert node there ,
                // search where to be inserted , call insert node there ,
            }
        }
        return;
    }

    public void deleteElement(Key k){
        int flag = 0;
        int i=0;
        for (i=0;i<this.elements.size();i++){
            if (this.elements.get(i).getKey().compareTo(k)==0){
                flag=1;
                break;//at the first occurence
            }
        }
        if (flag==1){//key is present in the node at  i position
            if (this.children.size()==0){//its a leaf
                this.elements.remove(i);//just remove that pair yo!
                //check for balancing ? not in CLRS but why not ? because you have to no longer delte below
            }
            else {//internal node with  the key to delete at ith position

                if (this.children.get(i).elements.size()>=(B/2)){//left node is good to go
                    //findPredecessor k' of k in subtree rooted at this child ?

                    Pair<Key,Value> current_copy = new Pair<>(this.elements.get(i).getKey(),this.elements.get(i).getValue());

                    Node<Key,Value> ptr = this.children.get(i);//the root of left subtree

                    while(ptr.children.size()!=0){
                        ptr = ptr.children.get(ptr.children.size()-1);//get the right most child
                    }

                    //final ptr will be a leaf and get it's right-most element
                    Pair<Key,Value> p = ptr.elements.get(ptr.elements.size()-1);
                    Pair<Key,Value> k0_copy = new Pair<Key,Value>(p.getKey(),p.getValue());//immediate predecessor

                    // then recursivley delete k' and replace k by k' in x (we can find and delete k' in single downward pass)
                    /////////breakdown 1) swap k0 and current and then delete current from left subree
                    this.elements.set(i,k0_copy);
                    ptr.elements.set(ptr.elements.size()-1,current_copy);

                    this.children.get(i).deleteElement(k);

                    return;
                }
                else if(this.children.get(i+1).elements.size()>=B/2){//this child will exist as key here is existing
                    //findSuccessor k' of k in subtree rooted at this child ?

                    Pair<Key,Value> current_copy = new Pair<>(this.elements.get(i).getKey(),this.elements.get(i).getValue());

                    Node<Key,Value> ptr = this.children.get(i+1);//the root of left subtree

                    while(ptr.children.size()!=0){
                        ptr = ptr.children.get(0);//get the right most child
                    }

                    Pair<Key,Value> p = ptr.elements.get(0);
                    Pair<Key,Value> k0_copy = new Pair<Key,Value>(p.getKey(),p.getValue());//immediate predecessor

                    // then recursivley delete k' and replace k by k' in x (we can find and delete k' in single downward pass)
                    //see above breakdown
                    this.elements.set(i,k0_copy);
                    ptr.elements.set(0,current_copy);

                    this.children.get(i+1).deleteElement(k);
                    return;
                }

                else{//both side children have (B/2)-1 keys only
                    //merge both into i with B-1 keys , bring down that //key to be deleted..
                    Pair<Key,Value> current_copy = new Pair<>(this.elements.get(i).getKey(),this.elements.get(i).getValue());
                    Node<Key,Value> leftN = this.children.get(i);
                    Node<Key,Value> rightN = this.children.get(i+1);
                    ArrayList<Pair<Key,Value>> arrayOfPairs = new ArrayList<Pair<Key,Value>>();

                    this.elements.remove(i);
                    this.children.remove(i+1);
                    leftN.elements.add(current_copy);// adding middle element to left

                    for (int b = 0 ; b<rightN.elements.size();b++){//adding other elements to left
                        leftN.elements.add(new Pair<Key,Value>(rightN.elements.get(b).getKey(),rightN.elements.get(b).getValue()));
                    }

                    leftN.children.addAll(rightN.children);//copying all the children refrences
//                    leftN.parent = this;//redundant

                    //recursively delete k from the merged node
                    leftN.deleteElement(k);
                    return;
                }
            }
        }

        else{//not found in this node,//flag is 0
            //to find which are the closest ?
            if (this.children.size()==0){//not found inside and this is leaf therefore it's not here and just return instead of deleting
                return;
            }
            int t = 0,leftIndex=-1,rightIndex = this.elements.size();
            for (t=0; t< this.elements.size();t++){
                if (this.elements.get(t).getKey().compareTo(k)>0){
                    if (rightIndex>t){
                        rightIndex = t;
                    }
                }
                if (this.elements.get(t).getKey().compareTo(k)<0){
                    if (leftIndex<t){
                        leftIndex = t;
                    }
                }
            }

            if (leftIndex==-1){//all values are > k
                //target subtree from which to delete is this.children.get(0) tree
                Node<Key,Value> toDel = this.children.get(0);
                //ensure that it has atleast t keys

                if (toDel.elements.size()<(B/2)){//less than critical keys
                    if (this.children.get(1).elements.size()>=(B/2)){//take it! right is plump
                        //left shift
                        Pair<Key, Value> up = new Pair<Key, Value>(this.elements.get(0).getKey(), this.elements.get(0).getValue());
                        Pair<Key, Value> right = new Pair<Key, Value>(this.children.get(1).elements.get(0).getKey(), this.children.get(1).elements.get(0).getValue());
                        try {
                            Node<Key, Value> hChild = this.children.get(1).children.get(0);
                            this.children.get(0).children.add(hChild);
                            hChild.parent = this.children.get(0);
                            this.children.get(1).children.remove(0);
                        }
                        catch (Exception e){
                            //do nothing
                        }
                        // . . . .  up  . . .
                        // . . . .  0    1 . .
                        //. . .  . left right
                        this.children.get(0).elements.add(up);
                        //left node has grown
                        this.elements.set(0, right);
                        //p is set
                        this.children.get(1).elements.remove(0);
                        //right node is set
                        // now recall insertElement Node on what ?
                        this.children.get(0).deleteElement(k);
                        //is it okay ?
                        return;
                    }
                    else{//both have (B/2)-1 elements and therfore merge!!
                        Pair<Key,Value> current_copy = new Pair<Key,Value>(this.elements.get(0).getKey(),this.elements.get(0).getValue());
                        Node<Key,Value> leftN = this.children.get(0);
                        Node<Key,Value> rightN = this.children.get(1);
                        ArrayList<Pair<Key,Value>> arrayOfPairs = new ArrayList<Pair<Key,Value>>();

                        this.elements.remove(0);
                        this.children.remove(1);
                        leftN.elements.add(current_copy);// adding middle element to left

                        for (int b = 0 ; b<rightN.elements.size();b++){//adding other elements to left
                            leftN.elements.add(new Pair<Key,Value>(rightN.elements.get(b).getKey(),rightN.elements.get(b).getValue()));
                        }

                        leftN.children.addAll(rightN.children);//copying all the children refrences
//                    leftN.parent = this;//redundant

                        //recursively delete k from the merged node
                        leftN.deleteElement(k);
                        return;
                    }
                }
                //by one of the following methods (if deficient)
                //1 take from right sibling --anticlockwise rotation
                //2 merge the right and this sibling if also deficient
                //then delete from the remaining subchild

                else{//size is greater equal to critial t leaves
                    toDel.deleteElement(k);
                    return;
                }
            }
            else if (rightIndex==this.elements.size()){ // all values are less than k
                //target subtree from which to delete is this.children.get(this.children.size()-1) tree
                Node<Key,Value> toDel = this.children.get(this.children.size()-1);
                //ensure that it has atleast t keys
                if (toDel.elements.size()<(B/2)){
                    if (this.children.get(this.children.size()-2).elements.size()>=(B/2)){//left sibling is plump so take it//right shift
                        // doing right shift
                        Node<Key, Value> leftN = this.children.get(this.children.size()-2);
                        Node<Key, Value> rightN = this.children.get(this.children.size()-1);

                        Pair<Key, Value> up = new Pair<Key, Value>(this.elements.get(this.elements.size()-1).getKey(), this.elements.get(this.elements.size()-1).getValue());
                        Pair<Key, Value> left = new Pair<Key, Value>(leftN.elements.get(leftN.elements.size() - 1).getKey(), leftN.elements.get(leftN.elements.size() - 1).getValue());

                        try {
                            Node<Key, Value> hChild = leftN.children.get(leftN.children.size() - 1);//hanging child
                            rightN.children.add(0, hChild);
                            hChild.parent = rightN;
                            leftN.children.remove(leftN.children.size() - 1);
                        }
                        catch (Exception e){
                            //do nothing
                        }
                        // . . . .  up  . . .
                        // . . . .  end-1   end . .
                        //. . .  . left    right

                        rightN.elements.add(0, up);
                        //right node has grown
                        this.elements.set(this.elements.size()-1, left);
                        //p is set
                        leftN.elements.remove(leftN.elements.size() - 1);
                        //left node ie this is also set
                        rightN.deleteElement(k);
                        return;

                    }
                    else{// both are deficient sooo merge!
                        Pair<Key,Value> current_copy = new Pair<Key,Value>(this.elements.get(this.elements.size()-1).getKey(),this.elements.get(this.elements.size()-1).getValue());
                        Node<Key,Value> leftN = this.children.get(this.children.size()-2);
                        Node<Key,Value> rightN = this.children.get(this.children.size()-1);
                        ArrayList<Pair<Key,Value>> arrayOfPairs = new ArrayList<Pair<Key,Value>>();

                        this.elements.remove(this.elements.size()-1);
                        this.children.remove(this.children.size()-1);
                        leftN.elements.add(current_copy);// adding middle element to left

                        for (int b = 0 ; b<rightN.elements.size();b++){//adding other elements to left
                            leftN.elements.add(new Pair<Key,Value>(rightN.elements.get(b).getKey(),rightN.elements.get(b).getValue()));
                        }

                        leftN.children.addAll(rightN.children);//copying all the children refrences
//                    leftN.parent = this;//redundant

                        //recursively delete k from the merged node
                        leftN.deleteElement(k);//right node is no longer there!
                        return;
                    }
                }
                //by one of the following methods (if deficient)
                //1 take from left sibling --clockwise rotation
                //2 merge the left and this sibling if also deficient

                //then delete from the remaining subchild
                else{//peaceful node as good enough size
                    toDel.deleteElement(k);
                    return;
                }
            }
            else{//some values less and some greater
                //target subtree from which to delete is this.children.get(leftIndex+1) tree
                Node<Key,Value> toDel = this.children.get(leftIndex+1);
                //ensure that it has atleast t keys
                //by one of the following methods (if deficient)
                if (toDel.elements.size()<(B/2)){

                    //1 take from left sibling --clockwise rotation
                    if (this.children.get(leftIndex).elements.size()>=(B/2)){//take it from the plump left child//right shift

                        Node<Key, Value> leftN = this.children.get(leftIndex);
                        Node<Key, Value> rightN = this.children.get(leftIndex+1);

                        Pair<Key, Value> up = new Pair<Key, Value>(this.elements.get(leftIndex).getKey(), this.elements.get(leftIndex).getValue());
                        Pair<Key, Value> left = new Pair<Key, Value>(leftN.elements.get(leftN.elements.size() - 1).getKey(), leftN.elements.get(leftN.elements.size() - 1).getValue());

                        try {
                            Node<Key, Value> hChild = leftN.children.get(leftN.children.size() - 1);//hanging child
                            rightN.children.add(0, hChild);
                            hChild.parent = rightN;
                            leftN.children.remove(leftN.children.size() - 1);
                        }
                        catch (Exception e){
                            //nothing?
                        }
                        // . . . .  up  . . .
                        // . . . .  end-1   end . .
                        //. . .  . left    right

                        rightN.elements.add(0, up);
                        //right node has grown
                        this.elements.set(leftIndex, left);
                        //p is set
                        leftN.elements.remove(leftN.elements.size() - 1);
                        //left node ie this is also set
                        rightN.deleteElement(k);//as now right is plump
                        return;
                    }

                    //2 take from right sibling --anticlockwise rotation
                    else if (this.children.get(leftIndex+2).elements.size() >= (B/2)){//left shift
                        Pair<Key, Value> up = new Pair<Key, Value>(this.elements.get(leftIndex+1).getKey(), this.elements.get(leftIndex+1).getValue());
                        Pair<Key, Value> right = new Pair<Key, Value>(this.children.get(leftIndex+2).elements.get(0).getKey(), this.children.get(leftIndex+2).elements.get(0).getValue());
                        // . . . .     up  . . . . . .  .
                        // . . . .  leftIndex+1 . .leftIndex+2 . .
                        //. . .  . . . left . . . . . right

                        try {
                            Node<Key, Value> hChild = this.children.get(leftIndex + 2).children.get(0);
                            this.children.get(leftIndex + 1).children.add(hChild);
                            hChild.parent = this.children.get(leftIndex + 1);
                            this.children.get(leftIndex + 2).children.remove(0);
                        }
                        catch (Exception e){
                            //nothing?
                        }

                        this.children.get(leftIndex+1).elements.add(up);
                        //left node has grown
                        this.elements.set(leftIndex+1, right);
                        //p is set
                        this.children.get(leftIndex+2).elements.remove(0);
                        //right node is set
                        // now recall insertElement Node on what ?
                        this.children.get(leftIndex+1).deleteElement(k);
                        //is it okay ?
                        return;
                    }

                    //3 merge the left and this sibling if also deficient
                    //4 merge the right and this sibling if also deficient(this must not be needed)
                    else{//both siblings are deficient so merge ... with whom ? anyone .. i doing with left...
                        Pair<Key,Value> current_copy = new Pair<Key,Value>(this.elements.get(leftIndex).getKey(),this.elements.get(leftIndex).getValue());
                        Node<Key,Value> leftN = this.children.get(leftIndex);
                        Node<Key,Value> rightN = this.children.get(leftIndex+1);//to del
                        ArrayList<Pair<Key,Value>> arrayOfPairs = new ArrayList<Pair<Key,Value>>();

                        this.elements.remove(leftIndex);
                        this.children.remove(leftIndex+1);//r node vanish
                        leftN.elements.add(current_copy);// adding middle element to left

                        for (int b = 0 ; b<rightN.elements.size();b++){//adding other elements to left
                            leftN.elements.add(new Pair<Key,Value>(rightN.elements.get(b).getKey(),rightN.elements.get(b).getValue()));
                        }

                        leftN.children.addAll(rightN.children);//copying all the children refrences
//                    leftN.parent = this;//redundant

                        //recursively delete k from the merged node
                        leftN.deleteElement(k);//right node is no longer there!
                        return;
                    }
                }

                //then delete from the remaining subchild

                else{//peaceful plump child
                    toDel.deleteElement(k);
                    return;
                }
            }
        }

        return;

    }

//    public static void main(String args[]){
//        Node<Integer,Integer> n1 = new Node<Integer,Integer>();
//        System.out.println(n1.isEmpty());
}
