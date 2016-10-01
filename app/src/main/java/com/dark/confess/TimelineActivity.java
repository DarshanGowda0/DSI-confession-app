package com.dark.confess;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomAdapter customAdapter;

    FireBaseHelper fireBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        fireBaseHelper = new FireBaseHelper(databaseReference);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

//                fireBaseHelper.writeNewPost(Constants.getImei(TimelineActivity.this), "darshan", "This is a testing confession");

//                fireBaseHelper.writeReply("-KSyq654Q_ZfEGtIGqKX",Constants.getImei(TimelineActivity.this),"Darshan N","This is a test comment");
//                fireBaseHelper.writeReply("-KSyq654Q_ZfEGtIGqKX",Constants.getImei(TimelineActivity.this),"Darshan N","This is a test comment");
//                fireBaseHelper.writeReply("-KSyq654Q_ZfEGtIGqKX",Constants.getImei(TimelineActivity.this),"Darshan N","This is a test comment");

//                fireBaseHelper.deleteReply("-KSrE9KBaiu4f_l4CuCt", "-KSrEoUxzjo5ay7NrXbZ", Constants.getImei(TimelineActivity.this));

//                fireBaseHelper.deletePost("-KSrE9KBaiu4f_l4CuCt", Constants.getImei(TimelineActivity.this));



//
                /*fireBaseHelper.fetchPosts(new FireBaseHelper.PostsFetched() {
                    @Override
                    public void onPostsFetched(ArrayList<Post> list) {

                        for (Post post : list) {
                            Log.d("TAG", "onPostsFetched: " + post.body);
                        }

                    }
                });*/


                /*

                fireBaseHelper.writeReply("-KSrJppgXZiTZeV2oJzQ",Constants.getImei(TimelineActivity.this),"DARSHAN N","this is to test the reply");
                fireBaseHelper.writeReply("-KSrJppgXZiTZeV2oJzQ",Constants.getImei(TimelineActivity.this),"DARSHAN 123","this is to test the reply again");
                fireBaseHelper.writeReply("-KSrJppgXZiTZeV2oJzQ",Constants.getImei(TimelineActivity.this),"DARSHAN ","this is to test the reply again and again");

                */


                /*fireBaseHelper.fetchComments("-KSyq654Q_ZfEGtIGqKX", new FireBaseHelper.RepliesFetched() {
                    @Override
                    public void onRepliesFetched(ArrayList<Reply> list) {
                        for (Reply reply : list) {
                            Log.d("TAG", "onRepliesFetched: " + reply.replyValue);
                        }
                    }
                });*/


//                fireBaseHelper.likeOrUnlikePost("-KSyq8Aoz8w5oeVE3t09", Constants.getImei(TimelineActivity.this));


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
            return 10;
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
