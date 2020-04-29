package com.techware.clickkart.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.techware.clickkart.R;
import com.techware.clickkart.widgets.CustomTextView;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public final class WalkThroughFragment extends Fragment {
    String text, descrptionWalk;
    int images;
    CustomTextView title, description;
    ImageView imageView;

    @SuppressLint("ValidFragment")
    public WalkThroughFragment(String text, String description, int image, Context context) {
        // Required empty public constructor
        this.text = text;
        this.descrptionWalk = description;
        this.images = image;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        title = view.findViewById(R.id.txt_walkThroughTitle);
        imageView = view.findViewById(R.id.walk_img);
        description = view.findViewById(R.id.txt_walkThroughDescription);
        description.setText(descrptionWalk);
        title.setText(text);
        imageView.setImageDrawable(getResources().getDrawable(images));
        return view;

    }

}
