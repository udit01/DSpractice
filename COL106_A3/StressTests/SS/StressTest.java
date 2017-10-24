package col106.a3;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StressTest {
    public static void main(String argv[]) throws Exception {
        long startTime = System.currentTimeMillis();
        DuplicateBTree<Integer, Integer> graph = new BTree<>(4);
        int V = 1000;
        int E = 10000;
        //        int x=0;
        ArrayList<ArrayList<Integer>> g = new ArrayList<>(V);
        Random r = new Random();
        for (int i = 0; i < V; i++) g.add(new ArrayList<>());
        for (int i = 0; i < E; i++) {
            int v1 = r.nextInt(V);
            int v2 = r.nextInt(V);
            if (v1 != v2) {
                g.get(v1).add(v2);
                //                System.out.println(v1+" "+v2);
                graph.insert(v1, v2);
                //                System.out.println(graph.toString());
                //                StringFormatChecker sf = new StringFormatChecker(graph.toString(), 4);
                //                if (!sf.verify()){
                //                    System.out.println(sf.getMessage());
                ////                    Syst
                //                    System.exit(0);
                //
                //                }
                //
                //                else
                //                    System.out.print("Verified:");
                //                System.out.println(sf.getArgument());

                //                x++;
                //                for (int j = 0; j < V; j++) {
                //                    List<Integer> neighbourhood;
                //                    ArrayList<Integer> correctAnswer = g.get(j);
                //                    correctAnswer.sort(Integer::compareTo);
                //                    try {
                //
                //                        neighbourhood = graph.search(j);
                //                        neighbourhood.sort(Integer::compareTo);
                //                        if (!neighbourhood.equals(correctAnswer)) {
                //                            System.out.println("Incorrect search result for " + j);
                //                            System.out.println(graph.toString());
                //
                //                            System.out.println(correctAnswer);
                //                            System.out.println(neighbourhood);
                //                            System.exit(0);
                //
                //                        }
                //                    } catch (IllegalKeyException e) {
                //                        if(correctAnswer.size()==0) {
                //                            continue;
                //                        }
                //                        else{
                //                            System.out.println("Incorrect search result for " + j);
                //                            System.out.println(graph.toString());
                //
                //                            System.out.println(correctAnswer);
                //                            System.out.println(neighbourhood);
                //                            System.exit(0);
                //                        }
                //                    }
                //                    i=
                //                }
                //                }
            }
        }
        //        System.out.println(x);
        //        System.out.println(graph.size());
        for (int i = 0; i < V; i++) {
            List<Integer> neighbourhood = graph.search(i);
            neighbourhood.sort(Integer::compareTo);
            ArrayList<Integer> correctAnswer = g.get(i);
            correctAnswer.sort(Integer::compareTo);
            if (!neighbourhood.equals(correctAnswer)) {
                System.out.println("Incorrect search result for " + i);
                System.out.println(graph.toString());

                System.out.println(correctAnswer);
                System.out.println(neighbourhood);
                throw new NotImplementedException();

            }
        }

        for (int i = 0; i < V; i++) {
            graph.delete(i);
            g.remove(i);

            List<Integer> neighbourhood = graph.search(i);
            neighbourhood.sort(Integer::compareTo);
            ArrayList<Integer> correctAnswer = g.get(i);
            correctAnswer.sort(Integer::compareTo);
            StringFormatChecker sf = new StringFormatChecker(graph.toString(), 4);
            System.out.println(sf.getArgument());
            if (neighbourhood.size()!=0) {
                System.out.println("Incorrect search result for " + i);
                System.out.println(graph.toString());

                System.out.println(correctAnswer);
                System.out.println(neighbourhood);
                throw new NotImplementedException();
            }
        }
        long time = System.currentTimeMillis() - startTime;
        //        System.out.println(graph);
        StringFormatChecker sf = new StringFormatChecker(graph.toString(), 4);
        if (!sf.verify()) System.out.println(sf.getMessage());
        else System.out.print("Verified:");
        System.out.println(sf.getArgument());
        System.out.println("time: " + time + " millis");
    }
}
