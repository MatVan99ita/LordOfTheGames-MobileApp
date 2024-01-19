package prodotticondb;

import java.util.Objects;

public class Pair<X, Y>{
    private X x;
    private Y y;

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public void setX(X x) {
        this.x = x;
    }

    public Y getY() {
        return y;
    }

    public void setY(Y y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(getX(), pair.getX()) && Objects.equals(getY(), pair.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    public boolean isXNull(){
        return x == null;
    }

    public boolean isYNull(){
        return y == null;
    }

    public boolean isPairNull(){
        return isXNull() || isYNull();
    }
}
