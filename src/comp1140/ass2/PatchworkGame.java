package comp1140.ass2;

import java.util.*;


/**
 * Represents the state of a Patchwork game in progress.
 */
public class PatchworkGame {

    static boolean DEBUG = false;
    static State p1 = new State(1), p2 = new State(2);

    //for the special tile event
    static boolean spt = false;

    public PatchworkGame(String patchCircleString) {
    }

    public PatchworkGame() {

    }

    public static final int[] specialButton = {5, 11, 17, 23, 29, 35, 41, 47, 53};
    public static final int[] specialTile = {20, 26, 32, 44, 50};
    public static final int[] tileCost = {2, 1, 3, 2, 3, 2, 1, 0, 6,
            4, 2, 1, 3, 7, 3, 7, 3,
            2, 4, 5, 2, 5, 10, 5, 10,
            1, 4, 7, 10, 1, 2, 7, 8, 0};

    public static final int[][] tileCover = {{2, 1}, {2, 2}, {2, 2}, {3, 1}, {3, 2}, {3, 2}, {3, 5},
            {4, 3}, {2, 2}, {4, 2}, {3, 2}, {4, 2}, {4, 1}, {1, 5}, {4, 2}, {4, 2}, {3, 3}, {4, 3}, {3, 2}, {3, 3},
            {3, 3}, {4, 3}, {5, 2}, {3, 3}, {4, 2}, {4, 3}, {3, 2}, {4, 3}, {3, 3}, {2, 3}, {4, 2}, {2, 3}, {3, 3}, {1, 1}};
    //{row,col}
    public static final int[][][] tileSpace = {{{0, 0}, {1, 0}}, {{0, 1}, {1, 0}, {1, 1}}, {{0, 1}, {1, 0}, {1, 1}},
            {{0, 0}, {1, 0}, {2, 0}}, {{0, 1}, {1, 0}, {1, 1}, {2, 0}}, {{0, 0}, {1, 0}, {2, 0}, {1, 1}, {2, 1}}, {{1, 0}, {1, 1}, {1, 2}, {1, 3}, {1, 4}, {0, 2}, {2, 2}},
            {{0, 1}, {1, 0}, {1, 1}, {1, 2}, {2, 1}, {3, 1}}, {{0, 0}, {0, 1}, {1, 0}, {1, 1}}, {{0, 0}, {1, 0}, {2, 0}, {1, 1}, {2, 1},
            {3, 1}}, {{1, 0}, {0, 1}, {1, 1}, {2, 1}}, {{0, 0}, {3, 0}, {0, 1}, {1, 1}, {2, 1}, {3, 1}}, {{0, 0}, {1, 0}, {2, 0}, {3, 0}},
            {{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}}, {{0, 0}, {1, 0}, {2, 0}, {3, 0}, {2, 1}}, {{0, 0}, {1, 0}, {2, 0}, {3, 0}, {2, 1}, {1, 1}},
            {{1, 0}, {2, 0}, {0, 1}, {1, 1}, {1, 2}, {2, 2}}, {{2, 0}, {0, 1}, {1, 1}, {2, 1}, {3, 1}, {1, 2}}, {{2, 0}, {0, 1}, {1, 1}, {2, 1}},
            {{1, 0}, {0, 1}, {1, 1}, {2, 1}, {1, 2}}, {{0, 0}, {1, 0}, {2, 0}, {1, 1}, {0, 2}, {1, 2}, {2, 2}}, {{1, 0}, {2, 0}, {0, 1}, {1, 1}, {2, 1}, {3, 1}, {1, 2}, {2, 2}},
            {{3, 0}, {0, 1}, {1, 1}, {2, 1}, {3, 1}}, {{2, 0}, {0, 1}, {1, 1}, {2, 1}, {2, 2}}, {{0, 0}, {1, 0}, {0, 1}, {1, 1}, {2, 1}, {3, 1}},
            {{0, 0}, {0, 1}, {1, 1}, {2, 1}, {3, 1}, {3, 2}}, {{0, 0}, {1, 0}, {2, 0}, {2, 1}}, {{3, 0}, {0, 1}, {1, 1}, {2, 1}, {3, 1}, {3, 2}},
            {{0, 0}, {1, 0}, {1, 1}, {2, 1}, {2, 2}}, {{0, 0}, {1, 0}, {1, 1}, {0, 2}, {1, 2}}, {{2, 0}, {3, 0}, {0, 1}, {1, 1}, {2, 1}},
            {{1, 0}, {0, 1}, {1, 1}, {0, 2}}, {{2, 0}, {0, 1}, {1, 1}, {2, 1}, {0, 2}, {1, 2}}, {{0, 0}}};

    public static final int[] tileTimetoken = {1, 3, 1, 2, 2, 2, 4, 3, 5,
            2, 2, 5, 3, 1, 4, 4, 6,
            1, 6, 4, 3, 3, 3, 5, 5,
            2, 2, 2, 4, 2, 3, 6, 6, 0};

    public static final int[] tileButton = {0, 0, 0, 0, 1, 0, 1, 1, 2, 0,
            0, 1, 1, 1, 1, 2, 2, 0, 2,
            2, 0, 1, 2, 2, 3, 0, 1,
            2, 3, 0, 1, 3, 3, 0};

    public static char[] three;
    static int aPlc;

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
        char tile, b, c, d;
        if (placement.charAt(0) == '.')
            return true;
        else if (placement.length() != 4)
            return false;
        else {
            tile = placement.charAt(0);
            b = placement.charAt(1);
            c = placement.charAt(2);
            d = placement.charAt(3);
            return d >= 'A' && d <= 'H' && b >= 'A' && b <= 'I' && c >= 'A' && c <= 'I' && ((tile >= 'A' && tile <= 'Z')
                    || (tile >= 'a' && tile <= 'h'));
        }
    }

    //init the patchCircle by randomly generate the whole patchCircle
    static String initPatchCircle() {
        StringBuilder res = new StringBuilder();
        for (char i = 'A'; i <= 'Z'; i++)
            res.append(i);
        for (char i = 'a'; i <= 'g'; i++)
            res.append(i);
        Random rng = new Random();
        char[] result = res.toString().toCharArray();
        for (int i = 0; i < 100; i++) {
            int x = rng.nextInt(33);
            int y = rng.nextInt(33);
            char tmp = result[x];
            result[x] = result[y];
            result[y] = tmp;
        }
        return new String(result);
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
        if (placement == null || placement.isEmpty())
            return false;
        //initialize a List<Integer> to store all the position of the patch
        List<Integer> patchposition = new ArrayList();
        String SubSting;
        for (int i = 0; i < placement.length(); i++) {
            if (placement.charAt(i) != '.') {
                if (placement.length() - i < 4)
                    SubSting = placement.substring(i, placement.length());
                else
                    SubSting = placement.substring(i, i + 4);
                patchposition.add(i);
                if (!isPatchPlacementWellFormed(SubSting))
                    return false;
                i = i + 3;
            }
        }

        // check whether there is a patch appear more than once
        for (int i = 0; i < patchposition.size(); i++) {
            for (int j = i + 1; j < patchposition.size(); j++) {
                if (placement.charAt(patchposition.get(i)) == placement.charAt(patchposition.get(j)) && placement.charAt(patchposition.get(i)) != 'h') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determine whether a placement is valid.  To be valid, the placement must be well-formed
     * and each tile placement must follow the game's placement rules.
     *
     * @param patchCircle a patch circle string
     * @param placement   A placement string
     * @return true if the placement is valid
     */

    // Here has some problems for test good
    /*need to check:
    1. whether is overlapped
    2. whether is out of bound
    3. not in scope
    4. not in patchCircle
    * */
    static boolean isPlacementValid(String patchCircle, String placement) {
        // FIXME Task 6: determine whether a placement is valid
      /*
        Well formed judgement
        * */
        if (!PatchworkGame.isPlacementWellFormed(placement)) {
            if (DEBUG) System.out.println("isPlacementWellFormed");
            return false;
        }
        /*---------------------------------------*/
        p1 = new State(1);
        p2 = new State(2);
        /*
        Move data to a linked list , and get the tile 'A' position
        * */
        aPlc = 0;
        LinkedList<Character> partches = new LinkedList<>();
        if (patchCircle == null || patchCircle.isEmpty()) {
            if (DEBUG) System.out.println("circle null");
            return false;
        }
        for (int i = 0; i < patchCircle.length(); i++)
            partches.add(i, patchCircle.charAt(i));
        aPlc = patchCircle.indexOf('A') + 1;

        /*--------------------------------------*/

        /*initialise the first player to be first*/
        boolean fstPlayer = true;
        /*instantiate two players in each round*/
        /*let player1 on the top in the initial sate*/
        p1.onTop = true;
        /*cut the placement in length 4 in each round*/
        for (int i = 0; i < placement.length(); i++) {
            /*for the action of advance*/
            if (placement.charAt(i) == '.') {
                fstPlayer = !fstPlayer;
                State.advanced(p1, p2);
            } else {
                /*
                * for the action of buying parches*/
                //slice the placement for this round
                String plc = placement.substring(i, i + 4);
                //check the turn
                if (State.check_turn(p1, p2) == 1) fstPlayer = true;
                else fstPlayer = false;
                if (fstPlayer) {
                    if (!outOfBoard(plc, p1)) {
                        if (DEBUG) System.out.println("out of Board/Overlap1");
                        return false;
                    }
                } else {
                    if (!outOfBoard(plc, p2)) {
                        if (DEBUG) System.out.println("out of Board/Overlap2");
                        return false;
                    }
                }
                //try to buy a parch
                try {
                    State.buyPartches(p1, p2, plc.charAt(0));
                } catch (Error e) {
                    if (e.getMessage().equals("cant afford the tile" + plc.charAt(0))
                            || e.getMessage().equals("no enough h")) {
                        if (DEBUG) System.out.println(e.getMessage());
                        return false;
                    }
                }
                three = new char[]{partches.get(aPlc % partches.size()), partches.get((aPlc + 1) % partches.size()), partches.get((aPlc + 2) % partches.size())};
                //move the nertral token
                //if the parches left in the partches circle doesn't contain the current
                //wanted partches,then is invalid
                if (plc.charAt(0) != 'h') {
                    if (!partches.contains(plc.charAt(0))) {
                        if (DEBUG) System.out.println("not cotains anymore" + plc.charAt(0));
                        return false;
                    }

                    if (!(plc.charAt(0) == three[0] || plc.charAt(0) == three[1] || plc.charAt(0) == three[2])) {
                        if (DEBUG)
                            System.out.println("three error:" + plc.charAt(0) + " " + patchCircle + " " + three[0] + " " + three[1] + " " + three[2]);
                        if (DEBUG)
                            for (int j = 0; j < partches.size(); j++)
                                System.out.println(partches.get(j));
                        return false;
                    } else if (plc.charAt(0) == three[0]) {
                        partches.remove(aPlc % partches.size());

                    } else if (plc.charAt(0) == three[1]) {
                        partches.remove((aPlc + 1) % partches.size());
                        aPlc++;
                    } else {
                        partches.remove((aPlc + 2) % partches.size());
                        aPlc += 2;
                    }
                    //if token goes to the end, make it back to the beginning
                    aPlc = aPlc > partches.size() ? aPlc % (partches.size() + 1) : aPlc % partches.size();//aPlc >= partches.size()? (aPlc % partches.size())-1:aPlc;
                    three = new char[]{partches.get(aPlc % partches.size()), partches.get((aPlc + 1) % partches.size()), partches.get((aPlc + 2) % partches.size())};
                }
                i = i + 3;
            }
        }
        return true;
    }

    /*
    * making the flip according to the tile's property and return the position occupied after the flip
    * */
    private static int[][] flipHandle(char tile, int[][] expose) {
        int index = getIndex(tile);

        if (tileSpace[index].length == tileCover[index][0] * tileCover[index][1])
            return expose;
        else if (index == 6 || index == 7 || index == 8 || index == 16 || index == 19 || index == 20 || index == 21 || index == 23 || index == 27 || index == 29)
            return expose;

        int[][] copy = new int[tileSpace[index].length][2];


        //to make it rotation sensitive,need to add 1 to avoid zero circumenstance
        for (int i = 0; i < tileSpace[index].length; i++) {
            copy[i][0] = tileSpace[index][i][0] + 1;
            copy[i][1] = tileSpace[index][i][1] + 1;
        }


        //flip on tiles that width is 2
        if (tileCover[index][1] == 2)
            for (int[] xs : copy)
                xs[1] = (xs[1] == 1 ? 2 : 1);
            //flip on tiles that width is 3
        else {
            for (int[] xs : copy) {
                if (xs[1] == 1) xs[1] = 3;
                else if (xs[1] == 3) xs[1] = 1;
            }
        }
        return copy;
    }

    /*
    * a simple method translated the char to the index of the tile array,
    * extracted because of using too many times
    * */
    static int getIndex(char tile) {
        int index;
        if (tile >= 'A' && tile <= 'Z')
            index = tile - 'A';
        else
            index = tile - 'a' + 26;
        return index;
    }

    /*
    * do the rotate and return the position index of the tile
    * */
    private static int[][] rotateHandle(char rotate, int[][] expose) {
        // do the rotation
        if (rotate == 'A')
            return expose;
        else if (rotate == 'B') {
            for (int[] xs : expose) {
                int tmp = xs[0];
                xs[0] = xs[1];
                xs[1] = -tmp;
            }
        } else if (rotate == 'C') {
            for (int[] xs : expose) {
                xs[0] = -xs[0];
                xs[1] = -xs[1];
            }
        } else if (rotate == 'D') {
            for (int[] xs : expose) {
                int tmp = xs[0];
                xs[0] = -xs[1];
                xs[1] = tmp;
            }
        } else {
            if (DEBUG) System.out.println("rotation wrong" + rotate);
            return null;
        }

        // process the position data according to the coordinate, further adjustment
        int[] topleft = new int[2];
        topleft[0] = expose[0][0];
        topleft[1] = expose[0][1];
        for (int[] xs : expose) {
            if (topleft[0] > xs[0]) topleft[0] = xs[0];
            if (topleft[1] > xs[1]) topleft[1] = xs[1];
        }
        topleft[0] = 1 - topleft[0];
        topleft[1] = 1 - topleft[1];
        for (int[] xs : expose) {
            xs[0] += topleft[0];
            xs[1] += topleft[1];
        }
        return expose;
    }

    //false for out of the board, true for in
    public static boolean outOfBoard(String placement, State player) {
        char row = placement.charAt(2), col = placement.charAt(1);
        int rowN = row - 'A', colN = col - 'A';
        char tile = placement.charAt(0);
        char rotate = placement.charAt(3);
        int index;
        int[][] expo, result;
        //get the relevant position and
        index = getIndex(tile);
        expo = new int[tileSpace[index].length][2];
        for (int i = 0; i < tileSpace[index].length; i++) {
            expo[i][0] = tileSpace[index][i][0] + 1;
            expo[i][1] = tileSpace[index][i][1] + 1;
        }
        // do the rotation if there is one
        if (rotate >= 'A' && rotate <= 'D') {
            result = rotateHandle(rotate, expo);
        }
        else {
            rotate = (char) ('A' + rotate - 'E');
            result = rotateHandle(rotate, flipHandle(tile, expo));
        }
        // change the data to the absolute position
        for (int[] xs : result) {
            xs[0] += rowN;
            xs[1] += colN;
        }
        for (int[] xs : result) {
            if (!(xs[0] >= 1 && xs[0] <= 9 && xs[1] >= 1 && xs[1] <= 9)) {
                if (DEBUG) System.out.println("out of board" + placement + " " + xs[0] + " " + xs[1] + "//");
                return false;
            }
            if (!player.squiltBoard[xs[0] - 1][xs[1] - 1])
                player.squiltBoard[xs[0] - 1][xs[1] - 1] = true;
            else
                   return false;
        }
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
        boolean ss = isPlacementValid(patchCircle, placement);
        // get the score if placement is valid
        if (ss)
            if (firstPlayer) return p1.getScore();
            else return p2.getScore();
        return 0;
    }

    public static void main(String[] args) {
//        System.out.println(isPlacementValid("FEJbDMKaBeLHfSXVcCOIUWgYRZPdGQTNA",".HAAA.LADB.dAFA.EAHB.aCAC...eCGF.....hDCACDEAODHD.UFBAhAAA.FFFH.KHGA...hFDADIAA............hHAA.......hHCA....."));
    }

}