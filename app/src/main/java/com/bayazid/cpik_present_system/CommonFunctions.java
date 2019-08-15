package com.bayazid.cpik_present_system;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

public class CommonFunctions {
    private int dialogResult;

    public void ImageGlider(Context context, String PhotoUri, CircularImageView circularImageView){
        //Round image view
        RequestOptions options = new RequestOptions()
                .circleCrop()
                .centerCrop()
                .placeholder(R.drawable.ic_round_user)
                .error(R.drawable.ic_round_user);
        Glide.with(context).load(PhotoUri).apply(options).into(circularImageView);
    }

    public void DialogWarning(final Context context, String title, String Massage, String PositiveButton, String NegativeButton){
        //DialogBox.......positive and Negative button
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //setting the view of the builder to our custom view that we already inflated
        //  builder.setView(dialogView);
        builder.setPositiveButton(PositiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              //  Toast.makeText(context,i+" is setPositiveButton",Toast.LENGTH_SHORT).show();
               setdialogResult(i);

            }
        }).setNegativeButton(NegativeButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              //  Toast.makeText(context,i+" is setNegativeButton",Toast.LENGTH_SHORT).show();
                setdialogResult(i);
            }
        });
        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.setTitle(title);
        alertDialog.setMessage(Massage);
        alertDialog.show();
    }

    //Get itemPosition
    public int getdialogResult() {
        return dialogResult;
    }
    public void setdialogResult(int dialogResult) {
        this.dialogResult = dialogResult;
    }


}
