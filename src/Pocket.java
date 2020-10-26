public class Pocket {
    protected int stones;

    public Pocket(int startingStones){
        stones = startingStones;
    }

    public int getStones() {
        return stones;
    }

    public int incrementStones(){
        stones++;
        return stones;
    }

    public int clearStones() {
        stones = 0;
        return stones;
    }
}
