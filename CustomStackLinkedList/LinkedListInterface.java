import java.lang.*;

public interface LinkedListInterface<E>{
	public boolean isEmpty();
	public int size();
	public E getFirst();
	//below mehtod return the acutal val of the req element
	public boolean contains(E item);
	public ListNode<E> getElementRef(E item); //might throw null or
	public void addFirst(E item);
	public E removeFirst();
	public void addAfter(ListNode<E> ln  , E elem);//verify that ln != null
	public void removeAllAfter(ListNode<E> ln);
	public void removeAllBefore(ListNode<E> ln);//essentially declare this as head
	public void print();
}
