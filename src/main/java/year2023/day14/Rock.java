package year2023.day14;

import java.util.List;
import java.util.Objects;

public class Rock {
    int x;
    int y;
    char c;
    int lastX;
    int lastY;

    public Rock(int x, int y, char c, int lastX, int lastY) {
        this.x = x;
        this.y = y;
        this.c = c;
        this.lastX = lastX;
        this.lastY = lastY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean up(List<Rock> rocks) {

        //check bounds
        if (y == 0) {
            return false;
        }


        boolean didMove = false;

        while (true) {
            if (y == 0) {
                break;
            }
            boolean canMove = true;
            for (Rock rock : rocks) {
                if (rock.x == x && rock.y == y - 1) {
                    canMove = false;
                    break;
                }
            }
            if (canMove) {
                y--;
                didMove = true;
            } else {
                break;
            }
        }
        return didMove;
    }

    public boolean left(List<Rock> rocks) {
        //check bounds
        if (x == 0) {
            return false;
        }

        boolean didMove = false;

        while (true) {
            if (x == 0) {
                break;
            }
            boolean canMove = true;
            for (Rock rock : rocks) {
                if (rock.x == x - 1 && rock.y == y) {
                    canMove = false;
                    break;
                }
            }
            if (canMove) {
                x--;
                didMove = true;
            } else {
                break;
            }
        }
        return didMove;
    }

    public boolean down(List<Rock> rocks) {
        //check bounds
        if (y == lastY) {
            return false;
        }

        boolean didMove = false;

        while (true) {
            if (y == lastY) {
                break;
            }
            boolean canMove = true;
            for (Rock rock : rocks) {
                if (rock.x == x && rock.y == y + 1) {
                    canMove = false;
                    break;
                }
            }
            if (canMove) {
                y++;
                didMove = true;
            } else {
                break;
            }
        }

        return didMove;
    }

    public boolean right(List<Rock> rocks) {
        //check bounds
        if (x == lastX) {
            return false;
        }

        boolean didMove = false;

        while (true) {
            if (x == lastX) {
                break;
            }
            boolean canMove = true;
            for (Rock rock : rocks) {
                if (rock.x == x + 1 && rock.y == y) {
                    canMove = false;
                    break;
                }
            }
            if (canMove) {
                x++;
                didMove = true;
            } else {
                break;
            }
        }
        return didMove;
    }

    @Override
    public String toString() {
        return "Rock{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public boolean equals(Object o) {
        if (o instanceof Rock rock) {
            return rock.x == x && rock.y == y && rock.c == c;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(x, y, c);
    }

}


