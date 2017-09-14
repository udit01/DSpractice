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

		System.out.println(gridHeight + " "+ gridWidth);

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

		System.out.println(gridHeight + " "+ gridWidth);
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

		public LinkedListImage(String filename)
		{
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
		public LinkedListImage(LinkedListImage imageCompressed){
			ll = new LinkedList2D(imageCompressed.ll.gridHeight,imageCompressed.ll.gridWidth);//numCols array range as paramerter
			ll.numRows=imageCompressed.ll.numRows;
			ll.numCols= imageCompressed.ll.numCols;
			ll.gridHeight=imageCompressed.ll.gridHeight;
			ll.gridWidth=imageCompressed.ll.gridWidth;

			//or simply
			//HOW TO TRUELY CLONE ?
			// this.ll = imageCompressed.ll;
		}
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
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void performAnd(CompressedImageInterface img) throws BoundsMismatchException
    {
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void performOr(CompressedImageInterface img) throws BoundsMismatchException
    {
			LinkedListImage image = (LinkedListImage)img;
			LinkedListImage imageOR = new LinkedListImage(this) ;//PROBABLY IMPLEMENT DEEP CLONING HERE..

			//TO XOR of image and this -> store it in imageOR
			Integer i=0,j=0,startIndex=0,endIndex=0,min1=0,max1=0,min2=0,max2=0,v1i1=0,v2i1=0,v1i2=0,v2i2=0;
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

					v1i1 =	jItr1.data;
					v2i1 =	jItr1.next.data;
					v1i2 =	jItr2.data;
					v2i2 =	jItr2.next.data;



					min1 = Math.min(v1i1,v1i2);
					max1 = Math.max(v1i1,v1i2);
					min2 = Math.min(v2i1,v2i2);
					max2 = Math.max(v2i1,v2i2);


					//cases depending on relative value of min12 and max12 6 or 3 cases

					// very very COMPLEX LOGIC if do by 6 cases and I think still cases left IN AND and XOR

					if (max1>min2) {//skewed case//skip the lesser case
						if(v1i1<v1i2){//values in 1 st img are lesser
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
						imageOR.ll.addAfter(jItr3,max2);
						jItr3 = jItr3.next;//has max2 data
						jItr3 = jItr3.next;//is null data
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
		imageOR.ll.printLLdo();
		this.ll = imageOR.ll;
		// throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		return;
    }

    public void performXor(CompressedImageInterface img) throws BoundsMismatchException
    {
		//you need to implement this
			// LinkedListImage image = (LinkedListImage)img;
			// LinkedListImage imageXOR = new LinkedListImage(this) ;
			//
			// //TO XOR of image and this -> store it in imageXOR
			// Integer i=0,j=0,startIndex=0,endIndex=0;
			// ListNodeHead iItr1 = this.ll.head;
			// ListNodeHead iItr2 = img.ll.head;
			// ListNodeHead jItr1 = iItr1;
			// ListNodeHead jItr2 = iItr2;
			//
			// // System.out.println(ll.gridHeight + " "+ ll.gridWidth);
			//
			// for(i=0;i<x;i++){
			// 	// System.out.println();
			// 	iItr=iItr.nextHead;
			// }
			// jItr = iItr;
			// for (j=0;j<ll.numCols[x] ;j = j+2 ) {
			// 	// System.out.print(jItr.data + " ");
			// 	// jItr=jItr.next;
			// 	if (jItr.data == -1) {
			// 		return true;
			// 	}
			// 	startIndex = jItr.data;
			// 	jItr = jItr.next;
			// 	endIndex = jItr.data;
			// 	jItr = jItr.next;
			// 	if ((y>=startIndex)&&(y<=endIndex)) {
			// 		return false;
			// 	}
			// }

			// this.ll = imageXOR.ll;

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
		public static void printGrid(boolean[][] grid,Integer width,Integer height){
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
    	String img_ans = "16 16, -1, 5 7 -1, 3 7 -1, 2 7 -1, 2 2 6 7 -1, 6 7 -1, 6 7 -1, 4 6 -1, 2 4 -1, 2 3 14 15 -1, 2 2 13 15 -1, 11 13 -1, 11 12 -1, 10 11 -1, 9 10 -1, 7 9 -1";
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
        String img_ans_uncomp = "16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1";
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
