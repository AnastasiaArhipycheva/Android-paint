package anastasia.draw.Model;

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

public class ClientImpl implements Client {

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

    public void modelForClient(MyAbstractModel model) {
        this.model = model;
        api = new APIforJSON(model);
        sendList(model);
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        ClientImpl.in = in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        ClientImpl.out = out;
    }

    public void createSocket() {
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


}
