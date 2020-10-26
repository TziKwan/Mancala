public class Mancala extends Pocket{
    public Mancala(int startingStones){
        super(startingStones);
    }

    @Override
    public int clearStones(){
        return stones;
    }
}
