package com.devgd.melonclone;

import android.util.Log;

import org.junit.Test;
import org.watermelon.framework.utils.db.SPHelper;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    public void ASP_HELPER_TEST() {
        SPHelper.getInstance().set("my_key", "my_value");
        Log.d("TEST", "[GET_VALUE] "+ SPHelper.getInstance().getValue("my_key"));

        Map<String, String> all = SPHelper.getInstance().getAll();
        for (String key : all.keySet()) {
            Log.d("TEST", "[GET_ALL] " + all.get(key));
        }

        SPHelper.getInstance().clear();

        Log.d("TEST", "[GET_VALUE_AFTER_CLEAR] "+SPHelper.getInstance().getValue("my_key"));
    }
}