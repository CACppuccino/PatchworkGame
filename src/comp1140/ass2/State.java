package comp1140.ass2;

import java.util.ArrayList;

public class State {

//    calculating the squares left in two quilt boards.
    int squareleft = 49;

//    calculating the time left in the time board of two players
    int timecount = 52;

//    calculating the button of each player holds
    int buttonCount = 0;

//    showing two players' states on the time board,
// 1 indicates moving first, 0 indicates moving later,
// 2 indicates the player has reached the final point.
    int tbState = 0;

//    indicating the place of the neutral token, if ntState is i,
// then the patch i,i+1,i+2 should be displayed in the candidate area.
    int ntState = 0;

    //showing whether the player is on the top
    boolean onTop = false;

    final static char[] PATCHES = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W',
            'X','Y','Z','a','b','c','d','e','f','g'};

//    this list should be changed when being initialised randomly by the initialization()/
// once the player buys an patch/ choose undo after placed a patch
    ArrayList<Character> currentPatch = new ArrayList<Character>();


    //    this function is used for recognizing the end of the game.
// Both of the players' time consumption is full,
// i.e. players' time token have reached the end square of the time board.
    public boolean check_fullstate(){
        return this.timecount==0;}

    //    this function is used for checking the who should be the person doing the next turn.
//    Two situation should be considered: if they are not in the same square of the time board,
// the one fall behind should take the turn. If they are in the same square, the player who
// last moved to that space takes the turn.
    public static int check_turn(State p1,State p2){
        if (p1.timecount<p2.timecount)
            return 2;
        else if (p1.timecount>p2.timecount)
            return 1;
        else {
            if (p1.onTop && p2.onTop)   throw new Error("both player token on the top");
            else if (p1.onTop)  return 1;
            else    return 2;
        }
    }

    //    the functions is called to get two players' score,
// first int is for player 1, second for player 2
    public int getScore(){return buttonCount-squareleft*2;}
}
