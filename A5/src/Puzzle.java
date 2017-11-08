import java.io.*;

public class Puzzle {
    public static void main(String args[]){
        //you need to implement this
        OutputStream oStream;
        long startTime;
        long time1;
        long time2;
        int t=0;
        BufferedReader readerI;//input reader
//        BufferedWriter writerO;

        try {
            startTime = System.currentTimeMillis();
            readerI = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])) );
            oStream = new BufferedOutputStream(new FileOutputStream(args[1]));
            time1 = System.currentTimeMillis();

            t = Integer.parseInt(readerI.readLine());
            for(int i=0;i<t;i++){

            }

            oStream.flush();

            time2 = System.currentTimeMillis();
//            oStream.write(("Vocabulary processed in: "+(time1-startTime)+"ms\n").getBytes());
//            oStream.write(("Input processed in: "+(time2-time1)+"ms\n").getBytes());
//            oStream.write(("Hashing took time "+(hashTime/1000000)+"ms\n").getBytes());
//            oStream.write((collisons+" is the number of collisons\n").getBytes());
            oStream.close();

        }catch (Exception e) {

            e.printStackTrace();
//            System.out.println("Incorrect file path or file format.");
        }
    }
}
