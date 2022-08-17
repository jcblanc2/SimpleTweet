package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import org.parceler.Parcels;
import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    ImageView detailProfileImage;
    ImageView postImage;
    TextView detailTvScreenName;
    TextView detailTvUsername;
    TextView detailTvBody;
    TextView detailTvTime;
    TextView detailTvReTweet;
    TextView detailTvFavorites;
    VideoPlayerView dvideo_player_1;
    ImageView dvideo_cover_1;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_twitter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

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
        dvideo_player_1 = findViewById(R.id.video_player_1);
        dvideo_cover_1 = findViewById(R.id.video_cover_1);

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
                    .into(postImage);
        }

        Glide.with(this)
                .load(tweet.getUser().getProfileImageUrl())
                .transform(new CircleCrop())
                .into(detailProfileImage);

        if(!tweet.exEntities.videoUrl.isEmpty()) {
            VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
                @Override
                public void onPlayerItemChanged(MetaData metaData) {
                }
            });

            dvideo_player_1.setVisibility(View.VISIBLE);
            dvideo_cover_1.setVisibility(View.VISIBLE);

            dvideo_player_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVideoPlayerManager.playNewVideo(null, dvideo_player_1, tweet.exEntities.videoUrl);
                }
            });
        }


    }
}