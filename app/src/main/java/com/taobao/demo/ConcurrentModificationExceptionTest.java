package com.taobao.demo;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jason on 18/5/2.
 */

public class ConcurrentModificationExceptionTest {
    public void testConcurrent() {
        ArrayList<String> testArry = new ArrayList();

        //Test
        for (String s : testArry) {
            testArry.add("test");
        }

        Iterator iterator = testArry.iterator();

        while (iterator.hasNext()) {
            testArry.add("test");
//            iterator.remove();
        }

    }
}
