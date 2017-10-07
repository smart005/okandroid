package com.cloud.coretest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.cloud.core.cache.CacheDataItem;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/27
 * @Description:数据库测试
 * @Modifier:
 * @ModifyContent:
 */
public class DbUtilsTest extends Activity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_utils_test);

//        Runnable r1 = new Runnable() {
//            @Override
//            public void run() {
//                //方法一
//                //2S insert一次
//                TestBean tempBean = new TestBean();
//                tempBean.setAge(1);
//                tempBean.setName("高耸");
//                tempBean.setGender("男");
//                DbUtils.getInstance().getDbManager().addOrUpdate(tempBean);
//                Log.d("test", "test11寫入成功");
//                handler.postDelayed(this, 500L);
//            }
//        };
//        handler.postDelayed(r1, 2000L);
//
//        Runnable r2r3 = new Runnable() {
//            @Override
//            public void run() {
//                //方法二三
//                //1S insert一次
//            }
//        };
//        handler.postDelayed(r2r3, 1000L);

        //方法四 加一列
        CacheDataItem dataItem = new CacheDataItem();
        String forKey = dataItem.getForKey(dataItem.getEffective());

    }
}
