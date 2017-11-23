import java.util.*;
import java.io.*;

public class Puzzle {

    public static HashMap<String,ArrayList<String>> graph;
    public static HashMap<String,ArrayList<String>> graphM;
    public static HashMap<String,Integer> indiceMap;
    public static HashMap<String,Node> visitednodesMap;
    public static ArrayList<Node> heap;
    public static OutputStream oStream;
    public static int[] weights = { 0, 0, 0, 0,
                                    0, 0, 0, 0};

    public static void main(String args[]) {
//        long startTime;
//        long time1;
//        long time2;
        int t = 0;
        String[] splice;
        String[] w;
        BufferedReader readerI;//input reader
        try {
//            startTime = System.currentTimeMillis();
            readerI = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
            oStream = new BufferedOutputStream(new FileOutputStream(args[1]));


            graph = new HashMap<String, ArrayList<String>>();
            graphM = new HashMap<String, ArrayList<String>>();

            constructGraphs();
//            time1 = System.currentTimeMillis();
            t = Integer.parseInt(readerI.readLine());
//
//
            for (int i = 0; i < t; i++) {
                splice = readerI.readLine().split(" ");
                w = readerI.readLine().split(" ");
                for (int k = 0; k < 8; k++) {
                    weights[k] = Integer.parseInt(w[k]);
                }
                dijkstra(splice[0], splice[1]);//will also print the moves and cost!yaya
                oStream.flush();
            }

//            time2 = System.currentTimeMillis();
//            oStream.write(("Graph(s) formed in: "+(time1-startTime)+"ms\n").getBytes());
//            oStream.write(("Input Processed in: "+(time2-time1)+"ms\n").getBytes());
            oStream.close();
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Incorrect file path or file format.");
        }
    }

    public static void dijkstra(String start,String end) throws IOException{
        int c=0;
        if (graph.containsKey(start)){
            c=1;
            if (!graph.containsKey(end)){
                //print something and then return nul
                oStream.write("-1 -1\n\n".getBytes());
                return;
            }
        }
        else if (graphM.containsKey(start)){
            c=2;
            if (!graphM.containsKey(end)){
                //print something and then return nul
                oStream.write("-1 -1\n\n".getBytes());
                return;
            }
        }

        HashMap<String,ArrayList<String>>g = (c==1) ? graph : graphM;
        indiceMap = new HashMap<String, Integer>();
        visitednodesMap = new HashMap<String, Node>();
        heap = new ArrayList<Node>();

        heap.add(new Node(start,null, 0,0 ));//added it at the 0th postion
        indiceMap.put(start,0);//seals that node 0 is of start

        Integer idx ;
        Node current;
        String str;
//        int l = 181440;
        /*typical life cycle of a string:-
        * 1. Only exists in g with its neighbours
        * 2. At some point detected that not in heap by indice map having null
        * 3. Then put in the heap with it's newly initialized data node (percolateUp too)
        * 4. Then updated at some points until minimized by nCouldBe's and comes on the top of the heap
        * 5. Gets extrated from the heap, deleted from the indice map and therefore node technicall non-existant
        * 6. Node full data is transferred to the visitedNodesMap (Elisiyum) where kept for path retracing
        * 7. Story of this node/string has ended until used in retracing of path
         */
        while (true) {//what if heap size = 0?
            current = deleteMin();//a node
            str = current.s;
            //now put in the visited node map!
            visitednodesMap.put(str,current);

            if (str.equals(end)){
                printPath(start,end);
                return;
            }

            for (String neighbour:g.get(str)){
                idx = indiceMap.get(neighbour);
                if (idx == null){//not in indice map
                    if (visitednodesMap.get(neighbour)!=null){
                        continue;//therefore it's visited
                    }
                    else {//not in heap therfore add one
                        Node n = new Node(neighbour,str,current.cost+calculateCost(str,neighbour),current.pathLength +1);
                        heap.add(n);
                        indiceMap.put(neighbour,heap .size()-1);
                        percolateUp(heap.size()-1);
                    }
                }
                else{//in the heap
                    Node n = heap.get(indiceMap.get(neighbour));
                    Node nCouldBe = new Node(neighbour,str,current.cost + calculateCost(str,neighbour),current.pathLength + 1);

                    if (nCouldBe.compareTo(n)<0){
                        heap.set(indiceMap.get(neighbour),nCouldBe);
                        percolateUp(indiceMap.get(neighbour));
                    }

                }
            }

        }
    }

    public static void printPath(String start,String end) throws IOException{
        ArrayList<String> path = new ArrayList<String>();
        String curr= end;

        while (!(curr.equals(start))){
            path.add(curr);
            curr = visitednodesMap.get(curr).parent;
        }
        path.add(start);

        oStream.write((visitednodesMap.get(end).pathLength+" "+visitednodesMap.get(end).cost+"\n").getBytes());

        for (int i=path.size()-1;i>0;i--){
            oStream.write((getMove(path.get(i),path.get(i-1))).getBytes());//this method by default includes space
        }
        oStream.write("\n".getBytes());

        return;
    }

    public static void percolateDown(int idx){
        Node curNode;
        Node lChild;
        Node rChild;
        Integer minIdx;
        Integer lIndex;
        Integer rIndex;
        while(idx<heap.size()){
            curNode = heap.get(idx);
            lIndex = (2*idx)+1;
            rIndex = (2*idx)+2;
            if (rIndex <= (heap.size()-1)) {
                lChild = heap.get(lIndex);
                rChild = heap.get(rIndex);
                minIdx = ( lChild.compareTo(rChild) <= 0 ? lIndex: rIndex);
                if (curNode.compareTo(heap.get(minIdx))<=0){
                    break;//as heap structure satisfied at this level
                }
                else{
                    swapNode(idx,minIdx);
                    idx = minIdx;
                    continue;
                }
            }
            else if( lIndex <= ( heap.size()-1 ) ) {//only left child
                if (curNode.compareTo(heap.get(lIndex))<=0){
                    break;//as heap structure satisfied at this level
                }
                else{
                    swapNode(idx,lIndex);
                    idx = lIndex;
                    continue;
                }
            }
            else{//no children
                //therefore  heap structure should be satisfied
                break;
            }
        }
    }

    public static void percolateUp(int idx){

        int parentIdx ;
        Node parentNode;
        Node curNode;

        while (idx>0){//if idx has reached 0 then the heap is equalized!
            curNode = heap.get(idx);
            parentIdx = ( ((idx%2)==1)? (idx/2): (idx/2)-1 );

            parentNode = heap.get(parentIdx);

            if (parentNode.compareTo(curNode)<=0){
                //it's equalized
                break;
            }
            else{//parent is greater than the child
                swapNode(idx,parentIdx);
                idx = parentIdx;//is the new index
                continue;
            }
        }
        return ;
    }

    private static void swapNode(int idx1, int idx2) {
        Node n1 = heap.get(idx1);
        Node n2 = heap.get(idx2);

        //put modified indices in indice map
        indiceMap.put(n1.s,idx2);
        indiceMap.put(n2.s,idx1);

        //actual swap in the heap
        //no need of //deep copy
//        Node temp = new Node(n1);//deep copy
        heap.set(idx1,n2);//or set the deep copy of n2 ?
        heap.set(idx2,n1);

        return;
    }

    public static Node deleteMin(){
        Node toReturn = heap.get(0);
        if (heap.size()==1){
            indiceMap.remove(heap.get(0).s);
            heap.remove(0);
            return toReturn;
        }
        //else replace the node by another

        swapNode(0,heap.size()-1);//swaps and changes the indiceMap too!
        indiceMap.remove(heap.get(heap.size()-1).s);
        heap.remove(heap.size()-1);//to remove the last 1
        //now to percolate the node down
        percolateDown(0);
        return toReturn;
    }

    public static String getMove(String s1,String s2){
        //returns a 3 character string
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
                sb.append("X");//append a normal character at the end
        }
        sb.append(" ");
        return sb.toString();
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
                nbors = generateNeighbours(current);//can be made faster
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
                nbors = generateNeighbours(current);//can be made faster
                graphM.put(current,nbors);

                qM.addAll(nbors);
            }
        }

    public static ArrayList<String> generateNeighbours(String s){
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
        return list;
    }

    public static String swappedString(String s,int x,int y){

        char[] c = s.toCharArray();

        char temp = c[x];
        c[x] = c[y];
        c[y] = temp;

        return new String(c);

    }

}
class Node{
    public String s;
    public String parent;//string or node ?
    public int cost;
    public int pathLength;
//    public Boolean visited;

    public Node(String str_,String parent_,Integer cost_,Integer pathLength_){
        this.s = str_;
        this.parent = parent_;
        this.cost = cost_;
        this.pathLength = pathLength_;
    }

    public int compareTo(Node n){
        if (this.cost<(n.cost)){
            return -1;
        }
        else if (this.cost>(n.cost)){
            return +1;
        }
        else{
            if (this.pathLength>(n.pathLength)){
                return 1;
            }
            else if (this.pathLength<(n.pathLength)){
                return -1;
            }
            else {
                return 0;
            }
        }
    }
}