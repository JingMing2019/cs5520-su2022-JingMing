package edu.neu.madcourse.numad22su_jingming;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * An implementation of the RecyclerView ViewHolder that is created specifically with respect to the
 * item_link.xml file. The aim of this class is to provide each item in the recyclerview to the
 * adapter, another important purpose of this class is to expose the TextViews in the xml file as
 * java objects for binding the data.
 */
public class LinkViewHolder extends RecyclerView.ViewHolder {
    private final TextView linkNameTV;
    private final TextView linkURLTV;

    public LinkViewHolder(@NonNull View itemView) {
        super(itemView);

        this.linkNameTV = itemView.findViewById(R.id.linkName);
        this.linkURLTV = itemView.findViewById(R.id.linkURL);
    }

    public void bindThisData(Link linkToBind){
        // set the linkName TextView of the ViewHolder as the name of the `linkToBind` link
        linkNameTV.setText(linkToBind.getName());
        // set the linkURL TextView of the ViewHolder as the URL of the `linkToBind` link
        linkURLTV.setText(linkToBind.getUrl());
    }
}
