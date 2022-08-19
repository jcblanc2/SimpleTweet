package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
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

import java.util.List;
import java.util.Objects;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    public static Context context;
    List<Tweet> listTweets;

    // Pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.listTweets = tweets;
    }


    // For each value inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // Bin values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get data at position
        Tweet tweet = listTweets.get(position);
        // Bind the tweet with view holder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return listTweets.size();
    }



    // Method to clean all elements of the recycler
    public void clear(){
        listTweets.clear();
        notifyDataSetChanged();
    }

    // Method to add a list of tweets -- change to type used
    public void addAll(List<Tweet> tweets){
        listTweets.addAll(tweets);
        notifyDataSetChanged();
    }


    //Define viewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfileImage;
        TextView tvScreenName;
        TextView tvBody;
        TextView tvUsername;
        TextView tvTime;
        TextView tvReply;
        TextView tvLike;
        RelativeLayout containerItem;
        ImageView imagePost;
        VideoPlayerView videoPlayer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvReply = itemView.findViewById(R.id.tvReply);
            tvLike = itemView.findViewById(R.id.tvLike);
            containerItem = itemView.findViewById(R.id.containerItem);
            imagePost = itemView.findViewById(R.id.ivPostImage);
            videoPlayer = itemView.findViewById(R.id.video_player);

        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.name);
            tvUsername.setText("@"+tweet.user.screenName);
            tvTime.setText(Tweet.getFormattedTimestamp(tweet.createdAt));
            tvReply.setText(tweet.retweet_count);
            tvLike.setText(tweet.favorite_count);

            if (!tweet.entities.media_url.isEmpty()){
                imagePost.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(tweet.entities.media_url)
                        .transform(new RoundedCorners(45))
                        .into(imagePost);
            }

            Glide.with(context)
                    .load(tweet.user.profileImageUrl)
                    .transform(new CircleCrop())
                    .into(ivProfileImage);


            if (!tweet.exEntities.videoUrl.isEmpty() && Objects.equals(tweet.exEntities.type, "video")){
                videoPlayer.setVisibility(View.VISIBLE);
                VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
                    @Override
                    public void onPlayerItemChanged(MetaData metaData) {

                    }
                });

                mVideoPlayerManager.playNewVideo(null, videoPlayer, tweet.exEntities.videoUrl);


            }

            // add click on a tweet
            containerItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("Tweet", Parcels.wrap(tweet));
                    context.startActivity(i);
                }
            });

            // click on icon
            tvLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int like = Integer.parseInt(tweet.favorite_count);
                    if (!tweet.favorited){
                     tvLike.setText(String.valueOf(++like));
//                     tvLike.setCompoundDrawables(R.drawable.red_heart);
                     tweet.favorited = true;
                 }else {
                        tvLike.setText(String.valueOf(--like));
                    }


                }
            });
        }
    }
}