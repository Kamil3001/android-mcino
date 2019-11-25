package com.example.home.ui.stats;

import android.content.res.Resources;
import android.database.Cursor;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;

import com.example.home.MainActivity;
import com.example.home.R;
import com.example.home.utility.sql.DBAssetHelper;

public class StatsFragment extends Fragment implements View.OnTouchListener {

    private StatsViewModel statsViewModel;
    private ImageView imageView;
    private TextView statsText;
    private DBAssetHelper dbAssetHelper;
    private int deviceOffsetY = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAssetHelper = MainActivity.dbAssetHelper;
        statsViewModel =
                ViewModelProviders.of(this).get(StatsViewModel.class);
        View root = inflater.inflate(R.layout.stats_fragment, container, false);
        ImageView imageView = root.findViewById(R.id.coloured_map);
        Resources resources = getContext().getResources();
        int navBar = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int statusBar = resources.getIdentifier("status_bar_height", "dimen", "android");
        deviceOffsetY += statusBar>0 ? resources.getDimensionPixelSize(statusBar) : 0;
        deviceOffsetY += navBar>0 ? resources.getDimensionPixelSize(navBar) : 0;

        imageView.setOnTouchListener(this);


        MainActivity main = (MainActivity) getActivity();
        main.getSupportActionBar().setTitle(R.string.title_stats);
        main.uncheckNav();
        statsText = root.findViewById(R.id.stats_textView);

        return root;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        imageView = v.findViewById(R.id.coloured_map);
        boolean handled = false;


        final int xValue = (int) event.getX();
        final int yValue = (int) event.getY();

        final int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN) {
            setCountyStatistics(xValue, yValue);
            handled = true;
        }
        return handled;
    }

    private void setCountyStatistics(int x, int y) {
        if (imageView != null ) {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            int paddingLeft = imageView.getPaddingLeft();
            if(y < bitmap.getHeight() && y > deviceOffsetY) {
                int pixelValue = bitmap.getPixel((x+paddingLeft), (y-deviceOffsetY));
                String hexColor = String.format("#%06X", (0xFFFFFF & pixelValue));
                setStatistics(hexColor.substring(1).toLowerCase());
            }
        }
    }
    private void setStatistics(String hexColour){
        Cursor c = dbAssetHelper.getCountyFigures(dbAssetHelper.getCountyByColour(hexColour));
        if(c.getCount()>0) {
            StringBuilder str = new StringBuilder();
            statsText.setText(String.format("%20s%20s%20s%20s","County","June '19","July '19","August '19\n"));

            str.append(String.format("%20s", c.getString(c.getColumnIndex("County"))));
            str.append(String.format("%20s", c.getString(c.getColumnIndex("June_19"))));
            str.append(String.format("%20s", c.getString(c.getColumnIndex("July_19"))));
            str.append(String.format("%20s", c.getString(c.getColumnIndex("August_19"))));

            statsText.append(str);
        }
    }
}
