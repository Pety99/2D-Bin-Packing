import java.util.ArrayList;

public class Info {
    private ArrayList<Integer> score;
    private FreeRectangle rect;
    private boolean rotation;

    public Info(ArrayList<Integer> score, FreeRectangle rect, boolean rotation) {
        this.score = score;
        this.rect = rect;
        this.rotation = rotation;
    }
    public Info(Integer score){
        this.score = new ArrayList<>();
        this.score.add(score);
        this.score.add(score);

        this.rect = new FreeRectangle(0, 0, 0, 0);
        this.rotation= false;
    }

    public ArrayList<Integer> getScore() {
        return score;
    }

    public FreeRectangle getRect() {
        return rect;
    }

    public boolean getRotation() {
        return rotation;
    }
}
