package comp1140.ass2;

import javax.sound.midi.Patch;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class MCTS {
    Random random = new Random();
    int ActionOfN = 3;
    MCTS [] children;
    double visits,values;
    double adjust = 1e-6;


    public void actionSelect(){
        List<MCTS> visitNode = new ArrayList<MCTS>();
        MCTS current = this;
        visitNode.add(this);
        while (current != null) {
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

    }

    public MCTS selection(){
        MCTS selects = null;
        double bestValue = Double.MIN_VALUE;
        char tile = PatchworkAI.smarterGenerator(PatchworkGame.initPathCircle(),"").charAt(0);
        if (tile == '.'){
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
        else {
            for (char i = 'A'; i < 'J'; i++)
                for (char j = 'A'; j < 'J'; j++) {
                    if (PatchworkGame.p2.squiltBoard[i - 'A'][j - 'A']) continue;
                    for (char k = 'A'; k < 'I'; k++)
                        if (PatchworkGame.isPlacementValid(Viewer.c, "" + tile + i + j + k)) {
                            for (MCTS m : children) {
                                double valueUCT = m.values / (m.visits + adjust) + Math.sqrt(Math.log(visits + 1) / (m.visits + adjust)) +
                                        random.nextDouble() * adjust;
                                if (valueUCT > bestValue) {
                                    selects = m;
                                    bestValue = valueUCT;
                                    break;
                                }
                            }
                        }
                }
        }
        return selects;
    }
    public void expandtion(){
        children = new MCTS[ActionOfN];
        for(int i = 0; i < ActionOfN;i++){
            children[i] = new MCTS();
        }
    }

    public double gameOver(MCTS last){
        return random.nextInt(2);
    }

    public void stateUpdates(double value){
        visits++;
        values = values + value;
    }

    public int argLength(){
        if(children == null) return 0;
        else return children.length;
    }

    public static void main(String[] args) {
        MCTS mcts = new MCTS();
    }

}
