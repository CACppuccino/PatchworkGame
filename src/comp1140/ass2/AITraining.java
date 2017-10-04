package comp1140.ass2;

public class AITraining {
    static final int ROUNDS = 100;
    public double loss(){
        return 0;
    }

    //adjusting the parameters
    public void adjust(){

    }

    //for training, recursive training
    public static void main(String[] args) {
        int winer;
        for (int i=1;i<=ROUNDS;i++) {
            String patchCircle = PatchworkGame.initPathCircle(), placement = new String("");
            while (PatchworkGame.p1.timecount != 53 && PatchworkGame.p2.timecount != 53) {
                placement = placement + PatchworkAI.generatePatchPlacement(patchCircle, placement);
            }
            winer = PatchworkGame.getScoreForPlacement(patchCircle,placement,true)>
                    PatchworkGame.getScoreForPlacement(patchCircle,placement,false)?1:2;
            System.out.println(winer);
//            if (PatchworkGame.isPlacementValid(patchCircle,placement)) System.out.println("OK");
            PatchworkGame.p1 = new State(1);
            PatchworkGame.p2 = new State(2);
            System.out.println(patchCircle);
            System.out.println(placement);

        }
    }
}
