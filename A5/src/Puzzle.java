import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Puzzle {
    HashMap<String,ArrayList<String>> graph;
//    private Pair<Integer[][], Pair<Integer, Integer>> ;

//    HashMap<Node,ArrayList<Node>> gr;

    public static void main(String args[]){
        //you need to implement this
        OutputStream oStream;
        long startTime;
        long time1;
        long time2;
        int t=0;
        BufferedReader readerI;//input reader
//        BufferedWriter writerO;
        try {
            startTime = System.currentTimeMillis();
            readerI = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])) );
            oStream = new BufferedOutputStream(new FileOutputStream(args[1]));
            time1 = System.currentTimeMillis();

            t = Integer.parseInt(readerI.readLine());
            for(int i=0;i<t;i++){

            }

            oStream.flush();

            time2 = System.currentTimeMillis();
//            oStream.write(("Vocabulary processed in: "+(time1-startTime)+"ms\n").getBytes());
//            oStream.write(("Input processed in: "+(time2-time1)+"ms\n").getBytes());
//            oStream.write((collisons+" is the number of collisons\n").getBytes());
            oStream.close();

        }catch (Exception e) {

            e.printStackTrace();
//            System.out.println("Incorrect file path or file format.");
        }
    }
    public static void constructGraph(){//will make changes in the global graph variable

    }
    public static ArrayList<String> generateNeigbours(String s){
        ArrayList<String> list = new ArrayList<>();
        int pos=0;
        for (int i=0;i<s.length();i++){
            if(s.charAt(i)=='G'){
                pos=i;
                break;
            }
        }
        int[] shifts = {-3,-1,1,3};
        for(int i=0;i<4;i++){
            if ((pos+shifts[i]<s.length())&&(pos+shifts[i]>=0)){//can hardcode values of s.length()etx
                list.add(swappedString(s,pos,pos+shifts[i]));
            }
        }
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