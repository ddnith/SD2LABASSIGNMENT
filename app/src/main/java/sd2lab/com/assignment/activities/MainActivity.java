package sd2lab.com.assignment.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;

import sd2lab.com.assignment.AppController;
import sd2lab.com.assignment.Constants.Constants;
import sd2lab.com.assignment.Constants.UrlConstants;
import sd2lab.com.assignment.R;
import sd2lab.com.assignment.adapters.MainListAdapter;
import sd2lab.com.assignment.models.ResultModel;
import sd2lab.com.assignment.models.UserModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mList;
    private LinearLayoutManager mLayoutManager;
    private MainListAdapter mListAdapter;
    ArrayList<UserModel> userModelArrayList = new ArrayList<>();
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean loading = true;

    int lastOffSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (RecyclerView) findViewById(R.id.mainList);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mLayoutManager = (LinearLayoutManager) mList.getLayoutManager();
        mListAdapter = new MainListAdapter(this, userModelArrayList);
        mList.setHasFixedSize(false);
        mList.setAdapter(mListAdapter);


        loadMainList();
        setLoadMoreListener();

    }

    private void loadMainList() {
        String url = UrlConstants.URL_SAMPLE;
        url = url.replace("<offset>", Constants.OFFSET);
        url = url.replace("<limit>",Constants.LIMIT);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ResultModel result = gson.fromJson(response, ResultModel.class);
                if(result != null && result.getDataModel()!=null && result.getDataModel().getUserList()!=null)
                    mListAdapter.updateUserList(result.getDataModel().getUserList());
                findViewById(R.id.initialProgressBar).setVisibility(View.GONE);
                loading = false;
                lastOffSet = Integer.parseInt(Constants.OFFSET)+ Integer.parseInt(Constants.LIMIT);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading = false;
                Log.d("DEEPAK","Error Check");
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void setLoadMoreListener() {
        mList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if (!loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        Toast.makeText(MainActivity.this,"Loading Next Page ...",Toast.LENGTH_LONG).show();
                        loadMoreContent();
                    }
                }
            }
        });
    }

    private void loadMoreContent() {
        loading = true;
        String url = UrlConstants.URL_SAMPLE;
        url = url.replace("<offset>", String.valueOf(lastOffSet));
        url = url.replace("<limit>",Constants.LIMIT);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ResultModel result = gson.fromJson(response, ResultModel.class);
                if(result != null && result.getDataModel()!=null && result.getDataModel().getUserList()!=null)
                   mListAdapter.updateUserList(result.getDataModel().getUserList());
                loading = false;
                lastOffSet = lastOffSet + Integer.parseInt(Constants.LIMIT);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("DEEPAK","Error Check");
                loading = false;
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
