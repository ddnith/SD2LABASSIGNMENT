package sd2lab.com.assignment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sd2lab.com.assignment.R;

/**
 * Created by deepak on 23/7/16.
 */
public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ItemViewHolder> {

    private final Context mContext;
    ArrayList<String> urlList;
    int mScreenWidth;
    boolean mIsEven;

    public ChildListAdapter(Context context,ArrayList<String> urlList) {
        this.urlList = urlList;
        mContext = context;
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        mIsEven = urlList.size()%2 ==0;

        mScreenWidth = metrics.widthPixels;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chld_list_row, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        if(!mIsEven && position ==0){
            holder.imageView2.setVisibility(View.GONE);

            //holder.imageView1.setMinimumHeight(mScreenWidth);
            holder.imageView1.getLayoutParams().height = mScreenWidth;
            holder.imageView1.getLayoutParams().width = mScreenWidth;

            Picasso.with(mContext)
                    .load(urlList.get(0))
                    .placeholder(mContext.getResources().getDrawable(R.drawable.downloading))
                    .into(holder.imageView1);


        }else {
            int oddFactor = 0;
            if(!mIsEven)
                oddFactor = 1;

            holder.imageView2.setVisibility(View.VISIBLE);
            holder.imageView1.getLayoutParams().height = mScreenWidth/2;
            holder.imageView2.getLayoutParams().height = mScreenWidth/2;


            Picasso.with(mContext)
                    .load(urlList.get(2 * position - oddFactor))
                    .placeholder(mContext.getResources().getDrawable(R.drawable.downloading))
                    .into(holder.imageView1);



            Picasso.with(mContext)
                    .load(urlList.get(2 * position + 1 - oddFactor))
                    .placeholder(mContext.getResources().getDrawable(R.drawable.downloading))
                    .into(holder.imageView2);
        }
    }

    @Override
    public int getItemCount() {
        return (urlList.size()+1)/2;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView1,imageView2;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView1 = (ImageView) itemView.findViewById(R.id.imageView1);
            imageView2 = (ImageView) itemView.findViewById(R.id.imageView2);
        }
    }
}
