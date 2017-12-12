package anastasia.draw.Model;

/**
 * Created by Администратор on 09.12.2017.
 */
import org.json.JSONException;
import org.json.JSONObject;

public class APIforJSON {

    private static final String
            CLEAR = "Clear", POINT = "Point";
    private MyAbstractModel model;

    public APIforJSON(MyAbstractModel model){
        this.model = model;
    }

    public String convertList(MyAbstractModel model) {
        try {
            JSONObject json = new JSONObject();
            if (!model.getType().equals(CLEAR)) {
                json.put("color", model.getColor());
                json.put("x1", model.getX1());
                json.put("y1", model.getY1());
                if (!model.getType().equals(POINT)) {
                    json.put("x2", model.getX2());
                    json.put("y2", model.getY2());
                }
            }
            json.put("type", model.getType());
            String msg = json.toString();
            return msg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "null";
    }

    public Model convertJSON (JSONObject json) {
        if (json.optString("type").equals(CLEAR)) {
            return new Model(CLEAR);
        } else {
            int x1 = (int) json.optDouble("x1");
            int y1 = (int) json.optDouble("y1");

            if (!json.optString("type").equals(POINT)) {
                int x2 = (int) json.optDouble("x2");
                int y2 = (int) json.optDouble("y2");
                int color = json.optInt("color");
                return new Model(json.optString("type"), x1, y1, x2, y2, color);
            }
            return new Model(POINT, x1, y1, json.optInt("color"));
        }
    }
}
