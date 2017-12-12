package anastasia.draw.Model;

import java.util.ArrayList;

/**
 * Created by Администратор on 10.12.2017.
 */

public interface Interactor {

    public void sendModel(MyAbstractModel model);
    public void update();

    public void setInteractorListener(InteractorListener interactorListener);

    public interface InteractorListener {
        public void receivePoint(int x,int y,int color);
        public void receiveLine(int x1, int x2, int y1, int y2, int color);
        public void receiveCircle(int x1, int x2, int y1, int y2, int color);
        public void receiveRect(int x1, int x2, int y1, int y2, int color);
        public void receiveClear();
    }

}
