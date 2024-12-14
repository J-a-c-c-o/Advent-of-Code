package nl.jtepoel.AOC.year2024.day14;

public class Robot {
    private int x;
    private int y;
    private int vx;
    private int vy;

    public Robot(int x, int y, int vx, int vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public void move(int boundsX, int boundsY) {
        this.x += vx;
        this.y += vy;

        if (this.x < 0) {
            this.x *= -1;
            this.x = boundsX - this.x;
        }

        if (this.x >= boundsX) {
            this.x = this.x - boundsX;
        }

        if (this.y < 0) {
            this.y *= -1;
            this.y = boundsY - this.y;
        }

        if (this.y >= boundsY) {
            this.y = this.y - boundsY;
        }




    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "x=" + x +
                ", y=" + y +
                ", vx=" + vx +
                ", vy=" + vy +
                '}';
    }
}
