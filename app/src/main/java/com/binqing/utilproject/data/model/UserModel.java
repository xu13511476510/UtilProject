package com.binqing.utilproject.data.model;

import com.binqing.utilproject.data.annotation.Member;
import com.binqing.utilproject.data.object.UserObject;

public class UserModel {
    @Member (order = 0)
    public int uid;

    @Member (order = 1)
    public String account;

    @Member (order = 2)
    public String uname;

    public UserObject fromModel() {
        UserObject object = new UserObject();
        object.setAccount(account);
        object.setUid(uid);
        object.setUname(uname);
        return object;
    }
}
