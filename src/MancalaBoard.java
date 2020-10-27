public class MancalaBoard {

    public enum Player{
        A,
        B
    }

    public enum MoveType{
        Normal, // nothing special happens
        Retake, // another turn
        Capture, // capture opponent stones (n-m)
        Invalid // no stones available
    }

    private BoardSide boardSideA;
    private BoardSide boardSideB;
    private int boardSize;

    public MancalaBoard(int size, int startingStones){
        boardSize = size + 1;
        boardSideA = new BoardSide(boardSize, startingStones);
        boardSideB = new BoardSide(boardSize, startingStones);

    }


    /**
     * moves stones across the MancalaBoard
     * @param movePosition
     * @param player
     * @return if move is successful
     */
    public MoveType tryMove(int movePosition, Player player){
        if(player == player.A){
            return move(boardSideA, boardSideB, movePosition);
        }
        else {
            return move(boardSideB, boardSideA, movePosition);
        }
    }

    public MoveType move(BoardSide boardSidePlayer, BoardSide boardSideOther, int movePosition){

        int availableMoves = boardSidePlayer.getPocket(movePosition).getStones();
        if(availableMoves == 0){
            return MoveType.Invalid;
        }

        boardSidePlayer.getPocket(movePosition).clearStones(); // empties stones.
        MoveType moveMade = MoveType.Normal;

        int playerPosition = movePosition + 1;
        int oppositionPosition = 0;

        while(availableMoves > 0){
            availableMoves = incrementStones(boardSidePlayer, playerPosition, availableMoves);
            availableMoves = incrementStones(boardSideOther, oppositionPosition, availableMoves);
        }

        /* cases:
            is there an overflow? i.e. do we go to opponent, if so, go ahead.
            if not, are we in mancala? if so take another move
            if in opponent Mancala, skip it
            are we in an empty pocket? capture (n - m)
            otherwise simple move
         */

        return moveMade;
    }

    private int incrementStones(BoardSide boardSide, int currentPosition, int availableMoves){
        while (currentPosition < boardSide.getPockets().length && availableMoves > 0){
            incrementStone(boardSide, currentPosition);
            currentPosition++;
            availableMoves--;
        }
        return availableMoves;
    }

    private void incrementStone(BoardSide boardSide, int incrementPosition){
        boardSide.getPocket(incrementPosition).incrementStones();

    }

}
