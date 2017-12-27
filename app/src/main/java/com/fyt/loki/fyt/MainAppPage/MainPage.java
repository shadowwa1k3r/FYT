package com.fyt.loki.fyt.MainAppPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.fyt.loki.fyt.MainAppPage.Map.MapPage;
import com.fyt.loki.fyt.MainAppPage.Menu.MenuPage;
import com.fyt.loki.fyt.MainAppPage.Messages.MessagePage;
import com.fyt.loki.fyt.MainAppPage.News.NewsPage;
import com.fyt.loki.fyt.MainAppPage.Schedule.SchedulerPage;
import com.fyt.loki.fyt.R;
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


                        //ChangeFragment("item1");


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
        bottomBar.setDefaultTab(R.id.item4);
        messages= bottomBar.getTabWithId(R.id.item2);
        messages.setBadgeCount(1);




    }
    private void clearBackStack() {
        FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(
                0);
        getSupportFragmentManager().popBackStack(entry.getId(),FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().executePendingTransactions();


    }
    public void clearBackStackInclusive(String tag) {
        getSupportFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
            case "item2": fragment = new MessagePage();messages.removeBadge();break;
            case "item3": fragment= new SchedulerPage();break;
            case "item4": fragment= new MapPage();break;
            case "item5": fragment= MenuPage.newInstance(Token,UserName);break;
        }

        if(fragment!=null) {int a = getSupportFragmentManager().getBackStackEntryCount();
        for(int i = 0;i<a;i++)
            {getSupportFragmentManager().popBackStack();
            Toast.makeText(this,getSupportFragmentManager().getBackStackEntryCount()+"s",Toast.LENGTH_LONG).show();}
         getSupportFragmentManager().popBackStack();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.contentContainer, fragment)
                    .commit();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      /*  for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }*/
        Toast.makeText(getApplicationContext(),"activity",Toast.LENGTH_LONG).show();


    }
/*
    @Override
    public void onBackPressed(){
        if(getSupportFragmentManager().getBackStackEntryCount()==0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error")
                    .setMessage("Are you sure want to exit?")
                    .setIcon(R.drawable.warning)
                    .setCancelable(false)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else onBackPressed();
    }*/

}
