import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.File;
import java.lang.reflect.Array;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Anagram {
    public static int sizeVocab ;
    public static Node[][] a = new Node[10][25000];
    public static void main (String args[]){
        //you need to implement this
        Scanner scannerVocab = null;
        Scanner scannerInput = null;
        File fileVocab = null;
        File fileInput = null;
        int sizeInput = 0;
        ArrayList<String> s;
        String st;
        try {
            String vocabFilePath = args[0];
            String inputFilePath = args[1];
            fileVocab = new File(vocabFilePath);
            fileInput = new File(inputFilePath);
            scannerVocab = new Scanner(fileVocab);
            scannerInput = new Scanner(fileInput);
            storeVocab(scannerVocab);

            sizeInput = scannerInput.nextInt();
            for (int i=0;i<sizeInput;i++){
                st = scannerInput.next();
                if (checkString(st)==true){
                    //a list will be returned
                    //does this put more strain on the heap and memory ?
                    s = special3sort(find1grams(st),find2grams(st),find3grams(st));
                    //YOU CAN STORE THIS ANAGRAM IN MEMORY IF THIS SUBWORD IS AGAIN ENCOUNTERED
                    for (int t = 0 ; t < s.size() ;t++){
                        System.out.println(s.get(t));
                    }
//                    List1 = find1grams(st);//already sorted
//                    //print sorted List1
//                    List2 = find2grams(st);
//                    //print already sorted list 2 and 3
//                    List3 = find3grams(st);
                }
                else {
                    continue;
                }
            }

        }catch (Exception e) {
//            System.out.println("Incorrect file path or file format.");
             e.printStackTrace();
        }
//        return;
    }
    public static void storeVocab(Scanner sc){//my HashT Implementation
        sizeVocab = sc.nextInt();
        int idx1=0,idx2=0;
        Node n;
//        Boolean b = false;
        String str;//= null;//new String ();
        for (int i=0;i<sizeVocab;i++){
            str = sc.next();
            if (checkString(str)==true){
                idx1 = str.length() - 3;
                idx2 = getHash(str);
                n = a[idx1][idx2];
                while(  ((n!=null)) && (checkAnagram(str,n.list.get(0))==false)  ){
                        //till it's not an anagram go to next
                        n = a[idx1][++idx2];//could use 2 skip method or exponetial skip method to prevent
                    //accumulation and clustering
                    }
                //n is null or check anagram is true
                if (n==null){
                    a[idx1][idx2-1] = new Node();
                }
                else{//checkanagram is true and we are set!
                    a[idx1][idx2-1].add(str);//this addition will preserve the lex order
                }
            }
            else {
                continue;
            }
        }
    }
    public static ArrayList<String> find1grams(String str){
        //get the hash of string see if it's present in the table already if yeas the print the elemets of that bucket
        int idx1=0,idx2=0;
        Node n;
        idx1 = str.length() - 3;
        idx2 = getHash(str);
        n = a[idx1][idx2];
        while(  ((n!=null)) && (checkAnagram(str,n.list.get(0))==false)  ){
            //till it's not an anagram go to next
            n = a[idx1][++idx2];//could use 2 skip method or exponetial skip method to prevent
            //accumulation and clustering
        }
        //n is null or check anagram is true
        if (n==null){
            return null;//or empty list ?
        }
        else{//checkanagram is true and we are set!
            return a[idx1][idx2-1].list;//already sorted!
        }
    }
    public static ArrayList<String>find2grams(String str){
        //if memory is exhausted then find combos in the function itself in temp variables then discard
//        ArrayList<String>[] list=new ArrayList<String>()[4];//or an array?
        ArrayList<ArrayList<String>> list=new ArrayList<ArrayList<String>>(4);//or an array?
        //LIST WILL ALWAYS REMAIN SORTEED WILL BE THE LOOP INVARIENT
        //of length k such that ..

        //will the below statement work?
        String [][][] l =new String[4][][];//will have comb of 3 and |S|-3
//        3 dimensional array with first dimenstion as SPLITS LENGTH
        //second dimenstion as N the number of such splits
        // third dimension is 2 for each pair
        for (int k=0;k<4;k++){
            l[k] = findNcomb(str,k+3);//will have comb of 3 and |S|-3
            if (l[k]==null){
                continue;
            }
            ArrayList<String> a1;
            ArrayList<String> a2;
            for (int i=0;i<l[k].length;i++){
                a1 = find1grams(l[k][i][0]);//basically a pointer inside the node
                if (a1 == null){ continue; }

                a2 = find1grams(l[k][i][1]);
                if (a2 == null){ continue; }

                //somehow do ordered addition of cross products to do very fast sorting
                //add the cross prod.in the kth list which was already sorted
                //do addSpecial like MERGER SORT BY COPYING IN A NEW ARRAY

                //or maybe every time copying the whole array is bad and we just sort at the end

                list.get(k).addAll(crossProduct(a1,a2));//cross product will do the cross of lists
//            basically the pointers inside nodes
                //1 more speccial sort here
                //basically add a sorted list to another sorted list;
            }
            list.get(k).sort(String::compareTo);
            //or a special add
        }

        ArrayList<String> listAppended =  specialSort(list);//4 lists to merge very faassssttt
        //can make another function for sorting wich does like the top level of merge sort
        return listAppended;
    }

    public static ArrayList<String> find3grams(String str){
        //will find 1 and 2 grams here..
        ArrayList<ArrayList<String>> list=new ArrayList<ArrayList<String>>(4);//or an array?
        //LIST WILL ALWAYS REMAIN SORTEED WILL BE THE LOOP INVARIENT
        //of length k such that ..

        //will the below statement work?
        String [][][] l =new String[4][][];//will have comb of 3 and |S|-3
//        3 dimensional array with first dimenstion as SPLITS LENGTH
        //second dimenstion as N the number of such splits
        // third dimension is 2 for each pair
        for (int k=0;k<4;k++){
            l[k] = findNcomb(str,k+3);//will have comb of 3 and |S|-3
            if (l[k]==null){
                continue;
            }
            ArrayList<String> a1;
            ArrayList<String> a2;
            for (int i=0;i<l[k].length;i++){
                a1 = find1grams(l[k][i][0]);//basically a pointer inside the node
                if (a1 == null){ continue; }

                a2 = find2grams(l[k][i][1]);//very very cautious
                if (a2 == null){ continue; }

                //somehow do orderd addition of cross products to do very fast sorting
                //add the cross prod.in the kth list which was already sorted
                //do addSpecial like MERGER SORT BY COPYING IN A NEW ARRAY
                //BE CAUTIOUS//cross will put a space automatically

                //or maybe every time copying the whole array is bad and we just sort at the end

                list.get(k).addSpecial(crossProduct(a1,a2));//cross product will do the cross of lists
//            basically the pointers inside nodes
                //1 more speccial sort here
                //basically add a sorted list to another sorted list;
            }
            //or a special add
        }

        ArrayList<String> listAppended =  specialSort(list);//4 lists to merge very faassssttt
        //can make another function for sorting wich does like the top level of merge sort
        return listAppended;
    }

    public static ArrayList<String> crossProduct(ArrayList<String> a1, ArrayList<String>a2){
        //find ways to optimize this
        StringBuilder s ;
        ArrayList<String> a = new ArrayList<String>();
        for (int i=0;i<a1.size();i++){
            for (int j=0;j<a2.size();j++){
                s = new StringBuilder();
                s.append(a1.get(i));
                s.append(" ");
                s.append(a2.get(j));
                a.add(s.toString());
            }
        }
        return a;//
    }
    public static boolean checkString (String str){
        if (str == null){
            return false;
        }
        if ((str.length()<3)||(str.length()>12)){
            return false;
        }

        for (int i=0;i<str.length();i++){
            if ( !( ((str.charAt(i)>='a')&&(str.charAt(i)<='z'))|| ((str.charAt(i)>='0')&&(str.charAt(i)<='9')) || (str.charAt(i)==("'").charAt(0)) )){
                return false;
            }
        }
        return true;
    }

    public static String[][] findNcomb(String query,int length){
        //splits of length k and n-k
        int min = Math.min(length,query.length()-length);//will give function overhead
        if ( (2*min>query.length()) || (min<3) ){
            return null;
        }
        char[] seq = query.toCharArray();
        StringBuilder builder = new StringBuilder();

        for (int j = 0; j<length;j++)
            builder = builder.append(" ");

        //now builder is "____" length spaces

        int[] pos = new int[length];
        int total = (int) Math.pow(query.length(), length);
        for (int i = 0; i < total; i++) {
            for (int x = 0; x < length; x++) {
                if (pos[x] == seq.length) {
                    pos[x] = 0;
                    if (x + 1 < length) {
                        pos[x + 1]++;
                    }
                }
                builder.setCharAt(x, seq[pos[x]]);
            }
            pos[0]++;
            String [] s = new String [2];
            s[0] = builder.toString();
//            s[1]  ;//the complement//;builder.toString();

            System.out.println(s);
        }

        return ;//a k*2 dimensional array with valid combinations
//        System.out.println(total);
    }

    public static int getHash(String str){
        //thinking about prime product modulo P
    }
    public static boolean checkAnagram(String a,String b){//specific only for this problem statement
        int [] counter = new int[37]={0} ;  //'a-z'+'0-9'+'''
        //initialize all to zero
        if (a.length()!=b.length()){
            return false;
        }

        for (int i=0;i<a.length();i++){
            counter[getCode(a.charAt(i))]++;
            counter[getCode(b.charAt(i))]--;
        }
        for (int k=0;k<37;k++){
            if (counter[k]!=0){
                return false;
            }
        }
        return true;
    }
    public static int getCode(char c){

        if ((c<='z')&&(c>='a')){
            return c-'a';
        }
        else if ((c<='9')&&(c>='0')){
//            return (c-'0')+('z'-'a')+1;
            return (c-'0')+26;
        }
        else if(c==("'".charAt(0))){
//            return ('9'-'0')+('z'-'a')+2;//should be 36
            return 36;//should be 36
        }
        else {
            return -1;//error code //shouldnt be this
        }
    }

}
