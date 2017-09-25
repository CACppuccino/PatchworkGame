package comp1140.ass2;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.min;

public class PatchworkAI {
    private final PatchworkGame game;

    final static char[] PATCHES = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W',
            'X','Y','Z','a','b','c','d','e','f','g'};

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
        String vaildPlacement = "";
        State p1 = new State(1);
        State p2 = new State(2);
        int aPosition = 0;
        Random r = new Random();
        ArrayList<Character> newPatchCircle = new ArrayList<>();
        for (int j = 0; j < patchCircle.length();j++)
            newPatchCircle.add(patchCircle.charAt(j));
        for (int i=0;i<patchCircle.length();i++)
            if (patchCircle.charAt(i)=='A')
                aPosition=i+1;
        char [] three = new char[] {newPatchCircle.get(aPosition%newPatchCircle.size()), newPatchCircle.get((aPosition + 1) % newPatchCircle.size()), newPatchCircle.get((aPosition + 2) % newPatchCircle.size())};
        int [] tileCost = new int[three.length];
        for ( int i = 0; i < three.length; i++)
            for ( int j = 0; j < PATCHES.length;j++)
                if ( PATCHES[j] == three[i])
                    tileCost[i] = PatchworkGame.tileCost[j];

        int minimalCost = min(min(tileCost[0],tileCost[1]),tileCost[2]);
        if ( State.check_turn(p1,p2) == 1)
            if ( minimalCost - p1.buttonCount < 0)
                vaildPlacement = ".";
            else {
                String threeTile = new String(three);
                ArrayList<String> validTileString = vaildPatchStirng(placement, threeTile);

                int indexAdd = r.nextInt(3);
                vaildPlacement = validTileString.get(indexAdd);
            }
        else
            if (minimalCost - p2.buttonCount < 0)
                vaildPlacement = ".";
            else {
                String threeTile = new String(three);
                ArrayList<String> validTileString = vaildPatchStirng(placement, threeTile);

                int indexAdd = r.nextInt(3);
                vaildPlacement = validTileString.get(indexAdd);
            }
        return vaildPlacement;
    }

    // this method is to take the valid patch string from the patch placement
    public static ArrayList<String> vaildPatchStirng(String placement, String patches){
        ArrayList<String> validPatches = new ArrayList<>();
        for (int i = 0; i < patches.length();i++)
            for ( int j = 0; j < placement.length();j+=4)
                if(placement.charAt(j) == patches.charAt(i))
                    validPatches.add(placement.substring(j,j+3));
//        String ans = String.join(",",(String[])validPatches.toArray(new String[validPatches.size()]));
        return validPatches;
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
