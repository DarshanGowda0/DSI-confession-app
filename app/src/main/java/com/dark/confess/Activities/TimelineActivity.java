package com.dark.confess.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dark.confess.Utilities.Constants;
import com.dark.confess.Helpers.FireBaseHelper;
import com.dark.confess.Models.Post;
import com.dark.confess.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomAdapter customAdapter;

    FireBaseHelper fireBaseHelper;
    ArrayList<Post> postArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        fireBaseHelper = new FireBaseHelper(databaseReference, TimelineActivity.this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                fireBaseHelper.writeNewPost(Constants.getImei(TimelineActivity.this), "darshan", "testing the hashtag with #cse and #dsi");


            }
        });

        init();

    }

    private void init() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TimelineActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        customAdapter = new CustomAdapter();
        recyclerView.setAdapter(customAdapter);

        fireBaseHelper.fetchPosts(new FireBaseHelper.PostsFetched() {
            @Override
            public void onPostsFetched(ArrayList<Post> list) {
                postArrayList.addAll(list);
            }
        }, Constants.POPULAR);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(TimelineActivity.this).inflate(R.layout.single_confession_view, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, final int position) {

            Post post = postArrayList.get(position);

            holder.nameTv.setText(post.getAuthor());
            holder.contentTv.setText(post.getBody());
            holder.timestampTv.setText(post.getTime());
            holder.likesTv.setText(post.getStarCount() + "");
            holder.repliesTv.setText(post.getReplyCount());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TimelineActivity.this, CommentsActivity.class);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return postArrayList.size();
        }

        class Holder extends RecyclerView.ViewHolder {

            TextView nameTv, contentTv, timestampTv, likesTv, repliesTv;
            ImageView likesIv, repliesIv;

            public Holder(View itemView) {
                super(itemView);

                nameTv = (TextView) itemView.findViewById(R.id.nameTv);
                contentTv = (TextView) itemView.findViewById(R.id.contentTV);
                timestampTv = (TextView) itemView.findViewById(R.id.timeStamp);

                likesTv = (TextView) itemView.findViewById(R.id.likesTv);
                repliesTv = (TextView) itemView.findViewById(R.id.repliesTv);

                likesIv = (ImageView) itemView.findViewById(R.id.likesIv);
                repliesIv = (ImageView) itemView.findViewById(R.id.repliesIv);

            }
        }

    }


}
