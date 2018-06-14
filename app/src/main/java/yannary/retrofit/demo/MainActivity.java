package yannary.retrofit.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import yannary.retrofit.demo.retrofit.RetrofitActivity;
import yannary.retrofit.demo.retrofit2rxjava.Retrofit2RxJavaActivity;
import yannary.retrofit.demo.rxjava.RxJavaActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRetrofitClick(View view) {
        mActivity.startActivity(new Intent(mActivity, RetrofitActivity.class));
    }

    public void onRxJavaClick(View view) {
        mActivity.startActivity(new Intent(mActivity, RxJavaActivity.class));
    }

    public void onRetrofit2RxJavaClick(View view) {
        mActivity.startActivity(new Intent(mActivity, Retrofit2RxJavaActivity.class));
    }
}
