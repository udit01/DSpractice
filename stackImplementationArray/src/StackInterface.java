import java.lang.*;

public interface StackInterface<E>{
    //private int MAX_ELEMENTS;
    //private int numElements;
    //private E[] a;
    //public stack();
    //public stack(int n);
    public E top() throws CustomException;
    public E pop() throws CustomException;
    public void push(E e) throws CustomException;
    public int size() ;
    public void printStack() throws CustomException;
}