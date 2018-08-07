package com.mobzeta.ankpacanival;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.vipul.hp_hp.library.Layout_to_Image;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //add image here
    public void addImage(View view) {
        setPopName();
        /**
        ImagePicker.create(this)
                .returnMode(ReturnMode.ALL)
                .single()
                .start();
         */

    }


    //Receive result
    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);
            CircleImageView setImg = (CircleImageView) findViewById(R.id.setImg);

            Matrix matrix = new Matrix();
            matrix.postRotate(-90);

            Bitmap bitmap = BitmapFactory.decodeFile(image.getPath());

            setImg.setImageBitmap(bitmap);

            /** SetBackground
            Glide.with(getApplicationContext()).applyDefaultRequestOptions(new RequestOptions()
            .override(8,8)
            .diskCacheStrategy(DiskCacheStrategy.NONE)).load(image.getPath()).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        //((RelativeLayout) findViewById(R.id.mainPIC)).setBackground(resource);
                       // ((RelativeLayout) findViewById(R.id.mainPIC2)).setBackground(resource);
                        //Convert frame and share

                    }
                }
            }); */
            frameImage();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //Frame converting
    public void frameImage(){

        Layout_to_Image layout_to_image;  //Create Object of Layout_to_Image Class

        RelativeLayout relativeLayout;   //Define Any Layout

        Bitmap bitmap;                  //Bitmap for holding Image of layout

        //provide layout with its id in Xml

        relativeLayout=(RelativeLayout)findViewById(R.id.mainPIC);

        //initialise layout_to_image object with its parent class and pass parameters as (<Current Activity>,<layout object>)

        layout_to_image=new Layout_to_Image(MainActivity.this,relativeLayout);

        //now call the main working function ;) and hold the returned image in bitmap

        bitmap=layout_to_image.convert_layout();

        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"Ankpa Carnival Banner", "MobZeta Image Powered");
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri );
        intent.putExtra(Intent.EXTRA_TEXT,"Click the link below to customize your own too !\nwww.enenaija.com");
        startActivity(intent);
    }

    //PopUp For Name
    public void setPopName(){
        final Dialog d = new Dialog(this);
        d.setCancelable(false);
        d.setContentView(R.layout.popname);
        Context dctx = d.getContext();
        d.getWindow().setLayout(DrawerLayout.LayoutParams.MATCH_PARENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
        //SetOn Button Click
        d.getWindow().findViewById(R.id.btnSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String name = ((TextView)d.findViewById(R.id.txtName)).getText().toString();
            if(!name.isEmpty()){
                ((TextView) findViewById(R.id.txtNameDisp)).setText(name);
                ImagePicker.create(MainActivity.this)
                        .returnMode(ReturnMode.ALL)
                        .single()
                        .start();
                d.dismiss();
                return;
            }
                Toast.makeText(d.getContext(), "You must set a name", Toast.LENGTH_SHORT).show();
            }
        });

        d.show();
    }

    public void shareApp(View view) {
        //Share app

    }
}
