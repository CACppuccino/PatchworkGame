package comp1140.ass2;

import java.io.*;
import java.util.Random;


public class AITraining {
    static final int ROUNDS = 5000;
    public double loss(){
        return 0;
    }

    //adjusting the parameters
    public void adjust(){

    }
    private static int tileToNum(char c){
        if (c=='.') return 34;
        if (c>='A' && c<='Z') return c-'A';
        else return (c-'a')+25;
    }
    public static void fileIO(String pathName){
        int winer;
        int[] stat = {0,0,0};
        int score1, score2, diff;
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
//                    placement = placement + PatchworkAI.randomGenerator(patchCircle,placement);
//
//                if (!PatchworkGame.isPlacementValid(patchCircle, placement)) throw new Error("wrong plc");
//                    placement = placement + PatchworkAI.randomGenerator(patchCircle, placement);
//                if (!PatchworkGame.isPlacementValid(patchCircle, placement)) throw new Error("wrong plc");
                }
                score1 =PatchworkGame.getScoreForPlacement(patchCircle,placement,true) ;
                score2 = PatchworkGame.getScoreForPlacement(patchCircle,placement,false);
                diff = Math.abs(score1-score2);
                winer = score1>score2?1:2;

                stat[winer]++;
                writer.write(placement+","+winer+","+score1+","+score2+","+diff+"\n");
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
                Random rng = new Random(3);
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
    public static void fileIO2(String pathName1, String pathName2){
        int winer;
        int[] stat = {0,0,0};
        int score1, score2, diff;
        File data = new File(pathName1);
        File data2 = new File(pathName2);
        try {
            data.createNewFile();
            BufferedWriter writer = new BufferedWriter( new FileWriter(data));

            data2.createNewFile();
            BufferedWriter writer2 = new BufferedWriter( new FileWriter(data2));
            for (int i=1;i<=ROUNDS;i++) {
                String patchCircle = PatchworkGame.initPatchCircle();
                String placement = new String("");
                System.out.println(patchCircle);

                while (PatchworkGame.p1.timecount != 53 && PatchworkGame.p2.timecount != 53) {
                    placement = placement + PatchworkAI.smarterGenerator(patchCircle,placement);
                }
                score1 =PatchworkGame.getScoreForPlacement(patchCircle,placement,true) ;
                score2 = PatchworkGame.getScoreForPlacement(patchCircle,placement,false);
                diff = Math.abs(score1-score2);
                winer = score1>score2?1:2;
                writer2.write(patchCircle+","+placement+","+winer+","+score1+","+score2+","+diff+"\n");
                PatchworkGame.p1 = new State(1);
                PatchworkGame.p2 = new State(2);
                placement = new String();
                while (PatchworkGame.p1.timecount != 53 && PatchworkGame.p2.timecount != 53) {
                    placement = placement + PatchworkAI.smarterGenerator(patchCircle,placement);
                    if (State.check_turn(PatchworkGame.p1,PatchworkGame.p2)==winer){
                        int order =tileToNum(PatchworkAI.smarterGenerator(patchCircle,placement).charAt(0));

                        //for tile info
                        if (order!=34) {
                            writer.write(order + "," +
                                    PatchworkGame.tileTimetoken[order]+","+
                                    PatchworkGame.tileButton[order]+","+
                                    PatchworkGame.tileSpace[order].length+","+
                                    PatchworkGame.tileCost[order]+","
                            );
                        }else {
                            int adv =PatchworkGame.p1.timecount-PatchworkGame.p2.timecount;
                            writer.write(34 + "," + adv + "," + 0 + "," +0+"," +(-Math.abs(adv))+",");
                        }



                        //for player info
                        if (winer==1) {
                            writer.write(PatchworkGame.p1.buttonCount+","
                            +PatchworkGame.p1.timecount+","
                            +PatchworkGame.p1.specialButton+","
                            +PatchworkGame.p1.squareleft+"\n");
                        }else
                            writer.write(PatchworkGame.p2.buttonCount+","
                                    +PatchworkGame.p2.timecount+","
                                    +PatchworkGame.p2.specialButton+","
                                    +PatchworkGame.p2.squareleft+"\n");

                    }

                }

                stat[winer]++;
                System.out.println(placement);
                System.out.println(winer);
//            if (PatchworkGame.isPlacementValid(patchCircle,placement)) System.out.println("OK");
                PatchworkGame.p1 = new State(1);
                PatchworkGame.p2 = new State(2);

            }
            System.out.println(stat[1]+" "+stat[2]);
            writer.flush();
            writer.close();
            writer2.flush();
            writer2.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void fileIO3(String pathName1, String pathName2){
        int winer;
        int[] stat = {0,0,0};
        int score1, score2, diff;
        File data = new File(pathName1);
        File data2 = new File(pathName2);
        try {
            data.createNewFile();
            BufferedWriter writer = new BufferedWriter( new FileWriter(data));

            data2.createNewFile();
            BufferedWriter writer2 = new BufferedWriter( new FileWriter(data2));
            for (int i=1;i<=ROUNDS;i++) {
                String patchCircle = PatchworkGame.initPatchCircle();
                String placement = new String("");
                System.out.println(patchCircle);
                Random rng = new Random(5);
                while (PatchworkGame.p1.timecount != 53 && PatchworkGame.p2.timecount != 53) {
                    placement = placement + PatchworkAI.randomGenerator(patchCircle,placement);
                }
                score1 =PatchworkGame.getScoreForPlacement(patchCircle,placement,true) ;
                score2 = PatchworkGame.getScoreForPlacement(patchCircle,placement,false);
                diff = Math.abs(score1-score2);
                winer = score1>score2?1:2;
                writer2.write(patchCircle+","+placement+","+winer+","+score1+","+score2+","+diff+"\n");
                PatchworkGame.p1 = new State(1);
                PatchworkGame.p2 = new State(2);
//                placement = new String();
                String plc = new String(), plc2 = new String();
                int x = 0;
                while (x<placement.length()) {
                    if (x<placement.length()) {
                        if (placement.charAt(x) == '.') {
                            x++;
                            plc = ".";
                        } else {
                            plc = placement.substring(x, x + 4);
                            x += 4;
                        }
                        plc2 = plc2 + plc;
                    }
                        boolean xx=PatchworkGame.isPlacementValid(patchCircle, plc2);
                    if (State.check_turn(PatchworkGame.p1,PatchworkGame.p2)==winer){
                        int order =tileToNum(plc.charAt(0));
                        //for tile info
                        if (order!=34) {
                            writer.write(
                                    (tileToNum(plc.charAt(1))+
                                    tileToNum(plc.charAt(2))*10+
                                    tileToNum(plc.charAt(3))*100)+","+
                                    order + "," +
                                    PatchworkGame.tileTimetoken[order]+","+
                                    PatchworkGame.tileButton[order]+","+
                                    PatchworkGame.tileSpace[order].length+","+
                                    PatchworkGame.tileCost[order]+","
                            );
                        }else {
                            int adv =PatchworkGame.p1.timecount-PatchworkGame.p2.timecount+1;
                            writer.write(0+","+34+"," + adv + "," + 0 + "," +0+"," +(-Math.abs(adv))+",");
                        }



                        //for player info
                        if (winer==1) {
                            writer.write(PatchworkGame.p1.buttonCount+","
                                    +PatchworkGame.p1.timecount+","
                                    +PatchworkGame.p1.specialButton+","
                                    +PatchworkGame.p1.squareleft+",");
                        }else
                            writer.write(PatchworkGame.p2.buttonCount+","
                                    +PatchworkGame.p2.timecount+","
                                    +PatchworkGame.p2.specialButton+","
                                    +PatchworkGame.p2.squareleft+",");

                        for (int k=0;k<9;k++)
                            for (int h =0 ;h<9;h++)
                                writer.write(PatchworkGame.p1.squiltBoard[k][h]?"0,":"1,");
                        for (int k=0;k<9;k++)
                            for (int h =0 ;h<9;h++)
                                writer.write(PatchworkGame.p2.squiltBoard[k][h]?"0,":"1,");
                        writer.write("\n");
                    }

//                    placement = placement + PatchworkAI.smarterGenerator(patchCircle,placement);

                }
                stat[winer]++;
                System.out.println(placement);
                System.out.println(winer);
                System.out.println("round:"+i);
//            if (PatchworkGame.isPlacementValid(patchCircle,placement)) System.out.println("OK");
                PatchworkGame.p1 = new State(1);
                PatchworkGame.p2 = new State(2);

            }
            System.out.println(stat[1]+" "+stat[2]);
            writer.flush();
            writer.close();
            writer2.flush();
            writer2.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void network01(char tile){
        Matrix[] nn = new Matrix[3];
        nn[0] = new Matrix(7,12,0);
        nn[1] = new Matrix(12,7,0);
        nn[2] = new Matrix(7,35,0);

    }
    //for training, recursive training
    public static void main(String[] args) {
//        fileIO3("./data/DetailDataset05.csv","./data/Dataset05.csv");

    }
}
