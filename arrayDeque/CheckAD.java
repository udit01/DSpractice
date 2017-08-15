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
        ln.contains(45);
        ln.contains(62);
        ln.print();

        System.out.println("END");

}
}
