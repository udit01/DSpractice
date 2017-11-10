import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class Puzzle {
    static HashMap<String,ArrayList<String>> graph;
//    private Pair<Integer[][], Pair<Integer, Integer>> ;

//    HashMap<Node,ArrayList<Node>> gr;
    static Integer[] weights = { 0, 0, 0, 0,
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
//        BufferedWriter writerO;
        try {
            startTime = System.currentTimeMillis();
            readerI = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])) );
            oStream = new BufferedOutputStream(new FileOutputStream(args[1]));

            time1 = System.currentTimeMillis();
//-------------test code
//            ArrayList<String> dummy = new ArrayList<String>();
//            dummy.add("random");
//            dummy.add("random2");
//            dummy.add("random3");
//            graph.put("12345678G",dummy);
//            printGraph();
//
            graph = new HashMap<String, ArrayList<String>>();
            constructGraph();
            printGraph();
//            t = Integer.parseInt(readerI.readLine());
//
//
//            for(int i=0;i<t;i++){
//                splice = readerI.readLine().split(" ");
//                //splice[0] contatins the start and other end
//                w = readerI.readLine().split(" ");
//                for (int k=0;k<8;k++){
//                    weights[k]=Integer.parseInt(w[k]);
//                }
////                constructGraph();//once per the t test cases or
//
//            }

            oStream.flush();

            time2 = System.currentTimeMillis();
            oStream.write((time2-time1));
            oStream.close();

        }catch (Exception e) {

            e.printStackTrace();
//            System.out.println("Incorrect file path or file format.");
        }
    }

    public static void constructGraph(){//will make changes in the global graph variable
        String s = "12345678G";
        Queue<String> q = new ArrayDeque<String>();//can be faster?
        q.add(s);
        String current;
        ArrayList<String> nbors;

        while(!q.isEmpty()){//this can be made much faster by marking
            current = q.remove();
            if (graph.containsKey(current)){
                continue;
            }
            nbors = generateNeigbours(current);//can be made faster
            graph.put(current,nbors);

            q.addAll(nbors);
        }
    }
    public static void printGraph(){
//        Set<String> kSet =  graph.keySet();
        ArrayList<String> l;
        try {
            for (String key : graph.keySet()) {
                l = graph.get(key);
                oStream.write(("String:" + key + " Neighbours:{").getBytes());
                for (String nbr:l){
                    oStream.write((nbr+", ").getBytes());
                }
                oStream.write((" }\n").getBytes());

            }
            oStream.flush();
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
//class Node{
//    String s;
////    ArrayList<Node> neighbours;
//}