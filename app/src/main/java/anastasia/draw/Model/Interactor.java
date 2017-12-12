package anastasia.draw.Model;

import java.util.ArrayList;

/**
 * Created by Администратор on 10.12.2017.
 */

public interface Interactor {

    public void sendModel(MyAbstractModel model);
    public void update();

    public void setInteractorPointListener(InteractorPointListener interactorPointListener);
    public void setInteractorLineListener(InteractorLineListener interactorLineListener);
    public void setInteractorCircleListener(InteractorCircleListener interactorCircleListener);
    public void setInteractorRectListener(InteractorRectListener interactorRectListener);
    public void setInteractorClearListener(InteractorClearListener interactorClearListener);


    public interface InteractorPointListener {
        public void receivePoint(int x,int y,int color);
    }
    public interface InteractorLineListener {
        public void receiveLine(int x1, int x2, int y1, int y2, int color);
    }
    public interface InteractorCircleListener {
        public void receiveCircle(int x1, int x2, int y1, int y2, int color);
    }
    public interface InteractorRectListener {
        public void receiveRect(int x1, int x2, int y1, int y2, int color);
    }
    public interface InteractorClearListener {
        public void receiveClear();
    }
}
