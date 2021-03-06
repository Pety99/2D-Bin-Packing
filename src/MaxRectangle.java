import java.util.ArrayList;

import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.Math.abs;

class DEFAULT {
    static Heuristic Heuristic = new BestArea();
}

public class MaxRectangle {
    int width;
    int height;
    int freeArea;
    private ArrayList<FreeRectangle> emptyRects = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Column> columns = new ArrayList<>();

    private Heuristic heuristic;

    MaxRectangle(int w, int h, Heuristic heuristic) {
        this.width = w;
        this.height = h;
        this.freeArea = width * height;
        this.heuristic = heuristic;
        emptyRects.add(new FreeRectangle(w, h, 0, 0));
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<FreeRectangle> getEmptyRects() {
        return emptyRects;
    }

    /**
     * Returns true if an item fits in a rectangle
     *
     * @param item an Item
     * @param rect A FreeRectangle
     * @return ..
     */
    public boolean fitsInRect(Item item, FreeRectangle rect, boolean rotation) {
        if (!rotation) {
            if (item.getWidth() <= rect.getWidth() && item.getHeight() <= rect.getHeight()) {
                return true;
            }
        }
        if (rotation) {
            if (item.getHeight() <= rect.getWidth() && item.getWidth() <= rect.getHeight()) {
                return true;
            }
        }
        return false;
    }

    public boolean intersectsColumn(Item item, FreeRectangle rect, boolean rotation) {
        boolean ret;
        if (!rotation) {
            int x1 = rect.getX();
            int x2 = item.getWidth() + x1;
            int y1 = rect.getY();
            int y2 = item.getHeight() + y1;

            for (Column column : columns) {
                //System.out.println("Colulm\tX:" +column.getX() + "\tY:" + column.getY());
                //System.out.println("X1:" + x1 + "\tX2:" + x2 + "\tY1:" + y1 + "\tY2:" + y2);
                if (column.getX() > x1 && column.getX() < x2 && column.getY() > y1 && column.getY() < y2) {
                    ret = true;
                    //System.out.println("Intersect: " + ret);
                    return ret;
                }
            }
        }
        if (rotation) {
            int x1 = rect.getX();
            int x2 = item.getHeight() + x1;
            int y1 = rect.getY();
            int y2 = item.getWidth() + y1;

            for (Column column : columns) {
                //System.out.println("Colulm\tX:" +column.getX() + "\tY:" + column.getY());
                //System.out.println("X1:" + x1 + "\tX2:" + x2 + "\tY1:" + y1 + "\tY2:" + y2);
                if (column.getX() > x1 && column.getX() < x2 && column.getY() > y1 && column.getY() < y2) {
                    ret = true;
                    //System.out.println("Intersect: " + ret);
                    return ret;
                }
            }
        }
        ret = false;
        //System.out.println("Intersect: " + ret);
        return ret;
    }

    /**
     * Takes the Area of Fr \ I (Where Fr is the Free Rectangle and I is the Item).
     * Assuming that I touches a corner of Fr this will create a list of new maximal Rectangles.
     *
     * @param rect Fr
     * @param item I
     * @return A list of the new Maximal Rectangles
     */
    public ArrayList<FreeRectangle> splitRect(FreeRectangle rect, Item item) {
        ArrayList<FreeRectangle> ret = new ArrayList<>();
        int Rw, Rh, Rx, Ry;
        if (item.getWidth() < rect.getWidth()) {
            Rw = rect.getWidth() - item.getWidth();
            Rh = rect.getHeight();
            Rx = rect.getX() + item.getWidth();
            Ry = rect.getY();
            ret.add(new FreeRectangle(Rw, Rh, Rx, Ry));
        }

        if (item.getHeight() < rect.getHeight()) {
            Rw = rect.getWidth();
            Rh = rect.getHeight() - item.getHeight();
            Rx = rect.getX();
            Ry = rect.getY() + item.getHeight();
            ret.add(new FreeRectangle(Rw, Rh, Rx, Ry));
        }
        return ret;
    }

    /**
     * Checks if an Item intersects a Free Rectangle
     *
     * @param rect
     * @param item
     * @return Ture if it finds intersect, false otherwise
     */
    public boolean checkIntersect(FreeRectangle rect, Item item) {
        if (item.getX() + item.getWidth() <= rect.getX() ||
                item.getX() >= rect.getX() + rect.getWidth() ||
                item.getY() + item.getHeight() <= rect.getY() ||
                item.getY() >= rect.getY() + rect.getHeight()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param rect
     * @return An array list of Maximal Rectangles with the removed intersections.
     */
    public ArrayList<FreeRectangle> chopOverlap(FreeRectangle rect, FreeRectangle overlap) {
        ArrayList<FreeRectangle> ret = new ArrayList<>();

        int Rw = rect.getWidth();
        int Rh = rect.getHeight();
        int Rx = rect.getX();
        int Ry = rect.getY();

        int Ow = overlap.getWidth();
        int Oh = overlap.getHeight();
        int Ox = overlap.getX();
        int Oy = overlap.getY();

        //overlap is on the Right side
        if (Ox > Rx) {
            ret.add(new FreeRectangle(Ox - Rx, Rh, Rx, Ry));
        }
        //overlap is on the Top
        if (Oy > Ry) {
            ret.add(new FreeRectangle(Rw, Oy - Ry, Rx, Ry));
        }
        //overlap is on the Left side
        if (Ox + Ow < Rx + Rw) {
            ret.add(new FreeRectangle(Rx + Rw - (Ox + Ow), Rh, Ox + Ow, Ry));
        }
        //overlap is on the Bottom
        if (Oy + Oh < Ry + Rh) {
            ret.add(new FreeRectangle(Rw, Ry + Rh - (Oy + Oh), Rx, Oy + Oh));
        }

        return ret;
    }

    /**
     * Gets the overlapping part of two Rectangles
     *
     * @param R1
     * @param R2
     * @return the overlapping part
     */
    public FreeRectangle getOverlap(FreeRectangle R1, Item R2) {
        int X1 = max(R1.getX(), R2.getX());
        int Y1 = max(R1.getY(), R2.getY());
        int X2 = min(R1.getX() + R1.getWidth(), R2.getX() + R2.getWidth());
        int Y2 = min(R1.getY() + R1.getHeight(), R2.getY() + R2.getHeight());

        return new FreeRectangle(abs(X2 - X1), abs(Y2 - Y1), X1, Y1);
    }

    /**
     * Checks is R2 is fully included in R1
     *
     * @param R1
     * @param R2
     * @return Returns true if R2 is fully included in R2, false otherwise
     */
    public boolean included(FreeRectangle R1, FreeRectangle R2) {
        if (R2.getX() < R1.getX() || R2.getX() > R1.getX() + R1.getWidth())
            return false;
        if (R2.getY() < R1.getY() || R2.getY() > R1.getY() + R1.getHeight())
            return false;
        if (R2.getX() + R2.getWidth() > R1.getX() + R1.getWidth())
            return false;
        if (R2.getY() + R2.getHeight() > R1.getY() + R1.getHeight())
            return false;
        return true;
    }

    /**
     * Removes all the Rectangles included in another from emptyRects
     *
     * @return emptyRects
     */
    public ArrayList<FreeRectangle> removeAllIncluded() {
        int i = 0;
        while (i < emptyRects.size()) {
            int j = i + 1;
            while (j < emptyRects.size()) {
                if (included(emptyRects.get(j), emptyRects.get(i))) {
                    emptyRects.remove(i);
                    i++;
                    break;
                }
                if (included(emptyRects.get(i), emptyRects.get(j))) {
                    emptyRects.remove(j);
                    j--;
                }
                j++;
            }
            i++;
        }
        return this.emptyRects;
    }

    /**
     * Cut the overlapping items in emptyRects
     *
     * @param item
     */
    public void cutOverlaps(Item item) {
        ArrayList<FreeRectangle> ret = new ArrayList<>();
        for (FreeRectangle rect : emptyRects) {
            if (checkIntersect(rect, item)) {
                FreeRectangle overlap = getOverlap(rect, item);
                ArrayList<FreeRectangle> newRects = chopOverlap(rect, overlap);
                ret.addAll(newRects);
            } else {
                ret.add(rect);
            }
        }
        emptyRects = ret;
        removeAllIncluded();
    }

    /**
     * Finds the minimal score and returns the Info of it(Rect, Rotation, Score)
     *
     * @param item
     * @return
     */
    public Info minimalScore(Item item) throws ArrayIndexOutOfBoundsException {
        //heuristic.printName();
        ArrayList<Info> scores = new ArrayList<>();
        ArrayList<Integer> currentScore;
        for (FreeRectangle rect : emptyRects) {
            if (fitsInRect(item, rect, false)) {
                if (!intersectsColumn(item, rect, false)) {
                    currentScore = heuristic.score(rect, item);
                    scores.add(new Info(currentScore, rect, false));
                }
            }
            if (fitsInRect(item, rect, true)) {
                if (!intersectsColumn(item, rect, true)) {
                    currentScore = heuristic.score(rect, item);
                    scores.add(new Info(currentScore, rect, true));
                }
            }
        }

        ArrayList<Info> mins = new ArrayList<>();
        if (!scores.isEmpty()) {
            Info min = new Info(Integer.MAX_VALUE);
            mins.add(new Info(Integer.MAX_VALUE));
            for (Info info : scores) {
                if (info.getScore().get(0) == mins.get(mins.size() - 1).getScore().get(0)) {
                    mins.add(info);
                } else if (info.getScore().get(0) < mins.get(mins.size() - 1).getScore().get(0)) {
                    mins.clear();
                    mins.add(info);
                }
            }

            for (Info info : mins) {
                if (info.getScore().get(1) < min.getScore().get(1)) {
                    min = info;
                }
            }

            return min;
        } else {
            throw new ArrayIndexOutOfBoundsException("No items in the score list");
        }
    }

    public boolean insert(Item item, Heuristic heuristic) throws ArrayIndexOutOfBoundsException {
        this.heuristic = heuristic;
        Info minInfo = minimalScore(item);
        FreeRectangle bestRect = minInfo.getRect();
        boolean rotation = minInfo.getRotation();
        if (minInfo != null) {
            if (rotation) {
                item.rotate();
            }
            item.setX(bestRect.getX());
            item.setY(bestRect.getY());
            this.items.add(item);
            this.freeArea -= bestRect.getArea();
            ArrayList<FreeRectangle> maxRects = this.splitRect(bestRect, item);
            this.emptyRects.remove(bestRect);
            this.emptyRects.addAll(maxRects);
            this.cutOverlaps(item);
            return true;
        }
        return false;
    }

    public boolean insert(Item item) {
        return insert(item, DEFAULT.Heuristic);
    }

    public void insertColumn(Column column) {
        int y = column.getY();
        column.setY(this.height - y);
        this.columns.add(column);
    }

    public String getResult() {
        String resultString = "";
        for (int i = this.height - 1; i >= 0; i--) {
            for (int j = 0; j < this.width; j++) {
                String result = checkAllItems(j, i);
                //System.out.print(result);
                resultString += result;
                if (j < this.width - 1) {
                    //System.out.print("\t");
                    resultString += "\t";
                }
            }
            //System.out.println();
            resultString += "\n";
        }
        return resultString;
    }

    public String checkAllItems(int xj, int yi) {
        for (Item item : this.items) {
            int result = getItemID(item, xj, yi);
            if (result != -1) {
                return String.valueOf(result);
            }
        }
        return " ";
    }

    public int getItemID(Item item, int xj, int yi) {
        int x1 = item.getX();
        int x2 = x1 + item.getWidth();
        int y1 = item.getY();
        int y2 = y1 + item.getHeight();

        if (xj >= x1 && xj < x2) {
            if (yi >= y1 && yi < y2) {
                return item.getId();
            }
        }
        return -1;
    }

    public void clear() {
        emptyRects.clear();
        items.clear();
        freeArea = width * height;
        emptyRects.add(new FreeRectangle(width, height, 0, 0));
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }
}
