package com.devgd.melonclone.domain.search.domain;

import org.watermelon.framework.global.model.domain.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Ads implements Domain {
    String adsImgUrl;
    String adsLinkUrl;
}
