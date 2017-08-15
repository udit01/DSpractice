import java.lang.*;

public class LinkedList<E> implements LinkedListInterface<E> {
    private ListNode<E> head = null;
    private int num_nodes = 0;
    public boolean isEmpty(){
    	return num_nodes == 0;
    }
	public int size(){
		return num_nodes;
	}
	public void addFirst(E item){
		//if(head == null){
		//	head = new ListNode<E>(item);
		//	return;
		//}
		//else{
		//	ListNode<E> ln = new ListNode<E>(item,head);
		//	head = ln;
		//	return;
		//}
		head = new ListNode<E>(item,head);
		num_nodes++;
		System.out.println("Added: " +item+" to the linked list.");
		return;
	}
	public E getFirst() throws CustomException{
		if(num_nodes == 0){
			throw new CustomException("Your LinkedList is empty!");
		}
		System.out.println("First Element of this list is: "+ head.getElement());
		return head.getElement();
	}
	//below method is impractical
	public boolean contains(E item){
    if (num_nodes==0) {
      return false;
    }
		ListNode<E> ptr =this.head;
		try{
      // System.out.println("Line4141");
      if(ptr.getElement()==item){
          System.out.println("Your Linkedlist contains: " +item);
        return true;
      }
		    while(ptr.getNext()!=null){
          // System.out.println("In linked list contains Line 42" +ptr.getElement());

			    if(ptr.getElement()==item){
			        System.out.println("Your Linkedlist contains: " +item);
				    return true;
			    }
          ptr = ptr.getNext();
		    }
		}
		catch(CustomException e) {
            e.printStackTrace();
		}
		System.out.println("Your Linkedlist does not contain: " +item);
		return false;
	}
	public ListNode<E> getElementRef(E item){//might throw null or
    if (num_nodes==0) {
      return null;
    }
    ListNode<E> ptr = head;
  		try{
        while(ptr.getNext()!=null){
    			if(ptr.getElement()==item){
    				return ptr;
    			}
    		}
      }
      catch(CustomException e){
        e.printStackTrace();
      }
		return null;
	}
	public E removeFirst() throws CustomException{
	    E temp = null;
	    if(head == null){
	        throw new CustomException("Your linked list is empty! SO cannot remove the first element.");
	    }
	    else{
	        temp = head.getElement();
          try{
            head = head.getNext();
          }
          catch(CustomException e){
            head = null;
          }
	        num_nodes--;
	    }

      System.out.println(head==null);
      System.out.println("Removed first element from the list.");
	    return temp;
	}
  public void print(){
    System.out.println("Printing list from left(head) to right:");
    if (num_nodes==0) {
      System.out.println("EMPTY LIST!");
    }
    else{
      System.out.print("HEAD is->");
      ListNode<E> ptr = head;
    		try{
          while(ptr!=null){
      			System.out.print(ptr.getElement()+ " , ");
            ptr = ptr.getNext();
      		}
        }
        catch(CustomException e){
          System.out.println("END.");
          // e.printStackTrace();
        }
      }
		return ;
  }
}
