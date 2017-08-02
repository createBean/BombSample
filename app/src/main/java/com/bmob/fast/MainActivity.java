package com.bmob.fast;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bmob.fast.model.LostModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.InsertListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 快速入门
 *
 * @author yu
 * @ClassName: MainActivity
 * @Description: TODO
 * @date 2017-5-20 下午4:10:29
 */
public class MainActivity extends BaseActivity implements OnClickListener {

    Button btn_add, btn_delete, btn_update, btn_query, btnAddMe, btnQueryMe, btnClick;
    LinearLayout layoutData;

    private String objectId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化View
        initView();
        initListener();
    }

    private void initView() {
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_query = (Button) findViewById(R.id.btn_query);
        btnAddMe = (Button) findViewById(R.id.btn_add_me);
        btnQueryMe = (Button) findViewById(R.id.btn_query_me);
        layoutData = (LinearLayout) findViewById(R.id.layout_data);
        btnClick = (Button) findViewById(R.id.btn_click);
    }

    private void initListener() {
        btn_add.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        btnAddMe.setOnClickListener(this);
        btnQueryMe.setOnClickListener(this);
        btnClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btn_add) {
            createPerson();
        } else if (v == btn_delete) {
            deletePersonByObjectId();
        } else if (v == btn_update) {
            updatePersonByObjectId();
        } else if (v == btn_query) {
            queryPersonByObjectId();
        } else if (v == btnAddMe) {
            createPerson();
        } else if (v == btnQueryMe) {
            queryAllData();
        } else if (v == btnClick) {
            Intent intent = new Intent(MainActivity.this,OtherActivity.class);
            startActivity(intent);
        }
    }

    private void queryAllData() {
        BmobQuery<Person> query = new BmobQuery<Person>();
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(this, new FindListener<Person>() {
            @Override
            public void onSuccess(List<Person> list) {
                layoutData.removeAllViews();
                for (int i = 0; i < list.size(); i++) {
                    Person model = list.get(i);
                    TextView textView = new TextView(MainActivity.this);
                    textView.setText(model.getName() + "-->" + model.getAddress());
                    layoutData.addView(textView);
                }
            }

            @Override
            public void onError(String s) {

            }
        });
    }

    private void insertDataByMe() {
        LostModel lost = new LostModel();
        lost.setDescribe("失物招领描述");
        lost.setPhone("手机号码");
        lost.setTitle("失物招领标题");
        lost.insertObject(this, new InsertListener() {
            @Override
            public void onSuccess() {
                ShowToast("失物招领信息添加成功");
            }

            @Override
            public void onFailure(String s) {
                ShowToast("添加失败" + s);
            }
        });
    }

    /**
     * 创建一条person数据 createPersonData
     *
     * @throws
     * @Title: createPersonData
     */
    private void createPerson() {
        final Person p2 = new Person();
        p2.setName("lucky");
        p2.setAddress("北京海淀");
        p2.insertObject(this, new InsertListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                // 返回成功就可以得到person里面的值
                ShowToast("创建数据成功:" + p2.getObjectId());
                objectId = p2.getObjectId();
            }

            @Override
            public void onFailure(String msg) {
                // TODO Auto-generated method stub
                ShowToast("创建数据失败：" + msg);
            }
        });
    }

    /**
     * 更新指定objectId的person数据
     *
     * @return void
     * @throws
     */
    private void updatePersonByObjectId() {
        //将指定ObjectId的Person这一行数据中的address内容更新为“北京朝阳”
        final Person p2 = new Person();
        p2.setAddress("北京朝阳");
        p2.updateObject(this, objectId, new UpdateListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                ShowToast("更新成功：更新后的地址->" + p2.getAddress());
            }

            @Override
            public void onFailure(String msg) {
                // TODO Auto-generated method stub
                ShowToast("更新失败：" + msg);
            }
        });
    }

    /**
     * 删除指定ObjectId的person数据 deletePersonByObjectId
     *
     * @return void
     * @throws
     * @Title: deletePersonByObjectId
     */
    private void deletePersonByObjectId() {
        Person p2 = new Person();
        p2.setObjectId(objectId);
        p2.deleteObject(this, new DeleteListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                ShowToast("删除成功");
            }

            @Override
            public void onFailure(String msg) {
                // TODO Auto-generated method stub
                ShowToast("删除失败：" + msg);
            }
        });
    }

    /**
     * 查询指定ObjectId的person数据
     * queryPerson
     *
     * @throws
     * @Title: queryPerson
     */
    private void queryPersonByObjectId() {
        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
        bmobQuery.getObject(this, objectId, new GetListener<Person>() {
            @Override
            public void onSuccess(Person object) {
                // TODO Auto-generated method stub
                ShowToast("查询成功:名称->" + object.getName() + "，地址->" + object.getAddress());
            }

            @Override
            public void onFailure(String msg) {
                // TODO Auto-generated method stub
                ShowToast("查询失败：" + msg);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
