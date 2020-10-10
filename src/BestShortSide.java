import java.util.ArrayList;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class BestShortSide implements Heuristic {
    @Override
    public void printName() {
        System.out.println("BestShortSide");
    }
    public ArrayList<Integer> score(FreeRectangle rect, Item item){
        ArrayList<Integer> ret = new ArrayList<>();
        ret.add(min(rect.getWidth()-item.getWidth(), rect.getHeight()-item.getHeight()));
        ret.add(max(rect.getWidth()-item.getWidth(), rect.getHeight()-item.getHeight()));
        return ret;
    }
}
