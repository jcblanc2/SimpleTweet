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

import org.parceler.Parcels;
import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    ImageView detailProfileImage;
    ImageView detailPostImage;
    TextView detailTvScreenName;
    TextView detailTvUsername;
    TextView detailTvBody;
    TextView detailTvTime;
    TextView detailTvReTweet;
    TextView detailTvFavorites;
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
        detailPostImage.findViewById(R.id.detailPostImage);

        // get intent
        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("Tweet"));

        detailTvScreenName.setText(tweet.user.name);
        detailTvUsername.setText("@"+tweet.user.screenName);
        detailTvBody.setText(tweet.body);
        detailTvTime.setText(tweet.getFormattedTime(tweet.createdAt));
        detailTvFavorites.setText(tweet.favorite_count+"FAVORITES");
        detailTvReTweet.setText(tweet.favorite_count+"RETWEETS");

        if (!tweet.entities.media_url.isEmpty()){
            detailPostImage.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(tweet.entities.media_url)
                    .into(detailPostImage);
        }

        Glide.with(this)
                .load(tweet.getUser().getProfileImageUrl())
                .transform(new CircleCrop())
                .into(detailProfileImage);


    }
}