package anastasia.draw.Model;

import org.json.JSONException;

/**
 * Created by Администратор on 10.12.2017.
 */

public class InteractorImpl implements Interactor, Client.PartnerListener {

    @Override
    public void setInteractorListener(InteractorListener interactorListener) {
        this.interactorListener = interactorListener;
    }

    InteractorListener interactorListener;

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
        if (interactorListener != null)
            interactorListener.receivePoint(x, y, color);
    }
    @Override
    public void receiveLine(int x1, int x2, int y1, int y2, int color) {
        if (interactorListener != null)
            interactorListener.receiveLine(x1, x2, y1, y2, color);
    }
    @Override
    public void receiveCircle(int x1, int x2, int y1, int y2, int color) {
        if (interactorListener != null)
            interactorListener.receiveCircle(x1, x2, y1, y2, color);
    }
    @Override
    public void receiveRect(int x1, int x2, int y1, int y2, int color) {
        if (interactorListener != null)
            interactorListener.receiveRect(x1, x2, y1, y2, color);
    }
    @Override
    public void receiveClear() {
        if (interactorListener != null)
            interactorListener.receiveClear();
    }



}
