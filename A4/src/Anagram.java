import java.io.File;
import java.util.*;

public class Anagram {
    public static int sizeVocab ;
    public static int P=19961;//better if we find a prime number closer to the size of vocabulary
    public static int minLength=3;//keep it > 0
    public static int maxLength=12;//
    public static Node[][] a = new Node[maxLength-minLength+1][P];
    //---------------------------------------------------------------------------------20th
    public static int[] Primes = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101,103,107,109,113,127,
            131,137,139,149,151,157,163,167,173,179,181,191,193,197,199,211,223,227,229,233,239,241,251,257, 263,269,271};
    //------------------35th

    public static void main (String args[]){
        //you need to implement this
        Scanner scannerVocab;// = null;
        Scanner scannerInput;// = null;
        File fileVocab;// = null;
        File fileInput;// = null;
        int sizeInput = 0;
        char[] charArr;
        String strSorted;
//        ArrayList<String> s;
        String st;
        long startTime;
        long vocabProcessedTime;
        long inputProcessedTime;
        try {
            startTime = System.currentTimeMillis();
            String vocabFilePath = args[0];
            String inputFilePath = args[1];
            fileVocab = new File(vocabFilePath);
            fileInput = new File(inputFilePath);
//            System.out.println(inputFilePath);
            scannerVocab = new Scanner(fileVocab);
            scannerInput = new Scanner(fileInput);
            storeVocab(scannerVocab);
            vocabProcessedTime = System.currentTimeMillis()-startTime;
            System.out.println("Vocabulary processed in: "+vocabProcessedTime+"ms");
            sizeInput = scannerInput.nextInt();
            for (int i=0;i<sizeInput;i++){
                st = scannerInput.next();
                if (checkString(st)==true){
//                    System.out.println(st);
                    //a list will be returned
                    //does this put more strain on the heap and memory ?
                    charArr = st.toCharArray();
                    //----------------------------------------------------------------find another sorting method-------------DOUBT-----
                    Arrays.sort(charArr);//will it use the appropriate comparator ?
                    StringBuilder sb = new StringBuilder();
                    sb.append(charArr);
                    strSorted = sb.toString();
//                    System.out.println(strSorted);
//
                    merge3AndPrint(find1grams(strSorted),find2grams(strSorted),find3grams(strSorted));
                    //YOU CAN STORE THIS ANAGRAM IN MEMORY IF THIS SUBWORD IS AGAIN ENCOUNTERED
                }
                System.out.println(-1);

            }
            inputProcessedTime = System.currentTimeMillis()-startTime;
            System.out.println("Input processed till: "+inputProcessedTime+"ms");

        }catch (Exception e) {

//            System.out.println(vocabProcessedTime);
//            System.out.println(inputProcessedTime);
            e.printStackTrace();
//            System.out.println("Incorrect file path or file format.");
        }
//        return;
    }

    public static void  merge3AndPrint(ArrayList<String> a1,ArrayList<String> a2,ArrayList<String> a3){
        int i=0,j=0,k=0;//assuming a3 is largest
        ArrayList<String> merged = new ArrayList<String>();
//        System.out.println("A1 size in merge = " + a1.size() );
//        System.out.println("A2 size in merge = " + a2.size() );
//        System.out.println("A3 size in merge = " + a3.size() );
        //can write a faster algorithm
        if ((a2==null)){
            merged = a1;//null or not
        }
        else if (a1 ==null){
            merged = a2;//null or not
        }
        else {
            while (true) {
                if (i >= a1.size() ) {//a1 is expired
                    merged.addAll(j, a2);
                    break;
                }
                if (j >= a2.size() ) {//a2 is expired
                    merged.addAll(i, a2);
                    break;
                }
                if (a1.get(i).compareTo(a2.get(j)) <= 0) {
                    merged.add(a1.get(i));
                    i++;
                } else {
                    merged.add(a2.get(j));
                    j++;
                }
            }
        }

        i=0;j=0;
        if (merged==null){
            //print a3 and go out
            for (int a=0 ; a<a3.size() ; a++){
                System.out.println(a3.get(a));
            }
            return;
        }
        else if (a3 == null){
            //print merged and go out
            for (int a=0 ; a<merged.size() ; a++){
                System.out.println(merged.get(a));
            }
            return;
        }
        else {
            while (true) {
                if (i >= merged.size() ) {//merged is expired
//                merged.addAll(j,a2);
                    for (k = j; k < a3.size(); k++) {
                        System.out.println(a3.get(k));
                    }
                    break;
                }
                if (j >= a3.size() ) {//a3 is expired
//                merged.addAll(i,a2);
                    for (k = i; k < merged.size(); k++) {
                        System.out.println(merged.get(k));
                    }
                    break;
                }
                if (merged.get(i).compareTo(a3.get(j)) <= 0) {
//                merged.add(merged.get(i));
                    System.out.println(merged.get(i));
                    i++;
                } else {// a3's current element < merged's current element
//                merged.add(a2.get(j));
                    System.out.println(a3.get(j));
                    j++;
                }
            }
        }
    }

    public static void storeVocab(Scanner sc){//my HashT Implementation
        sizeVocab = sc.nextInt();
        int idx1=0,idx2=0;
        Node n;
        String str;//= null;//new String ();
        int flag;
        for (int i=0;i<sizeVocab;i++){
//            System.out.println(i);
            flag = 0;
            str = sc.next();
            if (checkString(str)==true){
                idx1 = str.length() - minLength;
                idx2 = getHash(str);
                n = a[idx1][idx2];
//                while(n != null){
//                    n = a[idx1][++idx2];//could use 2 skip method or exponential skip method to prevent
//                }
                if (n!=null){
                    while ((checkAnagram(str, n.list.get(0)) == false)) {
                        //till it's not an anagram go to next
                        idx2%=Anagram.P;
                        n = a[idx1][++idx2];//could use 2 skip method or exponential skip method to prevent
                        //accumulation and clustering
                        flag = 1;
                        if (n==null){
                            break;
                        }
                    }
                }
                idx2 = flag==1?idx2-1:idx2;
//                idx2--;
                n = a[idx1][idx2];
//                idx2 = idx2>1?idx2-1:idx2;
                //n is null or check anagram is true

                if (n==null){
                    a[idx1][idx2] = new Node();
                    a[idx1][idx2].add(str);
                    a[idx1][idx2].printNode();
                }
                else{//checkanagram is true and we are set!
//                    try {
                    a[idx1][idx2].add(str);//this addition will preserve the lex order
                    a[idx1][idx2].printNode();

//                    }
//                    catch (Exception e){
//                        System.out.println(str);
//                        System.out.println(idx2);
//                        System.out.println(a[idx1][idx2]);
//                        e.printStackTrace();
//                        System.exit(0);
//                    }
                }

            }
//            else {
//                continue;
//            }
        }
    }

    public static ArrayList<String> find1grams(String str){
        //get the hash of string see if it's present in the table already if yeas the print the elements of that bucket

        if ((str.length()<minLength)||(str.length()>maxLength)){
            return new ArrayList<String>();
        }
        int idx1=0,idx2=0;
        Node n;
        idx1 = str.length() - minLength;
        idx2 = getHash(str);
        n = a[idx1][idx2];
        int flag=0;
        if (n!=null){
            while ((checkAnagram(str, n.list.get(0)) == false)) {
                //till it's not an anagram go to next
                idx2%=Anagram.P;
                n = a[idx1][++idx2];//could use 2 skip method or exponential skip method to prevent
                //accumulation and clustering
                flag = 1;
                if (n==null){
                    break;
                }
            }
//            idx2--;
        }
        idx2 = flag==1?idx2-1:idx2;

        n = a[idx1][idx2];
//
        //n is null or check anagram is true
        if (n==null){
            return new ArrayList<String>();//or empty list ?
        }
        else{//checkanagram is true and we are set!
            return a[idx1][idx2].list;//already sorted!
        }
    }

    public static ArrayList<String>find2grams(String str){
        //assume called on a sorted string
//        System.out.println("String in find2 grams is: "+str);
        int tl = str.length();
        if ((tl<minLength)||(tl>maxLength)){
            return null;
        }
        //assume the string is sorted
//        char[] charArr = s.toCharArray();
//        //----------------------------------------------------------------find another sorting method-------------DOUBT-----
//        Arrays.sort(charArr);//will it use the appropriate comparator ?
//        StringBuilder sb = new StringBuilder();
//        sb.append(charArr);
//        String str = sb.toString();
////        str.sort(String::compareTo);


        int i=0,j=0;
        int count=0;
        boolean bit ;
        ArrayList<String> a1;
        ArrayList<String> a2;
        ArrayList<String> list=new ArrayList<String>();
        StringBuilder b1 ;//= new StringBuilder();
        StringBuilder b2 ;//= new StringBuilder();
        int t=0;
        for (i=0 ; i < Math.pow(2,tl) ; i++){
            t = i;
            count=0;
            while(t!=0){//O(log(n))
                t = t&(t-1);
                count++;
            }
            //count has its true value
            if ((count<minLength)||(count>maxLength)){
                continue;
            }
            //clear them
            b1 = new StringBuilder();
            b2 = new StringBuilder();
            for ( j=0 ; j<tl;j++){
                bit = ((i>>j)&1)==1;//?true:false;
                if (bit){//bit is 1
                    b2.append(str.charAt(j));
                }
                else{//bit is 0
                    b1.append(str.charAt(j));
                }
            }
            //now builder 1 and 2 are good to go
            a1 = find1grams(b1.toString());
            if ( (a1==null)||(a1.size()==0) ){//i am relying on that java will not evaluate the 2nd condition if first is true
                continue;
            }
            a2 = find1grams(b2.toString());
            if ( (a2==null)||(a2.size()==0) ){
                continue;
            }
            //now lists are good to go
            //and this list therefore will already be sorted and will also contain a1 cross a2 and sometime
//            a2Xa1
            list.addAll(crossProduct(a1,a2));//auto sorted?

        }
        return list;
//        //if memory is exhausted then find combos in the function itself in temp variables then discard
////        ArrayList<String>[] list=new ArrayList<String>()[4];//or an array?
//        ArrayList<ArrayList<String>> list=new ArrayList<ArrayList<String>>(4);//or an array?
//        //LIST WILL ALWAYS REMAIN SORTEED WILL BE THE LOOP INVARIENT
//        //of length k such that ..
//
//        //will the below statement work?
//        String [][][] l =new String[4][][];//will have comb of 3 and |S|-3
////        3 dimensional array with first dimenstion as SPLITS LENGTH
//        //second dimenstion as N the number of such splits
//        // third dimension is 2 for each pair
//        for (int k=0;k<4;k++){
//            l[k] = findNcomb(str,k+3);//will have comb of 3 and |S|-3
//            if (l[k]==null){
//                continue;
//            }
//            ArrayList<String> a1;
//            ArrayList<String> a2;
//            for (int i=0;i<l[k].length;i++){
//                a1 = find1grams(l[k][i][0]);//basically a pointer inside the node
//                if (a1 == null){ continue; }
//
//                a2 = find1grams(l[k][i][1]);
//                if (a2 == null){ continue; }
//
//                //somehow do ordered addition of cross products to do very fast sorting
//                //add the cross prod.in the kth list which was already sorted
//                //do addSpecial like MERGER SORT BY COPYING IN A NEW ARRAY
//
//                //or maybe every time copying the whole array is bad and we just sort at the end
//
//                //------------------------------------------------------------------------------
//                //what about all a2 cross a1?
//                list.get(k).addAll(crossProduct(a1,a2));//cross product will do the cross of lists
////            basically the pointers inside nodes
//                //1 more special sort here
//                //basically add a sorted list to another sorted list;
//            }
//            list.get(k).sort(String::compareTo);
//            //or a special add
//        }
//
//        ArrayList<String> listAppended =  specialSort(list);//4 lists to merge very faassssttt
//        //can make another function for sorting wich does like the top level of merge sort
//        return listAppended;
    }

    public static ArrayList<String> find3grams(String str){
        //assume the string is sorted
        //will find 1 and 2 grams here..

        int tl = str.length();
        //no need of below somewhat as already checked
//        if ((tl<3)||(tl>12)){
//            return null;
//        }
//        char[] charArr = s.toCharArray();
//        //----------------------------------------------------------------find another sorting method-------------DOUBT-----
//        Arrays.sort(charArr);//will it use the appropriate comparator ?
//        StringBuilder sb = new StringBuilder();
//        sb.append(charArr);
//        String str = sb.toString();
////        str.sort(String::compareTo);


        int i=0,j=0;
        int count=0;
        boolean bit ;
        ArrayList<String> a1;
        ArrayList<String> a2;
        ArrayList<String> list=new ArrayList<String>();
        StringBuilder b1 ;//= new StringBuilder();
        StringBuilder b2 ;//= new StringBuilder();
        int t=0;
        for (i=0 ; i < Math.pow(2,tl) ; i++){
            t = i;
            count=0;
            while(t!=0){//O(logn)
                t = t&(t-1);
                count++;
            }
            //count has it's true value
            if ((count<minLength)||(count>maxLength)){
                continue;
            }
            //clear them
            b1 = new StringBuilder();
            b2 = new StringBuilder();
            for ( j=0 ; j<tl;j++){
                bit = ((i>>j)&1)==1;//?true:false;
                if (bit){//bit is 1
                    b2.append(str.charAt(j));
                }
                else{//bit is 0
                    b1.append(str.charAt(j));
                }
            }
            //now builder 1 and 2 are good to go
            a1 = find1grams(b1.toString());
            if ( (a1==null)||(a1.size()==0) ){//i am relying on that java will not evaluate the 2nd condition if first is true
                continue;
            }
            a2 = find2grams(b2.toString());
            if ( (a2==null)||(a2.size()==0) ){
                continue;
            }
            //now lists are good to go
            //and this list therefore will already be sorted and will also contain a1 cross a2 and sometime
//            a2Xa1 will come in due time

            list.addAll(crossProduct(a1,a2));//auto sorted?

        }
        return list;
//        ArrayList<ArrayList<String>> list=new ArrayList<ArrayList<String>>(4);//or an array?
//        //LIST WILL ALWAYS REMAIN SORTEED WILL BE THE LOOP INVARIENT
//        //of length k such that ..
//
//        //will the below statement work?
//        String [][][] l =new String[4][][];//will have comb of 3 and |S|-3
////        3 dimensional array with first dimenstion as SPLITS LENGTH
//        //second dimenstion as N the number of such splits
//        // third dimension is 2 for each pair
//        for (int k=0;k<4;k++){
//            l[k] = findNcomb(str,k+3);//will have comb of 3 and |S|-3
//            if (l[k]==null){
//                continue;
//            }
//            ArrayList<String> a1;
//            ArrayList<String> a2;
//            for (int i=0;i<l[k].length;i++){
////                /---------------------------
//                //also do opposite // ie find 1 grams of a2 and 2 grams of a1
//                a1 = find1grams(l[k][i][0]);//basically a pointer inside the node
//                if (a1 == null){ continue; }
//
//                a2 = find2grams(l[k][i][1]);//very very cautious
//                if (a2 == null){ continue; }
//
//                //somehow do orderd addition of cross products to do very fast sorting
//                //add the cross prod.in the kth list which was already sorted
//                //do addSpecial like MERGER SORT BY COPYING IN A NEW ARRAY
//                //BE CAUTIOUS//cross will put a space automatically
//
//                //or maybe every time copying the whole array is bad and we just sort at the end
//
//                list.get(k).addSpecial(crossProduct(a1,a2));//cross product will do the cross of lists
////            basically the pointers inside nodes
//                //1 more speccial sort here
//                //basically add a sorted list to another sorted list;
//            }
//            //or a special add
//        }
//
//        ArrayList<String> listAppended =  specialSort(list);//4 lists to merge very faassssttt
//        //can make another function for sorting wich does like the top level of merge sort
//        return listAppended;
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
        if ((str.length()<minLength)||(str.length()>maxLength)){
            return false;
        }

        for (int i=0;i<str.length();i++){
            if ( !( ((str.charAt(i)>='a')&&(str.charAt(i)<='z'))|| ((str.charAt(i)>='0')&&(str.charAt(i)<='9')) || (str.charAt(i)==("'").charAt(0)) )){
                return false;
            }
        }
        return true;
    }

    public static int getHash(String str){
        //thinking about prime product modulo P
        int res = 1;
        //OR I COULD ALSO ADD 71^CODE OF CHAR AND SUM THEM UP!
        for (int i=0;i<str.length();i++){
            res = ((res)*Anagram.Primes[getCode(str.charAt(i))])%P;
        }
        return res;
    }

    public static boolean checkAnagram(String a,String b){//specific only for this problem statement
        //array of 37 zeroes ie indices are 0 to 36
        //is this faster than for loop ?
        int [] counter = new int[]{0,0,0,0,0,0,0,0,0,
                                   0,0,0,0,0,0,0,0,0,
                                   0,0,0,0,0,0,0,0,0,
                                   0,0,0,0,0,0,0,0,0,0} ;  //'a-z'+'0-9'+'''
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

    public static int getCode(char c){//returns 0 to 36 under normal circumstances

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

    //    public static String[][] findNcomb(String query,int length){
//        //splits of length k and n-k
//        int min = Math.min(length,query.length()-length);//will give function overhead
//        if ( (2*min>query.length()) || (min<3) ){
//            return null;
//        }
//        char[] seq = query.toCharArray();
//        StringBuilder builder = new StringBuilder();
//
//        for (int j = 0; j<length;j++)
//            builder = builder.append(" ");
//
//        //now builder is "____" length spaces
//
//        int[] pos = new int[length];
//        int total = (int) Math.pow(query.length(), length);
//        for (int i = 0; i < total; i++) {
//            for (int x = 0; x < length; x++) {
//                if (pos[x] == seq.length) {
//                    pos[x] = 0;
//                    if (x + 1 < length) {
//                        pos[x + 1]++;
//                    }
//                }
//                builder.setCharAt(x, seq[pos[x]]);
//            }
//            pos[0]++;
//            String [] s = new String [2];
//            s[0] = builder.toString();
////            s[1]  ;//the complement//;builder.toString();
//
//            System.out.println(s);
//        }
//
//        return ;//a k*2 dimensional array with valid combinations
////        System.out.println(total);
//    }
}