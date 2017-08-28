package comp1140.ass2;

public class PatchworkAI {
    private final PatchworkGame game;

    public PatchworkAI(PatchworkGame game) {
        this.game = game;
    }

    /**
     * Generate a valid move that follows from the given patch circle and game placement string.
     * @param patchCircle a patch circle string to initialize the game
     * @param placement  A valid placement string indicating a game state
     * @return a valid patch placement string, which will be "." if the player chooses to advance
     */
    public static String generatePatchPlacement(String patchCircle, String placement) {
        // FIXME Task 10: generate a valid move
        return null;
    }

    //choose to move the time token or buy a patch
    public static int choose(){
        return 1;
    }

    //evaluate the decision and get the best choice
    public static void main(String[] args) {

    }

    //after the decision, execute should be called to use APIs provided by the game to
    //interact with the game
    public static void execute(){

    }
}
