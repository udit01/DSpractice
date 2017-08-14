public class Main {
    public static void main(String args[]) throws CustomException {
        Stack<Integer> s = new Stack(20);


//        s.printStack();

        try {
            Integer t = s.pop();
        } catch (CustomException e) {
            e.printStackTrace();
        }
        try {
            s.push(4);
        } catch (CustomException e) {
            e.printStackTrace();
        }

        try {
            s.push(5);
        } catch (CustomException e) {
            e.printStackTrace();
        }

        try {
            s.push(6);
        } catch (CustomException e) {
            e.printStackTrace();
        }

        try {
            s.push(8);
        } catch (CustomException e) {
            e.printStackTrace();
        }


        try {
            s.printStack();
        } catch (CustomException e) {
            e.printStackTrace();
        }
        System.out.println(s.size());
        Integer i;
//        i = s.top();

    }
}
