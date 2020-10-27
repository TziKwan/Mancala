import javax.swing.plaf.multi.MultiViewportUI;

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
     * @return the type of move made
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
        MoveType moveMade;

        MoveState moveState = new MoveState(boardSidePlayer, movePosition + 1, availableMoves, false);

        // need to update player position, which board and

        while(availableMoves > 0){
            moveState = incrementStones(moveState);
            if(moveState.availableMoves > 0) {
                moveState = updateMoveState(moveState, true, boardSideOther);
            }
            moveState = incrementStones(moveState);
            if(moveState.availableMoves > 0){
                moveState = updateMoveState(moveState, false, boardSidePlayer);
            }
        }

        if(moveState.boardSide == boardSideOther){
            moveMade = MoveType.Normal;
        }
        else{
            // Mancala landed
            if(moveState.position == boardSize - 1){
                moveMade = MoveType.Retake;
            }
            // Capture
            else if (moveState.boardSide.getPocket(moveState.position).getStones() == 1){
                moveMade = MoveType.Capture;
            }
            // Simple move
            else{
                moveMade = MoveType.Normal;
            }
        }

        return moveMade;

    }

    private MoveState updateMoveState(MoveState moveState, boolean opponent, BoardSide boardSide){
        moveState.opponent = opponent;
        moveState.position = 0;
        moveState.boardSide = boardSide;
        return moveState;
    }

    private MoveState incrementStones(MoveState moveState){

        while (moveState.position < boardSize && moveState.availableMoves > 0){
            if(moveState.position <= boardSize - 1 && !moveState.opponent) {   // skips opponent Mancala
                incrementStone(moveState.boardSide, moveState.position);
                moveState.availableMoves--;
            }
            moveState.position++;
        }
        return moveState;
    }

    private void incrementStone(BoardSide boardSide, int incrementPosition){
        boardSide.getPocket(incrementPosition).incrementStones();

    }

    private class MoveState{
        private BoardSide boardSide;
        private int position;
        private int availableMoves;
        private boolean opponent;

        public MoveState(BoardSide boardSide, int position, int availableMoves, boolean opponent){
            this.boardSide = boardSide;
            this.position  = position;
            this.availableMoves = availableMoves;
            this.opponent = opponent;
        }
    }

}
