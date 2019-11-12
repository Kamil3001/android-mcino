package com.example.home.ui.faq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.home.R;

import java.util.List;


/*
Implemented following this example: https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
 */
public class FaqAdapter extends ArrayAdapter<QA> {

    private List<QA> faq;
    Context mContext;

    private static class ViewHolder {
        TextView question;
        TextView answer;
    }

    public FaqAdapter(List<QA> faq, Context context) {
        super(context, R.layout.faq_row_item, faq);
        this.faq = faq;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        QA qa = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.faq_row_item, parent, false);
            viewHolder.question = convertView.findViewById(R.id.question);
            viewHolder.answer = convertView.findViewById(R.id.answer);

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.question.setText(qa.question);
        viewHolder.answer.setText(qa.answer);

        return convertView;
    }
}

class QA {
    String question;
    String answer;

    QA(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}