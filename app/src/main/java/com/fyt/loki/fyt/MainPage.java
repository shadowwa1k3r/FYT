package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainPage extends AppCompatActivity {

    private String Token,UserName;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            Token = bundle.getString("Token");
            UserName = bundle.getString("UName");
        }

        BottomBar bottomBar = (BottomBar)findViewById(R.id.bottom_navigationbar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.item1:
                        ChangeFragment("item1");
                        break;
                    case R.id.item2:
                        ChangeFragment("item2");
                        break;
                    case R.id.item3:
                        ChangeFragment("item3");
                        break;
                    case R.id.item4:
                        ChangeFragment("item4");
                        break;
                    case R.id.item5:
                        ChangeFragment("item5");
                        break;

                }

            }
        });




    }
    private void ChangeFragment(String value){
        Fragment fragment = null;
        switch (value) {
            case "item1":    fragment = NewsPage.newInstance(Token,UserName);break;
            case "item2": fragment = new MessagePage();break;
            case "item3": fragment= new SchedulerPage();break;
            case "item4": fragment= new MapPage();break;
            case "item5": fragment= ProfilePage.newInstance(Token,UserName);break;
        }

        if(fragment!=null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.grow_from_middle,R.anim.shrink_to_middle)
                    .replace(R.id.contentContainer, fragment)
                    .commit();

    }
}
