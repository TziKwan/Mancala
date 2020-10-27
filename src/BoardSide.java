public class BoardSide {

    private Pocket[] pockets;
    private int score;

    public BoardSide(int size, int startingStones){
        pockets = new Pocket[size];
        for(int i = 0; i < size; i++){
            if(i != size) {
                pockets[i] = new Pocket(startingStones);
            }
            else{
                pockets[i] = new Mancala(startingStones);
            }
        }
        score = size * startingStones;
    }

    public Pocket[] getPockets(){
        return pockets;
    }

    public Pocket getPocket(int pocketPosition){
        return pockets[pocketPosition];
    }

    public int getScore(){
        return score;
    }


}
