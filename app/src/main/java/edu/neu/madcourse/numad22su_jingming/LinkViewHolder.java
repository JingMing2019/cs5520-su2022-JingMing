package edu.neu.madcourse.numad22su_jingming;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinkViewHolder extends RecyclerView.ViewHolder {
    private TextView linkNameTV;
    private TextView linkURLTV;

    public LinkViewHolder(@NonNull View itemView) {
        super(itemView);

        this.linkNameTV = itemView.findViewById(R.id.linkName);
        this.linkURLTV = itemView.findViewById(R.id.linkURL);
    }

    public void bindThisData(Link linkToBind){
        linkNameTV.setText(linkToBind.getName());
        linkURLTV.setText(linkToBind.getUrl());
    }
}
