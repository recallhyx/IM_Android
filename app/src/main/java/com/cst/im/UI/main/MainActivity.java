package com.cst.im.UI.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.cst.im.R;
import com.cst.im.UI.main.discovery.DiscoveryFragment;
import com.cst.im.UI.main.friend.FriendViewFragment;
import com.cst.im.UI.main.me.SettingFragment;
import com.cst.im.UI.main.msg.MsgFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //导航栏切换的Fragment切换 相当于Mux
            switch (item.getItemId()) {
                case R.id.navigation_chat:
                    transaction.replace(R.id.content, new MsgFragment()).commit();
                    return true;
                case R.id.navigation_contact:
                    transaction.replace(R.id.content, new FriendViewFragment()).commit();
                    return true;
                case R.id.navigation_discovery:
                    transaction.replace(R.id.content, new DiscoveryFragment()).commit();
                    return true;
                case R.id.navigation_me:
                    transaction.replace(R.id.content, new SettingFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setCurrentFragment();
    }

    private void setCurrentFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MsgFragment msgFragment = new MsgFragment();
        transaction.replace(R.id.content, msgFragment).commit();
    }


}
