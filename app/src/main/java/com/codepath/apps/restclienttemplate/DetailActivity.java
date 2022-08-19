package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
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
    VideoPlayerView videoPlayer;

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
        postImage = findViewById(R.id.detailPostImage);
        videoPlayer = findViewById(R.id.video_player_1);

        // get intent
        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("Tweet"));

        detailTvScreenName.setText(tweet.user.name);
        detailTvUsername.setText("@"+tweet.user.screenName);
        detailTvBody.setText(tweet.body);
        detailTvTime.setText(tweet.getFormattedTime(tweet.createdAt));
        detailTvFavorites.setText(tweet.favorite_count+"FAVORITES");
        detailTvReTweet.setText(tweet.favorite_count+"RETWEETS");

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

        if (!tweet.exEntities.videoUrl.isEmpty() && Objects.equals(tweet.exEntities.type, "video")){
            videoPlayer.setVisibility(View.VISIBLE);
            VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
                @Override
                public void onPlayerItemChanged(MetaData metaData) {

                }
            });

            mVideoPlayerManager.playNewVideo(null, videoPlayer, tweet.exEntities.videoUrl);
        }





    }

    // Toolbar menu
            @Override
            public boolean onOptionsItemSelected(MenuItem menuItem) {
                int homeAsUp = R.id.homeAsUp;
                    Intent i = new Intent(DetailActivity.this, TimeLineActivity.class);
                    startActivity(i);
                    return true ;
            }

    }