package anastasia.draw.View;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import java.util.UUID;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import anastasia.draw.Presenter.Presenter;
import anastasia.draw.Presenter.PresenterImpl;
import anastasia.draw.R;


@Fullscreen
@EActivity(R.layout.activity_main)

public class MainActivity extends Activity
        implements DrawingView.DrawListener, PaintView {


    Presenter presenter;

    @ViewById(R.id.save_btn)
    ImageButton saveBtn;

    @ViewById(R.id.drawing)
    DrawingView drawView;

    @ViewById(R.id.new_btn)
    ImageButton newBtn;

    @Click({R.id.save_btn, R.id.new_btn})
    void onClick(View view) {
        if (view.getId() == R.id.new_btn) {
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Новый рисунок");
            newDialog.setMessage("Начать новый рисунок(потерять старый)?");
            newDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    drawView.drawClear();
                    presenter.clearDraw();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            newDialog.show();

        } else if (view.getId() == R.id.save_btn) {
//            save drawing
            AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
            saveDialog.setTitle("Сохранить рисунок");
            saveDialog.setMessage("Сохранить рисунок в галерею устройства?");
            saveDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
//                    save drawing
                }
            });
            saveDialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            saveDialog.show();

            drawView.setDrawingCacheEnabled(true);

            String imgSaved = MediaStore.Images.Media.insertImage(
                    getContentResolver(), drawView.getDrawingCache(),
                    UUID.randomUUID().toString() + ".png", "drawing");
            if (imgSaved != null) {
                Toast savedToast = Toast.makeText(getApplicationContext(),
                        "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                savedToast.show();
            } else {
                Toast unsavedToast = Toast.makeText(getApplicationContext(),
                        "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                unsavedToast.show();
            }
            drawView.destroyDrawingCache();
        }
    }

    @ViewById(R.id.paint_colors)
    LinearLayout paintLayout;

    @Click(R.id.draw_btn)
    public void clickb(View view) {
        setcurrentAction(1);
        drawView.setcurrentAction(getCurrentAction());
    }

    @Click(R.id.line_btn)
    public void clickl(View view) {
        setcurrentAction(2);
        drawView.setcurrentAction(getCurrentAction());
    }

    @Click(R.id.circle_btn)
    public void clickc(View view) {
        setcurrentAction(3);
        drawView.setcurrentAction(getCurrentAction());
    }

    @Click(R.id.rect_btn)
    public void clickr(View view) {
        setcurrentAction(4);
        drawView.setcurrentAction(getCurrentAction());
    }

    @Click(R.id.update_btn)
    public void clickUpdate(View v) {
        presenter.updateDraw();
    }

    ImageButton currPaint;   //цвет краски

    @AfterViews
    void initPaint() {
        presenter  = new PresenterImpl(this);

        drawView.setDrawListener(this);
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
    }

    public void paintClicked(View view){
        if(view!=currPaint){
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint=(ImageButton)view;
        }

    }

    private int currentAction=1;

    public void setcurrentAction(int c) {
        currentAction = c;
    }

    public int getCurrentAction() {return currentAction;}

    @Override
    public void OnPointTouch(int touchX, int touchY, int paintColor) {
        presenter.onDrawPoint(touchX, touchY, paintColor);
    }

    @Override
    public void CircleDraw(int touchX1, int touchX2, int touchY1, int touchY2, int paintColor) {
        presenter.onDrawCircle(touchX1, touchX2, touchY1, touchY2, paintColor);
    }

    @Override
    public void LineDraw(int touchX1, int touchX2, int touchY1, int touchY2, int paintColor) {
        presenter.onDrawLine(touchX1, touchX2, touchY1, touchY2, paintColor);
    }

    @Override
    public void RectDraw(int touchX1, int touchX2, int touchY1, int touchY2, int paintColor) {
        presenter.onDrawRect(touchX1, touchX2, touchY1, touchY2, paintColor);
    }

    @UiThread
    @Override
    public void drawNewPoint(int x, int y, int color) {
        drawView.drawPoint(x, y ,color);
    }

    @UiThread
    @Override
    public void drawNewLine(int x1, int x2, int y1, int y2, int color) {
        drawView.drawLine(x1, x2, y1, y2, color);
    }

    @UiThread
    @Override
    public void drawNewCircle(int x1, int x2, int y1, int y2, int color) {
        drawView.drawCircle(x1, x2, y1, y2, color);
    }

    @UiThread
    @Override
    public void drawNewRect(int x1, int x2, int y1, int y2, int color) {
        drawView.drawRect(x1, x2, y1, y2, color);
    }
    @UiThread
    @Override
    public void drawClear() {
        drawView.drawClear();
    }
}



