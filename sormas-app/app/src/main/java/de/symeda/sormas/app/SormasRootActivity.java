package de.symeda.sormas.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import de.symeda.sormas.app.caze.CaseNewActivity;
import de.symeda.sormas.app.caze.CasesActivity;
import de.symeda.sormas.app.contact.ContactNewActivity;
import de.symeda.sormas.app.event.EventsActivity;
import de.symeda.sormas.app.contact.ContactsActivity;
import de.symeda.sormas.app.rest.RetroProvider;
import de.symeda.sormas.app.sample.SamplesActivity;
import de.symeda.sormas.app.task.TasksActivity;
import de.symeda.sormas.app.settings.SettingsActivity;

public abstract class SormasRootActivity extends AppCompatActivity {

    private ActionBarDrawerToggle menuDrawerToggle;
    private DrawerLayout menuDrawerLayout;
    private String[] menuTitles;
    private ListView menuDrawerList;

    private CharSequence mainViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!RetroProvider.isInitialized()) {
            RetroProvider.init(this);
        }

        menuTitles = new String[]{
                getResources().getString(R.string.main_menu_cases),
                getResources().getString(R.string.main_menu_contacts),
                getResources().getString(R.string.main_menu_tasks),
                getResources().getString(R.string.main_menu_events),
                getResources().getString(R.string.main_menu_samples),
                getResources().getString(R.string.main_menu_user)
                };

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        menuDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        menuDrawerList.setAdapter(
                new ArrayAdapter<String>(
                        this,
                        R.layout.drawer_list_item,
                        menuTitles));
        // Set the list's click listener
        menuDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


        setupDrawer();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (menuDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mainViewTitle = title;
        getSupportActionBar().setTitle(mainViewTitle);
    }

    private void setupDrawer() {

        menuDrawerToggle = new ActionBarDrawerToggle(
                this,
                menuDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(mainViewTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        menuDrawerToggle.setDrawerIndicatorEnabled(true);
        menuDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
        menuDrawerLayout.addDrawerListener(menuDrawerToggle);


    }

    // go to cases overview
    public void showCasesView() {
        Intent intent = new Intent(this, CasesActivity.class);
        startActivity(intent);
    }

    public void showCaseNewView() {
        Intent intent = new Intent(this, CaseNewActivity.class);
        startActivity(intent);
    }

    // go to contacts overview
    public void showContactsView() {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    public void showContactNewView() {
        Intent intent = new Intent(this, ContactNewActivity.class);

    }

    // go to tasks overview
    public void showTasksView() {
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }

    // go to tasks overview
    public void showEventsView() {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }

    // go to samples overview
    public void showSamplesView() {
        Intent intent = new Intent(this, SamplesActivity.class);
        startActivity(intent);
    }

    public void showSettingsView() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }



    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Cases
        if(position==0) {
            showCasesView();
        }

        // Contacts
        else if(position==1) {
            showContactsView();
        }

        // Tasks
        else if(position==2) {
            showTasksView();
        }
        // Events
        else if(position==3) {
            showEventsView();
        }
        // Samples
        else if(position==4) {
            showSamplesView();
        }
        // Users
        else if(position==5) {
            showSettingsView();
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            menuDrawerLayout.closeDrawers();
        }
    }


}
