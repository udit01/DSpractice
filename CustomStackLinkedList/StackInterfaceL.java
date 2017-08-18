public interface StackInterface<E>{
    public E top() throws CustomException;
    public E pop() throws CustomException;
    public void push(E e) ;
    public int size() ;
    public void printStack();
    public boolean isEmpty();
    public int findLargest(); //returns the depth of largest element from top;//returns -999 if cannot find any element ie empty
    public int topple(int depth);//will topple the elems and return depth-1 ie the number of toppled elements
}
