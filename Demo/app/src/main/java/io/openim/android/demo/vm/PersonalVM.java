package io.openim.android.demo.vm;

import androidx.lifecycle.MutableLiveData;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.widget.WaitDialog;
import io.openim.android.sdk.OpenIMClient;
import io.openim.android.sdk.listener.OnBase;
import io.openim.android.sdk.models.UserInfo;

public class PersonalVM extends BaseViewModel {
    public WaitDialog waitDialog;
    public MutableLiveData<UserInfo> userInfo = new MutableLiveData<>();

    @Override
    protected void viewCreate() {
        super.viewCreate();
        waitDialog  = new WaitDialog(getContext());
    }

    OnBase<String> callBack = new OnBase<String>() {
        @Override
        public void onError(int code, String error) {
            waitDialog.dismiss();
            IView.toast(error + code);
        }

        @Override
        public void onSuccess(String data) {
            waitDialog.dismiss();
            userInfo.setValue(userInfo.getValue());
        }
    };

    public void getSelfUserInfo() {
        waitDialog.show();
        OpenIMClient.getInstance().userInfoManager.getSelfUserInfo(new OnBase<UserInfo>() {
            @Override
            public void onError(int code, String error) {
                waitDialog.dismiss();
                IView.toast(error + code);
            }

            @Override
            public void onSuccess(UserInfo data) {
                waitDialog.dismiss();
                userInfo.setValue(data);
            }
        });

    }

    public void setSelfInfo(String nickname, String faceURL, int gender, int appMangerLevel, String phoneNumber, long birth, String email, String ex) {
        waitDialog.show();
        OpenIMClient.getInstance().userInfoManager.setSelfInfo(callBack, nickname, faceURL, gender, appMangerLevel, phoneNumber, birth, email, ex);
    }

    public void setNickname(String nickname) {
        userInfo.getValue().setNickname(nickname);
        setSelfInfo(nickname, null, 0, 0, null, 0, null, null);
    }

    public void setFaceURL(String faceURL) {
        userInfo.getValue().setFaceURL(faceURL);
        setSelfInfo(null, faceURL, 0, 0, null, 0, null, null);
    }
}