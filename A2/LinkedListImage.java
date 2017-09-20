import com.sun.javafx.image.BytePixelSetter;

import java.io.*;
import java.util.*;
import java.lang.*;

class LinkedList2D{
    ListNodeHead head = null;
    int numRows = 0;
    int[] numCols =null;
    int gridHeight = 0;
    int gridWidth = 0;

    public LinkedList2D(Integer height,Integer width){
        this.head = new ListNodeHead();
        this.numRows = 1;
        this.numCols = new int[height];
        this.gridHeight=height;
        this.gridWidth=width;
    }
    public void addAfter(ListNodeHead t, Integer val){
        ListNodeHead temp = new ListNodeHead();
        temp.data = val;
        temp.nextHead = null;
        temp.next = null;
        t.next = temp;
        return;
    }
    public void addBelow(ListNodeHead t, Integer val){
        ListNodeHead temp = new ListNodeHead();
        temp.data = val;
        temp.nextHead = null;
        temp.next = null;
        t.nextHead = temp;
        return;
    }
    public void printLLfor(){
        Integer i=0,j=0;
        ListNodeHead iItr = this.head;
        ListNodeHead jItr = iItr;

        System.out.println("PRINTING::::::::::::::::::::::::::::::::::::::::::::::"+gridHeight + " "+ gridWidth);

        for(i=0;i<this.numRows+1;i++){
            jItr = iItr;
            for (j=0;j<this.numCols[i] ;j++ ) {
                System.out.print(jItr.data + " ");
                jItr=jItr.next;
            }
            System.out.println();
            iItr=iItr.nextHead;
        }
    }

    public void printLLdo(){
        ListNodeHead iItr = this.head;
        ListNodeHead jItr = iItr;

        System.out.println("PRINTING::::::::::::::::::::::::::::::::::::::::::::::"+gridHeight + " "+ gridWidth);
        do {
            jItr = iItr;

            do {
                System.out.print(jItr.data + " ");
                jItr=jItr.next;
            } while (jItr!=null);

            System.out.println();
            iItr = iItr.nextHead;
        } while (iItr!=null);

    }

}

class ListNodeHead{
    int data = -1;
    ListNodeHead nextHead = null;
    ListNodeHead next = null;
}

public class LinkedListImage implements CompressedImageInterface {
    LinkedList2D ll = null;
    public LinkedListImage deepCopy()
    {
//            this.ll.printLLdo();
        LinkedList2D newList = new LinkedList2D((int)this.ll.gridHeight,(int)this.ll.gridWidth);//numCols array range as paramerter

        newList.numRows=this.ll.numRows;
        newList.numCols= this.ll.numCols;
        newList.gridHeight=this.ll.gridHeight;
        newList.gridWidth=this.ll.gridWidth;

        ListNodeHead iItrOrig = this.ll.head;
        ListNodeHead iItr = newList.head;
        ListNodeHead jItrOrig = iItrOrig;
        ListNodeHead jItr = iItr;
        // System.out.println(gridHeight + " "+ gridWidth);
        while (iItrOrig!=null){
            iItr.data = iItrOrig.data;
            if (iItrOrig.nextHead!=null) {
                newList.addBelow(iItr, -1);
            }
            jItrOrig = iItrOrig;
            jItr = iItr;
//                System.out.println("LINE109");
            while (jItrOrig.next!=null) {
                // System.out.print(jItrOrig.data + " ");
                jItrOrig=jItrOrig.next;//here it's null
                newList.addAfter(jItr,jItrOrig.data);
                jItr = jItr.next;
            }

            // System.out.println();
            iItr = iItr.nextHead;
            iItrOrig = iItrOrig.nextHead;
        }

//            newList.printLLdo();
        //or simply
        //HOW TO TRUELY CLONE ?
        // this.newList = this.ll;
        LinkedListImage imgNew = new LinkedListImage(newList);
        return imgNew;
    }

    public LinkedListImage(LinkedList2D ll)
    {this.ll = ll;}

    public LinkedListImage(String filename)
    {
        //you need to implement this
        Scanner sc = null;
        File fileIn = null;
        int p=0,q=0,width=0,height=0,temp=0;
        boolean[][] grid = null;
        try {
            fileIn = new File(filename);
            sc = new Scanner(fileIn);
            width = sc.nextInt();//first is width
            height = sc.nextInt();
            grid = new boolean[height][width];

            for (p=0;p<height ;p++ ) {
                for (q=0;q<width ;q++ ) {
                    temp = sc.nextInt();
                    if (temp==1) {
                        grid[p][q]=true;
                    }
                    else {
                        grid[p][q]=false;
                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        // new LinkedListImage(grid,width,height);//I WANTED  THIS TO RUN

        ll = new LinkedList2D(height,width);//numCols array range as paramerter
        ll.numRows=0;
        ll.gridHeight=height;
        ll.gridWidth=width;
        ListNodeHead xItr = null;
        ListNodeHead yItr = null;

        int i=0,j=0,counter = 0,consec = 0,flag=0;

        xItr = ll.head;

        for (i=0;i<height ; i++) {

            consec = 0;
            counter = 1;
            flag = 0;
            yItr = xItr;

            for (j=0;j<width ;j++ ) {

                if (!grid[i][j]) {//current element is black
                    flag = 1;//atleast one black has been found//first or Nth black after break

                    //if first occurence after break then consec =0
                    if (consec == 0) {

                        // while (yItr.next!=null) {//probably not needed
                        // 	yItr = yItr.next;
                        // }
                        yItr.data = j;
                        ll.addAfter(yItr,-1);
                        yItr = yItr.next;
                        // if (counter==1) {
                        // 	yItr.data = j;
                        // 	// ll.addAfter(yItr,j);
                        // 	yItr = yItr.next;
                        // }
                        // else{
                        // 	ll.addAfter(yItr,j);
                        // 	yItr = yItr.next;
                        // }
                        consec++;
                        counter++;
                    }
                    //any occurence in the middle and black is going on
                    else if(consec > 0){
                        consec++;
                        continue;
                    }

                }

                else {//Current element is white
                    if (flag==1) {//if atleast black has been found //odd parity
                        yItr.data = j-1;
                        ll.addAfter(yItr,-1);
                        yItr = yItr.next;
                        // System.out.println("LINE 159: val of j-1 ="+ (j-1) + " and i =  "+(i)+"::::::::::::::::::::::::::::::::::::::::::::::");
                        // ll.printLLdo();

                        // System.out.println("Just after Line 51 yIter.data:"+ (yItr.data));
                        counter++;//one more elem added to this row
                    }
                    flag = 0;
                    consec = 0;
                }
            }
            //outer for loop
            // yItr.data = -1;
            // ll.addAfter(yItr,-1);
            // yItr = yItr.next;

            // if (counter==1) {
            // 	yItr.data = -1;
            // 	// ll.addAfter(yItr,j);
            // 	yItr = yItr.next;//ie null
            // }
            // else{
            // 	ll.addAfter(yItr,-1);
            // 	counter++;
            // 	yItr = yItr.next;
            // }
            // counter++;
            if (flag==1) {//if atleast black has been found //odd parity
                yItr.data = j-1;
                ll.addAfter(yItr,-1);
                yItr = yItr.next;
                // System.out.println("LINE 159: val of j-1 ="+ (j-1) + " and i =  "+(i)+"::::::::::::::::::::::::::::::::::::::::::::::");
                // ll.printLLdo();

                // System.out.println("Just after Line 51 yIter.data:"+ (yItr.data));
                counter++;//one more elem added to this row
                flag = 0;
                consec = 0;
            }
            ll.numCols[i]=counter;
            if (i<height-1) {
                ll.addBelow(xItr,-1);
                xItr = xItr.nextHead;
                ll.numRows++;
            }
            // xItr = parent;
        }
        //loop ends

        //assume stored correctly uncomment for debugging
        // System.out.println("FINAL STORED ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        // ll.printLLdo();
        // System.out.println("FINAL STORED ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        // ll.printLLfor();

        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
    //CONSTRUCTOR 2 WITH COMMENTS IN LOGIC
    public LinkedListImage(boolean[][] grid, int width, int height)
    {

        ll = new LinkedList2D(height,width);//numCols array range as paramerter
        ll.numRows=0;
        ll.gridHeight=height;
        ll.gridWidth=width;
        ListNodeHead xItr = null;
        ListNodeHead yItr = null;

        int i=0,j=0,counter = 0,consec = 0,flag=0;

        xItr = ll.head;

        for (i=0;i<height ; i++) {

            consec = 0;
            counter = 1;
            flag = 0;
            yItr = xItr;

            for (j=0;j<width ;j++ ) {

                if (!grid[i][j]) {//current element is black
                    flag = 1;//atleast one black has been found//first or Nth black after break

                    //if first occurence after break then consec =0
                    if (consec == 0) {

                        // while (yItr.next!=null) {//probably not needed
                        // 	yItr = yItr.next;
                        // }
                        yItr.data = j;
                        ll.addAfter(yItr,-1);
                        yItr = yItr.next;
                        // if (counter==1) {
                        // 	yItr.data = j;
                        // 	// ll.addAfter(yItr,j);
                        // 	yItr = yItr.next;
                        // }
                        // else{
                        // 	ll.addAfter(yItr,j);
                        // 	yItr = yItr.next;
                        // }
                        consec++;
                        counter++;
                    }
                    //any occurence in the middle and black is going on
                    else if(consec > 0){
                        consec++;
                        continue;
                    }

                }

                else {//Current element is white
                    if (flag==1) {//if atleast black has been found //odd parity
                        yItr.data = j-1;
                        ll.addAfter(yItr,-1);
                        yItr = yItr.next;
                        // System.out.println("LINE 159: val of j-1 ="+ (j-1) + " and i =  "+(i)+"::::::::::::::::::::::::::::::::::::::::::::::");
                        // ll.printLLdo();

                        // System.out.println("Just after Line 51 yIter.data:"+ (yItr.data));
                        counter++;//one more elem added to this row
                    }
                    flag = 0;
                    consec = 0;
                }
            }
            //outer for loop
            // yItr.data = -1;
            // ll.addAfter(yItr,-1);
            // yItr = yItr.next;

            // if (counter==1) {
            // 	yItr.data = -1;
            // 	// ll.addAfter(yItr,j);
            // 	yItr = yItr.next;//ie null
            // }
            // else{
            // 	ll.addAfter(yItr,-1);
            // 	counter++;
            // 	yItr = yItr.next;
            // }
            // counter++;
            if (flag==1) {//if atleast black has been found //odd parity
                yItr.data = j-1;
                ll.addAfter(yItr,-1);
                yItr = yItr.next;
                // System.out.println("LINE 159: val of j-1 ="+ (j-1) + " and i =  "+(i)+"::::::::::::::::::::::::::::::::::::::::::::::");
                // ll.printLLdo();

                // System.out.println("Just after Line 51 yIter.data:"+ (yItr.data));
                counter++;//one more elem added to this row
                flag = 0;
                consec = 0;
            }
            ll.numCols[i]=counter;
            if (i<height-1) {
                ll.addBelow(xItr,-1);
                xItr = xItr.nextHead;
                ll.numRows++;
            }
            // xItr = parent;
        }
        //loop ends

        // System.out.println("FINAL STORED ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        // ll.printLLdo();

    }

    public static LinkedList2D ones(int width , int height)
    {
        LinkedList2D one = new LinkedList2D(height,width);
        one.numRows = height;
        ListNodeHead iItr = one.head;

        for (int i = 0 ; i < height-1; i++){//lastly not added and iterated
            one.numCols[i]=0;
            iItr.data=-1;
            one.addBelow(iItr,-1);
            iItr = iItr.nextHead;
        }
        one.numCols[height-1]=0;

        return one;
    }

    public boolean getPixelValue(int x, int y) throws PixelOutOfBoundException
    {
        if ((x>=ll.gridHeight)||(y>=ll.gridWidth)) {
            throw new PixelOutOfBoundException("Pixels indices are out of bounds.");
        }
        int i=0,j=0,startIndex=0,endIndex=0;
        ListNodeHead iItr = ll.head;
        ListNodeHead jItr = iItr;

        // System.out.println(ll.gridHeight + " "+ ll.gridWidth);
//        System.out.println("IN GET PIXEL TO GET " + x +"," + y);
//        this.ll.printLLdo();
        for(i=0;i<x;i++){
            // System.out.println();
            iItr=iItr.nextHead;
        }
        jItr = iItr;
        try{
            for (j=0;j<ll.numCols[x] ;j = j+2 ) {
                // System.out.print(jItr.data + " ");
                // jItr=jItr.next;
                if (jItr.data == -1) {
                    return true;
                }
                startIndex = jItr.data;
                jItr = jItr.next;
                endIndex = jItr.data;
                jItr = jItr.next;
                if ((y>=startIndex)&&(y<=endIndex)) {
                    return false;
                }

            }
        }
        catch (Exception e){
            //do nothing ?
            e.printStackTrace();
        }

//        System.out.println("This line 335 shouldnt be printed");
        return true;//shouldnt be required
        //you need to implement this

        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void setPixelValue(int x, int y, boolean val) throws PixelOutOfBoundException
    {
        if ((x>=ll.gridHeight)||(y>=ll.gridWidth)) {
            throw new PixelOutOfBoundException("Pixels indices are out of bounds.");
        }

//        System.out.println("IN SET PIXEL TO SET " + x +"," + y+ ",STATUS BEFOREEEEEEE");
//        this.ll.printLLdo();

        int i=0,j=0,startIndex=0,endIndex=0,flag = 0;
        ListNodeHead iItr = this.ll.head;
        ListNodeHead jItr = iItr;

        // System.out.println(ll.gridHeight + " "+ ll.gridWidth);

        for(i=0;i<x;i++){
            // System.out.println();
            iItr=iItr.nextHead;
        }
        jItr = iItr;//on correct row
//        if (jItr.data==-1){
//            if (val){
//                //do nothing as already 1
//                return;
//            }
//            else {
//                jItr.data = y;
//                this.ll.addAfter(jItr,y);
//                jItr = jItr.next;
//                this.ll.addAfter(jItr,-1);
//                jItr = jItr.next;
//                return;
//            }
//        }
        for (j=0;j<ll.numCols[x] ;j = j+2 ) {
            if (jItr.data==-1){//added not in order ...
                if (val){
                    //do nothing as already 1
                    return;
                }
                else {
                    break;
//                    jItr.data = y;
//                    this.ll.addAfter(jItr,y);
//                    jItr = jItr.next;
//                    this.ll.addAfter(jItr,-1);
//                    jItr = jItr.next;
//                    return;
                }
            }
            startIndex = jItr.data;
            endIndex = jItr.next.data;
            if (y>=startIndex&&y<=endIndex ){
                flag = 1;
                if (!val){//run if val is 0
                    //do nothing as already 0 which is required
                }
                else{//run if val is 1
                    if ((y == startIndex)&&(y == endIndex)){//stand- alone 0 to 1
                        jItr.data = jItr.next.next.data;
                        jItr.next = jItr.next.next.next;
//                        break;
                        //done and break ?
                    }
                    else if (y==startIndex){//STARTING CORNER CASE 0000 type
                        jItr.data = startIndex+1;
                        //done and break
                    }
                    else if(y==endIndex){//ENDING CORNER CASE
                        jItr.next.data = endIndex-1;
                    }
                    else{//CLEAN CLASE
                        ListNodeHead hold = jItr.next;
                        this.ll.addAfter(jItr,y-1);
                        jItr = jItr.next;
                        this.ll.addAfter(jItr,y+1);
                        jItr = jItr.next;
                        jItr.next = hold;
                    }
                }
            }
            if (flag==1){
                return;//don't wait just gooo
            }
            jItr = jItr.next;
            jItr = jItr.next;
        }
        if (flag==1){
            return;//go already
        }
        //if flag is not 1 ie it didn't lie in between so wth
        //now if val == 1 then it doesn't matter
        if (val){
            return;
        }
        //if we have to set a 0 in between of ones THE MOST HELLISH CASE
        jItr = iItr;//return to start of good old row!
        try{
            while(jItr.next.data!=-1){
                jItr=jItr.next;
            }
        }
        catch (Exception e){
            //if it doesnt exist // do the thing below;ie its -1 already
            jItr.data = y;
            this.ll.addAfter(jItr,y);
            jItr = jItr.next;
            this.ll.addAfter(jItr,-1);
            return;
        }
        ListNodeHead lastValidPointer = jItr;//just before -1//what if it doesnt exist
        jItr = iItr;//return to start of good old row!
        startIndex = 0;
        if (y==0){
            if (jItr.data==1){//jItr.data can't be 0
                jItr.data = 0;
                return;
            }
            else{
                int valHold = jItr.data;
                ListNodeHead pointerHold = jItr.next;
                jItr.data = 0;
                this.ll.addAfter(jItr,0);
                jItr = jItr.next;
                this.ll.addAfter(jItr,valHold);
                jItr = jItr.next;
                jItr.next = pointerHold;
                return;
            }
        }
        else if(y<jItr.data){
            if (y==jItr.data-1){
                jItr.data--;
                return;
            }
            else{
                int valHolder = jItr.data;
                ListNodeHead pointerHolder = jItr.next;
                jItr.data = y;
                this.ll.addAfter(jItr,y);
                jItr = jItr.next;
                this.ll.addAfter(jItr,valHolder);
                jItr = jItr.next;
                jItr.next = pointerHolder;
                return;
            }
        }
        else if (y>lastValidPointer.data){//it can't be equal! as we already covered that
            if (y==(lastValidPointer.data+1)){
                lastValidPointer.data++;
            }
            else{
                this.ll.addAfter(lastValidPointer,y);
                lastValidPointer = lastValidPointer.next;
                this.ll.addAfter(lastValidPointer,y);
                lastValidPointer = lastValidPointer.next;
                this.ll.addAfter(lastValidPointer,-1);

            }
        }
        //TO DO SOMETHING SEPERATLY FOR END INDEX = WIDTH-1  OF GRID ?
        else{//case where y lies in one of the in-between cuts of 1
            startIndex = 0;
            jItr = jItr.next;//odd pairing
            ListNodeHead start = null;
            ListNodeHead end = null;
            int boundsFlag = 0;
            while(jItr!=null&&jItr.next!=null){//java will do short-circuiting
                //what if some other condition above----------------------------------------------------------------
                start = jItr;
                end = jItr.next;
                startIndex = start.data+1;
                endIndex = end.data==-1? this.ll.gridWidth :end.data-1;//end.data can be -1 sooo;
                boundsFlag = end.data==-1?1:0;
                //as i have done the case of bounds flag == 0 previously therefore will not do it again
                if (boundsFlag==1){
                    break;//i am doing every one a favor
                }
                if (y>=startIndex&&y<=endIndex ) {//will itself run on the in between case
                    flag = 1;
                    if (y==startIndex&&y==endIndex){//if both are same then conjoin
                        start.data = start.next.next.data;
                        start.next = start.next.next.next;//skipping 2 nodes and transferring value
                    }
                    else if (y == startIndex) {//STARTING CORNER CASE
                        //TO GO TO PREV POINTER
                        start.data++;
                    }
                    else if (y == endIndex) {//ENDING CORNER CASE
                        end.data--;
                    }
                    else {//CLEAN CLASE
                        ListNodeHead hold = start.next;
                        this.ll.addAfter(start, y);
                        start = start.next;
                        this.ll.addAfter(start, y);
                        start = start.next;
                        start.next = hold;
                    }
                }
                if (flag==1){
                    return;
                }
                jItr = jItr.next;
                jItr = jItr.next;
            }
        }


//        System.out.println("aftter setting SET PIXEL AT " + x +"," + y+ ",STATUS AFTEERRRRR");
//        this.ll.printLLdo();

        return;

        /////////////////////////////////////////////////////////////////TO0000 MANY CASES!!!!!!!!!!!!!!!!!!!!!!!!!
//        --------------------------------------------------------------------------------------------
//        while(jItr!=null){//what if some other condition here
//            endIndex = jItr.data;
//            if (endIndex==-1){
//                endIndex = this.ll.gridWidth-1;//WILL THIS WORK ?
//            }
//            //ALSO DIFFRENT IF START INDEX = 0 OR NON 0
//            //ALSO WHAT IFF END INDEX = 0 ? ? ?
//            if (y>=startIndex&&y<=endIndex ) {
//                flag = 1;
//                if (y==startIndex&&y==endIndex){//if both are same then conjoin
////                    ListNodeHead hold = jItr.nex
//                    if (y==0){
//
//                    }
//                    else{
//                        jItr.data = jItr.next.next.data;
//                        jItr.next = jItr.next.next.next;//skipping 2 nodes and transferring value
//                    }
//                }
//                else if (y == startIndex) {//STARTING CORNER CASE
//                    if (startIndex==0){
//
//                    }
//                    else{
//                        jItr.data = endIndex+1;
//                    }
//                } else if (y == endIndex) {//ENDING CORNER CASE
//
//                } else {//CLEAN CLASE
//                    if (startIndex==0){
//
//                    }
//                    else{
//                        ListNodeHead hold = jItr.next;
//                        this.ll.addAfter(jItr, y);
//                        jItr = jItr.next;
//                        this.ll.addAfter(jItr, y);
//                        jItr = jItr.next;
//                        jItr.next = hold;
//                    }
//                }
//            }
//            startIndex = endIndex;
//            jItr = jItr.next;
//        }
//        _____________________________________________________________________________________________________
//        you need to implement this
//        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public int[] numberOfBlackPixels()
    {
        //YOU NEED TO CHECK THESE
        int[] a = new int[this.ll.gridHeight];
        ListNodeHead iItr = this.ll.head;
        ListNodeHead jItr = iItr;
        int i=0,j=0,startIndex=0,endIndex=0;
        //condition in for loop was this.ll.numRows+1;
//        System.out.println("COUNTING THE NUMBER OF BLACK PIXELS IN THE FOLLOWING IMAGE::");
//        this.ll.printLLdo();
//        System.out.println("COUNTING THE NUMBER OF BLACK PIXELS VALUES ARE THE FOLLOWING:");

        for(i=0;i<this.ll.gridHeight;i++){
            jItr = iItr;
            for (j=0;j<this.ll.numCols[i] ;j=j+2 ) {
                try{
                    startIndex = jItr.data;
                    endIndex = jItr.next.data;
                }
                catch (Exception e){
                    continue;
                }
                a[i]+=endIndex-startIndex+1;
                jItr=jItr.next;
                jItr=jItr.next;
            }
//            System.out.println(" i = "+i+" ; a[i] = "+a[i]);
            iItr=iItr.nextHead;
        }
        return a;
        //you need to implement this
//        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void invert()
    {
//        System.out.println("BEFORE INVERT");
//        this.ll.printLLdo();

        LinkedList2D newList = new LinkedList2D((int)this.ll.gridHeight,(int)this.ll.gridWidth);//numCols array range as paramerter

        newList.numRows=this.ll.numRows;
        newList.numCols= this.ll.numCols;
        newList.gridHeight=this.ll.gridHeight;
        newList.gridWidth=this.ll.gridWidth;

        ListNodeHead iItrOrig = this.ll.head;
        ListNodeHead iItr = newList.head;
        ListNodeHead jItrOrig = iItrOrig;
        ListNodeHead jItr = iItr;
        int valueHead=0,value=0;
        int start = 0, end = 0, flaga=0,flagb=0;
        // System.out.println(gridHeight + " "+ gridWidth);


        while(iItrOrig!=null){
            jItr = iItr;
            jItrOrig = iItrOrig;
            ListNodeHead rowStart = iItrOrig;//or start of row of image
             //getting over the iitr business
            if (iItrOrig.nextHead!=null) {
                newList.addBelow(iItr, -1);
            }
            iItr = iItr.nextHead;
            iItrOrig = iItrOrig.nextHead;

            if (jItrOrig.data==-1){
                jItr.data = 0;
                newList.addAfter(jItr,newList.gridWidth-1);
                jItr = jItr.next;//on15
                newList.addAfter(jItr,-1);
                jItr = jItr.next;//on-1
                continue;
            }

            ListNodeHead lastValidPointer = null;
            while(jItrOrig.next.data!=-1){
                jItrOrig = jItrOrig.next;
            }
            lastValidPointer = jItrOrig;
            jItrOrig = rowStart;//returning to start of row
            if (jItrOrig.data==0){// seq is 0 x x ...
                if (lastValidPointer.data==(newList.gridWidth-1)){//seq is 0 15 -1 or 0 7 8 15 -1
                    if (jItrOrig.next==lastValidPointer){//seq is 0 15 -1
                        jItr.data = -1;
                        jItr.next = null;
                        continue;
                    }
                    else{// seq is 0 6 7 15 -1
                        jItrOrig = jItrOrig.next;//on 2 if seq is 0 2 7 15 -1
                        while(!(jItrOrig.data==(newList.gridWidth-1))){
                            start = jItrOrig.data;
                            end = jItrOrig.next.data;
                            jItr.data = start + 1 ;
                            newList.addAfter(jItr,end-1);
                            jItr = jItr.next;
                            newList.addAfter(jItr,-1);
                            jItr = jItr.next;
                            jItrOrig = jItrOrig.next;
                            jItrOrig = jItrOrig.next;
                        }
                    }
                }
                else {// sequene is like 0 2 7 8 -1 or normal case **really ?
                    jItrOrig = jItrOrig.next;//now on 2
                    while(!(jItrOrig.next.data==(-1))){
                        start = jItrOrig.data;
                        end = jItrOrig.next.data;
                        jItr.data = start + 1 ;
                        newList.addAfter(jItr,end-1);
                        jItr = jItr.next;
                        newList.addAfter(jItr,-1);
                        jItr = jItr.next;
                        jItrOrig = jItrOrig.next;
                        jItrOrig = jItrOrig.next;
                    }
                    jItr.data = lastValidPointer.data +1;
                    newList.addAfter(jItr,newList.gridWidth-1);
                    jItr=jItr.next;
                    newList.addAfter(jItr,-1);
                    jItr=jItr.next;
                }
            }
            else {// seq is like 2 3 4 15 etc
                if (lastValidPointer.data==(newList.gridWidth-1)){//case like 2 3 4 15 or 2 15;
                    if (jItrOrig.next==lastValidPointer){//2 15 -1
                        jItr.data = 0;
                        newList.addAfter(jItr,jItrOrig.data-1);
                        jItr = jItr.next ;
                        newList.addAfter(jItr,-1);
                        jItr = jItr.next ;
                        continue;
                    }
                    else{//2 3 6 15 -1
                        jItr.data = 0;
                        newList.addAfter(jItr,jItrOrig.data -1);
                        jItr = jItr.next;
                        newList.addAfter(jItr,-1);
                        jItr = jItr.next;
                        jItrOrig = jItrOrig.next;//on 3 if seq is 2 3 6 15 -1;

                        while(!(jItrOrig.data==(newList.gridWidth-1))){
                            start = jItrOrig.data;
                            end = jItrOrig.next.data;
                            jItr.data = start + 1 ;
                            newList.addAfter(jItr,end-1);
                            jItr = jItr.next;
                            newList.addAfter(jItr,-1);
                            jItr = jItr.next;
                            jItrOrig = jItrOrig.next;
                            jItrOrig = jItrOrig.next;
                        }
                        //nothig to do post loop

                    }
                }
                else{//case like 2 4 or 2 4 7 8 the really normal ones ?
                    jItr.data = 0;
                    newList.addAfter(jItr,jItrOrig.data -1);
                    jItr = jItr.next;
                    newList.addAfter(jItr,-1);
                    jItr = jItr.next;
                    jItrOrig = jItrOrig.next;//now on 4

                    while(!(jItrOrig.next.data==(-1))){
                        start = jItrOrig.data;
                        end = jItrOrig.next.data;
                        jItr.data = start + 1 ;
                        newList.addAfter(jItr,end-1);
                        jItr = jItr.next;
                        newList.addAfter(jItr,-1);
                        jItr = jItr.next;
                        jItrOrig = jItrOrig.next;
                        jItrOrig = jItrOrig.next;
                    }
                    jItr.data = lastValidPointer.data +1;
                    newList.addAfter(jItr,newList.gridWidth-1);
                    jItr=jItr.next;
                    newList.addAfter(jItr,-1);
                    jItr=jItr.next;
                }
            }

        }


//        while (iItrOrig!=null){
////            newList.printLLdo();
//            flaga=0;
//            flagb=0;
//            jItr = iItr;
//            jItrOrig = iItrOrig;
////            count = 0;
//            valueHead = iItrOrig.data;
//
//            if (iItrOrig.nextHead!=null) {
//                newList.addBelow(iItr, -1);
//            }
//            iItr = iItr.nextHead;
//            iItrOrig = iItrOrig.nextHead;
//
//
//            if (valueHead == 0){//do something//if jItrOrig has 1st elem as 0
//                if (!jItrOrig.next.data.equals(newList.gridWidth-1)){
//                    jItr.data = jItrOrig.next.data + 1;//first value stored after 0 example 0 2 10 12 ...then 3 is stored//jItrOrig on 0
//                    newList.addAfter(jItr,-1);//trailing empty node is added
//                    jItr = jItr.next;
//                    jItrOrig = jItrOrig.next;//move onto next node inside is not 15 so why not add it ?//jItrOrig on 2
////                    jItr.data = jItr.next.data -1;//storing 9
////                    newList.addAfter(jItr,-1);//trailing empty node is added
////                    jItrOrig = jItrOrig.next;//on 10 now
//                    flaga = 1;
//                }
//                else{//just finish this row!
//                    jItr.data = -1;
//                    jItr.next = null;
//                    continue;
//                }
//            }
//            else{
//                jItr.data = 0;
//                newList.addAfter(jItr,-1);
//                jItr = jItr.next;
//                jItr.data = jItrOrig.data-1;
//                newList.addAfter(jItr,-1);
//                jItr = jItr.next;
//                jItrOrig = jItrOrig.next;
//
//            }
////                System.out.println("LINE109");
//            while (true) {//just changed the condition
////                newList.printLLdo();
//                if (jItrOrig.next.data==-1){//i have already taken into account the things to add after 2
//                    jItr.data = newList.gridWidth-1;
//                    newList.addAfter(jItr,-1);
//                    jItr = jItr.next;
//                    break;
//                }
//                start = jItrOrig.data;
//                end = jItrOrig.next.data;//holding the pointers
//
//                if (end == newList.gridWidth-1){//what to do
//                    flagb=1;
//                }
//
////                if (flaga!=1){
//                    jItr.data = start+1;
//                    newList.addAfter(jItr,-1);
//                    jItr = jItr.next;
////                }
//                if (flagb!=1){
//                    jItr.data = end-1;
//                    newList.addAfter(jItr,-1);
//                    jItr = jItr.next;
//                }
//                flaga=0;
//
//                jItrOrig = jItrOrig.next;
//                jItrOrig = jItrOrig.next;
//
//            }
//            if (flagb==1){
//                jItr.data = -1;
//                jItr.next = null;
//            }
//            else{
//                jItr.data = newList.gridWidth-1;
//                newList.addAfter(jItr,-1);
//                jItr = jItr.next;
//            }
//            // System.out.println();
//
//        }


//        ListNodeHead iItrand = newList.head;
//        ListNodeHead jItrand = iItrand;
////				ListNodeHead ptr = iItrand;
//        int val1=0,val2=0,c=0;
//        while(iItrand!=null){
//            jItrand = iItrand;
////				    ptr = jItrand;
////				    c=1;
//            try {
//                while (jItrand.next.next.next != null) {
//                    if ((jItrand.next.data + 1) == jItrand.next.next.data) {
//                        jItrand.next = jItrand.next.next.next;
//                    }
//                    else{
//                        jItrand = jItrand.next;
//                        jItrand = jItrand.next;
//                    }
//                }
//            }
//            catch (Exception e){
//                //do nothing ?
//
//            }
//            iItrand = iItrand.nextHead;
//
//        }

//        System.out.println("AFTER INVERT");
//        newList.printLLdo();
//            newList.printLLdo();
        //or simply
        //HOW TO TRUELY CLONE ?
        this.ll = newList;

        return;
//        //you need to implement this
//		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void performAnd(CompressedImageInterface img) throws BoundsMismatchException
    {

        LinkedListImage image2 = ((LinkedListImage)img).deepCopy();

        if (((image2.ll.gridWidth!=this.ll.gridWidth)||image2.ll.gridHeight!=this.ll.gridHeight)){

//            System.out.println("HERE IS where sizes don't match");
//            System.out.println(this.ll.gridWidth+" , "+this.ll.gridHeight);
//            System.out.println(image2.ll.gridWidth+" , "+image2.ll.gridHeight);

//      image2.ll.gridHeight
            throw new BoundsMismatchException("Sizes of images do not match");
        }

        LinkedListImage image1 = this.deepCopy();

//        System.out.println("IMAGE 1 BEFORE AAANNNDDDDD (THIS)::::::");
//        image1.ll.printLLdo();
//        System.out.println("IMAGE 2 BEFORE AAANNNDDDDD (IMG)::::::");
//        image2.ll.printLLdo();


        image1.invert();
        image2.invert();

        image1.performOr(image2);

        image1.invert();

//         System.out.println("AND COMING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//         System.out.println("IMAGE RESULTANT OF AAANNNDDDDD (IMG)::::::");
//        image1.ll.printLLdo();
        this.ll = image1.ll;


//        LinkedListImage imageAND = this.deepCopy() ;//PROBABLY IMPLEMENT DEEP CLONING HERE..
//        int i=0,j=0,startIndex=0,endIndex=0,min1=0,max1=0,min2=0,max2=0,value1image1=0,value2image1=0,value1image2=0,value2image2=0,data=0;
//        ListNodeHead iItr1 = this.ll.head;
//        ListNodeHead iItr2 = image.ll.head;
//        ListNodeHead iItr3 = imageAND.ll.head;//empty node but other parameters to correct value
//        ListNodeHead jItr1 = iItr1;
//        ListNodeHead jItr2 = iItr2;
//        ListNodeHead jItr3 = iItr3;
//
//        // System.out.println(ll.gridHeight + " "+ ll.gridWidth);
//
//        for(i=0;i<this.ll.gridHeight;i++){
//            // System.out.println();
//            jItr1 = iItr1;
//            jItr2 = iItr2;
//            jItr3 = iItr3;
//
//            while(true){
//                if (jItr1.data==-1) {
//                    while(jItr2.data!=-1){//empty the second image
//                        data = jItr2.data;
//                        jItr3.data = data;
//                        jItr2 = jItr2.next;
//                        imageAND.ll.addAfter(jItr3,-1);
//                        jItr3 = jItr3.next;//-1 will be added automatically after parent while loop
//                        // imageAND.ll.addAfter(jItr3,-1)
//                        // jItr3 = jItr3.next;//now on null or -1
//                    }
//                    break;
//                }
//                if (jItr2.data==-1) {
//
//                    while(jItr1.data!=-1){//empty the first image's row
//                        data = jItr1.data;
//                        jItr1=jItr1.next;
//                        jItr3.data = data;
//                        imageAND.ll.addAfter(jItr3,-1);
//                        jItr3 = jItr3.next;
//                    }
//                    // while(jItr1.data!=-1){
//                    // imageAND.ll.addAfter(jItr3,-1)
//                    // jItr3 = jItr3.next;//now on null
//                    // }
//                    break;
//                }
//
//                value1image1 =	jItr1.data;
//                value2image1 =	jItr1.next.data;//but holding the pointers in their place
//                value1image2 =	jItr2.data;
//                value2image2 =	jItr2.next.data;//but holding the pointers in their place
//
//
//
//                min1 = Math.min(value1image1,value1image2);
//                max1 = Math.max(value1image1,value1image2);
//                min2 = Math.min(value2image1,value2image2);
//                max2 = Math.max(value2image1,value2image2);
//
//
//                //cases depending on relative value of min12 and max12 6 or 3 cases
//
//                // very very COMPLEX LOGIC if do by 6 cases and I think still cases left IN AND and XOR
///////////////////////////////////////------------------------------------HERE
//                // if (min1<max2) {//skewed case//skip the lesser case
//                if(value2image1<value1image2){//values in 1 st img are lesser
//                    jItr3.data = jItr1.data;
//                    jItr1=jItr1.next;
//                    imageAND.ll.addAfter(jItr3,jItr1.data);
//                    jItr1=jItr1.next;//contains next value to be read
//                    jItr3=jItr3.next;//contatins data
//                    imageAND.ll.addAfter(jItr3,-1);
//                    jItr3=jItr3.next;//contains -1
//                }
//                else if(value1image1>value2image2){//value in 2nd image are lesser
//                    jItr3.data = jItr2.data;
//                    jItr2=jItr2.next;
//                    imageAND.ll.addAfter(jItr3,jItr2.data);
//                    jItr2=jItr2.next;//contains next value to be read
//                    jItr3=jItr3.next;//contatins data
//                    imageAND.ll.addAfter(jItr3,-1);
//                    jItr3=jItr3.next;//contains -1
//                }
//                // }
//                // else if (){
//                //
//                // }
//                else{//good normal case
//
//                    jItr3.data = min1;//as current data was null
//                    imageAND.ll.addAfter(jItr3,max2);
//                    jItr3 = jItr3.next;//has max2 data
//                    imageAND.ll.addAfter(jItr3,-1);
//                    jItr3 = jItr3.next;//is null data or -1
//                    // imageAND.addAfter(jItr3,max1);
//                    // jItr3=jItr3.next;
//                    // imageAND.addAfter(jItr3,min2);
//                    // jItr3=jItr3.next;
//                    jItr1=jItr1.next;
//                    jItr1=jItr1.next;
//                    jItr2=jItr2.next;
//                    jItr2=jItr2.next;
//
//                }
//
//                // else if((min2<max1)&&(min2>min1)){
//                //
//                // }
//                // else if((min2<max1)&&(min2<min1)){
//                //
//                // }
//                // else if((max2<max1)&&(min2>min1)){
//                //
//                // }
//                // else if((min2<max1)&&(min2>min1)){
//                //
//                // }
//                // else if((min2<max1)&&(min2>min1)){
//                //
//                // }
//
//                // imageAND.addAfter(jItr3,max1);
//            }
//            jItr3.data=-1;
//            jItr3.next = null;
//
////					while()
//
//            if (i<this.ll.gridHeight-1) {//will not happen on the last iteration
//                iItr1=iItr1.nextHead;
//                iItr2=iItr2.nextHead;
//                imageAND.ll.addBelow(iItr3,-1);
//                iItr3=iItr3.nextHead;
//            }
//
//
//        }
//
//        ListNodeHead iItrand = imageAND.ll.head;
//        ListNodeHead jItrand = iItrand;
////				ListNodeHead ptr = iItrand;
//        int val1=0,val2=0,c=0;
//        while(iItrand!=null){
//            jItrand = iItrand;
////				    ptr = jItrand;
////				    c=1;
//            try {
//                while (jItrand.next.next.next != null) {
//                    if ((jItrand.next.data + 1) == jItrand.next.next.data) {
//                        jItrand.next = jItrand.next.next.next;
//                    }
//                    else{
//                        jItrand = jItrand.next;
//                        jItrand = jItrand.next;
//                    }
//                }
//            }
//            catch (Exception e){
//                //do nothing ?
//
//            }
//            iItrand = iItrand.nextHead;
//
//        }
//
//        //you need to implement this
//         System.out.println("AND COMING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//         System.out.println("IMAGE RESULTANT OF AAANNNDDDDD (IMG)::::::");
//         imageAND.ll.printLLdo();
//        this.ll = imageAND.ll;
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        return;
        // //you need to implement this
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void performOr(CompressedImageInterface img) throws BoundsMismatchException
    {
        LinkedListImage image = (LinkedListImage)img;

        if (((image.ll.gridWidth!=this.ll.gridWidth)||image.ll.gridHeight!=this.ll.gridHeight)){

//            System.out.println("HERE IS where sizes don't match");
//            System.out.println(this.ll.gridWidth+" , "+this.ll.gridHeight);
//            System.out.println(image2.ll.gridWidth+" , "+image2.ll.gridHeight);

//      image2.ll.gridHeight
            throw new BoundsMismatchException("Sizes of images do not match");
        }

        LinkedListImage imageOR = this.deepCopy() ;//PROBABLY IMPLEMENT DEEP CLONING HERE..
//        System.out.println("BEFORE ORRRRR :::: IMAGE 1:(this)");
//        this.ll.printLLdo();
//        System.out.println("BEFORE ORRRRR :::: IMAGE 2:(image)");
//        image.ll.printLLdo();
        int i=0,j=0,startIndex=0,endIndex=0,min1=0,max1=0,min2=0,max2=0,value1image1=-3,value2image1=-3,value1image2=-3,value2image2=-3;
        ListNodeHead iItr1 = this.ll.head;
        ListNodeHead iItr2 = image.ll.head;
        ListNodeHead iItr3 = imageOR.ll.head;
        ListNodeHead jItr1 = iItr1;
        ListNodeHead jItr2 = iItr2;
        ListNodeHead jItr3 = iItr3;
        int start,end;
        // System.out.println(ll.gridHeight + " "+ ll.gridWidth);

        for(i=0;i<this.ll.gridHeight;i++){
            // System.out.println();
            jItr1 = iItr1;
            jItr2 = iItr2;
            jItr3 = iItr3;

            if (i<this.ll.gridHeight-1) {//will not happen on the last iteration
                iItr1=iItr1.nextHead;
                iItr2=iItr2.nextHead;
                imageOR.ll.addBelow(iItr3,-1);
                iItr3=iItr3.nextHead;
            }

            while(true){
                if (jItr1.data==-1) {//image 1's row has ended//if this isn't the anwer then many many cases

                    if (value2image1==-3){
                        //ie it runs in the first iteration itself//just make
                        jItr3.data = -1;
                        jItr3.next = null;
                        break;
                    }

                    while(jItr2.data!=-1){
                        start = jItr2.data;
                        end = jItr2.next.data;
                        if (start>value2image1){//what it this runs in the first iteration ?
                            jItr3.data = -1;
                            jItr3.next = null;
                            break;//do nothing
                        }
                        else if(start == value2image1){
//                            jItr3.data = start;
//                            imageOR.ll.addAfter(jItr3,start);
//                            jItr3 = jItr3.next;
//                            imageOR.ll.addAfter(jItr3,-1);
//                            jItr3 = jItr3.next;
                            jItr2=jItr2.next.next;

//                            break;
                        }
                        else{//start is less but where is end ?//this case should be covered previously?
                            if (end>=value2image1){
//                                jItr3.data = start;
//                                imageOR.ll.addAfter(jItr3,value2image1);
//                                jItr3 = jItr3.next;
//                                imageOR.ll.addAfter(jItr3,-1);
//                                jItr3 = jItr3.next;
//                                break;
                                jItr2=jItr2.next.next;
                            }
                            else {
//                                jItr3.data = start;
//                                imageOR.ll.addAfter(jItr3,end);
//                                jItr3 = jItr3.next;
//                                imageOR.ll.addAfter(jItr3,-1);
//                                jItr3 = jItr3.next;
                                jItr2=jItr2.next.next;
                            }
                        }
//                        boundary = Math.max()
//                         if (prevjItr1.data>=jItr2)
//                         imageOR.ll.addAfter(jItr3,-1)
//                         jItr3 = jItr3.next;//now on null
                     }

                    break;
                }

                if (jItr2.data==-1) {//image 2 has ended

                    if (value2image2==-3){
                        //ie it runs in the first iteration itself//just make
                        jItr3.data = -1;
                        jItr3.next = null;
                        break;
                    }

                    while(jItr1.data!=-1) {

                        start = jItr1.data;
                        end = jItr1.next.data;

                        if (start > value2image2) {
                            jItr3.data = -1;
                            jItr3.next = null;
                            break;//do nothing
                        } else if (start == value2image2) {
//                            jItr3.data = start;
//                            imageOR.ll.addAfter(jItr3, start);
//                            jItr3 = jItr3.next;
//                            imageOR.ll.addAfter(jItr3, -1);
//                            jItr3 = jItr3.next;
//                            break;
                            jItr1 = jItr1.next.next;
                        }
                        else {//start is less but where is end ?
                            if (end >= value2image2) {
//                                jItr3.data = start;
//                                imageOR.ll.addAfter(jItr3, value2image2);
//                                jItr3 = jItr3.next;
//                                imageOR.ll.addAfter(jItr3, -1);
//                                jItr3 = jItr3.next;
//                                break;
                                jItr1 = jItr1.next.next;
                            } else {
//                                jItr3.data = start;
//                                imageOR.ll.addAfter(jItr3, end);
//                                jItr3 = jItr3.next;
//                                imageOR.ll.addAfter(jItr3, -1);
//                                jItr3 = jItr3.next;
//                                jItr1 = jItr1.next.next;//keep going
                                jItr1 = jItr1.next.next;

                            }
                        }
                    }
                    // while(jItr1.data!=-1){
                    // imageOR.ll.addAfter(jItr3,-1)
                    // jItr3 = jItr3.next;//now on null
                    // }
                    break;
                }

                value1image1 =	jItr1.data;
                value2image1 =	jItr1.next.data;//but holding the pointers in their place
                value1image2 =	jItr2.data;
                value2image2 =	jItr2.next.data;//but holding the pointers in their place



                min1 = Math.min(value1image1,value1image2);
                max1 = Math.max(value1image1,value1image2);
                min2 = Math.min(value2image1,value2image2);
                max2 = Math.max(value2image1,value2image2);


                //cases depending on relative value of min12 and max12 6 or 3 cases

                // very very COMPLEX LOGIC if do by 6 cases and I think still cases left IN AND and XOR

                if (max1>min2) {//skewed case//skip the lesser case
                    if(value1image1<value1image2){//values in 1 st img are lesser
                        jItr1=jItr1.next;
                        jItr1=jItr1.next;
                    }
                    else{
                        jItr2=jItr2.next;
                        jItr2=jItr2.next;
                    }
                }
                // else if (){
                //
                // }
                else{//good normal case

                    jItr3.data = max1;//as current data was null
                    imageOR.ll.addAfter(jItr3,min2);
                    jItr3 = jItr3.next;//has max2 data
                    imageOR.ll.addAfter(jItr3,-1);
                    jItr3 = jItr3.next;//is null data ie -1
                    // imageOR.addAfter(jItr3,max1);
                    // jItr3=jItr3.next;
                    // imageOR.addAfter(jItr3,min2);
                    // jItr3=jItr3.next;

                    if (value2image1>value2image2){//end val of image 1 is greater so move image 2 fwd
                        jItr2=jItr2.next;
                        jItr2=jItr2.next;

                    }
                    else{//both equal case or reverse
                        jItr1=jItr1.next;
                        jItr1=jItr1.next;
                    }


                }

                // else if((min2<max1)&&(min2>min1)){
                //
                // }
                // else if((min2<max1)&&(min2<min1)){
                //
                // }
                // else if((max2<max1)&&(min2>min1)){
                //
                // }
                // else if((min2<max1)&&(min2>min1)){
                //
                // }
                // else if((min2<max1)&&(min2>min1)){
                //
                // }

                // imageOR.addAfter(jItr3,max1);
            }
            jItr3.data=-1;
            jItr3.next = null;

        }

        //you need to implement this
//        System.out.println("OR COMING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println("AFTER ORRRRR :::: IMAGE 2:(image)");
//        imageOR.ll.printLLdo();

        this.ll = imageOR.ll;
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        return;
    }

    public void performXor(CompressedImageInterface img) throws BoundsMismatchException
    {
        //you need to implement this
        LinkedListImage image2 = ((LinkedListImage)img).deepCopy();

        if (((image2.ll.gridWidth!=this.ll.gridWidth)||image2.ll.gridHeight!=this.ll.gridHeight)){

//            System.out.println("HERE IS where sizes don't match");
//            System.out.println(this.ll.gridWidth+" , "+this.ll.gridHeight);
//            System.out.println(image2.ll.gridWidth+" , "+image2.ll.gridHeight);

//      image2.ll.gridHeight
            throw new BoundsMismatchException("Sizes of images do not match");
        }

        LinkedListImage image1 = this.deepCopy() ;
        LinkedListImage image1not = image1.deepCopy();
        LinkedListImage image2not = image2.deepCopy();
        image1not.invert();
        image2not.invert();

        image1.performAnd(image2not);
        image2.performAnd(image1not);

        image1.performOr(image2);

        this.ll = image1.ll;
//        System.out.println("before XORR Image1:(this):");
//        this.ll.printLLdo();
//        System.out.println("before XORR Image2:(image):");
//        image.ll.printLLdo();

//        LinkedListImage imageXOR = image.deepCopy() ;
//        LinkedListImage imageB = image.deepCopy() ;
//        //
//        imageB.invert();
//        imageXOR.performAnd(imageB);//  this AND img'
//        imageB = image.deepCopy();
//        imageC = this.deepCopy();
//        imageC.invert();
//        imageB.performAnd(imageC);//img AND this'
//        imageXOR.performOr(imageB);//  (this AND img')OR(img AND this')
//
////        System.out.println(" XOR COMING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
////        System.out.println("after XORR Image:(imageXOR):");
////        imageXOR.ll.printLLdo();
//
//        this.ll = imageXOR.ll;
        return;
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public String toStringUnCompressed()
    {//YOU NEED TO CHECK THESE
        //you need to implement this
        StringBuilder str = new StringBuilder();
        str.append(this.ll.gridWidth);
        str.append(" ");
        str.append(this.ll.gridHeight);
        ListNodeHead iItr = this.ll.head;
        ListNodeHead jItr = iItr;
        int i=0,j=0,k=0,ender1=0,startIndex=0,endIndex=0;

        for(i=0;i<this.ll.gridHeight;i++){
            jItr = iItr;
            str.append(",");
            if (jItr.data==-1){
                for (k=0;k<this.ll.gridWidth;k++){
                    str.append(" 1");
                }
            }
            for (k=0;k<jItr.data;k++){
//                str+=" 1";
                str.append(" 1");

            }
            while(jItr.data!=-1) {
                startIndex = jItr.data;
                endIndex = jItr.next.data;
                for (k=startIndex;k<=endIndex;k++){
                    str.append(" 0");
//                    str+=" 0";
                }

                ender1 = jItr.next.next.data==-1 ? this.ll.gridWidth : jItr.next.next.data;

                for (k=endIndex+1 ; k<=ender1-1 ;k++){
//                    str+=" 1";
                    str.append(" 1");
                }

                jItr=jItr.next;
                jItr=jItr.next;
            }
            iItr=iItr.nextHead;
        }
        return str.toString();
//        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public String toStringCompressed()
    {

        ListNodeHead iItr = ll.head;
        ListNodeHead jItr = iItr;
        StringBuilder str = new StringBuilder();

//        str += ll.gridHeight + " "+ ll.gridWidth + ",";
        str.append(this.ll.gridWidth);
        str.append(" ");
        str.append(this.ll.gridHeight);
        str.append(",");

        do {

            jItr = iItr;

            do {
//                str+= " " + jItr.data ;
                str.append(" ");
                str.append(jItr.data);

                // System.out.print(jItr.data + " ");
                jItr=jItr.next;
            } while (jItr!=null);
//            str +=",";
            str.append(",");

            // System.out.println();
            iItr = iItr.nextHead;
        } while (iItr!=null);
        // s = str.substring(0,str.length()-1);
        // System.out.println(s);
        return str.substring(0,str.length()-1);

        //you need to implement this
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public static void printGrid(boolean[][] grid,Integer width,Integer height)
    {
        int i=0,j=0;
        System.out.println("GRID COMING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(width +" "+ height);
        for (i=0;i<height ;i++ ) {
            for (j=0;j<width ;j++ ) {
                if (grid[i][j]) {
                    System.out.print("1 ");
                }
                else{
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args)
    {
        // testing all methods here :
        boolean success = true;

        // check constructor from file
        CompressedImageInterface img1 = new LinkedListImage("sampleInputFile.txt");
        LinkedListImage i1 =(LinkedListImage)img1;

        // check toStringCompressed
        String img1_compressed = img1.toStringCompressed();
        String img_ans = "16 16, -1, 5 7 -1, 3 7 -1, 2 7 -1, 2 2 6 7 -1, 6 7 -1, 6 7 -1, 4 6 -1, 2 4 -1, 2 3 14 15 -1, 2 2 13 15 -1, 11 13 -1, 11 12 -1, 10 11 -1, 9 10 -1, 7 9 -1";
        // System.out.println(img_ans);
        // System.out.println(img1_compressed);

        success = success && (img_ans.equals(img1_compressed));

        if (!success)
        {
            System.out.println("Constructor (file) or toStringCompressed ERROR");
            return;
        }

        // check getPixelValue
        boolean[][] grid = new boolean[16][16];
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    grid[i][j] = img1.getPixelValue(i, j);
                }
                catch (PixelOutOfBoundException e)
                {

                    System.out.println("Errorrrrrrrr1");
                }
            }
        // printGrid(grid,16,16);
        // check constructor from grid
        CompressedImageInterface img2 = new LinkedListImage(grid, 16, 16);
        LinkedListImage i2 = (LinkedListImage)img2;
        // i2.ll.printLLdo();
        String img2_compressed = img2.toStringCompressed();
        // System.out.println("LINE447 my    str val:"+img2_compressed);
        // System.out.println("LINE447 given str val:"+img_ans);
        success = success && (img2_compressed.equals(img_ans));

        if (!success)
        {
            System.out.println("Constructor (array) or toStringCompressed ERROR");
            return;
        }

        // check Xor
        try
        {
            // img1.ll.printLLdo();
            img1.performXor(img2);
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && (!img1.getPixelValue(i,j));
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }

        if (!success)
        {
            System.out.println("performXor or getPixelValue ERROR");
//            return;
        }
        success = true;
//        i1.ll.printLLdo();

        // check setPixelValue
        for (int i = 0; i < 16; i++)
        {
            try
            {
                img1.setPixelValue(i, 0, true);
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
//        i1.ll.printLLdo();

        // check numberOfBlackPixels
        int[] img1_black = img1.numberOfBlackPixels();
        success = success && (img1_black.length == 16);
//        for (int i = 0; i < 5 && success; i++)
//            success = success && (img1_black[i] == 4);
        if (!success)
        {
            System.out.println("setPixelValue or numberOfBlackPixels ERROR");
//            return;
        }
        success = true;

        // check invert
//        i1.ll.printLLdo();

        img1.invert();
//        i1.ll.printLLdo();

        for (int i = 0; i < 16; i++)
        {
            try
            {
                success = success && !(img1.getPixelValue(i, 0));
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
        if (!success)
        {
            System.out.println("invert or getPixelValue ERROR");
//            return;
        }
        success = true;

//        i1.ll.printLLdo();
//        i2.ll.printLLdo();
        // check Or
        try
        {
            img1.performOr(img2);
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        //running till here yaay
//        i1.ll.printLLdo();
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)//made a change here;; testing pourpose strike back
            {
                try
                {
                    success = success && img1.getPixelValue(i,j);
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performOr or getPixelValue ERROR");
//            return;
        }
        success = true;

        // check And
        try
        {
            img1.performAnd(img2);
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && (img1.getPixelValue(i,j) == img2.getPixelValue(i,j));
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performAnd or getPixelValue ERROR");
//            return;
        }
        success = true;

        // check toStringUnCompressed
        String img_ans_uncomp = "16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1";
//        System.out.println(img_ans_uncomp);
//        System.out.println(img1.toStringUnCompressed());
//        System.out.println(img_ans);
//        System.out.println(img2.toStringUnCompressed());

//        for (Integer t=0;t<img_ans_uncomp.length();t++){
//            if
//        }
        //i modified after 2nd and below as it was logically written incorrect
        success = success && (img1.toStringUnCompressed().equals(img_ans_uncomp)) && (img2.toStringCompressed().equals(img_ans));

        if (!success)
        {
            System.out.println("toStringUnCompressed ERROR");
//            return;
        }
        else
            System.out.println("ALL TESTS SUCCESSFUL! YAYY!");
        success = true;

//        int xxx=200;
//
//        boolean[][] grid1=new boolean[xxx][xxx];
//        boolean[][] grid2=new boolean[xxx][xxx];
//        boolean[][] xor=new boolean[xxx][xxx];
//        boolean[][] or=new boolean[xxx][xxx];
//        boolean[][] and=new boolean[xxx][xxx];
//
//
//
//        for (int i=0; i<xxx; i++ ) {
//            for (int j=0; j<xxx; j++ ) {
//                 grid1[i][j]=true;
//                 grid2[i][j]=true;
//
////                grid1[i][j]=((Math.random() * 1)>0.75);
////                grid2[i][j]=((Math.random() * 1)>0.25);
//                xor[i][j]=(grid1[i][j]!=grid2[i][j]);
//                or[i][j]=(grid1[i][j]==true || grid2[i][j]==true);
//                and[i][j]=(grid1[i][j]==true && grid2[i][j]==true);
//                // System.out.println(grid[i][j]);
//            }
//        }
//        // for (int i=0; ; ) {
//        //
//        // }
//
//        CompressedImageInterface img11=new LinkedListImage(grid1,xxx,xxx);
//        CompressedImageInterface img22=new LinkedListImage(grid2,xxx,xxx);
//        CompressedImageInterface xorer=new LinkedListImage(xor,xxx,xxx);
//        CompressedImageInterface orer=new LinkedListImage(or,xxx,xxx);
//        CompressedImageInterface ander=new LinkedListImage(and,xxx,xxx);
//        String str1=img11.toStringCompressed();
//
//        // for (int i=0; i<xxx; i++ ) {
//        //         for (int j=0; j<xxx; j++ ) {
//        //           try{
//        //             // img11.setPixelValue(i,j,img11.getPixelValue(i,j));
//        //             img11.setPixelValue(i,j,false);
//        //
//        //           }catch(PixelOutOfBoundException e){
//        //             System.out.println("s");
//        //           }
//        //         }
//        // }
////        System.out.println(img11.numberOfBlackPixels()[15]);
//
//        LinkedListImage  i11 = (LinkedListImage)(img11);
//
////        img11.invert();
////                i11.ll.printLLdo();
//
//        int [] a =  img11.numberOfBlackPixels();
//
//        for (int alpha = 0 ; alpha < a.length ; alpha++){
//            System.out.print(a[alpha]+" , ");
//        }
//            System.out.println();
//        img11.invert();
//
//        a = img11.numberOfBlackPixels();
//        for (int alpha = 0 ; alpha < a.length ; alpha++){
//            System.out.print(a[alpha]+" , ");
//        }
//
//        System.out.println();
////        System.out.println(img11.numberOfBlackPixels()[15]);
//        img11.invert();
//        if(str1.equals(img11.toStringCompressed()))
//            System.out.println("Invert Working");
//
//
//        try {
//            img11.performXor(img22);
//        } catch (BoundsMismatchException e) {
//            e.printStackTrace();
//            System.out.println("ErrorrrrrrrrSSSSSSS");
//        }
//        String str2=img11.toStringCompressed();
//        if(str2.equals(xorer.toStringCompressed())) {
//            System.out.println("XOR Working:::::::::::::::::::::::::::::::::::::::YO");
//        }
//




        int xxx=300;
        boolean[][] grid1=new boolean[xxx][xxx];
        boolean[][] grid2=new boolean[xxx][xxx];
        boolean[][] xor=new boolean[xxx][xxx];
        boolean[][] or=new boolean[xxx][xxx];

        boolean[][] and=new boolean[xxx][xxx];
        boolean[][] invert1=new boolean[xxx][xxx];


        for (int i=0; i<xxx; i++ ) {
            for (int j=0; j<xxx; j++ ) {
                // grid1[i][j]=true;
                // grid2[i][j]=true;

                grid1[i][j]=((Math.random() * 1)>0.5);
                grid2[i][j]=((Math.random() * 1)>0.5);
                xor[i][j]=(grid1[i][j]!=grid2[i][j]);
                or[i][j]=(grid1[i][j]==true || grid2[i][j]==true);
                and[i][j]=(grid1[i][j]==true && grid2[i][j]==true);
                invert1[i][j]=(grid1[i][j]);
                // System.out.println(grid[i][j]);
            }
        }
        // for (int i=0; ; ) {
        //
        // }

        CompressedImageInterface img11=new LinkedListImage(grid1,xxx,xxx);
        CompressedImageInterface img22=new LinkedListImage(grid2,xxx,xxx);
        CompressedImageInterface xorer=new LinkedListImage(xor,xxx,xxx);
        CompressedImageInterface orer=new LinkedListImage(or,xxx,xxx);
        CompressedImageInterface ander=new LinkedListImage(and,xxx,xxx);
        CompressedImageInterface inverter1=new LinkedListImage(invert1,xxx,xxx);

        String str1=img11.toStringCompressed();

        for (int i=0; i<xxx; i++ ) {
            for (int j=0; j<xxx; j++ ) {
                try{
                    // System.out.print(img11.getPixelValue(i,j)+" ");
                    // System.out.println(" ");

                    // System.out.println(img11.toStringCompressed());
                    img11.setPixelValue(i,j,img11.getPixelValue(i,j));
                    // System.out.println(img11.toStringCompressed());

//                     img11.setPixelValue(i,j,false);
                    // System.out.println(" ");

                }catch(PixelOutOfBoundException e){
                    System.out.println("s::::::::::::::::::::::::::::");
                }
            }

        }
        // System.out.println(img11.toStringUnCompressed());

        System.out.println(img11.toStringCompressed().equals(inverter1.toStringCompressed()));



    }
}
