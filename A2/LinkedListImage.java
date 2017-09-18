import com.sun.javafx.image.BytePixelSetter;

import java.io.*;
import java.util.*;
import java.lang.*;

class LinkedList2D{
    ListNodeHead head = null;
    Integer numRows = 0;
    Integer[] numCols =null;
    Integer gridHeight = 0;
    Integer gridWidth = 0;

    public LinkedList2D(Integer height,Integer width){
        this.head = new ListNodeHead();
        this.numRows = 1;
        this.numCols = new Integer[height];
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
    Integer data = -1;
    ListNodeHead nextHead = null;
    ListNodeHead next = null;
}

public class LinkedListImage implements CompressedImageInterface {
    LinkedList2D ll = null;
    // public LinkedListImage clone() throws CloneNotSupportedException {
    //     return super.clone();
    // }
    public LinkedListImage deepCopy(){
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
    public LinkedListImage(LinkedList2D ll){
        this.ll = ll;
    }
    public LinkedListImage(String filename){
        //you need to implement this
        Scanner sc = null;
        File fileIn = null;
        Integer p=0,q=0,width=0,height=0,temp=0;
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

        // new LinkedListImage(grid,width,height);//I WANT  THIS TO RUN
///////////////////BELOW IS EXACT COPY(without helper commnets) OF THE CONSTRUCTOR BELOW AS THE ABOVE COMMAND WON'T WORK PROPERLY
        ll = new LinkedList2D(height,width);//numCols array range as paramerter
        ll.numRows=0;
        ll.gridHeight=height;
        ll.gridWidth=width;
        ListNodeHead xItr = null;
        ListNodeHead yItr = null;

        Integer i=0,j=0,counter = 0,consec = 0,flag=0;

        xItr = ll.head;

        for (i=0;i<height ; i++) {

            consec = 0;
            counter = 1;
            flag = 0;
            yItr = xItr;

            for (j=0;j<width ;j++ ) {

                if (!grid[i][j]) {//current element is black
                    flag = 1;//atleast one black has been found//first or Nth black after break

                    if (consec == 0) {
                        yItr.data = j;
                        ll.addAfter(yItr,-1);
                        yItr = yItr.next;
                        consec++;
                        counter++;
                    }
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
                        counter++;//one more elem added to this row
                    }
                    flag = 0;
                    consec = 0;
                }
            }
            if (flag==1) {//if atleast black has been found //odd parity
                yItr.data = j-1;
                ll.addAfter(yItr,-1);
                yItr = yItr.next;
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
        }
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

        Integer i=0,j=0,counter = 0,consec = 0,flag=0;

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

                        // System.out.println("Just after Line 161 yIter.data:"+ (yItr.data));
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

                // System.out.println("Just after Line 161 yIter.data:"+ (yItr.data));
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


    // -------------------------------------------------------------------------------------------
    public boolean getPixelValue(int x, int y) throws PixelOutOfBoundException
    {
        if ((x>=ll.gridHeight)||(y>=ll.gridWidth)) {
            throw new PixelOutOfBoundException("Pixels indices are out of bounds.");
        }
        Integer i=0,j=0,startIndex=0,endIndex=0;
        ListNodeHead iItr = ll.head;
        ListNodeHead jItr = iItr;

        // System.out.println(ll.gridHeight + " "+ ll.gridWidth);

        for(i=0;i<x;i++){
            // System.out.println();
            iItr=iItr.nextHead;
        }
        jItr = iItr;
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
        System.out.println("This line 335 shouldnt be printed");
        return true;//shouldnt be required
        //you need to implement this

        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
    // -------------------------------------------------------------------------------------------------------
    public void setPixelValue(int x, int y, boolean val) throws PixelOutOfBoundException
    {
        if ((x>=ll.gridHeight)||(y>=ll.gridWidth)) {
            throw new PixelOutOfBoundException("Pixels indices are out of bounds.");
        }

        //you need to implement this
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public int[] numberOfBlackPixels()
    {
        //you need to implement this
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
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
        Integer valueHead=0,value=0;
        Integer start = 0, end = 0, flaga=0,flagb=0;
        // System.out.println(gridHeight + " "+ gridWidth);

        while (iItrOrig!=null){
            flaga=0;
            flagb=0;
            jItr = iItr;
            jItrOrig = iItrOrig;
//            count = 0;
            valueHead = iItrOrig.data;

            if (iItrOrig.nextHead!=null) {
                newList.addBelow(iItr, -1);
            }
            iItr = iItr.nextHead;
            iItrOrig = iItrOrig.nextHead;


            if (valueHead == 0){//do something
                if (!jItrOrig.next.data.equals(newList.gridWidth-1)){
                    jItr.data = jItrOrig.next.data;
                    newList.addAfter(jItr,-1);
                    jItr = jItr.next;
                    flaga = 1;
                }
                else{
                    jItr.data = -1;
                    continue;
                }
            }
            else{
                jItr.data = 0;
                newList.addAfter(jItr,-1);
                jItr = jItr.next;
            }
//                System.out.println("LINE109");
            while (jItrOrig.data!=-1) {
                start = jItrOrig.data;
                end = jItrOrig.next.data;//holding the pointers

                if (end == newList.gridWidth-1){
                    flagb=1;
                }

                if (flaga!=1){
                    jItr.data = start-1;
                    newList.addAfter(jItr,-1);
                    jItr = jItr.next;
                }
                if (flagb!=1){
                    jItr.data = end+1;
                    newList.addAfter(jItr,-1);
                    jItr = jItr.next;
                }
                flaga=0;

                jItrOrig = jItrOrig.next;
                jItrOrig = jItrOrig.next;

            }
            if (flagb==1){
                jItr.data = -1;
            }
            else{
                jItr.data = newList.gridWidth-1;
                newList.addAfter(jItr,-1);
                jItr = jItr.next;
            }
            // System.out.println();

        }
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
    // -------------------------------------------------------------------------------------------------------
    public void performAnd(CompressedImageInterface img) throws BoundsMismatchException
    {

        LinkedListImage image = (LinkedListImage)img;
        LinkedListImage imageAND = this.deepCopy() ;//PROBABLY IMPLEMENT DEEP CLONING HERE..
        // System.out.println("IMAGE 1 BEFORE AAANNNDDDDD (IMG)::::::");
        // image.ll.printLLdo();
        // System.out.println("IMAGE 2 BEFORE AAANNNDDDDD (IMG)::::::");
        // this.ll.printLLdo();
        Integer i=0,j=0,startIndex=0,endIndex=0,min1=0,max1=0,min2=0,max2=0,value1image1=0,value2image1=0,value1image2=0,value2image2=0,data=0;
        ListNodeHead iItr1 = this.ll.head;
        ListNodeHead iItr2 = image.ll.head;
        ListNodeHead iItr3 = imageAND.ll.head;//empty node but other parameters to correct value
        ListNodeHead jItr1 = iItr1;
        ListNodeHead jItr2 = iItr2;
        ListNodeHead jItr3 = iItr3;

        // System.out.println(ll.gridHeight + " "+ ll.gridWidth);

        for(i=0;i<this.ll.gridHeight;i++){
            // System.out.println();
            jItr1 = iItr1;
            jItr2 = iItr2;
            jItr3 = iItr3;

            while(true){
                if (jItr1.data==-1) {
                    while(jItr2.data!=-1){//empty the second image
                        data = jItr2.data;
                        jItr3.data = data;
                        jItr2 = jItr2.next;
                        imageAND.ll.addAfter(jItr3,-1);
                        jItr3 = jItr3.next;//-1 will be added automatically after parent while loop
                        // imageAND.ll.addAfter(jItr3,-1)
                        // jItr3 = jItr3.next;//now on null or -1
                    }
                    break;
                }
                if (jItr2.data==-1) {

                    while(jItr1.data!=-1){//empty the first image's row
                        data = jItr1.data;
                        jItr1=jItr1.next;
                        jItr3.data = data;
                        imageAND.ll.addAfter(jItr3,-1);
                        jItr3 = jItr3.next;
                    }
                    // while(jItr1.data!=-1){
                    // imageAND.ll.addAfter(jItr3,-1)
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
/////////////////////////////////////------------------------------------HERE
                // if (min1<max2) {//skewed case//skip the lesser case
                if(value2image1<value1image2){//values in 1 st img are lesser
                    jItr3.data = jItr1.data;
                    jItr1=jItr1.next;
                    imageAND.ll.addAfter(jItr3,jItr1.data);
                    jItr1=jItr1.next;//contains next value to be read
                    jItr3=jItr3.next;//contatins data
                    imageAND.ll.addAfter(jItr3,-1);
                    jItr3=jItr3.next;//contains -1
                }
                else if(value1image1>value2image2){//value in 2nd image are lesser
                    jItr3.data = jItr2.data;
                    jItr2=jItr2.next;
                    imageAND.ll.addAfter(jItr3,jItr2.data);
                    jItr2=jItr2.next;//contains next value to be read
                    jItr3=jItr3.next;//contatins data
                    imageAND.ll.addAfter(jItr3,-1);
                    jItr3=jItr3.next;//contains -1
                }
                // }
                // else if (){
                //
                // }
                else{//good normal case

                    jItr3.data = min1;//as current data was null
                    imageAND.ll.addAfter(jItr3,max2);
                    jItr3 = jItr3.next;//has max2 data
                    imageAND.ll.addAfter(jItr3,-1);
                    jItr3 = jItr3.next;//is null data or -1
                    // imageAND.addAfter(jItr3,max1);
                    // jItr3=jItr3.next;
                    // imageAND.addAfter(jItr3,min2);
                    // jItr3=jItr3.next;
                    jItr1=jItr1.next;
                    jItr1=jItr1.next;
                    jItr2=jItr2.next;
                    jItr2=jItr2.next;

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

                // imageAND.addAfter(jItr3,max1);
            }
            jItr3.data=-1;

//					while()

            if (i<this.ll.gridHeight-1) {//will not happen on the last iteration
                iItr1=iItr1.nextHead;
                iItr2=iItr2.nextHead;
                imageAND.ll.addBelow(iItr3,-1);
                iItr3=iItr3.nextHead;
            }


        }

        ListNodeHead iItrand = imageAND.ll.head;
        ListNodeHead jItrand = iItrand;
//				ListNodeHead ptr = iItrand;
        Integer val1=0,val2=0,c=0;
        while(iItrand!=null){
            jItrand = iItrand;
//				    ptr = jItrand;
//				    c=1;
            try {
                while (jItrand.next.next.next != null) {
                    if ((jItrand.next.data + 1) == jItrand.next.next.data) {
                        jItrand.next = jItrand.next.next.next;
                    }
                    else{
                        jItrand = jItrand.next;
                        jItrand = jItrand.next;
                    }
                }
            }
            catch (Exception e){
                //do nothing ?

            }
            iItrand = iItrand.nextHead;

        }

        //you need to implement this
        // System.out.println("AND COMING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//			imageAND.ll.printLLdo();
        // System.out.println("IMAGE RESULTANT OF AAANNNDDDDD (IMG)::::::");
        // imageAND.ll.printLLdo();
        this.ll = imageAND.ll;
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        return;
        // //you need to implement this
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
    // ----------------------------------------------------------------------------
    public void performOr(CompressedImageInterface img) throws BoundsMismatchException
    {
        LinkedListImage image = (LinkedListImage)img;
        LinkedListImage imageOR = this.deepCopy() ;//PROBABLY IMPLEMENT DEEP CLONING HERE..
//        System.out.println("BEFORE ORRRRR :::: IMAGE 1:(this)");
//        this.ll.printLLdo();
//        System.out.println("BEFORE ORRRRR :::: IMAGE 2:(image)");
//        image.ll.printLLdo();
        Integer i=0,j=0,startIndex=0,endIndex=0,min1=0,max1=0,min2=0,max2=0,value1image1=0,value2image1=0,value1image2=0,value2image2=0;
        ListNodeHead iItr1 = this.ll.head;
        ListNodeHead iItr2 = image.ll.head;
        ListNodeHead iItr3 = imageOR.ll.head;
        ListNodeHead jItr1 = iItr1;
        ListNodeHead jItr2 = iItr2;
        ListNodeHead jItr3 = iItr3;

        // System.out.println(ll.gridHeight + " "+ ll.gridWidth);

        for(i=0;i<this.ll.gridHeight;i++){
            // System.out.println();
            jItr1 = iItr1;
            jItr2 = iItr2;
            jItr3 = iItr3;

            while(true){
                if (jItr1.data==-1) {
                    // while(jItr2.data!=-1){
                    // imageOR.ll.addAfter(jItr3,-1)
                    // jItr3 = jItr3.next;//now on null
                    // }
                    break;
                }
                if (jItr2.data==-1) {
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
                    jItr1=jItr1.next;
                    jItr1=jItr1.next;
                    jItr2=jItr2.next;
                    jItr2=jItr2.next;

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

            if (i<this.ll.gridHeight-1) {//will not happen on the last iteration
                iItr1=iItr1.nextHead;
                iItr2=iItr2.nextHead;
                imageOR.ll.addBelow(iItr3,-1);
                iItr3=iItr3.nextHead;
            }
        }

        //you need to implement this
//        System.out.println("OR COMING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println("AFTER ORRRRR :::: IMAGE 2:(image)");
//        imageOR.ll.printLLdo();

        this.ll = imageOR.ll;
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        return;
    }

    // ----------------------------------------------------------------------------------------
    public void performXor(CompressedImageInterface img) throws BoundsMismatchException
    {
        //you need to implement this
        LinkedListImage image = (LinkedListImage)img;

//        System.out.println("before XORR Image1:(this):");
//        this.ll.printLLdo();
//        System.out.println("before XORR Image2:(image):");
//        image.ll.printLLdo();

        LinkedListImage imageXOR = image.deepCopy() ;
        LinkedListImage imageC = this.deepCopy() ;
        LinkedListImage imageB = image.deepCopy() ;
        //
        imageB.invert();
        imageXOR.performAnd(imageB);//  this AND img'
        imageB = image.deepCopy();
        imageC = this.deepCopy();
        imageC.invert();
        imageB.performAnd(imageC);//img AND this'
        imageXOR.performOr(imageB);//  (this AND img')OR(img AND this')

//        System.out.println(" XOR COMING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println("after XORR Image:(imageXOR):");
//        imageXOR.ll.printLLdo();

        this.ll = imageXOR.ll;
        return;
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public String toStringUnCompressed()
    {
        //you need to implement this
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public String toStringCompressed()
    {

        ListNodeHead iItr = ll.head;
        ListNodeHead jItr = iItr;
        String str = "";
        str += ll.gridHeight + " "+ ll.gridWidth + ",";
        do {
            jItr = iItr;

            do {
                str+= " " + jItr.data ;
                // System.out.print(jItr.data + " ");
                jItr=jItr.next;
            } while (jItr!=null);
            str +=",";
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
        Integer i=0,j=0;
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
    public static void main(String[] args) {
        // testing all methods here :
        boolean success = true;

        // check constructor from file
        CompressedImageInterface img1 = new LinkedListImage("sampleInputFile.txt");

        // check toStringCompressed
        String img1_compressed = img1.toStringCompressed();
        String img_ans = "16 16, -1, 0 15 -1, 3 7 -1, 2 7 -1, 2 2 6 7 -1, 6 7 -1, 6 7 -1, 4 6 -1, 2 4 -1, 2 3 14 15 -1, 2 2 13 15 -1, 11 13 -1, 11 12 -1, 10 11 -1, 9 10 -1, 7 9 -1";
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
                    System.out.println("Errorrrrrrrr");
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
            return;
        }

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

        // check numberOfBlackPixels
        int[] img1_black = img1.numberOfBlackPixels();
        success = success && (img1_black.length == 16);
        for (int i = 0; i < 16 && success; i++)
            success = success && (img1_black[i] == 15);
        if (!success)
        {
            System.out.println("setPixelValue or numberOfBlackPixels ERROR");
            return;
        }

        // check invert
        img1.invert();
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
            return;
        }

        // check Or
        try
        {
            img1.performOr(img2);
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
            return;
        }

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
            return;
        }

        // check toStringUnCompressed
        String img_ans_uncomp = "16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1";
        success = success && (img1.toStringUnCompressed().equals(img_ans_uncomp)) && (img2.toStringUnCompressed().equals(img_ans));

        if (!success)
        {
            System.out.println("toStringUnCompressed ERROR");
            return;
        }
        else
            System.out.println("ALL TESTS SUCCESSFUL! YAYY!");
    }
}
