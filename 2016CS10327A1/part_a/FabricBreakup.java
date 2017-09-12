import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class FabricBreakup{
  public static void main(String[] args) {

    Integer process_id = 0;
    Integer process_type = 0;
    Scanner scanner = null;
    // BufferedWriter writer = null;
    File fileIn = null;
    // File fileOut = null;
    CustomLlist stack;
    Integer queries = 0;
    Integer value = -1;

    try {
      fileIn = new File(args[0]);

      scanner = new Scanner(fileIn);

      queries = scanner.nextInt();
      stack = new CustomLlist();
      while(((queries--)!=0)&&(scanner.hasNext())){

        process_id = scanner.nextInt();
        process_type = scanner.nextInt();

        if (process_type == 1) {
          value = scanner.nextInt();
          stack.insertTop(value);
        }

        else if (process_type == 2) {
            System.out.println(process_id + " " + stack.topple());
        }

        else {
          //or maybe throw an Exception
          System.out.println("Incorrect process type in the file.. Stopping Execution.");
          break;//SHould I remove this?
        }
      }
      scanner.close();
    }catch(Exception e){
      e.printStackTrace();//DEBUG
    }
  }
}

class CustomLlist {
  LNode head = null;
  Integer num_nodes = 0;
  Integer max = -1;

  public boolean isEmpty(){
          return num_nodes == 0;
  }

  public void insertTop(Integer v){

    LNode mb = null;

    if (head == null) {
      LNode n = new LNode(v , head , mb , num_nodes);
      this.head = n;
      num_nodes++;
      return;
    }

    else{
      if (head.maxBelow == null) {
        if (v>=head.value) {
          mb = null;
        }
        else{
          mb = head;
        }
      }
      else  {
        if (v >= head.maxBelow.value) {
          mb = null;
        }
        else {
            mb = head.maxBelow;
        }
      }
      LNode n = new LNode(v , head , mb , num_nodes);
      this.head = n;
      num_nodes++;
      return;
    }
  }

  public Integer topple (){
    if (this.isEmpty()) {
      return -1;
    }
    if (this.head.maxBelow == null) {
      head = head.next;
      num_nodes--;
      return 0;
    }
    else{
      Integer eb = head.maxBelow.elementsBelow;
      Integer toppled = num_nodes - eb -1;
      this.num_nodes = eb ;
      head = head.maxBelow.next;
      return toppled;
    }
  }
}

class LNode{
  public LNode next =null;
  public LNode maxBelow =null;//null will mean that it itself is the max
  public Integer value = -1;
  public Integer elementsBelow = 0;

  public LNode(Integer v , LNode next , LNode maxB , Integer eb){
    this.value = v ;
    this.next = next;
    this.maxBelow = maxB;
    this.elementsBelow =eb;
  }
}

