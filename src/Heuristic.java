import java.util.ArrayList;

public interface Heuristic {
    public void printName();
    public ArrayList<Integer> score(FreeRectangle rect, Item item);
}
