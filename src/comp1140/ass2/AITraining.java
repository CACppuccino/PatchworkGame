package comp1140.ass2;

public class AITraining {
    public double loss(){
        return 0;
    }

    //adjusting the parameters
    public void adjust(){

    }

    //for training, recursive training
    public static void main(String[] args) {
        String patchCircle = PatchworkGame.initPathCircle(),placement = new String("");
        while (PatchworkGame.p1.timecount != 53 && PatchworkGame.p2.timecount!=53){
            placement = placement + PatchworkAI.generatePatchPlacement(patchCircle,placement);
        }
        System.out.println("----------------------------------");
        System.out.println(placement);
        System.out.println("----------------------------------");
    }
}
