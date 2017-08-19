public class StackL implements StackInterfaceL{
  private LinkedList<Integer> ll = new LinkedList<Integer>();
  private int num_elements = 0;

  public int top() throws EmptyStackException{
    if(ll.head == null){
      throw new EmptyStackException("The stack is empty.");
    }
    return ll.getFirst();
  }
  public int pop() throws EmptyStackException{
    if (ll.head == null) {
      throw new EmptyStackException("The stack is empty.");
    }
    this.num_elements-- ;
    return ll.removeFirst();
  }
  public void push(int e){
    this.num_elements++ ;
    ll.addFirst(e);
    return;
  }
  public int size() {
    return this.num_elements;
  }
  public void print(){
    ll.print();
    return;
  }
  public boolean isEmpty(){
    return this.num_elements == 0;
  }
  public int findLargest(){
    if (this.num_elements == 0) {
      //THROW AN EXCEPTION PROBABLY
      return -1;//depth returned is -1;
    }
    int max = -1;
    ListNode ptr = ll.head ;
    int position = 0;
    int depth = 0;
    int elem = -3;
    while(ptr != null){
      elem = (int)ptr.getElement();
      System.out.println("45 line in STACKL"+elem);//DEBUG
      if (elem > max) {
        max = elem;
        depth = position +1 ;//depth is 1 indexed
      }
      ptr = ptr.getNext();
      position++;
    }
    return depth; //depth is 1 indexed
  }
  public int topple(int depth){
    if (depth <= 0) {
      return 0;
    }
    else if (depth > num_elements) {
      return -1;//error code
    }
    ListNode ptr = ll.head ;

    for (int i = 0; i<depth ; i++) {
        ptr = ptr.getNext();
    }
    ll.head = ptr;
    return depth-1;//number of toppled elements;
  }

}
