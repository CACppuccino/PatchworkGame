package comp1140.ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the state of a Patchwork game in progress.
 */
public class PatchworkGame {


    public PatchworkGame(String patchCircleString) {
    }

    public PatchworkGame() {

    }
    public final int [] tileCost = {2,1,3,2,3,2,1,0,6,
                                    4,2,1,3,7,3,7,3,
                                    2,4,5,2,5,10,5,10,
                                    1,4,7,10,1,2,7,8};

    public final int [] tileCover = {2,3,3,3,4,5,7,6,4,
                                    6,4,6,4,5,5,6,6,
                                    6,4,5,7,8,5,5,6,
                                    6,4,6,5,5,5,4,6};

    public final int [] tileTimetoken = {1,3,1,2,2,2,4,3,5,
                                         2,2,5,3,1,4,4,6,
                                         1,6,4,3,3,3,5,5,
                                         2,2,2,4,2,3,6,6};

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
        boolean ans = false;
        System.out.println(placement);
        // Initialize an ArrayList to store all the tiles of the placement in the placement
        ArrayList<Character> chars = new ArrayList<>();
        if ( isPlacementWellFormed(placement))
        {
            if ( patchCircle == null || patchCircle.isEmpty()){
                ans = false;
                return ans;
            }
            else
            {
                // may need a function check_fullstate
                // may need a function check_turn
                // may need a function getScore

                // This block is to get the tile order of the placement
                for ( int i = 0;i < placement.length();i++){
                    if (placement.charAt(i) == '.'){
                        continue;
                    }
                    else
                    {
                        if ( placement.charAt(i) == 'h')
                        {
                            i = i + 3;
                        }
                        else
                        {
                            chars.add(placement.charAt(i));
                            i = i + 3;
                        }
                    }
                }
                System.out.println(chars.size());
                // This block is to get the start position in the patchCircle
                int positonOfA = 0;
                for ( int i = 0;i < patchCircle.length();i++){
                    if ( patchCircle.charAt(i) == 'A'){
                        positonOfA = i;
                    }
                }

                // This block is to cast the patchCircle into an ArrayList
                ArrayList<Character> patchCircleList  = new ArrayList<>();
                for ( int i = 0; i < patchCircle.length();i++){
                    patchCircleList.add(patchCircle.charAt(i));
                }

                // Initialize a newPosition that every time the player choose a tile
                int newPosition = positonOfA + 1;
                // This block is to check whether the tile is in the next three position of the neutral token
                for ( int i = 0; i < chars.size();i++){
                    // Initialize a variable currentPosition to get the which tile is chosen by the player
                    int currentPosition = 0;
                    // Initialize a variable to check whether the tile is picking up from the next three
                    // tiles after neutral token
                    int checkExist = 0;
                    int check = 0;
                    HashMap<Character,Integer> nextThreeTiles = new HashMap<>();
                    if (newPosition <= patchCircleList.size() - 3) {
                        nextThreeTiles.put(patchCircleList.get(newPosition), newPosition);
                        nextThreeTiles.put(patchCircleList.get(newPosition + 1), newPosition+1);
                        nextThreeTiles.put(patchCircleList.get(newPosition + 2), newPosition+2);
                    }
                    else
                    {
                        int gap = 3 - (patchCircleList.size() - newPosition);
                        if ( gap == 1){
                            nextThreeTiles.put(patchCircleList.get(newPosition),newPosition);
                            nextThreeTiles.put(patchCircleList.get(newPosition + 1),newPosition+1);
                            nextThreeTiles.put(patchCircleList.get(0),0);
                        }
                        else if ( gap == 2)
                        {
                            nextThreeTiles.put(patchCircleList.get(newPosition),newPosition);
                            nextThreeTiles.put(patchCircleList.get(0),0);
                            nextThreeTiles.put(patchCircleList.get(1),1);
                        }
                        else
                        {
                            nextThreeTiles.put(patchCircleList.get(0),0);
                            nextThreeTiles.put(patchCircleList.get(1),1);
                            nextThreeTiles.put(patchCircleList.get(2),2);
                        }
                    }
                    System.out.println();
                    System.out.println(currentPosition);
                    System.out.println(chars.get(i));
                    System.out.println(nextThreeTiles);
                    System.out.println(patchCircleList);
                    System.out.println(chars);
                    for ( int l = 0; l < nextThreeTiles.size();l++) {
                        if (nextThreeTiles.containsKey(chars.get(i))) {
                            currentPosition = nextThreeTiles.get(chars.get(i));
                            check = 1;
                        }

                        if (check == 0 && l == 2) {
                            ans = false;
                            return false;
                        }

                    }
                    System.out.println(currentPosition);
                    newPosition = currentPosition;
                    patchCircleList.remove(newPosition);
                    System.out.println(i);
                }
                ans = true;
                return ans;
            }
        }
        else
        {
            ans = false;
        }
        return ans;
    }

    public static boolean checkOverlap(String placement){
        ArrayList<String> tilePosition = new ArrayList<>();
        boolean answer = false;
        int counter = 0;
        for( int i = 0; i < placement.length();i++){
            char [] position = new char[2];
            if ( placement.charAt(i) == '.'){
                continue;
            }
            else
            {
                position[0] = placement.charAt(i+1);
                position[1] = placement.charAt(i+2);
                String positionArray = new String(position);
                tilePosition.add(positionArray);
                i = i + 3;
            }
        }
        for ( int i = 0; i < tilePosition.size();i++){
            for ( int j = i+1; j < tilePosition.size();j++){
                if ( tilePosition.get(i).equals(tilePosition.get(j))){
                    return answer;
                }
            }
        }
        answer = true;
        return answer;
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
