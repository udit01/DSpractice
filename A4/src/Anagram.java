import java.io.File;
import java.util.Scanner;

public class Anagram {
    public static int sizeVocab ;

    public static void main (String args[]){
        //you need to implement this
        Scanner scVocab = null;
        Scanner scInput = null;
        File fileVocab = null;
        File fileInput = null;
        int sizeInput = 0;
        try {
            String vocabFilePath = args[0];
            String inputFilePath = args[1];
            fileVocab = new File(vocabFilePath);
            fileInput = new File(inputFilePath);
            scVocab = new Scanner(fileVocab);
            scInput = new Scanner(fileInput);
            storeVocab(scVocab);

            sizeInput = scInput.nextInt();


        }catch (Exception e) {
//            System.out.println("Incorrect file path or file format.");
             e.printStackTrace();
        }
//        return;
    }
    public static void storeVocab(Scanner sc){
        sizeVocab = sc.nextInt();

    }
}
