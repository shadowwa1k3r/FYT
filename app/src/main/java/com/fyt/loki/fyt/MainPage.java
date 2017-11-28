package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

public class MainPage extends AppCompatActivity {

    private String Token,UserName;
    BottomBarTab messages;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            Token = bundle.getString("Token");
            UserName = bundle.getString("UName");
        }

        FrameLayout fl=(FrameLayout)findViewById(R.id.contentContainer);


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
        messages= bottomBar.getTabWithId(R.id.item2);
        messages.setBadgeCount(5);




    }
    private void ChangeFragment(String value){
        Fragment fragment = null;
        switch (value) {
            case "item1":  try {
                fragment = NewsPage.newInstance(Token,UserName);
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
            break;
            case "item2": fragment = new ChatPage();messages.removeBadge();break;
            case "item3": fragment= new SchedulerPage();break;
            case "item4": fragment= new MapPage();break;
            case "item5": fragment= MenuPage.newInstance(Token,UserName);break;
        }

        if(fragment!=null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.contentContainer, fragment)
                    .commit();

    }
}
