package com.boxcast.android;

/**
 * Created by camdenfullmer on 5/17/17.
 */

class QueryBuilder {

    private String mString;

    enum Logic {
        AND {
            @Override public String toString() {
                return "+";
            }
        },
        OR {
            @Override public String toString() {
                return "";
            }
        }
    }

    QueryBuilder() {
        mString = "";
    }

    void append(Logic logic, String key, String value) {
        if (mString.isEmpty()) {
            mString = logic.toString() + key + ":" + value;
        } else {
            mString += " " + logic.toString() + key + ":" + value;
        }
    }

    String build() {
        return mString;
    }

}
