package com.example.lotto.analyzer.utils;

import com.example.lotto.analyzer.Entity.GameType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlWithParametersCreator {
    private final String apiUrl = "https://www.lotto.pl/api/lotteries/draw-results/by-gametype?";

    public String getApiUrlWithParameters(final GameType game, final int index, final int size, final String sort, final String order) {
        return apiUrl + "game=" + game + "&index=" + index + "&size=" + size + "&sort=" + sort + "&order=" + order;
    }

    public String getApiUrlWithParameters(final GameType game, final int index, final int size) {
        return apiUrl + "game=" + game + "&index=" + index + "&size=" + size + "&sort=drawDate&order=DESC";
    }

    public String getApiUrlWithParameters(final GameType game) {
        return apiUrl + "game=" + game + "&index=1&size=1000000&sort=drawDate&order=DESC";
    }
}
