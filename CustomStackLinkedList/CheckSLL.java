public class CheckSLL {
public static void main(String args[]){
        System.out.println("Stack checker class:");
        StackL s = new StackL();
        //try {
        System.out.println(s.isEmpty());
        //} catch (CustomException e) {
        //    e.printStackTrace();
        //}
        s.print();

        s.push(67);
        try {    //is this allowed? Logically yes as at a time only one exception can propogate
        System.out.println(s.pop());
        } catch (EmptyStackException e) {
                e.printStackTrace();
        }
        s.push(62);
        // ln.isEmpty();
        System.out.println(s.isEmpty());
        System.out.println(s.size());
        // ln.size();
        // ln.contains(45);
        // ln.contains(62);

        s.push(100);
        s.push(95);
        s.push(75);
        s.push(60);
        s.print();
        try {    //is this allowed? Logically yes as at a time only one exception can propogate
          System.out.println(s.top());
        } catch (EmptyStackException e) {
                e.printStackTrace();
        }
        System.out.println(s.findLargest());
        s.print();
        System.out.println(s.topple(s.findLargest()));
        s.print();
        System.out.println("END");

}
}
