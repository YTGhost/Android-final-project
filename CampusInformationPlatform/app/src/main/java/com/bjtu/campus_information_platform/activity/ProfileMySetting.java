package com.bjtu.campus_information_platform.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.util.network.HttpRequest;
import com.bjtu.campus_information_platform.util.network.OkHttpException;
import com.bjtu.campus_information_platform.util.network.ResponseCallback;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;

public class ProfileMySetting extends AppCompatActivity {


    private final static int SELECT_BACKGROUND = 102;
    private final static int SELECT_AVATAR = 101;

    LinearLayout linearLayoutImage;
    LinearLayout linearLayoutBackground;

    private ImageView avatarImageView;
    private ImageView backgroudImageView;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_setting);

        linearLayoutImage = (LinearLayout) findViewById(R.id.imageLayout);
        linearLayoutBackground = (LinearLayout) findViewById(R.id.backgroundLayout);
        avatarImageView = (ImageView) findViewById(R.id.h_head);
        backgroudImageView = (ImageView) findViewById(R.id.h_back);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 设置头像
        linearLayoutImage.setOnClickListener(v -> {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, SELECT_AVATAR);
        });

        // 设置背景
        linearLayoutBackground.setOnClickListener(v -> {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, SELECT_BACKGROUND);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            File file = new File(getRealPathFromURI(imageUri));
            HttpRequest.postMultipartApi(null, file, new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    JSONObject result = null;
                    try {
                        result = new JSONObject(responseObj.toString());
                        String url = result.getString("data");
                        if(requestCode == SELECT_AVATAR) {
                            MyApplication.account.setAvatarUrl(url);
                            MyApplication.profileFragment.changeAvatar();
                        } else {
                            MyApplication.account.setBackgroundUrl(url);
                            MyApplication.profileFragment.changeBackground();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(OkHttpException failuer) {
                }
            });
        }
    }

    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}