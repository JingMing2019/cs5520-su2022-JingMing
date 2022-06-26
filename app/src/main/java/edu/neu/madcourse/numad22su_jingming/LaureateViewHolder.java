package edu.neu.madcourse.numad22su_jingming;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LaureateViewHolder extends RecyclerView.ViewHolder {
    private final TextView laureateIDTV;
    private final TextView laureateFullNameTV;
    private final TextView laureatePrizeTV;
    private final TextView laureateYearTV;
    RelativeLayout layout;

    public LaureateViewHolder(@NonNull View itemView) {
        super(itemView);

        this.laureateIDTV = itemView.findViewById(R.id.laureateIDTV);
        this.laureateFullNameTV = itemView.findViewById(R.id.laureateFullNameTV);
        this.laureatePrizeTV = itemView.findViewById(R.id.laureatePrizeTV);
        this.laureateYearTV = itemView.findViewById(R.id.laureateYearTV);
        this.layout = itemView.findViewById((R.id.relativeLayout));
    }

    public void bindThisData(Laureate LaureateToBind){
        laureateIDTV.setText(LaureateToBind.getId());
        laureateFullNameTV.setText(LaureateToBind.getFullName());
        laureatePrizeTV.setText(LaureateToBind.getPrize());
        laureateYearTV.setText(LaureateToBind.getYear());
    }
}