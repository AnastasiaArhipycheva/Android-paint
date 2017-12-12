package anastasia.draw.Model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Администратор on 09.12.2017.
 */

public class Client {

    private MyAbstractModel model;
    private APIforJSON api;

    public void sendUpdate() throws JSONException {
        for (int i = 0; i < jsonOut.size(); i++)
            receiveJson(jsonOut.get(i));
    }

    public static ArrayList<String> jsonOut = new ArrayList<String>();


    private static final String LINE = "Line", ELLIPSE = "Ellipse", RECTANGLE = "Rectangle",
            CLEAR = "Clear", POINT = "Point";
    private static final int PORT = 2551;
    private static PrintWriter out = null;
    private static BufferedReader in = null;
    private static Socket socket;

    public void setPartnerListener(PartnerListener partnerListener) {
        this.partnerListener = partnerListener;
    }

    private PartnerListener partnerListener;


//    public static final Client client = new Client();

    public void modelForClient(MyAbstractModel model) {
        this.model = model;
        api = new APIforJSON(model);
        sendList(model);
    }

    public static BufferedReader getIn() {
        return in;
    }

    public static void setIn(BufferedReader in) {
        Client.in = in;
    }

    public static PrintWriter getOut() {
        return out;
    }

    public static void setOut(PrintWriter out) {
        Client.out = out;
    }

    public static void createSocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.1.197", PORT);   //ip телефона 192.168.43.64 //ip дом 192.168.1.197
                    setOut(new PrintWriter(socket.getOutputStream(), true));
                    setIn(new BufferedReader(new InputStreamReader(socket.getInputStream())));

                    while (true)
                    {
                        String line = in.readLine();
                        System.out.println(line);
                        jsonOut.add(line);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void sendList(MyAbstractModel model) {
        String msg = api.convertList(model);
        PrintWriter out = getOut();
        out.println(msg);
        setOut(out);
    }

    public void receiveJson(String json) {
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                model = api.convertJSON(jsonObject);
                if (model.getType().equals(CLEAR)) {
                    partnerListener.receiveClear();
                }
                if ((model.getType().equals(POINT)) && (partnerListener != null))
                    partnerListener.receivePoint((int) model.getX1(), (int) model.getY1(), (int) model.getColor());
                if (model.getType().equals(LINE))
                    partnerListener.receiveLine((int) model.getX1(), (int) model.getY1(),
                            (int) model.getX2(), (int) model.getY2(), (int) model.getColor());
                if (model.getType().equals(ELLIPSE))
                    partnerListener.receiveCircle((int) model.getX1(), (int) model.getY1(),
                            (int) model.getX2(), (int) model.getY2(), (int) model.getColor());
                if (model.getType().equals(RECTANGLE))
                    partnerListener.receiveRect((int) model.getX1(), (int) model.getY1(),
                            (int) model.getX2(), (int) model.getY2(), (int) model.getColor());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public interface PartnerListener {
        public void receivePoint(int x,int y,int color);
        public void receiveLine(int x1, int x2, int y1, int y2, int color);
        public void receiveCircle(int x1, int x2, int y1, int y2, int color);
        public void receiveRect(int x1, int x2, int y1, int y2, int color);
        public void receiveClear();
    }

}
