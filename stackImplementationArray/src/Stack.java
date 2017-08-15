import java.lang.*;


public class Stack<E> implements StackInterface<E> {
    private int MAX_ELEMENTS = 0;
    private int numElements = 0;
    private E[] a;


    public Stack() {
        this(100);
    }

    public Stack(int n) {
        a = (E[]) new Object[n];
//        a =  new E[n];
        MAX_ELEMENTS = n;
        //return ;//what?;
    }

    public E top() throws CustomException {
        if (numElements == 0)
            throw new CustomException("The stack is empty");
        System.out.println("The element at the top of the stack is: " + a[numElements-1]);
        return a[numElements - 1];
    }

    public E pop() throws CustomException {
        if (numElements == 0)
            throw new CustomException("The stack is empty");
        numElements--;
        System.out.println("You have just popped out " + a[numElements] +" from the stack.");
        return a[numElements];
    }

    public void push(E i) throws CustomException {
        if (numElements == MAX_ELEMENTS)
            throw new CustomException("The stack is full, max size of array is reached");

        a[numElements] = i;
        numElements++;
        System.out.println("You have just pushed " +   i +" in the stack.");
        return;
    }

    public int size() {
        return this.numElements;
    }

    public void printStack() throws CustomException{
        if (numElements == 0 ){
            throw new CustomException("This stack is empty, nothing to print");
        }
        System.out.println("Below is the stack from top(left) to bottom(right).");
        for (int i = numElements-1 ; i>=0 ; i--){
            System.out.print(a[i] + " , ");
        }
        System.out.println("END");
    }

}

