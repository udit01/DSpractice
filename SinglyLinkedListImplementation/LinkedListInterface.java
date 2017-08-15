import java.lang.*;

public interface LinkedListInterface<E>{
	public boolean isEmpty();
	public int size();
	public E getFirst() throws CustomException;
	//below mehtod return the acutal val of the req element
	public boolean contains(E item);
	public ListNode<E> getElementRef(E item);//might throw null or
	public void addFirst(E item);
	public E removeFirst() throws CustomException;
	public void print() ;
}
