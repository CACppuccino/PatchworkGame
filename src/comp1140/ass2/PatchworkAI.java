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
     *
     * @param patchCircle a patch circle string to initialize the game
     * @param placement   A valid placement string indicating a game state
     * @return a valid patch placement string, which will be "." if the player chooses to advance
     */
    public static String generatePatchPlacement(String patchCircle, String placement) {
        // FIXME Task 10: generate a valid move
        String pC;
        State cp1 = new State(1), cp2 = new State(2);
        //inittialization
        PatchworkGame.p1 = new State(1);
        PatchworkGame.p2 = new State(2);
        PatchworkGame.three = new char[3];
        Boolean dot = allDot(placement);

        if (patchCircle.indexOf('A') == 0)
            pC = patchCircle.substring(1) + 'A';
        else if (patchCircle.indexOf('A') != patchCircle.length() - 1)
            pC = patchCircle.substring(patchCircle.indexOf('A') + 1) + patchCircle.substring(0, patchCircle.indexOf('A') + 1);
        else pC = patchCircle;
        if (placement.equals("") || dot)
            //nothing to record since its the start of the game
            for (int i = 0; i < 3; i++) PatchworkGame.three[i] = pC.charAt(i);
        else
            //record the current state according to placement
            PatchworkGame.isPlacementValid(patchCircle, placement);


        int turn = State.check_turn(PatchworkGame.p1, PatchworkGame.p2) == 1 ? 1 : 2;
        //backup state data in order to revert it back when find the try is invalid
        State player = turn == 1 ? PatchworkGame.p1 : PatchworkGame.p2;
        cp1.copy(PatchworkGame.p1);
        cp2.copy(PatchworkGame.p2);

        //back up the game data

        int cpaPlc;
        char[] cpThree = new char[3];
        for (int i = 0; i < 3; i++) cpThree[i] = PatchworkGame.three[i];
        cpaPlc = PatchworkGame.aPlc;
//        System.out.println("aplc: "+PatchworkGame.aPlc);
        //specialCase--holding tile 'h'
        if (player.specialH) {
            for (char i = 'A'; i < 'J'; i++) {
                for (char j = 'A'; j < 'J'; j++) {
                    if (PatchworkGame.outOfBoard("h" + "" + i + "" + j + "A", player)) {
                        try {
                            State.buyPartches(PatchworkGame.p1, PatchworkGame.p2, 'h');
                        } catch (Error e) {
                            return "h" + "" + i + "" + j + "A";
                        }
                    }
                }
            }

        }
        //brute force trying
        boolean isValid;
        if (player.buttonCount == 0) {
            State.advanced(PatchworkGame.p1, PatchworkGame.p2);
            return ".";
        }

        for (int l = 0; l < 3; l++) {
            char t = PatchworkGame.three[l];
            int n = t > 'Z' ? t - 'a' : t - 'A';
            if (!State.affordPartch(player.buttonCount, PatchworkGame.tileCost[n])) continue;
            for (char i = 'A'; i < 'J'; i++) {
                for (char j = 'A'; j < 'J'; j++) {
                    if (player.squiltBoard[i - 'A'][j - 'A']) continue;
                    for (char k = 'A'; k < 'I'; k++) {
                        isValid = PatchworkGame.isPlacementValid(patchCircle, placement + t + i + j + k);
                        if (isValid) {
                            return "" + t + i + j + k;
                        }
//                        System.out.println(placement+t+i+j+k);
                        PatchworkGame.p1.copy(cp1);
                        PatchworkGame.p2.copy(cp2);
                        PatchworkGame.aPlc = cpaPlc;
                        for (int lve = 0; lve < 3; lve++) PatchworkGame.three[lve] = cpThree[lve];
                    }
                }
            }
        }
        State.advanced(PatchworkGame.p1, PatchworkGame.p2);
        return ".";
    }

    public static String smarterGenerator(String patchCircle, String placement){
        ArrayList<String> ans = generateAllPatchPlacement(patchCircle,placement);
        double max = 0, tmp;
        int ind;
        String re = new String();
        if (ans.size()<=2) return ans.get(0);
        else{
            for (int i=0;i<ans.size()-1;i++){
                ind =PatchworkGame.getIndex(ans.get(i).charAt(0));
                tmp = ((double) (2*PatchworkGame.tileSpace[ind][0].length - PatchworkGame.tileCost[ind] +
                        PatchworkGame.tileButton[ind]*3))/PatchworkGame.tileTimetoken[ind];
                if (max<=tmp){
                    re = ans.get(i);
                    max = tmp;
                }
            }
            return re;
        }
    }
    public static ArrayList<String> generateAllPatchPlacement(String patchCircle, String placement) {
        ArrayList<String> ans = new ArrayList<>();
        boolean success= false;
        String pC;
        State cp1 = new State(1), cp2 = new State(2);
        //inittialization
        PatchworkGame.p1 = new State(1);
        PatchworkGame.p2 = new State(2);
        PatchworkGame.three = new char[3];
        Boolean dot = allDot(placement);

        if (patchCircle.indexOf('A') == 0)
            pC = patchCircle.substring(1) + 'A';
        else if (patchCircle.indexOf('A') != patchCircle.length() - 1)
            pC = patchCircle.substring(patchCircle.indexOf('A') + 1) + patchCircle.substring(0, patchCircle.indexOf('A') + 1);
        else pC = patchCircle;
        if (placement.equals("") || dot)
            //nothing to record since its the start of the game
            for (int i = 0; i < 3; i++) PatchworkGame.three[i] = pC.charAt(i);
        else
            //record the current state according to placement
            PatchworkGame.isPlacementValid(patchCircle, placement);


        int turn = State.check_turn(PatchworkGame.p1, PatchworkGame.p2) == 1 ? 1 : 2;
        //backup state data in order to revert it back when find the try is invalid
        State player = turn == 1 ? PatchworkGame.p1 : PatchworkGame.p2;
        cp1.copy(PatchworkGame.p1);
        cp2.copy(PatchworkGame.p2);

        //back up the game data

        int cpaPlc;
        char[] cpThree = new char[3];
        for (int i = 0; i < 3; i++) cpThree[i] = PatchworkGame.three[i];
        cpaPlc = PatchworkGame.aPlc;
//        System.out.println("aplc: "+PatchworkGame.aPlc);
        //specialCase--holding tile 'h'
        if (player.specialH) {
            for (char i = 'A'; i < 'J'; i++) {
                for (char j = 'A'; j < 'J'; j++) {
                    if (PatchworkGame.outOfBoard("h" + "" + i + "" + j + "A", player)) {
                        try {
                            State.buyPartches(PatchworkGame.p1, PatchworkGame.p2, 'h');
                        } catch (Error e) {
                            ans.add( "h" + "" + i + "" + j + "A");
                            return ans;
                        }
                    }
                }
            }

        }
        //brute force trying
        boolean isValid;
        if (player.buttonCount == 0) {
            State.advanced(PatchworkGame.p1, PatchworkGame.p2);
            ans.add( ".");
            return ans;
        }

        for (int l = 0; l < 3; l++) {
            char t = PatchworkGame.three[l];
            success = false;
            int n = t > 'Z' ? t - 'a' : t - 'A';
            if (!State.affordPartch(player.buttonCount, PatchworkGame.tileCost[n])) continue;
            for (char i = 'A'; i < 'J'; i++) {
                for (char j = 'A'; j < 'J'; j++) {
                    if (player.squiltBoard[i - 'A'][j - 'A']) continue;
                    if (success) break;
                    for (char k = 'A'; k < 'I'; k++) {

                        isValid = PatchworkGame.isPlacementValid(patchCircle, placement + t + i + j + k);
                        if (isValid) {
                            ans.add( "" + t + i + j + k);
                            success = true;
                        }
//                        System.out.println(placement+t+i+j+k);
                        PatchworkGame.p1.copy(cp1);
                        PatchworkGame.p2.copy(cp2);
                        PatchworkGame.aPlc = cpaPlc;
                        for (int lve = 0; lve < 3; lve++) PatchworkGame.three[lve] = cpThree[lve];
                        if (success)     break;
                    }
                }
            }
        }

        State.advanced(PatchworkGame.p1, PatchworkGame.p2);
        ans.add(".");
        return ans;
    }
    private static boolean allDot(String placement) {
        for (char c : placement.toCharArray()) {
            if (c != '.') return false;
        }
        return true;
    }
}