public interface StackInterfaceL{
    public int top() throws EmptyStackException;
    public int pop() throws EmptyStackException;
    public void push(int e) ;
    public int size() ;
    public void print();
    public boolean isEmpty();
    public int findLargest(); //returns the depth of largest element from top;//returns -999 if cannot find any element ie empty/Or it could retrun the pointer direcly
    public int topple(int depth);//will topple the elems and return depth-1 ie the number of toppled elements

}
