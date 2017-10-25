package comp1140.ass2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static comp1140.ass2.PatchworkAI.generateAllPositionPlacement;
import static comp1140.ass2.PatchworkAI.smarterGenerator;


public class AITraining {
    static final int ROUNDS = 500;
    static final int LAYERS = 6;
    static Matrix[] WEIGHTS = new Matrix[LAYERS];
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
                    placement = placement + PatchworkAI.smarterGenerator(patchCircle, placement);
                    placement = placement + network02(patchCircle,placement);
//                if (!PatchworkGame.isPlacementValid(patchCircle, placement)) throw new Error("wrong plc");
                }
                score1 =PatchworkGame.getScoreForPlacement(patchCircle,placement,true) ;
                score2 = PatchworkGame.getScoreForPlacement(patchCircle,placement,false);
                diff = Math.abs(score1-score2);
                winer = score1>score2?1:2;

                stat[winer]++;
                writer.write(placement+","+winer+","+score1+","+score2+","+diff+"\n");
                System.out.println("Round:"+i);
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
//                    if (State.check_turn(PatchworkGame.p1,PatchworkGame.p2)==winer){
                if (true){
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
                    boolean xx=PatchworkGame.isPlacementValid(patchCircle, plc2);
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
    //activation function - ReLu
    static Matrix Relu(Matrix a) throws Exception{
        for (int i=0;i<a.row;i++)
            for (int j=0;j<a.col;j++)
                a.setMatrix(i,j,Math.max(0,a.getElement(i,j)));
        return a;
    }
    //activation function - Softmax
    static Matrix Softmax(Matrix a) throws Exception{
        if (a.col!=1) throw new Exception("not a vecotr in Softmax");
        double sum =0;

        for (int i=0;i<a.matrix[0].length;i++){
            a.matrix[0][i] = Math.exp(a.matrix[0][i]);
            sum += a.matrix[0][i];
        }
        for (int i=0;i<a.matrix[0].length;i++)
            a.matrix[0][i] = a.matrix[0][i]/sum;

        return a;
    }
    //
    private static double[] encoding(String patchCircle, String placement,char tile){
//        ArrayList<String> candi = generateAllPositionPlacement(patchCircle,placement);
        State[] players = {PatchworkGame.p1,PatchworkGame.p2};
        int turn = State.check_turn(players[0],players[1])-1;
        double[] result = new double[171];
            PatchworkGame.isPlacementValid(patchCircle,placement);
            int order = tileToNum(tile);
            result[0] = order;
            if (order!=34) {
                result[1] = PatchworkGame.tileTimetoken[order];
                result[2] = PatchworkGame.tileButton[order];
                result[3] = PatchworkGame.tileSpace[order].length;
                result[4] = PatchworkGame.tileCost[order];
            }else {
                for (int i=1;i<=4;i++)
                    result[i] = 0;
            }
                result[5] = players[turn].buttonCount;
                result[6] = players[turn].timecount;
                result[6] = players[turn].specialButton;
                result[7] = players[turn].squareleft;

            for (int k=0;k<9;k++)
                for (int h =0 ;h<9;h++)
                    result[k*9+h+8] = (PatchworkGame.p1.squiltBoard[k][h]?0:1);
            for (int k=0;k<9;k++)
                for (int h =0 ;h<9;h++)
                    result[k*9+h+89] = (PatchworkGame.p2.squiltBoard[k][h]?0:1);

        return result;
    }
    static String network02(String patchCircle, String placement){
        try {

            String smg = smarterGenerator(patchCircle,placement);
            if (smg==null) return "";
            char tile = smg.charAt(0);
            //        input.
            double[] input = encoding(patchCircle, placement,tile);
            double[][] inputVec = new double[1][input.length];
            for (int j=0;j<input.length;j++)
                inputVec[0][j] = input[j];
            Matrix[] nn = new Matrix[LAYERS];
            Matrix result;
            // initialise the layers
            nn[0] = new Matrix(171, 12, 0);
            nn[1] = new Matrix(12, 1, 0);
            nn[2] = new Matrix(12, 171, 0);
            nn[3] = new Matrix(171, 1, 0);
            nn[4] = new Matrix(171, 788, 0);
            nn[5] = new Matrix(788,1,0);
            // read and set the layers'weight
            String file = "./model/weight.csv";
            try {
                Scanner s = new Scanner(new File(file));
                s.useDelimiter("[,\n]");
                for (int h=0;h<6;h++)
                    for (int i =0;i<nn[h].row;i++)
                        for (int j=0;j<nn[h].col;j++)
                            nn[h].setMatrix(i,j,s.nextFloat());

//                System.out.println("***");
//                System.out.println(Arrays.deepToString(nn[0].matrix));
            }catch (Exception e){e.printStackTrace();}

            // use the layers to calculate the anwser
//            System.out.println(inputVec);
            Matrix cc = new Matrix(1,171);
            for (int x=0;x<1;x++)
                for (int y=0;y<171;y++)
                    cc.matrix[x][y] = inputVec[0][y];
                result = Matrix.multiply(cc, nn[0]);
                result = Matrix.multiply(result,nn[2]);
                result = Relu(Matrix.multiply(result,nn[4]));
//                result = Relu(Matrix.multiply(result,nn[]));
                result = Softmax(Matrix.multiply(result,nn[5]));
                double max=0;
                int sign=0;
                for (int i=0;i<result.row;i++)
                    if (max<=result.matrix[0][i]){
                        max = result.matrix[0][i];
                        sign = i;
                    }

            String ans = tile+""+((char)((sign%10)+'A'))+""+((char)((sign%100)/10+'A'))+""+((char)(sign/100+'A'));
            for (String pans:generateAllPositionPlacement(patchCircle,placement))
                if (pans.equals(ans)) return ans;
            return smg;
        }catch (Exception e){e.printStackTrace();}finally {
            return smarterGenerator(patchCircle,placement);
        }
    }
    //for training, recursive training
    public static void main(String[] args) {
        fileIO("./data/Dataset07.csv");
    }

}
