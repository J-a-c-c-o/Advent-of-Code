package nl.jtepoel.AOC.year2021.day7;

public class crab {
    private int pos;

    public crab(int pos) {
        this.pos = pos;
    }


    public int getPos() {
        return pos;
    }



    public int moveTo(int pos) {
        return Math.abs(this.pos - pos);
    }

    public int moveToP2(int pos) {
        int steps = Math.abs(this.pos - pos);

        return (steps*(steps+1))/2;
    }
}
