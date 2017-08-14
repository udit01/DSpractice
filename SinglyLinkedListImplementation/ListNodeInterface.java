import java.lang.*;

public interface ListNodeInterface<E>{
    
    public ListNode<E> getNext() throws CustomException;
    public E getElement();
    public void setNext(ListNode<E> ln) ;
}
