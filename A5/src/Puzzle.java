import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.util.Pair;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Puzzle {
    public static HashMap<String,ArrayList<String>> graph;
    public static HashMap<String,ArrayList<String>> graphM;
    public static ArrayList<Pair<Integer,String>> heap;
//    public static int heapSize ;
    public static HashMap<String,Integer> indiceMap;// = new HashMap<String, Integer>();
    public static HashMap<String,String> parentMap;// = new HashMap<String,Integer>();
    public static HashMap<String,Boolean> visitedMap;// = new HashMap<String,Integer>();

//    private Pair<Integer[][], Pair<Integer, Integer>> ;

//    HashMap<Node,ArrayList<Node>> gr;
    public static int[] weights = { 0, 0, 0, 0,
                             0, 0, 0, 0};

    public static OutputStream oStream;

    public static void main(String args[]){
        //you need to implement this
        long startTime;
        long time1;
        long time2;
        int t=0;
        String[] splice;
        String[] w;
        BufferedReader readerI;//input reader
//        Integer[] moveAndCost;
//        BufferedWriter writerO;
        try {
            startTime = System.currentTimeMillis();
            readerI = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])) );
            oStream = new BufferedOutputStream(new FileOutputStream(args[1]));

//-------------test code
//            ArrayList<String> dummy = new ArrayList<String>();
//            dummy.add("random");
//            dummy.add("random2");
//            dummy.add("random3");
//            graph.put("12345678G",dummy);
//            printGraph();
//

            graph = new HashMap<String, ArrayList<String>>();
            graphM = new HashMap<String, ArrayList<String>>();

            constructGraphs();
            time1 = System.currentTimeMillis();
//            printGraphs();
            t = Integer.parseInt(readerI.readLine());
//
//
            for(int i=0;i<t;i++){
                splice = readerI.readLine().split(" ");
                //splice[0] contatins the start and other end
                w = readerI.readLine().split(" ");
                for (int k=0;k<8;k++){//weight[0] is actually weight of moving 1 to GAP
                    weights[k]=Integer.parseInt(w[k]) + 1;//remember as we don't want 0 paths to trouble us and we will subtract after
                    //these are actually pseudo weights
                }

//                moveAndCost =  dijkstra(splice[0],splice[1]);//will also print the moves and cost!yaya
                dijkstra(splice[0],splice[1]);//will also print the moves and cost!yaya

            }


            time2 = System.currentTimeMillis();
            oStream.write(("Graph(s) formed in: "+(time1-startTime)+"ms\n").getBytes());
            oStream.write(("Input Processed in: "+(time2-time1)+"ms\n").getBytes());
            oStream.flush();
            oStream.close();

        }catch (Exception e) {

            e.printStackTrace();
//            System.out.println("Incorrect file path or file format.");
        }
    }

    public static void dijkstra(String start,String end)throws IOException{
        //IO -- Prints the total moves and cost requried to traverse from start to end! also print the moves
        //if in different components then immediatly exit
        int c=0;
//        for (HashMap<String,ArrayList<String>>g : {graph,graphM})
        if (graph.containsKey(start)){
            c=1;
            if (!graph.containsKey(end)){
                //print something and then return nul
                oStream.write("-1 -1\n\n".getBytes());
                oStream.flush();
                return;
            }
        }
        else if (graphM.containsKey(start)){
            c=2;
            if (!graphM.containsKey(end)){
                //print something and then return nul
                oStream.write("-1 -1\n\n".getBytes());
                oStream.flush();
                return;
            }
        }
        //hash map for parent pointers and hash map for storing indices in the array

        HashMap<String,ArrayList<String>>g = (c==1)?graph:graphM;
        indiceMap = new HashMap<String, Integer>();
        parentMap= new HashMap<String,String>();
        visitedMap= new HashMap<String,Boolean>();
        heap = new ArrayList<Pair<Integer, String>>();
        //
//        heap = new ArrayList<Pair<Integer, String>>(181440);
//        ArrayList<String> tempNeighbours = g.get(start);//optimization
//        g.remove(start);//OPTIMIZATION?remove first then

//        int l = 0;
        heap.add(new Pair<Integer, String>(0,start));//added it at the 0th postion
        indiceMap.put(start,0);
        parentMap.put(start,null);//start has no parent or itself ?
//        ++l;
//        for (String k : g.keySet() ){//initialization
//            //no need to check as we are adding 0th initially
//            heap.add(new Pair<Integer, String>(Integer.MAX_VALUE,k));//yay//unmarked nodes
//            indiceMap.put(k,l);
//            parentMap.put(k,null);//null integer
//            //now all nodes are in heap
//            ++l;
//        }

//        g.put(start,tempNeighbours);//reinserting after
//        heapSize = 1;
//        heapSize = 181440;
        //constructed the outside of the cloud
        //some kind of parent pointer
        int w ;
        Integer idx ;
        Pair<Integer,String> current;
        String node;
        int nodeDist;
        int neighbourDist;
        while (heap.size()>=0){//what if heap size = 0?

            current = deleteMin();//it will throw out distance and node and remove etc
            node = current.getValue();
            visitedMap.put(node,true);
//            indiceMap.remove(node);//node is removed

            if(node.equals(end)){
                //then it is here to relax it's neigbours which we don't want // just exit
//                pseudo distance is current.getkey() and - numMoves
                //parent is also set and everything is good!
                printPath(start,current);//current has end
                oStream.flush();
                return;
            }

            nodeDist = current.getKey();
            for (String neighbour:g.get(node)){//getting the neighbours of node from graphs
                //dist of neighbour = dist of current + calculate cost(s,current)
                w = calculateCost(node,neighbour);
                //what if neigbhour not in the heap?
                idx = indiceMap.get(neighbour);
                if (idx == null){//not in the heap

                    //case if it's not in the heap its in the cloud ?
                    Boolean b = visitedMap.get(neighbour);
                    if ((b==null)||(b==false)){//not visited node//not in heap not in cloud
                        heap.add(new Pair<>(nodeDist+w,neighbour));
                        indiceMap.put(neighbour,heap.size());//appended at the end
//                        heapSize++;
                        percolateUp(heap.size()-1);//this value has decreased so required to be maintained
                        visitedMap.put(neighbour,false);
                        parentMap.put(neighbour,node);//bec
                    }
                    else{//visited is true ie it's already in the cloud!!
                        continue;//let 's see for another neighbour
                    }
                }
                else{//its in the heap
                    neighbourDist = heap.get(idx).getKey();
                    if (neighbourDist>nodeDist+w){
                        heap.set(indiceMap.get(neighbour),new Pair<>(nodeDist+w,neighbour));
                        percolateUp(indiceMap.get(neighbour));//this value has decreased so required to be maintained
                        //do i need to put false everytime ?
                        parentMap.put(neighbour,node);//because node is the new parent of indice
                    }
                }

//                try {
//
//                    neighbourDist = heap.get(indiceMap.get(neighbour)).getKey();
//                }
//                catch (Exception e){
//                    continue;//On with the loop boys//if neighbour is not in the heap then it's in the cloud!
//                }
//                if (neighbourDist>nodeDist+w){
//                    heap.set(indiceMap.get(neighbour),new Pair<>(nodeDist+w,neighbour));
//                    percolateUp(indiceMap.get(neighbour));//this value has decreased so required to be maintained
////                    oStream.write(("child/neighbour: "+neighbour+" node/parent: "+node+"seperated by: " + getMove(neighbour,node)+"new Dist(par+w):"+(nodeDist+w)+"oldDist(v.d):"+neighbourDist+"\n").getBytes());
////                    printHeap();
//                    parentMap.put(neighbour,node);//because node is the new parent of indice
//                }
                //else do nothing for that neighbour
            }

        }
        //what if it was never stopped inside the while loop and comes here? then its an error
        oStream.write("THIS line should not be printed! (213)".getBytes());
        oStream.flush();
        return;
    }
    public static void printHeap(){
        try {
            oStream.write(("PHeap: ").getBytes());
            for (int i = 0; i < 5; i++) {
                oStream.write((heap.get(i) + " ").getBytes());
            }
            oStream.write(("]\n").getBytes());

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void printPath(String start, Pair<Integer,String> endN) throws IOException{
        //some kind of ordered set where we have the parent indices of end --> start
        ArrayList<String> path = new ArrayList<String>();
        int pseudoCost = endN.getKey();
        String curr= endN.getValue();
//        System.out.println("Line 185:"+curr+"(current | start:)"+start+"\n");
//        oStream.write(("Line 185:"+curr+"(current | start:)"+start+"\n").getBytes());
//        oStream.flush();
        //pray that it doesn't get stuck in an infinite loop
        while (!(curr.equals(start))){
//            oStream.write((curr+"Line 180\n").getBytes());
            System.out.println(curr);
            path.add(curr);
            //shit but the heap is now empty
            curr = parentMap.get(curr);//it will spit out the next element ie parent
            if (curr==null){
                break;
            }
        }
        path.add(start);//last cherry on the cake
        for(int i=0;i<path.size();i++) {
//            oStream.write(("LINE 200: i:"+i+" Node "+ path.get(i)+"\n").getBytes());
        }
        int pathLength = path.size() - 1;//the number of edges is = V-1
        int cost = pseudoCost - pathLength;//due to all the extra ones added
        oStream.write((pathLength+" "+cost+"\n").getBytes());
        for (int i=path.size()-1;i>0;i--){
            oStream.write((getMove(path.get(i),path.get(i-1))).getBytes());//this method by default includes space
        }
        oStream.write("\n".getBytes());
        oStream.flush();
        return;
    }
    public static String getMove(String s1,String s2){//returns a 3 character string

        //what to move to get from s1 -> s2 ?
        int gPos1 = 0;
        int gPos2 = 0;
        for (int i=0;i<s1.length();i++){
            if (s1.charAt(i)=='G'){
                gPos1 = i;
            }
            if (s2.charAt(i)=='G'){
                gPos2 = i;
            }
        }

        StringBuilder sb =new StringBuilder();//same as s1.charAt(gPos2)
        //will it append the character or the integer ?
        sb.append(s2.charAt(gPos1));
        //ex 12345678G then 1234567G8
        int diff = gPos1 - gPos2;
        switch (diff){
            case -3:
                //ex 123 4G5 678 then 123 475 6G8
                sb.append("U");
                break;
            case -1:
                //ex 123 4G5 678 then 123 45G 678
                sb.append("L");
                break;
            case 1:
                //ex 123 4G5 678 then 123 G45 678
                sb.append("R");
                break;
            case 3:
                //ex 123 4G5 678 then 1G3 425 678
                sb.append("D");
                break;
            default:
                sb.append("U");//append a normal character at the end
                try {
//                    oStream.write(("Line 248: diff: "+diff+" s1: " + s1 + " s2: " + s2+"\n").getBytes());
                    throw new Exception("Why in this case?");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
        }
        sb.append(" ");
        return sb.toString();
    }
    public static void percolateUp(int idx){
        // somthingis wrong at idx //we just have decreased the value now what ?
        Pair<Integer,String>parent ;
        Pair<Integer,String>curr;
        int parentIndex=0;
        while (idx>0){//if idx has reached 0 then the heap is equalized!
            curr = heap.get(idx);
            if((idx%2)==1){//current index is odd
                parentIndex = (int)(idx/2);
            }
            else {//current idx is non 0 even +ive
                parentIndex = ((int)(idx/2)) - 1;//draw and you'll see!
            }
            parent = heap.get(parentIndex);
            if (parent.getKey()<= curr.getKey()){
                //it's equalized ! yippie
                //should is return or break?
                break;
            }
            else{//parent is greater than the child
                heap.set(parentIndex,curr);//now current is on the parent index
                indiceMap.put(curr.getValue(),parentIndex);
                heap.set(idx,parent);//now parent is on the current index
                indiceMap.put(parent.getValue(),idx);
                idx = parentIndex;//is the new index
                continue;
            }
        }
        return ;
    }
    public static Pair<Integer,String > deleteMin(){
        Pair<Integer,String > toReturn = heap.get(0);//top and min element
        //follow the alogrithm
        indiceMap.remove(heap.get(0).getValue());//0 indexed element is removed
        heap.set(0,heap.get(heap.size()-1));//now push this element down
        indiceMap.put(heap.get(heap.size()-1).getValue(),0);
//        heapSize--;//reducing heapsize is equivalent to deleting the last elemtn
        //lazy deleting the last element
        //SOMETHING BAD WILL HAPPEN WITH IDX==0 fixed! based on 0 index
        int idx = 0;
        Pair<Integer,String > lchild ;
        Pair<Integer,String > rchild ;
        Pair<Integer,String > cur;
//        Pair<Integer,String > temp;//UNREQUIRED

        while(idx<(heap.size()-1)){//if idx = heap size then nothing left to equalize
            cur = heap.get(idx);//will function both as temp and comparable

            //if they it has 2 children what if they don't ?
            if (((2*idx)+2)<=(heap.size()-1)) {
                lchild = heap.get((2 * idx) + 1);
                rchild = heap.get((2 * idx) + 2);
                if ((cur.getKey() <= lchild.getKey()) && (cur.getKey() <= rchild.getKey())) {//whats the symbol for and ?
                    //do something
                    //satisfied at this level therefore it's okay and done!
                    break;
                }
                if (lchild.getKey() <= rchild.getKey()) { //left child is smaller
                    //if code has reached this point then curr is > leftchild(min of the 2)
//                    temp = cur;
                    heap.set(idx, lchild);//now left child at curr position
                    indiceMap.put(lchild.getValue(),idx);
                    heap.set((2 * idx) + 1, cur);//now cur at left child postion
                    indiceMap.put(cur.getValue(),(2*idx)+1);
                    //heap property is satisfied at this level
                    idx = (2 * idx)+ 1 ;//now see that it should be satisfied at the next level
                    continue;
                } else {//right child is smaller
                    //if code has reached this point then curr is > rightchild(min of the 2)
//                    temp = cur;
                    heap.set(idx, rchild);//now left child at curr position
                    indiceMap.put(rchild.getValue(),idx);
                    heap.set((idx * 2) + 2, cur);//now cur at left child postion
                    indiceMap.put(cur.getValue(),(2*idx)+2);
                    //heap property is satisfied at this level
                    idx = (idx * 2) + 2;//now see that it should be satisfied at the next level
                    continue;
                }
            }
            else if(((2 * idx)+1)<=(heap.size()-1)){//only left child
                lchild = heap.get((2 * idx)+1);
                if (cur.getKey()>lchild.getKey()){//violation if it is bigger !
//                    temp = cur;
                    heap.set(idx,lchild);//put left child at current postion
                    indiceMap.put(lchild.getValue(),idx);
                    heap.set((2 * idx)+1,cur);//put  current at left child postion
                    indiceMap.put(cur.getValue(),(2*idx)+1);
                }
//                idx = 2*idx;//to be consistent but not required!
//                as it had only 1 child therfore it is last of its generations
                break;
            }
            else{//no children
                //therefore  all are satisfied
                break;
            }

        }
        //now it's ordered and return the orignial first element please!
        //do something?
        return toReturn;
    }
    public static int  calculateCost(String s1,String s2){
        for (int i=0;i<s1.length();i++){
            if (s1.charAt(i)=='G'){
                return weights[s2.charAt(i)-'1'];//as 0th weight is actually weight of sliding 1
            }
            if (s2.charAt(i)=='G'){
                return weights[s1.charAt(i)-'1'];//as 0th weight is actually weight of sliding 1
            }
        }
        try{
            oStream.write("THiS line shouldn't be printed!".getBytes());
        }
        catch (Exception e){
            e.printStackTrace();//or do nothing ?
        }
        return 0;//if it reaches here then error
    }
    public static void constructGraphs(){//will make changes in the global graph variable
        String s = "12345678G";
        Queue<String> q = new ArrayDeque<String>();//can be faster?
        q.add(s);
        String current;
        ArrayList<String> nbors = new ArrayList<String>();

        while(!q.isEmpty()){//this can be made much faster by marking
            current = q.remove();
            if (graph.containsKey(current)){
                continue;
            }
            nbors = generateNeigbours(current);//can be made faster
            graph.put(current,nbors);

            q.addAll(nbors);
        }

        //making the graph gM
        String sM = "321654G87";

        Queue<String> qM = new ArrayDeque<String>();//can be faster?
        qM.add(sM);
        nbors.clear();
        while(!qM.isEmpty()){//this can be made much faster by marking
            current = qM.remove();
            if (graphM.containsKey(current)){
                continue;
            }
            nbors = generateNeigbours(current);//can be made faster
            graphM.put(current,nbors);

            qM.addAll(nbors);
        }
    }
    public static void printGraphs(){
//        Set<String> kSet =  graph.keySet();
        ArrayList<String> l;
        try {
            oStream.write(("Graph 1:\n").getBytes());
            for (String key : graph.keySet()) {
                l = graph.get(key);
                oStream.write(("Node:" + key + " Neighbours{").getBytes());
                for (String nbr:l){
                    oStream.write((nbr+" ").getBytes());
                }
                oStream.write((" }\n").getBytes());

            }
            oStream.flush();
            //printing the 2nd graphs
            oStream.write(("Graph 2:\n").getBytes());
            for (String key : graphM.keySet()) {
                l = graphM.get(key);
                oStream.write(("Node:" + key + " Neighbours{").getBytes());
                for (String nbr:l){
                    oStream.write((nbr+" ").getBytes());
                }
                oStream.write((" }\n").getBytes());

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return;
    }
    public static ArrayList<String> generateNeigbours(String s){
        ArrayList<String> list = new ArrayList<>();
        int pos=0;
        for (int i=0;i<s.length();i++){
            if(s.charAt(i)=='G'){
                pos=i;
                //should we replace G by 0 here?
                break;
            }
        }
        //what is more generalized?
        int r = pos%3;//col
        int q = pos/3;//row
        //all those below have G
        if ((pos%3)==0){//left column
            list.add(swappedString(s,pos,pos+1));
            if (q==0){//top row
                list.add(swappedString(s,pos,pos+3));
            }
            else if(q==2){//bottom row
                list.add(swappedString(s,pos,pos-3));
            }
            else{//middle row
                list.add(swappedString(s,pos,pos-3));
                list.add(swappedString(s,pos,pos+3));
            }
        }
        else if((pos%3)==2){//rigth col
            list.add(swappedString(s,pos,pos-1));
            if (q==0){//top row
                list.add(swappedString(s,pos,pos+3));
            }
            else if(q==2){//bottom row
                list.add(swappedString(s,pos,pos-3));
            }
            else{//middle row
                list.add(swappedString(s,pos,pos-3));
                list.add(swappedString(s,pos,pos+3));
            }
        }
        else{//pos%3==1//middle column
            list.add(swappedString(s,pos,pos-1));
            list.add(swappedString(s,pos,pos+1));
            if (q==0){//top row
                list.add(swappedString(s,pos,pos+3));
            }
            else if(q==2){//bottom row
                list.add(swappedString(s,pos,pos-3));
            }
            else{//middle row
                list.add(swappedString(s,pos,pos-3));
                list.add(swappedString(s,pos,pos+3));
            }
        }

        //wrong answer, correct approach
//        int[] shifts = {-3,3,-1,1};
//        for(int i=0;i<4;i++){
//            if (i<2) {
//                if ((pos + shifts[i] < s.length()) ) {//can hardcode values of s.length()etx
//                    list.add(swappedString(s, pos, pos + shifts[i]));
//                }
//                if ( (pos + shifts[i] >= 0)){
//                    list.add(swappedString(s,pos))
//                }
//            }
//            else{
//                if ((pos%3)==0){
//                    list.add();
//                }
//                else if(pos%3==1){
//
//                }
//                else{
//
//                }
//            }
//        }
        return list;
        //
//        Pair<Integer[][],Pair<Integer,Integer>> p = strToGrid(s);//decoding
//        Integer [][]rep = p.getKey();
//        int row = p.getValue().getKey();
//        int col = p.getValue().getValue();
//
//        ArrayList<Pair<Integer,Integer>> nIndices = new ArrayList<>();
//        nIndices.add(new Pair<Integer, Integer>(row,col-1));//left
//        nIndices.add(new Pair<Integer, Integer>(row-1,col));//up
//        nIndices.add(new Pair<Integer, Integer>(row,col+1));//right
//        nIndices.add(new Pair<Integer, Integer>(row+1,col));//down
//        int x=0,y=0;
//        for(int k=0;k<nIndices.size();k++){
//            x = nIndices.get(k).getKey();
//            y = nIndices.get(k).getValue();
//            if (((x<3)&&(x>=0))&&((y<3)&&(y>=0))){
//                list.add(gridToStr(getNewMatrix(rep,row,col,x,y)));
//            }
//        }
//        return list;
//        Pair<Integer,Integer> left = new Pair<>(row,col-1);
//        Pair<Integer,Integer> up = new Pair<>(row-1,col);
//        Pair<Integer,Integer> right = new Pair<>(row,col+1);
//        Pair<Integer,Integer> down = new Pair<>(row+1,col);


//        if (row == 0){
//            if (col == 0){
//
//            }
//            else if(col == 1){
//
//            }
//            else{ // col == 2
//
//            }
//        }
//        else if(row == 1){
//            if (col == 0){
//
//            }
//            else if(col == 1){
//
//            }
//            else{ // col == 2
//
//            }
//        }
//        else{ // row == 2
//            if (col == 0){
//
//            }
//            else if(col == 1){
//
//            }
//            else{ // col == 2
//
//            }
//        }
//
    }
    public static String swappedString(String s,int x,int y){

        char[] c = s.toCharArray();

        char temp = c[x];
        c[x] = c[y];
        c[y] = temp;

        return new String(c);

    }
    //
//    public static Integer[][] getNewMatrix(Integer[][] rep,int row,int col,int x, int y){
//        //return new matrix for valid swapping
//        Integer[][] newGrid = new Integer[3][3];
//        for (int i=0;i<3;i++){
//            for(int j=0;j<3;j++){
//                newGrid[i][j] = rep[i][j];
//            }
//        }
//        Integer temp = newGrid[row][col];
//        newGrid[row][col] = newGrid[x][y];
//        newGrid[x][y]= temp;
//        return newGrid;
//    }
//
//    public static Pair<Integer[][],Pair<Integer,Integer>> strToGrid(String s){
//        // G will be replaced by 0...
//        int[][] rep = new int[3][3];
//        Pair<Integer,Integer> mark;
////        Pair<Integer,Integer> mark = new Pair<>(0,0);
//        for (int i=0;i<3;i++){
//            for(int j=0;j<3;j++){
//                char c = s.charAt(3*i+j);
//                if (c=='G'){
//                    mark = new Pair<Integer,Integer>(i,j);//row,column
//                    rep[i][j] = 0;//representing it by a 0 temporarily
//                }
//                else{
//                    rep[i][j]=c-'0';
//                }
//            }
//        }
//        return new Pair(rep,mark);
//    }
//    public static String gridToStr(Integer [][] rep){
//        //not useful so 0 will be replaced by 0
//        //0 in the grid will be replaced by G
//        StringBuilder s = new StringBuilder();
//        for (int i=0;i<3;i++){
//            for(int j=0;j<3;j++){
////                s.append((rep[i][j]==0)?"G":rep[i][j]);//is this correct ?
//                s.append(rep[i][j]);//is this correct ?
//            }
//        }
//        return s.toString();
//    }
//    public static boolean checkString(String s){
//
//    }

}