package comp1140.ass2;

import javax.sound.midi.Patch;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Scanner;

public class MCTS {
    Random random = new Random();
    int ActionOfN = 50;
    List<MCTS> children = new ArrayList<MCTS>();
    double visits, values;
    double adjust = 1e-6;
    String circle, placement;

    public MCTS(String c, String p) {
        placement = p;
        circle = c;
        PatchworkGame.three = c.substring(0, 3).toCharArray();
    }

    public static void readInfo(String file, MCTS m) throws FileNotFoundException {
        Scanner s = new Scanner(new File(file));
        s.useDelimiter("[,\n]");
        while (s.hasNext()) {
            String c = s.next();
//            System.out.println(c);
            if (c.equals(m.circle)) {
                String n = s.next();
                String w = "0";
                for (int j = 0; j < 4; j++) w = s.next();
                String p = "";
                while (n.length() > 0) {
                    if (n.charAt(0) == '.') {
                        p += ".";
                        n = n.substring(1);
                    } else {
                        p += n.substring(0, 4);
                        n = n.substring(4);
                    }
                    m.children.add(0, new MCTS(c, p));
                    m = m.children.get(0);
                    m.visits++;
                    m.values += Integer.parseInt(w);
                }
            }
            for (int j = 0; j < 5; j++) if (s.hasNext()) s.next();
        }
    }

    public MCTS actionSelect() {
        List<MCTS> visitNode = new ArrayList<MCTS>();
        MCTS current = this;
        visitNode.add(this);
        while (current.children == null) {
            current = current.selection();
            visitNode.add(current);
        }
        current.expandtion();
        MCTS newNode = current.selection();
        visitNode.add(newNode);
        double value = gameOver(newNode);
        for (MCTS mcts : visitNode) {
            mcts.stateUpdates(value);
        }

        return this;
    }

    public MCTS selection() {
        MCTS selects = null;
        double bestValue = Double.MIN_VALUE;
        for (MCTS m : children) {
            double valueUCT = m.values / (m.visits + adjust) + Math.sqrt(Math.log(visits + 1) / (m.visits + adjust)) +
                    random.nextDouble() * adjust;
            if (valueUCT > bestValue) {
                selects = m;
                bestValue = valueUCT;
            }
        }
//        System.out.println(selects.placement);
        return selects;
    }


    public void expandtion() {
        children = new ArrayList<MCTS>();
        int n = 0;
        char t = PatchworkAI.smarterGenerator(circle, placement).charAt(0);//New Tile
        while (true) {
            int r = random.nextInt(8 * 9 * 9);
            String p = "" + t + (char) ('A' + r / 72) + (char) ('A' + r / 8 % 9) + (char) ('A' + r % 8);
            if (PatchworkGame.isPlacementValid(circle, p)) {
                children.add(new MCTS(circle, placement + p));
                n++;
            }
            if (n >= ActionOfN) break;
        }
//        outer:
//        for (char i = 'A'; i < 'J'; i++) {
//            for (char j = 'A'; j < 'J'; j++) {
//                if (PatchworkGame.p2.squiltBoard[i - 'A'][j - 'A']) continue;
//                for (char k = 'A'; k < 'I'; k++) {
//                    String p = "" + t + i + j + k;
//                    if (PatchworkGame.isPlacementValid(circle, p)) {
//                        children.add(new MCTS(circle, placement + p));
//                        n++;
//                    }
//                    if (n >= ActionOfN) break outer;
//                }
//            }
//        }
        children.add(new MCTS(circle, placement + '.'));
    }

    public double gameOver(MCTS last) {
        String p = last.placement;
        while (PatchworkGame.p1.timecount < 53 && PatchworkGame.p2.timecount < 53) {
            p += PatchworkAI.smarterGenerator(circle, p);
            PatchworkGame.isPlacementValid(circle, p);
        }
        return PatchworkGame.getScoreForPlacement(circle, p, false) - PatchworkGame.getScoreForPlacement(circle, p, true);
    }

    public void stateUpdates(double value) {
        visits++;
        values += value;
    }

    static void printChildren(MCTS m,int layer){
        layer++;
        if (m.children.size()!=0){
            for (MCTS mcts:m.children){
                System.out.print("layer "+layer+" "+mcts.values+" ");
                printChildren(mcts,layer);
            }
            System.out.println();
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        MCTS mcts = new MCTS("eTLcRBMDGHJaOYAbdSfFNPUIVCWZEXKQg", "");
        readInfo("./data/Dataset02.csv", mcts);
        mcts.actionSelect();
//        for (MCTS m : mcts.children) System.out.println(m.values);
        printChildren(mcts,0);
    }

}