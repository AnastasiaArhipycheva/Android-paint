package anastasia.draw.Presenter;

/**
 * Created by Администратор on 10.12.2017.
 */

public interface Presenter {
    void onDrawPoint(int x, int y, int color);
    void onDrawLine(int x1, int x2, int y1, int y2, int color);
    void onDrawCircle(int x1, int x2, int y1, int y2, int color);
    void onDrawRect(int x1, int x2, int y1, int y2, int color);
    void clearDraw();
    void updateDraw();

}
