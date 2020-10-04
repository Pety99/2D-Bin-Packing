import java.util.ArrayList;

public interface Heuristic {
    public ArrayList<Integer> score(FreeRectangle rect, Item item);
}
