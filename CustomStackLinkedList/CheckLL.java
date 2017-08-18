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
        try {    //is this allowed? Logically yes as at a time only one exception can propogate
                ln.removeFirst();
        } catch (CustomException e) {
                e.printStackTrace();
        }
        ln.addFirst(62);
        // ln.isEmpty();
        System.out.println(ln.isEmpty());
        System.out.println(ln.size());
        // ln.size();
        // ln.contains(45);
        // ln.contains(62);

        ln.addFirst(100);
        ln.addFirst(95);
        ln.addFirst(75);
        ln.addFirst(60);
        ln.print();
        ln.addAfter(ln.getElementRef(95) , 20);
        ln.print();
        ln.removeAllAfter(ln.getElementRef(20));
        ln.print();
        ln.addAfter(ln.getElementRef(45) , 20);
        ln.print();
        System.out.println("END");

}
}
