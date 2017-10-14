package comp1140.ass2;

import java.io.*;


public class AITraining {
    static final int ROUNDS = 5000;
    public double loss(){
        return 0;
    }

    //adjusting the parameters
    public void adjust(){

    }
    public static void fileIO(String pathName){
        int winer;
        int[] stat = {0,0,0};
        File data = new File(pathName);
        try {
            data.createNewFile();
            BufferedWriter writer = new BufferedWriter( new FileWriter(data));

            for (int i=1;i<=ROUNDS;i++) {
                String patchCircle = PatchworkGame.initPathCircle();
                String placement = new String("");
                System.out.println(patchCircle);
                writer.write(patchCircle+",");
                while (PatchworkGame.p1.timecount != 53 && PatchworkGame.p2.timecount != 53) {
//                System.out.print("sol");
//                for (String sol: PatchworkAI.generateAllPatchPlacement(patchCircle,placement)){
//                    System.out.print(sol+" ");
//                }
                    placement = placement + PatchworkAI.randomGenerator(patchCircle,placement);
//
//                if (!PatchworkGame.isPlacementValid(patchCircle, placement)) throw new Error("wrong plc");
                    placement = placement + PatchworkAI.randomGenerator(patchCircle, placement);
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
                String patchCircle = PatchworkGame.initPathCircle();
                String placement = new String("");
                System.out.println(patchCircle);
                while (PatchworkGame.p1.timecount != 53 && PatchworkGame.p2.timecount != 53) {
//                System.out.println("sol:"+PatchworkAI.generateAllPositionPlacement(patchCircle,placement).size());
//                for (String sol: PatchworkAI.generateAllPositionPlacement(patchCircle,placement)){
//                    System.out.print(sol+" ");
//                }
                    placement = placement + PatchworkAI.smarterGenerator(patchCircle,placement);
//
//                if (!PatchworkGame.isPlacementValid(patchCircle, placement)) throw new Error("wrong plc");
//                    placement = placement + PatchworkAI.generatePatchPlacement(patchCircle, placement);
//                if (!PatchworkGame.isPlacementValid(patchCircle, placement)) throw new Error("wrong plc");
                      placement = placement + PatchworkAI.randomGenerator(patchCircle,placement);
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
    //used to difference two players'scores
    public static void absoluteScore(String fileName, String newFileName){
        //File data = new File("file:/home/cup/IdeaProjects/comp1140-ass2-wed15c/"+fileName);
        File data = new File(fileName);
        String line = new String();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(data));
            BufferedWriter writer = new BufferedWriter(new FileWriter(data));
            while((line=reader.readLine())!=null){
                String[] res = line.split(",");
                System.out.println(res[0]+" "+res[1]);
            }
            reader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //for training, recursive training
    public static void main(String[] args) {
//        AITrainingMultiThreads[] trainingThreads = new AITrainingMultiThreads[10];
//        for (int i=0;i<10;i++){
//            trainingThreads[i] = new AITrainingMultiThreads("thread"+i);
//            trainingThreads[i].start();
//        }
        absoluteScore("r-rDataset-5000.csv","./r-rDataset-5000-S.csv");

    }
}
