package edu.neu.madcourse.numad22su_jingming;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LaureateAdapter extends RecyclerView.Adapter<LaureateViewHolder>{
    private final List<Laureate> laureates;

    /**
     * Creates a LaureateAdapter with the provided arraylist of Person objects.
     *
     * @param laureates    arraylist of Laureate object.
     */
    public LaureateAdapter(List<Laureate> laureates){
        this.laureates = laureates;
    }

    @NonNull
    @Override
    public LaureateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create an instance of the viewHolder by passing it the layout inflated as view and not
        // attach to parent
        return new LaureateViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_laureate, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LaureateViewHolder holder, int position) {
        // display the item in the given position
        holder.bindThisData(laureates.get(position));
    }

    @Override
    public int getItemCount() {
        // Returns the size of the recyclerView that is the list of the arraylist.
        return laureates != null ? laureates.size() : 0;
    }
}
