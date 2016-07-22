package sd2lab.com.assignment.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import sd2lab.com.assignment.AppController;
import sd2lab.com.assignment.R;
import sd2lab.com.assignment.Utils.CustomLinearLayoutManager;
import sd2lab.com.assignment.models.UserModel;

/**
 * Created by deepak on 22/7/16.
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ItemViewHolder> {

        private final Context mContext;
        private List<UserModel> userModelList;
    ChildListAdapter adapter;


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView userImageView;
        public TextView userNameTextView;
        public RecyclerView userImageList;

        public ItemViewHolder(View view) {
            super(view);
            userImageView = (ImageView) view.findViewById(R.id.userImage);
            userNameTextView = (TextView) view.findViewById(R.id.userName);

            userImageList = (RecyclerView) itemView.findViewById(R.id.userImageList);
        }
    }


    public MainListAdapter(Context context,List<UserModel> userModelList) {
        this.userModelList = userModelList;
        mContext = context;
    }

    public void updateUserList(List<UserModel> userModelList){
        this.userModelList.addAll(userModelList);
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        UserModel userModel = userModelList.get(position);

        String url = userModel.getUserImage();
        ArrayList<String> urlList = userModel.getItemImageList();
        String name = userModel.getUserName();

        Picasso.with(mContext)
                .load(url)
                .into(holder.userImageView);

        holder.userNameTextView.setText(name);

        RecyclerView.LayoutManager layoutManager = new CustomLinearLayoutManager(mContext);
        holder.userImageList.setHasFixedSize(false);
        holder.userImageList.setLayoutManager(layoutManager);

        adapter = new ChildListAdapter(mContext,urlList);
        holder.userImageList.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

}
