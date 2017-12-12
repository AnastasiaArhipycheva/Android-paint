package anastasia.draw.Model;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Created by Администратор on 13.12.2017.
 */

public interface Client {

    public void sendUpdate() throws JSONException;
    public void setPartnerListener(ClientImpl.PartnerListener partnerListener);
    public void modelForClient(MyAbstractModel model);
    public BufferedReader getIn();
    public void setIn(BufferedReader in);
    public PrintWriter getOut();
    public void setOut(PrintWriter out);
    public void createSocket();
    public void sendList(MyAbstractModel model);
    public void receiveJson(String json);

    public interface PartnerListener {
        public void receivePoint(int x,int y,int color);
        public void receiveLine(int x1, int x2, int y1, int y2, int color);
        public void receiveCircle(int x1, int x2, int y1, int y2, int color);
        public void receiveRect(int x1, int x2, int y1, int y2, int color);
        public void receiveClear();
    }

}
