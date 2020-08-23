package com.liyuan.github.api;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

public class GithubAdapter implements ApiAdapter<Github> {
    private GithubUser user;

    public GithubAdapter() {
    }

    public GithubAdapter(String id) {
    }

    @Override
    public boolean test(Github api) {
        System.out.println("com.liyuan.github.api.GithubAdapter.test is invoked!");
        return true;
    }

    @Override
    public void setConnectionValues(Github api, ConnectionValues values) {
        user = api.userOperations().getUserProfile();
        values.setProviderUserId(user.getProviderUserId());
        values.setDisplayName(user.getUsername());
        values.setImageUrl(user.getAvatar_url());
    }

    @Override
    public UserProfile fetchUserProfile(Github api) {
//        api.userOperations().getUserProfile();
//        return user;
        throw new UnsupportedOperationException("不想实现这个,因为github实在太卡了-》com.liyuan.github.api.GithubAdapter.fetchUserProfile");
//        return new UserProfile(id, null, );
    }

    @Override
    public void updateStatus(Github api, String message) {

    }
}
