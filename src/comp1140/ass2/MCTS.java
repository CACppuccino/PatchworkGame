package comp1140.ass2;

import javax.sound.midi.Patch;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class MCTS {
    Random random = new Random();
    int ActionOfN = 3;
    MCTS[] children;
    double visits, values;
    double adjust = 1e-6;
    String circle, placement;

    public MCTS(String c, String p) {
        placement = p;
        circle = c;
        PatchworkGame.three = c.substring(0, 3).toCharArray();
    }

    public void readInfo(String file) {

    }

    public String actionSelect() {
        List<MCTS> visitNode = new ArrayList<MCTS>();
        MCTS current = this;
        visitNode.add(this);
        while (current.children != null) {
            current = current.selection();
            visitNode.add(current);
        }
        current.expandtion();
        MCTS newNode = current.selection();
        visitNode.add(newNode);
        double value = gameOver(newNode);
        for (MCTS mcts : visitNode) {
            // would need extra logic for n-player game
            mcts.stateUpdates(value);
        }
        return ""+this.values;
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
        return selects;
    }


//        char tile = PatchworkAI.smarterGenerator(PatchworkGame.initPathCircle(),"").charAt(0);

//
//        if (tile == '.'){
//            for (MCTS m : children) {
//                double valueUCT = m.values / (m.visits + adjust) + Math.sqrt(Math.log(visits + 1) / (m.visits + adjust)) +
//                        random.nextDouble() * adjust;
//                if (valueUCT > bestValue) {
//                    selects = m;
//                    bestValue = valueUCT;
//                }
//            }
//            return selects;
//        }
//        else {
//            for (char i = 'A'; i < 'J'; i++)
//                for (char j = 'A'; j < 'J'; j++) {
//                    if (PatchworkGame.p2.squiltBoard[i - 'A'][j - 'A']) continue;
//                    for (char k = 'A'; k < 'I'; k++)
//                        if (PatchworkGame.isPlacementValid(Viewer.c, "" + tile + i + j + k)) {
//                            for (MCTS m : children) {
//                                double valueUCT = m.values / (m.visits + adjust) + Math.sqrt(Math.log(visits + 1) / (m.visits + adjust)) +
//                                        random.nextDouble() * adjust;
//                                if (valueUCT > bestValue) {
//                                    selects = m;
//                                    bestValue = valueUCT;
//                                    break;
//                                }
//                            }
//                        }
//                }
//        }
//        return selects;

    public void expandtion() {
        children = new MCTS[ActionOfN+1];
        int n = 0;
        char t = PatchworkAI.smarterGenerator(circle, placement).charAt(0);//New Tile
        outer:
        while (n < ActionOfN) {
            for (char i = 'A'; i < 'J'; i++) {
                for (char j = 'A'; j < 'J'; j++) {
                    if (PatchworkGame.p2.squiltBoard[i - 'A' + 1][j - 'A' + 1]) continue;
                    for (char k = 'A'; k < 'I'; k++) {
                        String p = "" + t + i + j + k;
                        if (PatchworkGame.isPlacementValid(circle, p)) {
                            children[n++] = new MCTS(circle, placement+p);
                        }
                        if (n>=ActionOfN)                        break outer;
                    }
                }
            }
        }
        children[n++]=new MCTS(circle, placement+'.');
    }

    public double gameOver(MCTS last) {
        String p = last.placement;
        while (PatchworkGame.p1.timecount < 53 && PatchworkGame.p2.timecount < 53) {
            System.out.println(p);
            p += PatchworkAI.smarterGenerator(circle, p);
            PatchworkGame.isPlacementValid(circle, p);
        }
        return PatchworkGame.getScoreForPlacement(circle, p, false) - PatchworkGame.getScoreForPlacement(circle, p, true);
    }

    public void stateUpdates(double value) {
        visits++;
        values+=value;
    }

    public static void main(String[] args) {
        MCTS mcts = new MCTS("BCDEFGHIJKLMNOPQRSTUVWXYZabcdefgA","");
        System.out.println(mcts.actionSelect());
    }

}