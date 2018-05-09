package com.cctv.ssfs.view.user;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.adapter.InvitationRecyclerAdapter;
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
 * 我的邀请
 *
 * @author qi
 */
public class InviteActivity extends BaseActivity implements View.OnClickListener, ObserverOnNextListener<ResponseBody> {

    private List<JSONObject> list;
    private InvitationRecyclerAdapter adapter;
    private TextView tvNoInvitation;
    private RecyclerView recycler;

    @Override
    public int getLayout() {
        return R.layout.activity_invite;
    }

    @Override
    public void initView() {
        list = new ArrayList<>();
        ImageView iv_back = getView(R.id.iv_back);
        TextView tvTitle = getView(R.id.tv_title);
        recycler = getView(R.id.recycler_invitation);
        tvNoInvitation = getView(R.id.tv_notinvitation);
        tvTitle.setText("我的邀请");
        iv_back.setOnClickListener(this);

        adapter = new InvitationRecyclerAdapter(this, list);
        recycler.setAdapter(adapter);
    }

    @Override
    public void initData() {
        String userId = (String) SPUtils.getParam(this, LoginActivity.USER_ID, "-1");

        RetrofitManage
                .getApiService(BaseUrl.BASE_WK_URL)
                .getInviteData(userId)
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
                tvNoInvitation.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                list.clear();
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(jsonArray.optJSONObject(i));
                }
                adapter.notifyLoadMoreToLoading();
            } else {
                recycler.setVisibility(View.GONE);
                tvNoInvitation.setVisibility(View.VISIBLE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
