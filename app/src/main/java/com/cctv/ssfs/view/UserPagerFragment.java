package com.cctv.ssfs.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.customview.UserItemLayout;
import com.cctv.ssfs.entity.LoginBean;
import com.cctv.ssfs.netdata.MyObserver;
import com.cctv.ssfs.netdata.ObserverOnNextListener;
import com.cctv.ssfs.netdata.RetrofitManage;
import com.cctv.ssfs.utils.ImageLoader;
import com.cctv.ssfs.utils.ShareUtils;
import com.cctv.ssfs.utils.SPUtils;
import com.cctv.ssfs.utils.ToastUtils;
import com.cctv.ssfs.view.base.BaseFragment;
import com.cctv.ssfs.view.base.WebActivity;
import com.cctv.ssfs.view.user.BoughtActivity;
import com.cctv.ssfs.view.user.BuyStockActivity;
import com.cctv.ssfs.view.user.CollectionActivity;
import com.cctv.ssfs.view.user.InviteActivity;
import com.cctv.ssfs.view.user.LoginActivity;
import com.cctv.ssfs.view.user.OriginalActivity;
import com.cctv.ssfs.view.user.SettingsActivity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * 我的页面
 *
 * @author QI
 */
public class UserPagerFragment extends BaseFragment implements ObserverOnNextListener<ResponseBody>, View.OnClickListener {

    private ImageView ivHeadportrait;
    private TextView tvPhoneMoney, tvUserInviteFriends;
    private PopupWindow mPopupWindow;
    private boolean bindStatus, letterStatus, inviteStatus;
    private String inviteMsg, bindMsg, letterMsg, s_money, p_money, pp_money, userImg, user_id, invite;
    private int pp_count;
    public static final int CODE_USER = 159;
    private TextView tvUserName, tvUserSex, tvNumber, tvUserAddress, tvMoney;
    private JSONObject letterJson, inviteJson;

    public UserPagerFragment() {
    }


    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_user_pager;
    }


    @Override
    protected void initFragmentView(View view) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        user_id = (String) SPUtils.getParam(getContext(), LoginActivity.USER_ID, "");

        ivHeadportrait = getView(R.id.iv_headportrait);
        tvUserName = getView(R.id.tv_user_name);
        tvUserSex = getView(R.id.tv_user_sex);
        tvNumber = getView(R.id.tv_number);
        tvUserAddress = getView(R.id.tv_user_address);
        tvMoney = getView(R.id.tv_money);
        tvPhoneMoney = getView(R.id.tv_phone_money);
        tvUserInviteFriends = getView(R.id.tv_user_invite_friends);

        TextView tvWithdraw = getView(R.id.tv_withdraw);
        FrameLayout btnInviteFriends = getView(R.id.btn_invite_friends);
        FrameLayout btnShareFriends = getView(R.id.btn_share_friends);
        FrameLayout btnPhonenumber = getView(R.id.btn_phonenumber);
        UserItemLayout btnCollection = getView(R.id.btn_collection);
        UserItemLayout btnOriginal = getView(R.id.btn_original);
        UserItemLayout btnInvite = getView(R.id.btn_invite);
        UserItemLayout btnbuyshare = getView(R.id.btn_buystock);
        UserItemLayout btnBought = getView(R.id.btn_bought);
        UserItemLayout btnSettings = getView(R.id.btn_settings);

        tvWithdraw.setOnClickListener(this);
        btnInviteFriends.setOnClickListener(this);
        btnShareFriends.setOnClickListener(this);
        btnPhonenumber.setOnClickListener(this);
        btnCollection.setOnClickListener(this);
        btnOriginal.setOnClickListener(this);
        btnInvite.setOnClickListener(this);
        btnbuyshare.setOnClickListener(this);
        btnBought.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
    }

    //在ui线程执行
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(LoginBean bean) {
        if (bean.isCode()) {
            request();
        }
    }

    @Override
    protected void initFragmentData() {
        request();

        RetrofitManage.getApiService(BaseUrl.BASE_WK_URL)
                .getUserMoneyData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(this));

    }

    private void request() {
        RetrofitManage.getApiService(BaseUrl.BASE_WK_URL)
                .getUserData(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(new UserInfoLisener()));
    }

    public void setUserInfo(LoginBean userInfo) {
        LoginBean.DataBean data = userInfo.getData();
        userImg = data.getUser_img();
        invite = data.getInvite();
        ImageLoader.getInstance().loadImageUrl(getContext(), ivHeadportrait, userImg);
        tvUserName.setText(data.getUser_name());
        tvUserSex.setText(data.getSex().equals("1") ? "男" : "女");
        tvUserAddress.setText(data.getArea());
        tvNumber.setText(String.format("风声号：%s", data.getUser_id()));
        tvMoney.setText(String.format("%s元", data.getUser_money() + ""));
    }

    private class UserInfoLisener implements ObserverOnNextListener<ResponseBody> {

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String json = responseBody.string();
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(json, LoginBean.class);
                setUserInfo(loginBean);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initFragmentData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //提现
            case R.id.tv_withdraw:
                RetrofitManage.getApiService(BaseUrl.BASE_WK_URL)
                        .getEncodeId(user_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyObserver<ResponseBody>(new ObserverOnNextListener<ResponseBody>() {
                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    JSONObject jsonObject = new JSONObject(responseBody.string());
                                    if (jsonObject.optBoolean("code")) {
                                        String data = jsonObject.optString("data");
                                        Intent intentTow = new Intent(getContext(), WebActivity.class);
                                        intentTow.putExtra(WebActivity.KEY_URL, BaseUrl.WITHDRAW_URL + "?app_user_id=" + user_id + "&encode_user_id=" + data);
                                        intentTow.putExtra(WebActivity.TITLE, "我的余额");
                                        startActivity(intentTow);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }));

                break;
            //邀请好友红包
            case R.id.btn_invite_friends:
                if (inviteStatus) {
                    showPopup();
                } else {
                    ToastUtils.showShortToast(getContext(), inviteMsg);
                }
                break;
            //分享干货红包
            case R.id.btn_share_friends:
                if (letterStatus && letterJson != null) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra(WebActivity.KEY_URL, BaseUrl.SHARE_URL);
                    intent.putExtra(WebActivity.TITLE, "密函详情");
                    intent.putExtra("isShare", true);
                    intent.putExtra("titleinfo", letterJson.optString("title"));
                    intent.putExtra("desc", letterJson.optString("desc"));
                    intent.putExtra("pic", letterJson.optString("pic"));
                    intent.putExtra("urls", letterJson.optString("url"));
                    startActivity(intent);
                } else {
                    ToastUtils.showShortToast(getContext(), letterMsg);
                }
                break;
            //绑定手机号
            case R.id.btn_phonenumber:
                if (bindStatus) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra(WebActivity.KEY_URL, String.format(BaseUrl.BINDPHONE_URL + "?user_id=%s&app=1", user_id));
                    intent.putExtra(WebActivity.TITLE, "手机绑定");
                    startActivity(intent);
                } else {
                    ToastUtils.showShortToast(getContext(), bindMsg);
                }
                break;
            //收藏
            case R.id.btn_collection:
                startActivity(new Intent(getContext(), CollectionActivity.class));
                break;
            //原创干货
            case R.id.btn_original:
                startActivity(new Intent(getContext(), OriginalActivity.class));
                break;
            //我的邀请
            case R.id.btn_invite:
                startActivity(new Intent(getContext(), InviteActivity.class));
                break;
            //想买个股
            case R.id.btn_buystock:
                startActivity(new Intent(getContext(), BuyStockActivity.class));
                break;
            //已买个股
            case R.id.btn_bought:
                startActivity(new Intent(getContext(), BoughtActivity.class));
                break;
            //设置
            case R.id.btn_settings:
                startActivityForResult(new Intent(getContext(), SettingsActivity.class), CODE_USER);
                break;
            case R.id.iv_close:
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.linear_bottom:
                if (mPopupWindow != null && inviteJson != null) {
                    mPopupWindow.dismiss();

                    ShareUtils.shareWeb(getActivity(), inviteJson.optString("url") + "/" + invite,
                            inviteJson.optString("title")
                            , inviteJson.optString("desc")
                            , inviteJson.optString("pic")
                    );
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            String json = responseBody.string();
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.optBoolean("code")) {
                JSONObject data = jsonObject.optJSONObject("data");

                JSONObject invite = data.optJSONObject("invite");
                JSONObject bind = data.optJSONObject("bind");
                JSONObject letter = data.optJSONObject("letter");
                JSONObject share = data.optJSONObject("share");
                letterJson = share.optJSONObject("letter");
                inviteJson = share.optJSONObject("invite");

                inviteStatus = invite.optBoolean("status");
                bindStatus = bind.optBoolean("status");
                letterStatus = letter.optBoolean("status");

                inviteMsg = invite.optString("msg");
                bindMsg = bind.optString("msg");
                letterMsg = letter.optString("msg");

                s_money = invite.optString("s_money");
                p_money = invite.optString("p_money");
                pp_money = invite.optString("pp_money");
                pp_count = invite.optInt("pp_count");

                tvUserInviteFriends.setText(String.format("%s元/人", Double.valueOf(p_money) + Double.valueOf(pp_money)));
                tvPhoneMoney.setText(String.format("%s元", bind.optDouble("z_money")));
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getFormat(String originalStr, String placeholderStr) {
        String str = null;
        if (placeholderStr != null) {
            if ("00".equals(placeholderStr.split("\\.")[1])) {
                str = String.format(originalStr, placeholderStr.split("\\.")[0]);
            } else {
                str = String.format(originalStr, placeholderStr);
            }
        }
        return str;
    }

    private void showPopup() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_radenvelope_dialog, null);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        TextView tvNumber1 = view.findViewById(R.id.tv_number1);
        TextView tvNumber2 = view.findViewById(R.id.tv_number2);
        TextView tvNumber3 = view.findViewById(R.id.tv_number3);
        ImageView ivCircleimg = view.findViewById(R.id.iv_circleimg);
        LinearLayout linearBottom = view.findViewById(R.id.linear_bottom);
        iv_close.setOnClickListener(this);
        linearBottom.setOnClickListener(this);
        ImageLoader.getInstance().loadImageUrl(getContext(), ivCircleimg, userImg);
        tvNumber1.setText(getFormat("1、邀请成功以为好友即获取%s元现金红包。", p_money + ""));
        tvNumber2.setText(getFormat("2、该好友可立即获得%s元现金红包。", s_money));
        if (pp_money != null) {
            if ("00".equals(pp_money.split("\\.")[1])) {
                tvNumber3.setText(String.format("3、如果该好友邀请了%s位火以上的好友，你可\n在得%s现金红包。", pp_count, pp_money.split("\\.")[0]));
            }
        }

        if (mPopupWindow == null) {
            mPopupWindow =
                    new PopupWindow(view,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT, false);
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setFocusable(false);
            mPopupWindow.showAtLocation(ivHeadportrait, Gravity.CENTER, 0, 0);
        } else {
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            } else {
                mPopupWindow.showAtLocation(ivHeadportrait, Gravity.CENTER, 0, 0);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除订阅
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void updateFragmentTitleBar() {

    }

    @Override
    protected void setBundle(Bundle bundle) {

    }

}

