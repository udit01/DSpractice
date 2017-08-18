import java.lang.*;

public interface ListNodeInterface<E>{

    public ListNode<E> getNext();
    public E getElement();
    public void setNext(ListNode<E> ln);
    public void setElement(E data);
}
