package comp1140.ass2;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the state of a Patchwork game in progress.
 */
public class PatchworkGame {


    public PatchworkGame(String patchCircleString) {
    }

    public PatchworkGame() {

    }

    /**
     * Determine whether a patch placement is well-formed according to the following:
     * - either it is the single character string ".", or
     * - it consists of exactly four characters:
     * - the first character is in the range A .. Z or a .. h
     * - the second character is in the range A .. I
     * - the third character is in the range A .. I
     * - the fourth character is in the range A .. H
     *
     * @param placement A string describing a patch placement
     * @return True if the tile placement is well-formed
     */
    static boolean isPatchPlacementWellFormed(String placement) {
        // FIXME Task 3: determine whether a patch placement is well-formed
        String [] allchars = new String[4];
        allchars[0] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefgh";
        allchars[1] = "ABCDEFGHI";
        allchars[2] = "ABCDEFGHI";
        allchars[3] = "ABCDEFGH";
        int count = 0;
        boolean result = false;
        if ( placement.charAt(0) == '.')
        {
            result = true;
        }
        else if (placement.length() != 4)
        {
            result = false;
        }
        else
        {
            for (int i = 0; i < placement.length(); i++) {
                for (int j = 0; j < allchars[i].length(); j++) {
                    if (placement.charAt(i) == allchars[i].charAt(j)) {
                        count = count + 1;
                    }
                }
            }
        }

        if ( count == 4){
            result = true;
        }
        return result;
    }

    /**
     * Determine whether a game placement string is well-formed:
     * - it consists of a sequence of patch placement strings, where
     * - each patch placement is well-formed, either as a single-character advance string "."
     * or a four-character patch tile placement
     * - no patch appears more than once in the placement, except the special tile 'h'
     *
     * @param placement A string describing a placement of one or more tiles
     * @return true if the placement is well-formed
     */
    static boolean isPlacementWellFormed(String placement) {
        // FIXME Task 4: determine whether a placement is well-formed`
        //initialize a List<Integer> to store all the position of the patch
        List<Integer> patchposition = new ArrayList();
        // to count the time of the String placement when it has more tiles
        int count = 0;
        // the count1 is to count how many times the '.' show
        int count1 = 0;
        boolean result = false;
        String SubSting;
        if (placement == null || placement.isEmpty())
        {
            result = false;
            return result;
        }
        else {
            for (int i = 0; i < placement.length(); i++) {
                if (placement.charAt(i) == '.') {
                    count = count + 1;
                    count1 = count1 + 1;
                } else {
                    if ( placement.length() - i < 4)
                    {
                        SubSting = placement.substring(i, placement.length());
                    }
                    else
                    {
                        SubSting = placement.substring(i, i + 4);
                    }
                    patchposition.add(i);
                    result = isPatchPlacementWellFormed(SubSting);
                    if (result == true) {
                        count = count + 1;
                    }
                    else
                    {
                        result = false;
                        return result;
                    }
                    i = i + 3;
                }
            }
        }
        // check whether there is a patch appear more than once
        for ( int i = 0; i < patchposition.size();i++){
            for ( int j = i + 1;j < patchposition.size();j++){
                if ( placement.charAt(patchposition.get(i)) == placement.charAt(patchposition.get(j)) && placement.charAt(patchposition.get(i)) != 'h'){
                    result = false;
                    return result;
                }
            }
        }
        result = true;
        return result;
    }

    /**
     * Determine whether a placement is valid.  To be valid, the placement must be well-formed
     * and each tile placement must follow the game's placement rules.
     *
     * @param patchCircle a patch circle string
     * @param placement   A placement string
     * @return true if the placement is valid
     */
    static boolean isPlacementValid(String patchCircle, String placement) {
        // FIXME Task 6: determine whether a placement is valid
        return true;
    }

    /**
     * Determine the score for a player given a placement, following the
     * scoring rules for the game.
     *
     * @param placement   A placement string
     * @param firstPlayer True if the score for the first player is requested,
     *                    otherwise the score for the second player should be returned
     * @return the score for the requested player, given the placement
     */
    static int getScoreForPlacement(String patchCircle, String placement, boolean firstPlayer) {
        // FIXME Task 7: determine the score for a player given a placement
        return 0;
    }

}
