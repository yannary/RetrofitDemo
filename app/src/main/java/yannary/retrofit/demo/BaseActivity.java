package yannary.retrofit.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zhangyanyan on 2018/2/27.
 */

public class BaseActivity extends AppCompatActivity {

    public BaseActivity mActivity;
    public String TAG = BaseActivity.this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = BaseActivity.this;
    }
}
