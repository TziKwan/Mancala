public class BoardSide {

    private Pocket[] pockets;

    public BoardSide(int size, int startingStones){
        pockets = new Pocket[size + 1];
        for(int i = 0; i < size + 1; i++){
            if(i != size) {
                pockets[i] = new Pocket(startingStones);
            }
            else{
                pockets[i] = new Mancala(startingStones);
            }
        }
    }

    public Pocket[] getPockets(){
        return pockets;
    }

    


}
