import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    public MaxRectangle parse(Creator creator){
        ArrayList<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter(System.getProperty("line.separator"));
        while (scanner.hasNext()) {
            String line = scanner.next();
            lines.add(line);
        }
        int height = Character.getNumericValue(lines.get(0).charAt(0));
        int width =  Character.getNumericValue(lines.get(0).charAt(2));

        MaxRectangle mr = creator.createNewBox(width, height);

        int numOfCols = Integer.parseInt(lines.get(1).trim());
        int numOfItems = Integer.parseInt(lines.get(2).trim());

        for( int i = 3; i< 3 + numOfCols; i++){
            //System.out.println("Col: " + lines.get(i));
            int x = Character.getNumericValue(lines.get(i).charAt(2));
            int y = Character.getNumericValue(lines.get(i).charAt(0));
            //System.out.println("Col: " + lines.get(i));
            creator.addColumn(x,y);
        }
        for(int i = 3 + numOfCols; i< 3 + numOfCols + numOfItems; i++){

            int w = Character.getNumericValue(lines.get(i).charAt(0));
            int h = Character.getNumericValue(lines.get(i).charAt(2));
            //System.out.println("item width: " + w + " item height: " + h + "id: " + (+ i - 3 - numOfCols + 1));
            creator.addItem(w, h, i - 3 - numOfCols + 1);
            //System.out.println("Items: " + lines.get(i));
        }
        //System.out.println("W " + width);
        //System.out.println("H " + height);
        scanner.close();
        return mr;
    }
}
