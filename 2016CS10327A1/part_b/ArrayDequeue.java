public class ArrayDequeue implements DequeInterface {
  public Object[] a = new Object[1];
  public int num_elements = 0;
  public int maxSize = 1;

  // class EmptyDequeException extends Exception{
  //   EmptyDequeException(String s){
  //     super(s);
  //   }
  // }

  public void growArray(int n){
    //here num_elements will be equal to the max size previous
    Object[] b = new Object[n];
    for (int i=0;i<num_elements;i++ ) {
      b[i] = this.a[i];
    }
    this.a = b;
    this.maxSize = n;
    return;
  }


  public void insertFirst(Object o){
    //you need to implement this
    if (num_elements < maxSize) {
      for (int i=num_elements-1;i>=0 ;i-- ) {
        this.a[i+1] = this.a[i];
      }
      this.a[0] = o ;
    }
    else if (num_elements == maxSize) {
      this.growArray(2*maxSize);
      for (int i=num_elements-1;i>=0 ;i-- ) {
        this.a[i+1] = this.a[i];
      }
      this.a[0] = o ;
    }
    else{
      //for debugging pourposes
      System.out.println("This else in insertFirst should not have been called.");
    }
    num_elements++;
    return;
    // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }



  public void insertLast(Object o){
//you need to implement this
    if (num_elements < maxSize) {
      this.a[num_elements] = o ;
    }
    else if (num_elements == maxSize) {
      this.growArray(2*maxSize);
      this.a[num_elements] = o ;
    }
    else{
      //for debugging pourposes
      System.out.println("This else in insertLast should not have been called.");
    }
    num_elements++;
    // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    return;
  }



  public Object removeFirst() throws EmptyDequeException{
    // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    if (num_elements == 0) {
      throw new EmptyDequeException("You cannot removeFirst() because Deque is empty.");
    }
    Object temp = this.a[0];
    for (int i=0;i<num_elements ;i++ ) {
      this.a[i] = this.a[i+1];
    }
    num_elements--;
    return temp;
  }


  public Object removeLast() throws EmptyDequeException{
    // throw new  java.lang.UnsupportedOperationException("Not implemented yet.");
    if (num_elements == 0) {
      throw new EmptyDequeException("You cannot removeLast() because Deque is empty.");
    }
    num_elements--;
    return this.a[num_elements];
  }


  public Object first() throws EmptyDequeException{
    // throw new  java.lang.UnsupportedOperationException("Not implemented yet.");
    if (num_elements == 0) {
      throw new EmptyDequeException("You cannot call first() because Deque is empty.");
    }
    return this.a[0];
  }



  public Object last() throws EmptyDequeException{
    // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    if (num_elements == 0) {
      throw new EmptyDequeException("You cannot call last() because Deque is empty.");
    }
    return this.a[num_elements-1];
  }



  public int size(){
    // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    return this.num_elements;
  }


  public boolean isEmpty(){
    // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    return (this.num_elements==0);
  }


  public String toString(){
    // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    String s = "";
    // System.out.print("[");
    s = s + "[";
    if (num_elements>0) {
      for (int i = 0 ; i < num_elements - 1 ; i++ ) {
        s = s + this.a[i] + ","  ;
      }
      s = s + this.a[num_elements-1];
    }
    s = s + "]";
    return s;
  }


  public static void main(String[] args){
    int  N = 10;
    DequeInterface myDeque = new ArrayDequeue();
    for(int i = 0; i < N; i++) {
      myDeque.insertFirst(i);
      myDeque.insertLast(-1*i);
    }

    int size1 = myDeque.size();
    System.out.println("Size: " + size1);
    System.out.println(myDeque.toString());

    if(size1 != 2*N){
      System.err.println("Incorrect size of the queue.");
    }

    //Test first() operation
    try{
      int first = (int)myDeque.first();
      int size2 = myDeque.size(); //Should be same as size1
      if(size1 != size2) {
        System.err.println("Error. Size modified after first()");
      }
    }
    catch (EmptyDequeException e){
      System.out.println("Empty queue");
    }

    //Remove first N elements
    for(int i = 0; i < N; i++) {
      try{
        int first = (Integer)myDeque.removeFirst();
      }
      catch (EmptyDequeException e) {
        System.out.println("Cant remove from empty queue");
      }

    }


    int size3 = myDeque.size();
    System.out.println("Size: " + myDeque.size());
    System.out.println(myDeque.toString());

    if(size3 != N){
      System.err.println("Incorrect size of the queue.");
    }

    try{
      int last = (int)myDeque.last();
      int size4 = myDeque.size(); //Should be same as size3
      if(size3 != size4) {
        System.err.println("Error. Size modified after last()");
      }
    }
    catch (EmptyDequeException e){
      System.out.println("Empty queue");
    }

    //empty the queue  - test removeLast() operation as well
    while(!myDeque.isEmpty()){
        try{
          int last = (int)myDeque.removeLast();
        }
        catch (EmptyDequeException e) {
          System.out.println("Cant remove from empty queue");
        }
    }

    int size5 = myDeque.size();
    if(size5 != 0){
      System.err.println("Incorrect size of the queue.");
    }

  }

}
