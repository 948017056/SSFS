package com.cctv.ssfs.view.user;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.adapter.BoughtRecyclerAdapter;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.netdata.MyObserver;
import com.cctv.ssfs.netdata.ObserverOnNextListener;
import com.cctv.ssfs.netdata.RetrofitManage;
import com.cctv.ssfs.utils.SPUtils;
import com.cctv.ssfs.view.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 已买个股
 *
 * @author qi
 */
public class BoughtActivity extends BaseActivity implements View.OnClickListener, ObserverOnNextListener<ResponseBody> {
    /*public TagFlowLayout tagBuyFlowLayout;
    public TagFlowLayout tagHotFlowLayout;
    private List<String> tagBuyCodeList = new ArrayList<>();
    String[] buyVals = new String[]{};
    String[] hotVals = new String[]{};
    private List<String> tagHotCodeList = new ArrayList<>();*/
    private TextView tvNoAdd;

    private List<JSONObject> list = new ArrayList<>();
    private BoughtRecyclerAdapter adapter;
    private RecyclerView recycler;

    @Override
    public int getLayout() {
        return R.layout.activity_bought;
    }

    @Override
    public void initView() {
        ImageView iv_back = getView(R.id.iv_back);
        TextView tvTitle = getView(R.id.tv_title);
        tvTitle.setText("已买个股");
        iv_back.setOnClickListener(this);
        recycler = getView(R.id.recycler_bought);
        adapter = new BoughtRecyclerAdapter(this, list);
        recycler.setAdapter(adapter);
//        tagBuyFlowLayout = getView(R.id.tag_buyFlowLayout);
//        tagHotFlowLayout = getView(R.id.tag_hotFlowLayout);
        tvNoAdd = getView(R.id.tv_noadd);
    }

    @Override
    public void initData() {
        String userId = (String) SPUtils.getParam(this, LoginActivity.USER_ID, "-1");
        RetrofitManage
                .getApiService(BaseUrl.BASE_WK_URL)
                .getUserHaveData(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(this));
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            String json = responseBody.string();
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.optBoolean("code")) {
                JSONArray jsonData = jsonObject.optJSONObject("data").optJSONArray("data");
                if (jsonData != null) {
                    recycler.setVisibility(View.VISIBLE);
                    tvNoAdd.setVisibility(View.GONE);
                    list.clear();
                    for (int i = 0; i < jsonData.length(); i++) {
                        list.add(jsonData.optJSONObject(i));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    recycler.setVisibility(View.GONE);
                    tvNoAdd.setVisibility(View.VISIBLE);
                }

                /*if (arrayHave.length() > 0) {
                    tvNoAdd.setVisibility(View.GONE);
                    tagBuyFlowLayout.setVisibility(View.VISIBLE);
                    tagBuyCodeList.clear();
                    List<String> tagBuyNameList = new ArrayList<>();
                    for (int i = 0; i < arrayHave.length(); i++) {
                        final JSONObject data = arrayHave.optJSONObject(i);
                        tagBuyNameList.add(data.optString("name"));
                        tagBuyCodeList.add(data.optString("code"));
                    }
                    buyVals = tagBuyNameList.toArray(buyVals);
                    tagBuyFlowLayout.setAdapter(new TagAdapter<String>(buyVals) {

                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            TextView tv = (TextView) View.inflate(BoughtActivity.this, R.layout.item_tag, null);
                            tv.setText(s);
                            return tv;
                        }
                    });
                    tagBuyFlowLayout.setOnTagClickListener(BoughtActivity.this);
                } else {
                    tvNoAdd.setVisibility(View.VISIBLE);
                    tagBuyFlowLayout.setVisibility(View.GONE);
                }
                JSONArray arrayHot = jsonData.optJSONArray("hot");
                List<String> tagHotNameList = new ArrayList<>();
                tagHotCodeList.clear();
                for (int i = 0; i < arrayHot.length(); i++) {
                    final JSONObject data = arrayHot.optJSONObject(i);
                    tagHotNameList.add(data.optString("name"));
                    tagHotCodeList.add(data.optString("code"));
                }
                hotVals = tagHotNameList.toArray(hotVals);

                tagHotFlowLayout.setAdapter(new TagAdapter<String>(hotVals) {

                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        TextView tv = (TextView) View.inflate(BoughtActivity.this, R.layout.item_tag, null);
                        tv.setText(s);
                        return tv;
                    }
                });*/
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 热门标签点击
     */
    /*@Override
    public boolean onTagClick(View view, int position, FlowLayout parent) {
        Intent intent = new Intent(BoughtActivity.this, StockDetailsActivity.class);
        intent.putExtra(StockDetailsActivity.CODE, tagHotCodeList.get(position));
        startActivity(intent);
        return false;
    }*/
}
