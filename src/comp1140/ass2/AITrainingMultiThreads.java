package comp1140.ass2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class AITrainingMultiThreads implements Runnable {
    static final int ROUNDS = 10;
    Thread t;
    String threadName;

    AITrainingMultiThreads(String tName){
        threadName = tName;
        System.out.println("Thread created:"+tName);
    }

    public void run(){
        System.out.println("Thread running:"+threadName);
        try {
            fileIO("./s-rDataset-"+threadName+".csv");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void start(){
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
    public  void fileIO(String pathName){
        int winer;
        int[] stat = {0,0,0};
        File data = new File(pathName);
        try {
            data.createNewFile();
            BufferedWriter writer = new BufferedWriter( new FileWriter(data));

            for (int i=1;i<=ROUNDS;i++) {
                String patchCircle = PatchworkGame.initPatchCircle();
                String placement = new String("");
                System.out.println(patchCircle);
                writer.write(patchCircle+",");
                while (PatchworkGame.p1.timecount != 53 && PatchworkGame.p2.timecount != 53) {
//                System.out.print("sol");
//                for (String sol: PatchworkAI.generateAllPatchPlacement(patchCircle,placement)){
//                    System.out.print(sol+" ");
//                }
//                    placement = placement + PatchworkAI.smarterGenerator(patchCircle,placement);
//
//                if (!PatchworkGame.isPlacementValid(patchCircle, placement)) throw new Error("wrong plc");
//                    placement = placement + PatchworkAI.randomGenerator(patchCircle, placement);
//                if (!PatchworkGame.isPlacementValid(patchCircle, placement)) throw new Error("wrong plc");
                }
                winer = PatchworkGame.getScoreForPlacement(patchCircle,placement,true)>
                        PatchworkGame.getScoreForPlacement(patchCircle,placement,false)?1:2;
                stat[winer]++;
                writer.write(placement+","+winer+"\n");
                System.out.println(placement);
                System.out.println(winer);
//            if (PatchworkGame.isPlacementValid(patchCircle,placement)) System.out.println("OK");
                PatchworkGame.p1 = new State(1);
                PatchworkGame.p2 = new State(2);

            }
            System.out.println(stat[1]+" "+stat[2]);
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void sysIO(){
        int winer;
        int[] stat = {0,0,0};
            for (int i=1;i<=ROUNDS;i++) {
                String patchCircle = PatchworkGame.initPatchCircle();
                String placement = new String("");
                System.out.println(patchCircle);
                while (PatchworkGame.p1.timecount != 53 && PatchworkGame.p2.timecount != 53) {
//                System.out.println("sol:"+PatchworkAI.generateAllPositionPlacement(patchCircle,placement).size());
//                for (String sol: PatchworkAI.generateAllPositionPlacement(patchCircle,placement)){
//                    System.out.print(sol+" ");
//                }
//                    placement = placement + PatchworkAI.smarterGenerator(patchCircle,placement);
//
//                if (!PatchworkGame.isPlacementValid(patchCircle, placement)) throw new Error("wrong plc");
//                    placement = placement + PatchworkAI.generatePatchPlacement(patchCircle, placement);
//                if (!PatchworkGame.isPlacementValid(patchCircle, placement)) throw new Error("wrong plc");
//                      placement = placement + PatchworkAI.randomGenerator(patchCircle,placement);
                }
                winer = PatchworkGame.getScoreForPlacement(patchCircle,placement,true)>
                        PatchworkGame.getScoreForPlacement(patchCircle,placement,false)?1:2;
                stat[winer]++;
                System.out.println(placement);
                System.out.println(winer);
//            if (PatchworkGame.isPlacementValid(patchCircle,placement)) System.out.println("OK");
                PatchworkGame.p1 = new State(1);
                PatchworkGame.p2 = new State(2);

            }
            System.out.println(stat[1]+" "+stat[2]);
    }
    //for training, recursive training
//    public static void main(String[] args) {
//        fileIO("./s-rDataset.csv");
//    }
}
