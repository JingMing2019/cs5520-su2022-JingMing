package edu.neu.madcourse.numad22su_jingming;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * This is a recyclerview adapter class, the purpose of this class is to act as a bridge between the
 * collection (arraylist) and the view (recyclerview). This class provides 3 methods that are
 * utilised for binding the data to the view.
 */
public class LinkAdapter extends RecyclerView.Adapter<LinkViewHolder>{
    private final List<Link> links;
    private final Context context;
    private final static String HTTP = "http://";
    private final static String HTTPS = "https://";
    private final static String WWW = "www.";

    /**
     * Creates a LinkAdapter with the provided arraylist of Person objects.
     *
     * @param links    arraylist of Link object.
     * @param context   context of the activity used for inflating layout of the viewHolder.
     */
    public LinkAdapter(List<Link> links, Context context){
        this.links = links;
        this.context = context;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create an instance of the viewHolder by passing it the layout inflated as view and not
        // attach to parent
        return new LinkViewHolder(LayoutInflater.from(context).
                inflate(R.layout.item_link, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        // display the item in the given position
        holder.bindThisData(links.get(position));

        // make item listen to click action
        holder.layout.setOnClickListener(v -> {
            // when item is clicked, toast shows which item is clicked
            Toast.makeText(context, links.get(position).getName() + " is clicked",
                    Toast.LENGTH_SHORT).show();
//            Log.v(links.get(position).getName(), "links_name_on_Click");

            String url = links.get(position).getUrl();
            // make sure url in the format of "http://www.example.com" or "https://www.example.com"
            if (!url.startsWith(WWW)) url = WWW + url;
            if(!url.startsWith(HTTP) && !url.startsWith(HTTPS)) url = HTTP + url;

            // open the url in a web browser
            try{
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(browserIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, "Activity not registered", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        // Returns the size of the recyclerView that is the list of the arraylist.
        return links.size();
    }
}
