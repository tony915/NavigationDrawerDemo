package com.tonycube.demo.navigationdrawerdemo;


import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private DrawerLayout layDrawer;
	private ListView lstDrawer;
	
	private ActionBarDrawerToggle drawerToggle;
	private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    
    private String[] drawer_menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initActionBar();
		initDrawer();
		initDrawerList();
		
		if (savedInstanceState == null) {
            selectItem(0);
        }
	}
	
	//================================================================================
	// Init
	//================================================================================
	private void initActionBar(){
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
	}
	
	private void initDrawer(){
		setContentView(R.layout.drawer);
		
		layDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		lstDrawer = (ListView) findViewById(R.id.left_drawer);
		
		layDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		mTitle = mDrawerTitle = getTitle();
		drawerToggle = new ActionBarDrawerToggle(
				this, 
				layDrawer,
				R.drawable.ic_drawer, 
				R.string.drawer_open,
				R.string.drawer_close) {

			@Override
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(mTitle);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(mDrawerTitle);
			}
		};
		drawerToggle.syncState();
		
		layDrawer.setDrawerListener(drawerToggle);
	}
	
	private void initDrawerList(){
		drawer_menu = this.getResources().getStringArray(R.array.drawer_menu);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawer_menu);
		lstDrawer.setAdapter(adapter);
		
		//側選單點選偵聽器
		lstDrawer.setOnItemClickListener(new DrawerItemClickListener());
	}

	//================================================================================
	// Action Button 建立及點選事件
	//================================================================================
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		//home
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		//action buttons
		switch (item.getItemId()) {
		case R.id.action_edit:
			//....
			break;

		case R.id.action_search:
			//....
			break;
			
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	//================================================================================
	// 側選單點選事件
	//================================================================================
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
	
	private void selectItem(int position) {
        Fragment fragment = null;
        
        switch (position) {
		case 0:
			fragment = new FragmentApple();
			break;
			
		case 1:
			fragment = new FragmentBook();
			break;
			
		case 2:
			fragment = new FragmentCat();
			Bundle args = new Bundle();
	        args.putString(FragmentCat.CAT_COLOR, "Brown");
	        fragment.setArguments(args);
			break;

		default:
			//還沒製作的選項，fragment 是 null，直接返回
			return;
		}

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // 更新被選擇項目，換標題文字，關閉選單
        lstDrawer.setItemChecked(position, true);
        setTitle(drawer_menu[position]);
        layDrawer.closeDrawer(lstDrawer);
    }
	
	@Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
	
}
