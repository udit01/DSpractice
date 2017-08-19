import java.lang.*;

public class LinkedList<E> implements LinkedListInterface<E> {
  public ListNode<E> head = null;
  public int num_nodes = 0;
  // public LinkedList<E>(){//HOW DO WE DECIDE IF WE NEED A CONSTRUCTOR
  //
  // }
  public boolean isEmpty(){
          return num_nodes == 0;
  }
  public int size(){
          return num_nodes;
  }
  public void addFirst(E item){
          head = new ListNode<E>(item,head);
          num_nodes++;
          return;
  }
  public E getFirst(){
          if(num_nodes == 0) {
                  System.out.println("Your LinkedList is empty!");
                  return null;
          }
          return head.getElement();
  }
  //below method is impractical
  public boolean contains(E item){
          if (num_nodes==0) {
                  return false;
          }
          ListNode<E> ptr =this.head;
          if(ptr.getElement()==item) {
                  System.out.println("Your Linkedlist contains: " +item);
                  return true;
          }
          while(ptr.getNext()!=null) {
                  ptr = ptr.getNext();

                  if(ptr.getElement()==item) {
                          System.out.println("Your Linkedlist contains: " +item);
                          return true;
                  }
          }
          System.out.println("Your Linkedlist does not contain: " +item);
          return false;
  }
  public ListNode<E> getElementRef(E item){    //might throw null or
          if (num_nodes==0) {
                  return null;
          }
          ListNode<E> ptr = this.head;
          // try{
                  while(ptr!=null) {
                          if(ptr.getElement()==item) {
                                  return ptr;
                          }
                          ptr = ptr.getNext();
                  }
          // }
          // catch(CustomException e) {
          //         e.printStackTrace();
          // }
          return null;
  }
  public E removeFirst(){
          E temp = null;
          if(head == null) {
                  System.out.println("Your linked list is empty! SO cannot remove the first element.");
                  return null;
          }
          else{
                  temp = head.getElement();
                  head = head.getNext();
                  num_nodes--;
          }

          // System.out.println(head==null);
          // System.out.println("Removed first element from the list.");
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
                  // try{
                          while(ptr!=null) {
                                  System.out.print(ptr.getElement()+ " , ");
                                  ptr = ptr.getNext();
                          }
                  // }
                  // catch(CustomException e) {
                  //         // e.printStackTrace();
                  // }
                  System.out.println("END.");
          }

          return;
  }

  public void addAfter(ListNode<E> ln , E elem){//verify that ln != null
    if (ln!=null) {
      ListNode<E> newNode = new ListNode<E>(elem , null);
      newNode.setNext(ln.getNext());
      ln.setNext(newNode);
      return;
    }
    else{
      System.out.println("No node address given, therefore element given is not added to list.");
      return;
    }
  }
	public void removeAllAfter(ListNode<E> ln){
    if (ln == null) {
      System.out.println("Null pointer is passed so cannot do removeAllAfter.");
      return;
    }
    ln.setNext(null);
    return;
  }
  public void removeAllBefore(ListNode<E> ln){
    if (ln == null) {
      System.out.println("Null pointer is passed so cannot do removeAllABefore.");
      return;
    }
    this.head = ln;
    return;
  }

  //How to make an iterator class ? thich will take this class as arg and iterate on it.?

}
