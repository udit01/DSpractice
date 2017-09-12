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

		for(i=0;i<this.numRows;i++){
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
			Integer i=0,j=0,width=0,height=0,temp=0;
			boolean[][] grid = null;
			try {
				fileIn = new File(filename);
				sc = new Scanner(fileIn);
				width = sc.nextInt();
				height = sc.nextInt();
				grid = new boolean[height][width];

				for (i=0;i<height ;i++ ) {
					for (j=0;j<width ;j++ ) {
						temp = sc.nextInt();
						if (temp==1) {
							grid[i][j]=true;
						}
						else {
							grid[i][j]=false;
						}
					}
				}

			}catch (Exception e) {
				e.printStackTrace();
			}

			new LinkedListImage(grid,width,height);
			// throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}

    public LinkedListImage(boolean[][] grid, int width, int height)
    {

			ll = new LinkedList2D(height,width);//numCols array range as paramerter

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

			System.out.println("FINAL STORED ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			ll.printLLdo();

    }

    public boolean getPixelValue(int x, int y)
    {
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void setPixelValue(int x, int y, boolean val)
    {
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

    public void performAnd(CompressedImageInterface img)
    {
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void performOr(CompressedImageInterface img)
    {
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void performXor(CompressedImageInterface img)
    {
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
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
					str+=jItr.data + " ";
					// System.out.print(jItr.data + " ");
					jItr=jItr.next;
				} while (jItr!=null);
				str +=",";
				// System.out.println();
				iItr = iItr.nextHead;
			} while (iItr!=null);
			System.out.println(str);
			return str;

		//you need to implement this
		// throw new java.lang.UnsupportedOperationException("Not implemented yet.");
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

    	// check constructor from grid
    	CompressedImageInterface img2 = new LinkedListImage(grid, 16, 16);
    	String img2_compressed = img2.toStringCompressed();
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
