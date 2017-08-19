import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class FabricBreakup{
  public static void main(String[] args) {

    int process_id = 0;
    int process_type = 0;
    Scanner scanner = null;
    BufferedWriter writer = null;
    File fileIn = null;
    File fileOut = null;
    StackL stack = new StackL();
    int queries = 0;
    int value = -1;
    int depth = -3;
    int toppled = -4;
    int dummy = 90;
    try {
      fileIn = new File(args[0]);
      fileOut = new File(args[1]);

      scanner = new Scanner(fileIn);
      // System.out.println(scanner.nextLine());
      // writer WILL OVER WRITE THE EXISTING CONTENTS OF fileOut
      writer = new BufferedWriter(new FileWriter(fileOut));
      // writer.write("HELLOLALLAoo.");
      // writer.newLine();
      // writer.write("HELLOoLLLLAAo.");
      // writer.write("HELLOoo.");

      queries = scanner.nextInt();
      while(((queries--)!=0)&&(scanner.hasNext())){

        process_id = scanner.nextInt();
        process_type = scanner.nextInt();

        if (process_type == 1) {
          value = scanner.nextInt();
          stack.push(value);
        }

        else if (process_type == 2) {
          depth = -3;
          // System.out.println("Line 49 depth is:" + depth);
          depth = stack.findLargest();
          if (depth == -1) {
            writer.write(process_id + " " + "-1");
            writer.newLine();
          }
          else if (depth == -3) {
            //What just happende?
          }
          else{
            toppled =  stack.topple(depth);
            // System.out.println("Line 60 toppled is:" + toppled);
            writer.write(process_id + " " + toppled);
            dummy++;
            writer.newLine();
          }
        }

        else {
          System.out.println("Incorrect process type in the file.. Stopping Execution.");
          break;//SHould I remove this?
        }
      }
      scanner.close();
      writer.close();
    }catch(Exception e){
      try{
        scanner.close();
        writer.close();
      }
      catch(Exception e1){
        e1.printStackTrace();
      }

      e.printStackTrace();//DEBUG

    }
  }
}
