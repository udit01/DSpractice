public class CheckLL {
    public static void main(String args[]){
    	System.out.println("Linked List checker class:");
        LinkedList<Integer> ln = new LinkedList<Integer>();
		//try {
            System.out.println(ln.isEmpty());
        //} catch (CustomException e) {
        //    e.printStackTrace();
        //}
            ln.print();

            ln.addFirst(67);
            try {//is this allowed? Logically yes as at a time only one exception can propogate
              ln.removeFirst();
            } catch (CustomException e) {
              e.printStackTrace();
            }
            ln.addFirst(62);
            // ln.isEmpty();
            System.out.println(ln.isEmpty());
            System.out.println(ln.size());
            // ln.size();
            ln.contains(45);
            ln.contains(62);
            ln.print();
      //   ln2.setNext(ln3);
      //   ln.setNext(ln2);
      //   ln3.setNext(ln);
      //
      //   try {//is this allowed? Logically yes as at a time only one exception can propogate
      //
			// System.out.println("BELOW LINE SHOULD PRINT 3");
      //       System.out.println("Next node elem for ln2 is:" + ln2.getNext().getElement());
			// System.out.println("BELOW LINE SHOULD PRINT 1");
      //       System.out.println("Next node elem for ln3 is:" + ln3.getNext().getElement());
			// System.out.println("BELOW LINE SHOULD PRINT 3");
      //       System.out.println("Next node elem for ln5 is:" + ln5.getNext().getElement());
			// System.out.println("BELOW LINE SHOULD PRINT 2");
      //       System.out.println("Next node elem for ln is:" + ln.getNext().getElement());
      //   } catch (CustomException e) {
      //       e.printStackTrace();
      //   }

        System.out.println("END");

    }
}
