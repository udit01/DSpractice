package col106.a3;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class BTree<Key extends Comparable<Key>,Value> implements DuplicateBTree<Key,Value> {

    public Node<Key,Value> root ;
    public static int B;
    public BTree(int b) throws bNotEvenException {  /* Initializes an empty b-tree. Assume b is even. */
        if (b%2==1){
            throw new bNotEvenException();
        }
        else {
            B = b;
            root = new Node<Key,Value>(b);
            Node.B = b;
        }

//        throw new RuntimeException("Not Implemented");
    }

    @Override
    public boolean isEmpty() {
//        if (this.)
        return this.root.isEmptyNode();
//        throw new RuntimeException("Not Implemented");
    }

    @Override
    public int size() {
        return root.sizeNode();
//        throw new RuntimeException("Not Implemented");
    }

    @Override
    public int height() {
        return root.heightNode();//if to compute true max height
//        throw new RuntimeException("Not Implemented");
    }

    @Override
    public List<Value> search(Key key) throws IllegalKeyException {
        //when to throw illegal key exception?
        //if key doesn't extend comparable then throw illegal key exception ?
        ArrayList<Value> list = new ArrayList<Value>();
        if (root.isEmptyNode()){
            return list;
        }
        else {
            root.searchNode(key, list);
        }
        return list;
//        throw new RuntimeException("Not Implemented");
    }

    @Override
    public void insert(Key key, Value val) {
//        what if root is empty?or null
        //how did i handle that ?
        if (root.elements.size()>=B-1){//leaf or internal but full
            //create a new root , split the root where required ,and make the two splits children of the root,

            Node<Key,Value> r2 = new Node<Key,Value>(B);//null parent just like the root

            int median = (root.elements.size())/(2);
            //okay for 0 sized too ? probably


            //add copy of median to above ie r2
            r2.elements.add(new Pair<Key,Value>(root.elements.get(median).getKey(),root.elements.get(median).getValue()) );
            Node lChild = root.copyNode(0,median-1);//therefore parent is same as root ie null
            lChild.parent=r2;
            Node rChild = root.copyNode(median+1,root.elements.size()-1);
            rChild.parent=r2;


            r2.children.add(lChild);//copy elements and child refreneces to a new node to the left
            r2.children.add(rChild);//same to the right

            this.root = r2;//now r2 is the new root
            //and the prev root is lost as it should be
//            System.out.println("LINE78 BTREE insert");
//            System.out.println(root.toStringNode().toString());
            //root is split and brand new, now where (which child )to go to  ?
            if (root.elements.get(0).getKey().compareTo(key)==0){
                //insert in the right child
                root.children.get(1).insertElementNode(key,val);
            }
            else if (root.elements.get(0).getKey().compareTo(key)>0){
                //my k is greater than key//therfore insert in left
                root.children.get(0).insertElementNode(key,val);
            }
            else {//root.elements.get(0).getKey().compareTo(key)<0
                //insert to the right child
                root.children.get(1).insertElementNode(key,val);
            }
        }
        else{//root is not full
            if (root.children.size()==0){//its a leaf
                int i = 0;
                for (i = 0; i < root.elements.size(); i++) {
                    if (root.elements.get(i).getKey().compareTo(key) >= 0) {
                        break;
                    }
                }
                root.elements.add(i, new Pair<Key, Value>(key, val));//add at that position and shift others to the right
            }
            else {//not full, not leaf
                int minIndexW = root.elements.size();
                int maxIndexW = -1;
                for (int a = 0 ; a<root.elements.size();a++){
                    if (root.elements.get(a).getKey().compareTo(key)==0){
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
                    root.children.get(minIndexW+1).insertElementNode(key,val);
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

                int i=0,idx1=root.elements.size(),idx2=-1;//initially out of bound indices
                for (i=0;i<root.elements.size();i++){
                    if (root.elements.get(i).getKey().compareTo(key)>0){
                        idx1=i;
                        break;
                    }
                }

                for (i=root.elements.size()-1;i>=0;i--){
                    if (root.elements.get(i).getKey().compareTo(key)<0){
                        idx2 = i;
                        break;
                    }
                }

                if (idx1 == root.elements.size()){
                    //first for loop doesnt run and therefore all elements are < k
                    root.children.get(root.children.size()-1).insertElementNode(key,val);
                    return;
                }
                else if (idx2 == -1){
                    // 2nd for loop doesnt run and therefore all elements are > k
                    root.children.get(0).insertElementNode(key,val);
                    return;
                }
                else{//both loops so .run ..
                    root.children.get(idx2+1).insertElementNode(key,val);
                    return;
                }

//                if(idx2<idx1){//CASE1
//                    root.children.get(idx2+1).insertElementNode(key,val);//inserting in the middle child
//                }
//                else{//CASE2
////                for ()
//                    root.children.get(idx1+1).insertElementNode(key,val);
//                }
                //search where to be inserted , call insert node there ,
            }

        }

//        throw new RuntimeException("Not Implemented");
    }

    @Override
    public void delete(Key key) throws IllegalKeyException {
        //when to throw illegal key exception ?
        List<Value> l = new ArrayList<Value>();
        l = this.search(key);
        while(l.size()!=0){
            root.deleteElement(key);
            //trim root to remove empty root or such root

            l = this.search(key);
        }
//        throw new RuntimeException("Not Implemented");
    }
//    @Override
    public String toString(){
        return root.toStringNode().toString();
    }
}
