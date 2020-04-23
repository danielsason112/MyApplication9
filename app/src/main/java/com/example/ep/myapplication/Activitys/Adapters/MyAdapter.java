package com.example.ep.myapplication.Activitys.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.example.ep.myapplication.Activitys.Activitys.MainActivity;
import com.example.ep.myapplication.Activitys.Model.State;
import com.example.ep.myapplication.R;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<State> mDataset;
    private Activity activity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout linearLayout;
        public MyViewHolder(LinearLayout layout) {
            super(layout);
            linearLayout = layout;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Activity activity, List<State> myDataset) {
        mDataset = myDataset;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout l = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rowlayout, parent, false);

        MyViewHolder vh = new MyViewHolder(l);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        State s = mDataset.get(position);

        TextView tv = (TextView) holder.linearLayout.findViewById(R.id.textView1);
        tv.setText(s.getName());

        tv = (TextView) holder.linearLayout.findViewById(R.id.textView2);
        tv.setText(s.getNativeName());

        ImageView imageView = (ImageView) holder.linearLayout.findViewById(R.id.image_flag);

        if (s.getFlag() != null) {
            SvgLoader.pluck()
                    .with(activity)
                    .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                    .load(s.getFlag(), imageView);

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(activity, R.anim.shake);
                    view.startAnimation(hyperspaceJumpAnimation);

                    State s = mDataset.get(position);
                    MainActivity ma =(MainActivity) activity;
                    ma.LoadSecFragment(s);
                }
            });
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public ArrayList<State> costumeFilter(ArrayList<State> input, String word) // for search edit text - filter function
    {
        ArrayList<State> arr = new ArrayList<State>();

        for(State s : input)
        {
            if(s.getName().toLowerCase().contains(word) || s.getNativeName().toLowerCase().contains(word))
            {
                arr.add(s);
            }
        }
        return arr;
    }

}
