package com.devgd.melonclone.utils.store;

import com.devgd.melonclone.domain.search.domain.Ads;

import java.util.ArrayList;
import java.util.List;

public class AdsSample {

    public static List<Ads> getSampleList() {
        ArrayList<Ads> adsList = new ArrayList<>();
        adsList.add(new Ads("https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80", "http://localhost"));
        adsList.add(new Ads("https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80", "http://localhost"));
        adsList.add(new Ads("https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80", "http://localhost"));
        adsList.add(new Ads("https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80", "http://localhost"));


        return adsList;
    }
}
