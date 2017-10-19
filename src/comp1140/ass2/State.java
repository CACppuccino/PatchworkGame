package comp1140.ass2;

import java.util.ArrayList;
import java.util.*;

public class State {
    final static int GRIDS = 53;
    final int id;
    List<Character> tiles = new ArrayList<Character>();
    //    calculating the squares left in two quilt boards.
    int squareleft = 81;

    //    calculating the time left in the time board of two players
    int timecount = 0;
    // for counting each player's score

    //when the game finshed, the score should be scoreCount = scoreCount + buttonCount - squareleft*2
    int scoreCount = 0;
    //    calculating the button of each player holds
    int buttonCount = 5;

    // how many buttons should a player be rewarded each special event of button event
    int specialButton = 0;
    //    showing two players' states on the time board,
// 1 indicates moving first, 0 indicates moving later,
// 2 indicates the player has reached the final point.
//    int tbState = 0;

    //    indicating the place of the neutral token, if ntState is i,
// then the patch i,i+1,i+2 should be displayed in the candidate area.
//    int ntState = 0;

    //for special h to be paied out
    boolean specialH = false;

    //showing whether the player is on the top
    boolean onTop = false;

    final static char[] PATCHES = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g'};

    State(int id) {
        this.id = id;
        for (boolean[] sq : squiltBoard)
            for (boolean sqq : sq)
                sqq = false;
    }

    //    this list should be changed when being initialised randomly by the initialization()/
// once the player buys an patch/ choose undo after placed a patch
    ArrayList<Character> currentPatch = new ArrayList<Character>();
    boolean[][] squiltBoard = new boolean[9][9];

    //for copying the state instance
    public void copy(State target) {
        squareleft = target.squareleft;
        timecount = target.timecount;
        scoreCount = target.scoreCount;
        buttonCount = target.buttonCount;
        specialButton = target.specialButton;
        specialH = target.specialH;
        onTop = target.onTop;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.squiltBoard[i][j] = target.squiltBoard[i][j];
            }
        }
    }

    //    this function is used for checking the who should be the person doing the next turn.
//    Two situation should be considered: if they are not in the same square of the time board,
// the one fall behind should take the turn. If they are in the same square, the player who
// last moved to that space takes the turn.
    public static int check_turn(State p1, State p2) {
        if (p1.specialH) return 1;
        else if (p2.specialH) return 2;
        if (p1.timecount > p2.timecount)
            return 2;
        else if (p1.timecount < p2.timecount)
            return 1;
        else {
            if (p1.onTop && p2.onTop) throw new Error("both player token on the top");
            else if (p2.onTop) return 2;
            else return 1;
        }
    }
    /*
    * the function is for player doing the action 'advance' and update the state of the player
    * */
    public static void advanced(State p1, State p2) {
        int t = check_turn(p1, p2);
        int diff;
        State[] states = {p1,p2};
        int another = t==1?1:0, thisOne = t==1?0:1;

        diff = states[another].timecount + 1 <= GRIDS ? (states[another].timecount-states[thisOne].timecount)+1: GRIDS-states[thisOne].timecount;
        states[thisOne].buttonCount += diff;
        State.specialEvent(states[thisOne],states[another],states[thisOne].timecount,diff);
        states[thisOne].timecount = states[another].timecount + 1 > GRIDS ? GRIDS : states[another].timecount + 1;
    }

    /*
    * the function is for player doing the action 'advance' and update the state of the player
    * */
    public static void buyPartches(State p1, State p2, char p) {
        int index;
        if (p >= 'A' && p <= 'Z') index = p - 'A';
        else index = p - 'a' + 26;
        //get the time decrement from the array
        final int timeInc = PatchworkGame.tileTimetoken[index];
        final int buttonDec = PatchworkGame.tileCost[index];
        final int specialButton = PatchworkGame.tileButton[index];

        int t = check_turn(p1, p2);
        State[] states = {p1,p2};
        int another = t==1?1:0, thisOne = t==1?0:1;

        if (p == 'h') {
            if (states[thisOne].tiles.size() > 0) {
                states[thisOne].tiles.remove(states[thisOne].tiles.size() - 1);
                states[thisOne].specialH = false;
                states[thisOne].squareleft--;
                throw new Error("spend a h");
            } else {
                throw new Error("no enough h");
            }
        }
        states[thisOne].buttonCount = states[thisOne].buttonCount - buttonDec;
        if (states[thisOne].buttonCount < 0) {
            states[thisOne].buttonCount += buttonDec;
            //suppose to notice the Viewer
            throw new Error("cant afford the tile" + p);
        }
        states[thisOne].specialButton += specialButton;
        specialEvent(states[thisOne], states[another], states[thisOne].timecount, timeInc);
        states[thisOne].timecount = states[thisOne].timecount + timeInc > GRIDS ? GRIDS : states[thisOne].timecount + timeInc;
        states[thisOne].squareleft -= PatchworkGame.tileSpace[index].length;
        //considering the timetoken overlap situation
        if (states[thisOne].timecount == states[another].timecount) {
            states[thisOne].onTop = true;
            states[another].onTop = false;
        }
    }

    /*
    * The function is only called when the signal is True
    */
    static boolean affordPartch(int buttonCount, int cost) {
        return buttonCount - cost >= 0;
    }

    public static void specialEvent(State player, State oplayer, int start, int steps) {
        for (int sb : PatchworkGame.specialButton) {
            if (sb >= start + 1 && sb <= start + steps)
                player.buttonCount += player.specialButton;
        }
        for (int i = 0; i < PatchworkGame.specialTile.length; i++) {
            if (PatchworkGame.specialTile[i] >= start + 1 && PatchworkGame.specialTile[i] <= start + steps
                    && oplayer.timecount < PatchworkGame.specialTile[i]) {
                player.tiles.add('h');
                player.specialH = true;
            }
        }
        if (player.tiles.size() > 0)
            player.specialH = true;
    }
    //    the functions is called to get two players' score,
// first int is for player 1, second for player 2

    static void printSb(State player){
        for (boolean[] x: player.squiltBoard)
        {
            for (boolean xx:x) {
                System.out.print(xx ? "*" : "@");
            }
            System.out.print("\n");
        }
    }
    //this function should only be called at the final of the game
    public int getScore() {
        scoreCount = buttonCount - squareleft * 2;
        return scoreCount;
    }
}