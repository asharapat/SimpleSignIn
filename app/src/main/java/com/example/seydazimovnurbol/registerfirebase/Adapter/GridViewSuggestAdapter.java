package com.example.seydazimovnurbol.registerfirebase.Adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.seydazimovnurbol.registerfirebase.Common.Common;
import com.example.seydazimovnurbol.registerfirebase.MainActivity;
import com.example.seydazimovnurbol.registerfirebase.PlayActivity;

import java.util.List;

/**
 * Created by seydazimovnurbol on 11/26/17.
 */

public class GridViewSuggestAdapter extends BaseAdapter {

    private List<String> suggestSource;
    private Context context;
    private PlayActivity playActivity;

    public GridViewSuggestAdapter(List<String> suggestSource, Context context, PlayActivity playActivity) {
        this.suggestSource = suggestSource;
        this.context = context;
        this.playActivity = playActivity;
    }

    @Override
    public int getCount() {
        return suggestSource.size();
    }

    @Override
    public Object getItem(int position) {
        return suggestSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Button button;
        if(convertView == null){
            if(suggestSource.get(position).equals("null")) {
                button = new Button(context);
                button.setLayoutParams(new GridView.LayoutParams(85, 85));
                button.setPadding(8, 8, 8, 8);
                button.setBackgroundColor(Color.DKGRAY);
            }else{
                button = new Button(context);
                button.setLayoutParams(new GridView.LayoutParams(85, 85));
                button.setPadding(8, 8, 8, 8);
                button.setBackgroundColor(Color.DKGRAY);
                button.setTextColor(Color.YELLOW);
                button.setText(suggestSource.get(position));
                button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        // if user select character correctly
                        if(String.valueOf(playActivity.answer).contains(suggestSource.get(position))){
                            char compare = suggestSource.get(position).charAt(0); // get char
                            for(int i = 0; i<playActivity.answer.length; i++){
                                if(compare == playActivity.answer[i]){
                                    Common.user_submit_answer[i] = compare;
                                }
                            }
                            // upgrade ui
                            GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(Common.user_submit_answer,context);
                            playActivity.gridViewAnswer.setAdapter(answerAdapter);
                            answerAdapter.notifyDataSetChanged();

                            // Remove from suggest source
                            playActivity.suggestSource.set(position,"null");
                            playActivity.suggestAdapter = new GridViewSuggestAdapter(playActivity.suggestSource,context,playActivity);
                            playActivity.gridViewSuggest.setAdapter(playActivity.suggestAdapter);
                            playActivity.suggestAdapter.notifyDataSetChanged();
                        }else{
                            // Remove from suggest source
                            playActivity.suggestSource.set(position,"null");
                            playActivity.suggestAdapter = new GridViewSuggestAdapter(playActivity.suggestSource,context,playActivity);
                            playActivity.gridViewSuggest.setAdapter(playActivity.suggestAdapter);
                            playActivity.suggestAdapter.notifyDataSetChanged();
                        }
                    }
                });

            }
        }else{
            button = (Button)convertView;
        }
        return button;
    }
}
