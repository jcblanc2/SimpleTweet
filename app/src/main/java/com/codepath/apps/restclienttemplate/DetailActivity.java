package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.SimpleMainThreadMediaPlayerListener;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    ImageView detailProfileImage;
    ImageView postImage;
    TextView detailTvScreenName;
    TextView detailTvUsername;
    TextView detailTvBody;
    TextView detailTvTime;
    TextView detailTvReTweet;
    TextView detailTvFavorites;
    TextView dTvRetweet;
    TextView dTvLike;
    TextView dTvShare;
    VideoPlayerView mVideoPlayer_1;
    ImageView mVideoCover;
    EditText editReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Display icon in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left_thin);
        getSupportActionBar().setLogo(R.drawable.ic_twitter);
        // change title
        getSupportActionBar().setTitle("   Tweet");


        detailProfileImage = findViewById(R.id.detailProfileImage);
        detailTvScreenName = findViewById(R.id.detailTvScreenName);
        detailTvUsername = findViewById(R.id.detailTvUsername);
        detailTvBody = findViewById(R.id.detailTvBody);
        detailTvTime = findViewById(R.id.detailTvTime);
        detailTvReTweet = findViewById(R.id.detailTvReTweet);
        detailTvFavorites = findViewById(R.id.detailTvFavorites);
        dTvRetweet = findViewById(R.id.dTvRetweet);
        dTvLike = findViewById(R.id.dTvLike);
        dTvShare = findViewById(R.id.dTvShare);
        postImage = findViewById(R.id.detailPostImage);
        mVideoPlayer_1 = findViewById(R.id.video_player_1);
        mVideoCover = findViewById(R.id.video_cover_1);
        editReply = findViewById(R.id.editReply);

        // get intent
        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("Tweet"));

        detailTvScreenName.setText(tweet.user.name);
        detailTvUsername.setText("@"+tweet.user.screenName);
        detailTvBody.setText(tweet.body);
        detailTvTime.setText(tweet.getFormattedTime(tweet.createdAt));
        detailTvFavorites.setText(tweet.favorite_count+"FAVORITES");
        detailTvReTweet.setText(tweet.retweet_count+"RETWEETS");
        editReply.setHint("Reply to " + tweet.user.name);

        if (!tweet.entities.media_url.isEmpty()){
            postImage.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(tweet.entities.media_url)
                    .transform(new RoundedCorners(45))
                    .into(postImage);
        }

        Glide.with(this)
                .load(tweet.getUser().getProfileImageUrl())
                .transform(new CircleCrop())
                .into(detailProfileImage);


        // --------- PLAY VIDEO
        if (!tweet.exEntities.videoUrl.isEmpty() && Objects.equals(tweet.exEntities.type, "video")){
            mVideoPlayer_1.setVisibility(View.VISIBLE);
            mVideoCover.setVisibility(View.VISIBLE);
        }

        mVideoCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
                        @Override
                        public void onPlayerItemChanged(MetaData metaData) {

                        }
                    });

                    mVideoPlayerManager.playNewVideo(null, mVideoPlayer_1, "https://github.com/jcblanc2/Flixster/blob/master/walkthrough.gif");
                }
            });

        // change heart icon to red if we like
        if(tweet.favorited){
            Drawable drawable = ContextCompat.getDrawable(DetailActivity.this,R.drawable.red_heart);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            dTvLike.setCompoundDrawables(drawable, null, null, null);
        }


        // change heart icon to green if we retweet
        if(tweet.retweeted){
            Drawable drawable = ContextCompat.getDrawable(DetailActivity.this, R.drawable.green_retweet);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            dTvRetweet.setCompoundDrawables(drawable, null, null, null);
        }


        // click on icon like
        dTvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tweet.favorited){
                    Drawable drawable = ContextCompat.getDrawable(DetailActivity.this,R.drawable.red_heart);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    dTvLike.setCompoundDrawables(drawable, null, null, null);

                    ++tweet.favorite_count;
                    detailTvFavorites.setText(tweet.favorite_count+"FAVORITES");
                    tweet.favorited = true;
                }else {
                    --tweet.favorite_count;

                    Drawable drawable = ContextCompat.getDrawable(DetailActivity.this,  R.drawable.cards_heart_outline);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    dTvLike.setCompoundDrawables(drawable, null, null, null);

                    detailTvFavorites.setText(tweet.favorite_count+"FAVORITES");
                    tweet.favorited = false;
                }
            }
        });


        // click on share icon
        dTvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, tweet.body);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, "Share this via");
                startActivity(shareIntent);
            }
        });


        // click on icon retweet
        dTvRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tweet.retweeted){
                    Drawable drawable = ContextCompat.getDrawable(DetailActivity.this, R.drawable.green_retweet);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    dTvRetweet.setCompoundDrawables(drawable, null, null, null);

                    ++tweet.retweet_count;
                    dTvRetweet.setText(String.valueOf(tweet.retweet_count));
                    tweet.retweeted = true;
                }else {
                    --tweet.retweet_count;

                    Drawable drawable = ContextCompat.getDrawable(DetailActivity.this, R.drawable.retweet);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    dTvRetweet.setCompoundDrawables(drawable, null, null, null);

                    dTvRetweet.setText(String.valueOf(tweet.retweet_count));
                    tweet.retweeted = false;
                }
            }
        });
    }


    // Toolbar menu
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
            Intent i = new Intent(DetailActivity.this, TimeLineActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(i, 0);
            return true;
    }

    }
