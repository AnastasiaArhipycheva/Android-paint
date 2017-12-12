package anastasia.draw.Presenter;

import anastasia.draw.Model.Client;
import anastasia.draw.Model.ClientImpl;
import anastasia.draw.Model.Interactor;
import anastasia.draw.Model.InteractorImpl;
import anastasia.draw.Model.Model;
import anastasia.draw.Model.MyAbstractModel;
import anastasia.draw.View.PaintView;

/**
 * Created by Администратор on 10.12.2017.
 */

public class PresenterImpl implements Presenter, Interactor.InteractorListener{

    private PaintView paintView;
    private MyAbstractModel model;
    private Client client;
    private boolean socketOn = true;

    public PresenterImpl(PaintView paintView) {
        if (socketOn) {
            client = new ClientImpl();
            client.createSocket();

        }
        this.paintView = paintView;
        this.interactor = new InteractorImpl(client);

        interactor.setInteractorListener(this);

        this.socketOn = false;
    }

    private Interactor interactor;

    @Override
    public void onDrawPoint(int x, int y, int color) {
        this.model = new Model("Point",x,y,color);
        interactor.sendModel(this.model);
    }

    @Override
    public void onDrawLine(int x1, int x2, int y1, int y2, int color) {
        this.model = new Model("Line",x1, x2, y1, y2, color);
        interactor.sendModel(this.model);
    }

    @Override
    public void onDrawCircle(int x1, int x2, int y1, int y2, int color) {
        this.model = new Model("Ellipse",x1, x2, y1, y2, color);
        interactor.sendModel(this.model);
    }

    @Override
    public void onDrawRect(int x1, int x2, int y1, int y2, int color) {
        this.model = new Model("Rectangle",x1, x2, y1, y2, color);
        interactor.sendModel(this.model);
    }

    @Override
    public void clearDraw() {
        this.model = new Model("Clear");
        interactor.sendModel(this.model);
    }

    @Override
    public void updateDraw() {
        interactor.update();
    }

    @Override
    public void receivePoint(int x, int y, int color) {
        paintView.drawNewPoint(x, y, color);
    }

    @Override
    public void receiveLine(int x1, int x2, int y1, int y2, int color) {
        paintView.drawNewLine(x1, x2, y1, y2, color);
    }

    @Override
    public void receiveCircle(int x1, int x2, int y1, int y2, int color) {
        paintView.drawNewCircle(x1, x2, y1, y2, color);
    }

    @Override
    public void receiveRect(int x1, int x2, int y1, int y2, int color) {
        paintView.drawNewRect(x1, x2, y1, y2, color);;
    }

    @Override
    public void receiveClear() {
        paintView.drawClear();
    }
}
