package anastasia.draw.View;

/**
 * Created by Администратор on 10.12.2017.
 */

public interface PaintView {
    void drawUpdates();
    //  здесь долдны быть объвления методов для view

    void drawNewPoint(int x, int y, int color);
    void drawNewLine(int x1, int x2, int y1, int y2, int color);
    void drawNewCircle(int x1, int x2, int y1, int y2, int color);
    void drawNewRect(int x1, int x2, int y1, int y2, int color);
    void drawClear();
}
