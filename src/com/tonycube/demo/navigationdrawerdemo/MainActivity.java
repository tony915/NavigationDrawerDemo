package com.tonycube.demo.navigationdrawerdemo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawer_menu);
		
		List<HashMap<String,String>> lstData = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, String> mapValue = new HashMap<String, String>();
			mapValue.put("icon", Integer.toString(R.drawable.ic_launcher));
			mapValue.put("title", drawer_menu[i]);
			lstData.add(mapValue);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, lstData, R.layout.drawer_list_item2, new String[]{"icon", "title"}, new int[]{R.id.imgIcon, R.id.txtItem});
		lstDrawer.setAdapter(adapter);
		
		//������I�ﰻť��
		lstDrawer.setOnItemClickListener(new DrawerItemClickListener());
	}

	//================================================================================
	// Action Button �إߤ��I��ƥ�
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
	// ������I��ƥ�
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
			//�٨S�s�@���ﶵ�Afragment �O null�A������^
			return;
		}

        FragmentManager fragmentManager = getFragmentManager();
        //[��k1]�����m���A�L�k�� Back ��^
//        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        //[��k2]�}�ҨñN�e�@�Ӱe�J���|
        //���n�I �����[�g "onBackPressed"
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.content_frame, fragment);
		fragmentTransaction.addToBackStack("home");
		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		fragmentTransaction.commit();
        

        // ��s�Q��ܶ��ءA�����D��r�A�������
        lstDrawer.setItemChecked(position, true);
        setTitle(drawer_menu[position]);
        layDrawer.closeDrawer(lstDrawer);
    }
	
	@Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
	
	/**
	 * Back ��B�z
	 * ��̫�@�� stack �� R.id.content_frame, ���� App
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		FragmentManager fragmentManager = this.getFragmentManager();
		int stackCount = fragmentManager.getBackStackEntryCount();
		if (stackCount == 0) {
			this.finish();
		}
	}
	
}
