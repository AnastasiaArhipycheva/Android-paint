package anastasia.draw.Model;

import org.json.JSONException;

/**
 * Created by Администратор on 10.12.2017.
 */

public class InteractorImpl implements Interactor, Client.PartnerListener {

    public void setInteractorPointListener(InteractorPointListener interactorPointListener) {
        this.interactorPointListener = interactorPointListener;
    }
    public void setInteractorLineListener(InteractorLineListener interactorLineListener) {
        this.interactorLineListener = interactorLineListener;
    }
    public void setInteractorRectListener(InteractorRectListener interactorRectListener) {
        this.interactorRectListener = interactorRectListener;
    }
    public void setInteractorCircleListener(InteractorCircleListener interactorCircleListener) {
        this.interactorCircleListener = interactorCircleListener;
    }
    public void setInteractorClearListener(InteractorClearListener interactorClearListener) {
        this.interactorClearListener = interactorClearListener;
    }
    InteractorPointListener interactorPointListener;
    InteractorLineListener interactorLineListener;
    InteractorCircleListener interactorCircleListener;
    InteractorRectListener interactorRectListener;
    InteractorClearListener interactorClearListener;

    Client client;

    public InteractorImpl(Client client) {
        this.client = client;
        this.client.setPartnerListener(this);
        client.setPartnerListener(this);
    }

    @Override
    public void sendModel(MyAbstractModel model) {
        client.modelForClient(model);
    }

    @Override
    public void update() {
        try {
            client.sendUpdate();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // пинаю презентера
    @Override
    public void receivePoint(int x, int y, int color) {
        if (interactorPointListener != null)
            interactorPointListener.receivePoint(x, y, color);
    }
    @Override
    public void receiveLine(int x1, int x2, int y1, int y2, int color) {
        if (interactorLineListener != null)
            interactorLineListener.receiveLine(x1, x2, y1, y2, color);
    }
    @Override
    public void receiveCircle(int x1, int x2, int y1, int y2, int color) {
        if (interactorCircleListener != null)
            interactorCircleListener.receiveCircle(x1, x2, y1, y2, color);
    }
    @Override
    public void receiveRect(int x1, int x2, int y1, int y2, int color) {
        if (interactorRectListener != null)
            interactorRectListener.receiveRect(x1, x2, y1, y2, color);
    }
    @Override
    public void receiveClear() {
        if (interactorClearListener != null)
            interactorClearListener.receiveClear();
    }



}
