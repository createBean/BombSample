package com.bmob.fast;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bmob.fast.model.LostModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.InsertListener;

public class OtherActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private EditText edtTitle, edtContent, edtPhone;
    private Button btnAddData;
    private LinearLayout layoutData;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        initView();
        initListener();
        queryAllData();
    }

    private void initListener() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createData(edtTitle.getText().toString(), edtContent.getText().toString(), edtPhone.getText().toString());
            }
        });

    }

    private void initView() {
        edtTitle = (EditText) findViewById(R.id.edt_title);
        edtContent = (EditText) findViewById(R.id.edt_content);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        btnAddData = (Button) findViewById(R.id.btn_add_data);
        layoutData = (LinearLayout) findViewById(R.id.layout_data);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_view);
        progressView = (ProgressBar) findViewById(R.id.progress_view);
        refreshLayout.setOnRefreshListener(this);
    }

    /**
     * 创建一条person数据 createPersonData
     *
     * @throws
     * @Title: createPersonData
     */
    private void createData(String title, String content, String phone) {
        final LostModel model = new LostModel();
        model.setTitle(title);
        model.setDescribe(content);
        model.setPhone(phone);
        model.insertObject(this, new InsertListener() {
            @Override
            public void onSuccess() {
                // 返回成功就可以得到person里面的值
                ShowToast("创建数据成功:");
                queryAllData();
            }

            @Override
            public void onFailure(String msg) {
                ShowToast("创建数据失败：" + msg);
            }
        });
    }

    private void queryAllData() {
        BmobQuery<LostModel> query = new BmobQuery<LostModel>();
        //按照时间降序
        query.order("-createdAt");
//        query.addQueryKeys("keyword");根据keyword查询，具体文章的position
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(this, new FindListener<LostModel>() {
            @Override
            public void onSuccess(List<LostModel> list) {
                refreshLayout.setRefreshing(false);
                progressView.setVisibility(View.GONE);
                layoutData.removeAllViews();
                for (int i = 0; i < list.size(); i++) {
                    LostModel model = list.get(i);
                    TextView textView = new TextView(OtherActivity.this);
                    textView.setText(model.getTitle() + "\n" + model.getDescribe() + "\n" + model.getPhone());
                    layoutData.addView(textView);
                    TextView textView1 = new TextView(OtherActivity.this);
                    textView1.setText("-------------------------------------");
                    textView.setGravity(Gravity.CENTER_HORIZONTAL);
                    textView1.setGravity(Gravity.CENTER_HORIZONTAL);
                    layoutData.addView(textView1);
                }
            }

            @Override
            public void onError(String s) {

            }
        });
    }

    @Override
    public void onRefresh() {
        queryAllData();
    }
}
