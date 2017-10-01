package comp1140.ass2;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.min;

public class PatchworkAI {
    public static ArrayList<String> candidates = new ArrayList<>();
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
        String pC;
        State cp1 = new State(1),cp2 = new State(2);
        //inittialization
        PatchworkGame.p1 = new State(1);
        PatchworkGame.p2 = new State(2);
//        PatchworkGame.aPlc = 0;
        PatchworkGame.three = new char[3];

        if (patchCircle.indexOf('A')==0)
            pC = patchCircle.substring(1)+'A';
        else if (patchCircle.indexOf('A')!=patchCircle.length()-1)
            pC = patchCircle.substring(patchCircle.indexOf('A')+1)+patchCircle.substring(0,patchCircle.indexOf('A')+1);
        else pC = patchCircle.toString();
        if (placement.equals(""))
            //nothing to record since its the start of the game
            PatchworkGame.three = pC.substring(0,3).toCharArray();
        else
            //record the current state according to placement
            PatchworkGame.isPlacementValid(patchCircle,placement);


        int turn = State.check_turn(PatchworkGame.p1,PatchworkGame.p2)==1?1:2;
        //backup state data in order to revert it back when find the try is invalid
        State player = turn==1?PatchworkGame.p1:PatchworkGame.p2;
        cp1.copy(PatchworkGame.p1);
        cp2.copy(PatchworkGame.p2);

        //back up the game data

        int cpaPlc;
        char[] cpThree = new char[3];
        for (int i=0;i<3;i++) cpThree[i] = PatchworkGame.three[i];
        cpaPlc = PatchworkGame.aPlc;
//        System.out.println("aplc: "+PatchworkGame.aPlc);

        //specialCase--holding tile 'h'
        if (player.specialH){
            for (char i = 'A'; i < 'J'; i++){
                for (char j = 'A'; j < 'J'; j++){
                    if (PatchworkGame.outOfBoard("h"+""+i+""+j+"A",player)){
                        try {
                            State.buyPartches(PatchworkGame.p1, PatchworkGame.p2, 'h');
                        }catch (Error e){return "h"+""+i+""+j+"A";}
                    }

                }
            }

        }
        //brute force trying
        boolean isValid = false;
        if (player.buttonCount==0) {
            State.advanced(PatchworkGame.p1,PatchworkGame.p2);
            return ".";}
        for (int l = 0; l < 3;l++) {
            char t = PatchworkGame.three[l];
            int n = t > 'Z' ? t - 'a' : t - 'A';
            for (char i = 'A'; i < 'J'; i++) {
                if (!State.affordPartch(player.buttonCount,PatchworkGame.tileCost[n]))  continue;
                for (char j = 'A'; j < 'J'; j++) {
                    for (char k = 'A'; k < 'I'; k++) {
                            isValid = PatchworkGame.isPlacementValid(patchCircle, placement + t + i + j + k);
                        if (isValid) {
                            return "" + t + i + j + k;
                        }
//                        System.out.println(placement+t+i+j+k);
                        PatchworkGame.p1.copy(cp1);
                        PatchworkGame.p2.copy(cp2);
                        PatchworkGame.aPlc = cpaPlc;
                        for (int lve = 0; lve < 3;lve++) PatchworkGame.three[lve] = cpThree[lve];
                    }
                }
            }
        }

        State.advanced(PatchworkGame.p1,PatchworkGame.p2);
        return ".";
//        PatchworkGame.isPlacementValid(patchCircle,placement);
//        State p = State.check_turn(PatchworkGame.p1, PatchworkGame.p2) == 1 ? PatchworkGame.p1 : PatchworkGame.p2;
//        String choices = ".";
//        for (char c : PatchworkGame.three) {
//            int n = c > 'Z' ? c - 'a' : c - 'A';
//            if (State.affordPartch(p.buttonCount, PatchworkGame.tileCost[n])) choices += c;
//        }
//        Random r = new Random();
//        char t = choices.charAt(r.nextInt(choices.length()));
//        if (t=='.') return ".";
//        State a=PatchworkGame.p1,b=PatchworkGame.p2;
//        int c = PatchworkGame.aPlc;
//        char[] d = PatchworkGame.three;
//        for (char i = 'A'; i < 'J'; i++) {
//            for (char j = 'A'; j < 'J'; j++) {
//                for (char k = 'A'; k < 'I'; k++) {
//                    if (PatchworkGame.isPlacementValid(patchCircle, placement + t + i + j + k)) {
//                        return "" + t + i + j + k;
//                    }
//                    PatchworkGame.p1=a;
//                    PatchworkGame.p2=b;
//                    PatchworkGame.aPlc=c;
//                    PatchworkGame.three=d;
//                }
//            }
//        }
//        return "";
    }


//        String vaildPlacement = "";
//        State p1 = new State(1);
//        State p2 = new State(2);
//        int aPosition = 0;
//        Random r = new Random();
//        ArrayList<Character> newPatchCircle = new ArrayList<>();
//        ArrayList<String> validTileString = new ArrayList<>();
//        for (int j = 0; j < patchCircle.length();j++)
//            newPatchCircle.add(patchCircle.charAt(j));
//        for (int i=0;i<patchCircle.length();i++)
//            if (patchCircle.charAt(i)=='A')
//                aPosition=i+1;
//        three = new char[] {newPatchCircle.get(aPosition%newPatchCircle.size()), newPatchCircle.get((aPosition + 1) % newPatchCircle.size()), newPatchCircle.get((aPosition + 2) % newPatchCircle.size())};
//        int [] tileCost = new int[three.length];
//        for ( int i = 0; i < three.length; i++)
//            for ( int j = 0; j < PATCHES.length;j++)
//                if ( PATCHES[j] == three[i])
//                    tileCost[i] = PatchworkGame.tileCost[j];
//
//        int minimalCost = min(min(tileCost[0],tileCost[1]),tileCost[2]);
//        if ( State.check_turn(p1,p2) == 1)
//            if ( minimalCost - p1.buttonCount < 0)
//                vaildPlacement = ".";
//            else {
//                String threeTile = new String(three);
//                validTileString = vaildPatchStirng(placement, threeTile);
//
//                if (validTileString.size() == 0) return vaildPlacement = ".";
//                int indexAdd = r.nextInt(validTileString.size());
//                vaildPlacement = (validTileString.get(indexAdd));
//            }
//        else
//            if (minimalCost - p2.buttonCount < 0)
//                vaildPlacement = ".";
//            else {
//                String threeTile = new String(three);
//                validTileString = vaildPatchStirng(placement, threeTile);
//
//                if (validTileString.size() == 0) return vaildPlacement = ".";
//                int indexAdd = r.nextInt(validTileString.size());
//                vaildPlacement = validTileString.get(indexAdd);
//            }
//        validTileStr = (String[])validTileString.toArray(new String[validTileString.size()]);
//        return vaildPlacement;
//    }
//
//    // this method is to take the valid patch string from the patch placement
//    public static ArrayList<String> vaildPatchStirng(String placement, String patches){
//        ArrayList<String> validPatches = new ArrayList<>();
//        for (int i = 0; i < patches.length();i++)
//            for ( int j = 0; j < placement.length();j+=4)
//                if(placement.charAt(j) == patches.charAt(i))
//                    validPatches.add(placement.substring(j,j+3));
////        String ans = String.join(",",(String[])validPatches.toArray(new String[validPatches.size()]));
//        return validPatches;
//    }
//
//    //choose to move the time token or buy a patch
//    public static int choose(){
//        return 1;
//    }
//
//    //evaluate the decision and get the best choice
//    public static void main(String[] args) {
//
//    }
//
//    //after the decision, execute should be called to use APIs provided by the game to
//    //interact with the game
//    public static void execute(){
//
//    }
}