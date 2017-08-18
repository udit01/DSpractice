import java.lang.*;

public class ListNode<E> implements ListNodeInterface<E> {
    private E element ;
    private ListNode<E> next = null;

    public ListNode() { this(null);}//ie now the data is also null

    public ListNode(E data) {this(data,null);}

    public ListNode(E data, ListNode<E> node) {
    	this.element = data;
    	next = node;//as both are only pointers	or better term is refrences
    }

    public ListNode<E> getNext(){
    	// if(this.next == null){
    	// 	throw new CustomException("There is no next node! You have reached a null refrence.");
    	// }
    	return next;
    }
    public E getElement(){
    	return element;
    }
    public void setNext(ListNode<E> ln) {
    	this.next = ln;
    	return;
    }
    public void setElement(E data){
        this.element = data;
        return;
    }
}
