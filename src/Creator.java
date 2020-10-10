import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import static java.lang.Integer.min;


public class Creator {
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Column> columns = new ArrayList<>();
    ArrayList<Heuristic> heuristics = new ArrayList<>();
    ArrayList<Comparator> comparators = new ArrayList<>();
    int fails = 0;
    boolean success = false;
    Random rd = new Random();

    Creator() {
        heuristics.add(new BestShortSide());
        heuristics.add(new BestArea());
        heuristics.add(new BestLongSide());
        comparators.add(new AreaSmallToLargeComparator());
        comparators.add(new AreaLargeToSmallComparator());
        comparators.add(new ShortSide());


    }

    public void addItem(int w, int h, int id) {
        items.add(new Item(w, h, 0, 0, false, id));
    }

    public void addColumn(int x, int y) {
        columns.add(new Column(x, y));
    }

    public MaxRectangle createNewBox(int w, int h) {
        MaxRectangle mr = new MaxRectangle(w, h, heuristics.get(0));
        return mr;
    }

    public void insertItems(MaxRectangle mr) {
            for (Item item : items) {
                try {
                    mr.insert(item, heuristics.get(rd.nextInt(3)));
                } catch (ArrayIndexOutOfBoundsException e) {
                    fails++;
                    //System.out.println("Nem sikerült");
                    if (fails <= 10 && !success) {
                        sort();
                        mr.clear();
                        insertItems(mr);
                        break;
                    }
                }
            }

    }

    public void insertColumns(MaxRectangle mr) {
        for (Column column : columns) {
            mr.insertColumn(column);
        }
    }

    public void sort() {
        Collections.sort(items, comparators.get(fails % (comparators.size())));
        for (Item item : items) {
            if (rd.nextBoolean()) {
                item.rotate();
            }
        }
    }
}

class AreaSmallToLargeComparator implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        return (o1.getArea() >= o2.getArea()) ? 1 : -1;
    }
}

class AreaLargeToSmallComparator implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        return (o1.getArea() < o2.getArea()) ? 1 : -1;
    }
}

class ShortSide implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        return (min(o1.getY(), o1.getX()) < min(o2.getX(), o2.getY())) ? 1 : -1;
    }
}
