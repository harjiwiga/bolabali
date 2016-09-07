package com.example.harjiwigaasmoko.irabukatoko;

import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.example.harjiwigaasmoko.irabukatoko.entity.User;

import junit.framework.TestCase;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by harjiwigaasmoko on 8/13/16.
 */



public class IraBukatokoTest extends TestCase{

    @SmallTest
    public void testSortList(){

        User user1= new User();
        User user2= new User();
        User user3= new User();

        user1.setId(1);
        user1.setName("harji");
        user2.setId(2);
        user2.setName("ira");
        user3.setId(3);
        user3.setName("iraha");

        List<User>users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        Comparator<User> comparator = new Comparator<User>() {
            public int compare(User c1, User c2) {
                return c2.getId() - c1.getId(); // use your logic
            }
        };
        Collections.sort(users,comparator );

        Log.i("sortList", "testDone");

        Log.i("sortList","users:"+users);
        System.out.println("users out:"+users);
    }
}
