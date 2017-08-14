public class CheckNode {
    public static void main(String args[]){
    	System.out.println("Node checker class:");
        ListNode<Integer> ln = new ListNode<Integer>(1);
        ListNode<Integer> ln2 = new ListNode<Integer>(2,ln);
        ListNode<Integer> ln3 = new ListNode<Integer>(3,ln);
        ListNode<Integer> ln5 = new ListNode<Integer>(5,ln3);
        
		//try {
            System.out.println("Element for ln is:" + ln.getElement());
            System.out.println("Element for ln2 is:" + ln2.getElement());
            System.out.println("Element for ln3 is:" + ln3.getElement());
            System.out.println("Element for ln5 is:" + ln5.getElement());
        //} catch (CustomException e) {
        //    e.printStackTrace();
        //}
        
        try {//is this allowed? Logically yes as at a time only one exception can propogate

            System.out.println("Next node elem for ln2 is:" + ln2.getNext().getElement());
            System.out.println("Next node elem for ln3 is:" + ln3.getNext().getElement());
            System.out.println("Next node elem for ln5 is:" + ln5.getNext().getElement());
            //THE BELOW STATEMENT SHOULD DEFINATELY THROW AN EXCEPTION
            System.out.println("Next node elem for ln is:" + ln.getNext().getElement());
        } catch (CustomException e) {
            e.printStackTrace();
        }
        
        ln2.setNext(ln3);
        ln.setNext(ln2);
        ln3.setNext(ln);
        
        try {//is this allowed? Logically yes as at a time only one exception can propogate

			System.out.println("BELOW LINE SHOULD PRINT 3");
            System.out.println("Next node elem for ln2 is:" + ln2.getNext().getElement());
			System.out.println("BELOW LINE SHOULD PRINT 1");
            System.out.println("Next node elem for ln3 is:" + ln3.getNext().getElement());
			System.out.println("BELOW LINE SHOULD PRINT 3");
            System.out.println("Next node elem for ln5 is:" + ln5.getNext().getElement());
			System.out.println("BELOW LINE SHOULD PRINT 2");
            System.out.println("Next node elem for ln is:" + ln.getNext().getElement());
        } catch (CustomException e) {
            e.printStackTrace();
        }
        
        System.out.println("END");
        
    }
}
