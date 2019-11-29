package com.example.home.ui.stats;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.home.MainActivity;
import com.example.home.R;
import com.example.home.utility.sql.DBAssetHelper;

public class StatsFragment extends Fragment implements View.OnTouchListener {

    private TableRow row;
    private ImageView imageView;
    private DBAssetHelper dbAssetHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAssetHelper = MainActivity.dbAssetHelper;
        View root = inflater.inflate(R.layout.stats_fragment, container, false);
        ImageView imageView = root.findViewById(R.id.coloured_map);
        imageView.setOnTouchListener(this);

        row = root.findViewById(R.id.countyStats);

        MainActivity main = (MainActivity) getActivity();
        main.getSupportActionBar().setTitle(R.string.title_stats);
        main.uncheckNav();

        return root;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        imageView = v.findViewById(R.id.coloured_map);
        boolean handled = false;
        float[] eventXY = new float[] {event.getX() , event.getY()};

        //Inverting the coordinates with a Matrix
        Matrix invert = new Matrix();
        imageView.getImageMatrix().invert(invert);
        invert.mapPoints(eventXY);

        //Only select when tapped
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            setCountyStatistics((int) eventXY[0], (int) eventXY[1]);
            handled = true;
        }
        return handled;
    }

    private void setCountyStatistics(int x, int y) {
        if (imageView != null ) {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

            int bw = bitmap.getWidth();
            int bh = bitmap.getHeight();

            //the bitmap scales with height so we need to modify x to get the correct coordinates on the bitmap
            x = (int) (x * (bh / (imageView.getHeight() * 1.0)));
            y = (int) (y * (bh / (imageView.getHeight() * 1.0)));

            //if coordinates are within the bitmap we get the colour of the pixel at those coordinates and get the statistics up
            if(0 <= x && x < bw && 0 <= y && y < bh) {
                int pixelValue = bitmap.getPixel(x, y);
                String hexColor = String.format("#%06X", (0xFFFFFF & pixelValue));
                setStatistics(hexColor.substring(1).toLowerCase());
            }
        }
    }

    private void setStatistics(String hexColour){
        Cursor c = dbAssetHelper.getCountyFigures(dbAssetHelper.getCountyByColour(hexColour));
        if(c.getCount()>0) {
            String[] data = new String[] {
                    c.getString(c.getColumnIndex("County")),
                    c.getString(c.getColumnIndex("June_19")),
                    c.getString(c.getColumnIndex("July_19")),
                    c.getString(c.getColumnIndex("August_19"))
            };

            TextView t;
            for(int i=0; i<4; i++) {
                t = (TextView) row.getVirtualChildAt(i);
                t.setText(data[i]);
            }
        }
    }
}
