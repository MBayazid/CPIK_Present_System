package com.bayazid.cpik_present_system;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

public class CommonFunctions {

    public void ImageGlider(Context context, String PhotoUri, CircularImageView circularImageView){
        //Round image view
        RequestOptions options = new RequestOptions()
                .circleCrop()
                .centerCrop()
                .placeholder(R.drawable.ic_round_user)
                .error(R.drawable.ic_round_user);
        Glide.with(context).load(PhotoUri).apply(options).into(circularImageView);
    }

}
