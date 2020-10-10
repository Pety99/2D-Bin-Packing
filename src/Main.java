import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Parser parser = new Parser();
        Creator creator = new Creator();
        MaxRectangle mr = parser.parse(creator);
        creator.insertColumns(mr);
        creator.insertItems(mr);
/*
        MaxRectangle mr = new MaxRectangle(7, 5, new BestShortSide());
        mr.insert(new Item(2, 5, 0, 0, true,1));
        mr.insert(new Item(4, 2, 0, 0, true, 2));
        mr.insert(new Item(3, 2, 0, 0, true, 3));
        mr.insert(new Item(2, 2, 0, 0, true, 4));
        mr.insert(new Item(1, 2, 0, 0, true, 5));
        mr.insert(new Item(3, 1, 0, 0, true, 6));
        mr.insert(new Item(2, 1, 0, 0, true, 7));

*/
        /*
        ArrayList<Item> list = mr.getItems();
        System.out.println("Items:");
        for (Item i:list) {
            System.out.println("X:" + i.getX() + " Y:" + i.getY() + " W:" + i.getWidth() +
                    " H:" + i.getHeight());
        }
        System.out.println("\nFree Rectangles:");

        ArrayList<FreeRectangle> rects = mr.getEmptyRects();
        for( FreeRectangle fr: rects){
            System.out.println("X:" + fr.getX() + " Y:" + fr.getY() + " W:" + fr.getWidth() +
                    " H:" + fr.getHeight());
        }
        */

        System.out.println(mr.getResult());

    }

}
