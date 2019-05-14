package com.example.andriod.miwok;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColor;





    WordAdapter(Activity context, ArrayList<Word> numberList, int color){

        super(context, 0, numberList);

        this.mColor = color;

    }





    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }




        Word currentWord = getItem(position);

        assert listItemView != null;
        TextView miwokTextView = listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord != null ? currentWord.getMiwokTranslation() : null);

        TextView defaultTextView = listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord != null ? currentWord.getDefaultTranslation() : null);

        ImageView imageView = listItemView.findViewById(R.id.image_view);

        assert currentWord != null;
        if (currentWord.hasImage()){
            imageView.setImageResource(currentWord.getmImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }

        else {
            imageView.setVisibility(View.GONE);
        }


        LinearLayout layout = listItemView.findViewById(R.id.text_area);
        layout.setBackgroundResource(mColor);









        return listItemView;
    }
}
