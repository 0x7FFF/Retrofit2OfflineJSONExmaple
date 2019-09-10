package com.smakhorin.Retrofit2OfflineJSONExample.Utils;

import com.smakhorin.Retrofit2OfflineJSONExample.REST.DTO.UserData;

import java.util.Comparator;

public class NameComparator implements Comparator<UserData> {
    @Override
    public int compare(UserData o1, UserData o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
