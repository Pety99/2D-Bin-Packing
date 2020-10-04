import java.util.ArrayList;

import static java.lang.Integer.min;

public class BestArea implements Heuristic {
    public void BestArea() {

    }

    public ArrayList<Integer> score(FreeRectangle rect, Item item){
        ArrayList<Integer> ret = new ArrayList<>();
        ret.add(rect.getArea()-item.getArea());
        ret.add(min(rect.getWidth()-item.getWidth(), rect.getHeight()-item.getHeight()));
        return ret;
    }
}
