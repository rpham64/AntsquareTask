package com.rpham64.android.antsquaretask.UI;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.rpham64.android.antsquaretask.Model.Post;
import com.rpham64.android.antsquaretask.R;
import com.squareup.picasso.Picasso;

import im.ene.lab.toro.ToroVideoViewHolder;
import im.ene.lab.toro.widget.ToroVideoView;
import pl.droidsonroids.gif.GifImageView;

/**
 * Helper ViewHolder class for handling cardviews
 *
 * Created by Rudolf on 6/7/2016.
 */
public class NewsFeedHolder extends ToroVideoViewHolder {

    private Context mContext;

    private Post mPost;

    private ImageView mLogo;
    private TextView mStoreName;
    private TextView mStoreCategory;

    private TextView mProductName;
    private TextView mProductDescription;
    private GifImageView mProductImage;
    private ToroVideoView mProductVideo;

    private Button mVideoCallButton;

    public NewsFeedHolder(Context context, View itemView) {
        super(itemView);

        mContext = context;

        mLogo = (ImageView) itemView.findViewById(R.id.logo);
        mStoreName = (TextView) itemView.findViewById(R.id.store_name);
        mStoreCategory = (TextView) itemView.findViewById(R.id.store_category);

        mProductName = (TextView) itemView.findViewById(R.id.product_name);
        mProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        mProductImage = (GifImageView) itemView.findViewById(R.id.product_image);
        mProductVideo = (ToroVideoView) itemView.findViewById(R.id.product_video);

        mVideoCallButton = (Button) itemView.findViewById(R.id.video_call_button);

        mProductVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProductVideo.isPlaying()) {
                    mProductVideo.pause();
                } else {
                    mProductVideo.start();
                }
            }
        });

        mVideoCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void bind(@Nullable Object object) {
        mPost = (Post) object;

        // Logo
        Picasso.with(mContext)
                .load(mPost.getLogo())
                .into(mLogo);

        // Store Name & Category
        mStoreName.setText(mPost.getStoreName());
        mStoreCategory.setText(mPost.getStoreCategory());

        // Product Name & Category
        mProductName.setText(mPost.getProductName());
        mProductDescription.setText(mPost.getProductDescription());

        // Product Image/Gif/Video
        if (mPost.getImageUrls().size() != 0) {

            final String firstImageUrl = mPost.getImageUrls().get(0);

            if (firstImageUrl.endsWith(".mp4")) {

                // Video
                mProductImage.setVisibility(View.INVISIBLE);
                mProductVideo.setVisibility(View.VISIBLE);

                mProductVideo.setVideoPath(firstImageUrl);
                mProductVideo.start();

            } else {

                mProductImage.setVisibility(View.VISIBLE);
                mProductVideo.setVisibility(View.INVISIBLE);

                if (firstImageUrl.endsWith(".gif")) {

                    // Gif
                    Ion.with(mProductImage)
                            .load(firstImageUrl);

                } else {

                    // Image
                    Picasso.with(mContext)
                            .load(firstImageUrl)
                            .into(mProductImage);

                }
            }

        }
    }

    @Override
    protected ToroVideoView findVideoView(View itemView) {
        return (ToroVideoView) itemView.findViewById(R.id.product_video);
    }

    @Nullable
    @Override
    public String getVideoId() {
        return "Video's id and order: " + getAdapterPosition();
    }
}