package com.autoapp.ct.cryptrack.helper;

/**
 * Created by shwg on 10/8/2017.
 */

public class QueryFactory {

    public static String getQueryPattern(QueryType queryType) {
        switch (queryType) {
            case BIT_COIN:
                return "1 Bitcoin = \\d+.\\d+ US dollars";
            default:
                return "";
        }
    }

    public static String getQuery(QueryType queryType) {
        switch (queryType) {
            case BIT_COIN:
                return "bitcoin price usd";
            default:
                return "";
        }
    }
}
