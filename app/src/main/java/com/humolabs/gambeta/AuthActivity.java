package com.humolabs.gambeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.Arrays;
import java.util.List;

public class AuthActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Drawer mDrawerResult;
    AccountHeader mHeaderResult;
    ProfileDrawerItem mProfileDrawerItem;
    PrimaryDrawerItem mItemLogin, mItemLogout, mItemVerifiedProfile, mItemHome, mItemSettings, mItemUnverifiedProfile, mCurrentProfile;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.PhoneBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        setupToolbar();
        instantiateMenuItems();
        intstantiateUser();
        setupProfileDrawer();
        setupNavigationDrawerWithHeader();
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(mToolbar);
    }

    private void instantiateMenuItems() {
        mItemVerifiedProfile = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.verified_profile).withIcon(getResources().getDrawable(R.mipmap.ic_verified_user_black_24dp));
        mItemUnverifiedProfile = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.unverified_profile).withIcon(getResources().getDrawable(R.mipmap.ic_report_problem_black_24dp));

        mItemLogin = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.login_menu_item).withIcon(getResources().getDrawable(R.mipmap.ic_login_black_48dp));
        mItemLogout = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.logout_menu_item).withIcon(getResources().getDrawable(R.mipmap.ic_logout_black_48dp));
        ;

        mItemHome = new PrimaryDrawerItem().withIdentifier(5).withName(R.string.home).withIcon(getResources().getDrawable(R.mipmap.ic_home_black_48dp));
        mItemSettings = new PrimaryDrawerItem().withIdentifier(6).withName(R.string.settings).withIcon(getResources().getDrawable(R.mipmap.ic_settings_black_48dp));
    }

    private void setupProfileDrawer() {
        //check if the user is logged in. If logged in, get details (name, email, pic etc) dynamically
        //For demonstration purpose, I have set a personal photo hard coded. In real-time, we can easily
        // pass the actual photo dynamically.
        if (mFirebaseUser != null) {
            mProfileDrawerItem = new ProfileDrawerItem()
                    .withName(mFirebaseUser.getDisplayName())
                    .withEmail(mFirebaseUser.getEmail())
                    .withIcon(getResources().getDrawable(R.drawable.profile));
        } else {//else if the user is not logged in, show a default icon
            mProfileDrawerItem = new ProfileDrawerItem()
                    .withIcon(getResources().getDrawable(R.mipmap.ic_account_circle_black_48dp));
        }
    }

    private AccountHeader setupAccountHeader() {
        mHeaderResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(mProfileDrawerItem)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                }).withSelectionListEnabledForSingleProfile(false)
                .build();
        return mHeaderResult;
    }

    private void setupNavigationDrawerWithHeader() {
        //Depending on user is logged in or not, decide whether to show Log In menu or Log Out menu
        if (!isUserSignedIn()){
            mDrawerResult = new DrawerBuilder()
                    .withActivity(this)
                    .withAccountHeader(setupAccountHeader())
                    .withToolbar(mToolbar)
                    .addDrawerItems(mItemLogin, new DividerDrawerItem(), mItemHome,mItemSettings)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            onNavDrawerItemSelected((int)drawerItem.getIdentifier());
                            return true;
                        }
                    })
                    .build();
            mDrawerResult.deselect(mItemLogin.getIdentifier());
        }else{
            mCurrentProfile = checkCurrentProfileStatus();
            mDrawerResult = new DrawerBuilder()
                    .withActivity(this)
                    .withAccountHeader(setupAccountHeader())
                    .withToolbar(mToolbar)
                    .addDrawerItems(mCurrentProfile, mItemLogout, new DividerDrawerItem(), mItemHome,mItemSettings)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            onNavDrawerItemSelected((int)drawerItem.getIdentifier());
                            return true;
                        }
                    })
                    .build();
        }
        mDrawerResult.closeDrawer();
    }

    private void intstantiateUser() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    private void onNavDrawerItemSelected(int drawerItemIdentifier) {
        switch (drawerItemIdentifier) {
            //Sign In
            case 3:
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN);
                break;
            //Sign Out
            case 4:
                signOutUser();
                Toast.makeText(this, "Logout menu selected", Toast.LENGTH_LONG).show();
                break;
            //Home
            case 5:
                Toast.makeText(this, "Home menu selected", Toast.LENGTH_LONG).show();
                break;
            //Settings
            case 6:
                Toast.makeText(this, "Settings menu selected", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            // Successfully signed in
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, R.string.login_success, Toast.LENGTH_LONG).show();
                signInUser();
                return;
            } else {
                //User pressed back button
                if (response == null) {
                    Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show();
                    mDrawerResult.deselect(mItemLogin.getIdentifier());
                    return;
                }
                //No internet connection.
                if (response.getError() != null && response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, R.string.no_connectivity, Toast.LENGTH_LONG).show();
                    return;
                }
                //Unknown error
                if (response.getError() != null && response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(this, R.string.login_unknown_Error, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void refreshMenuHeader() {
        mDrawerResult.closeDrawer();
        mHeaderResult.clear();
        setupProfileDrawer();
        setupAccountHeader();
        mDrawerResult.setHeader(mHeaderResult.getView());
        mDrawerResult.resetDrawerContent();
    }

    private void signInUser() {
        intstantiateUser();
        if (!mFirebaseUser.isEmailVerified()) {
            //mFirebaseUser.sendEmailVerification();
        }
        mCurrentProfile = checkCurrentProfileStatus();
        mDrawerResult.updateItemAtPosition(mCurrentProfile, 1);
        mDrawerResult.addItemAtPosition(mItemLogout, 2);
        mDrawerResult.deselect(mItemLogout.getIdentifier());
        refreshMenuHeader();
        ((TextView) findViewById(R.id.content)).setText(R.string.welcome_on_signin);
    }

    private void signOutUser() {
        //Sign out
        mFirebaseAuth.signOut();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (!isUserSignedIn()) {

            mDrawerResult.updateItemAtPosition(mItemLogin, 1);
            mDrawerResult.removeItemByPosition(2);

            mDrawerResult.deselect(mItemLogin.getIdentifier());
            refreshMenuHeader();
            ((TextView) findViewById(R.id.content)).setText(R.string.default_nouser_signin);
        } else {
            //check if internet connectivity is there
        }
    }

    private PrimaryDrawerItem checkCurrentProfileStatus(){
        if (mFirebaseUser.isEmailVerified()){
            mCurrentProfile = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.verified_profile).withIcon(getResources().getDrawable(R.mipmap.ic_verified_user_black_24dp));;
        }else{
            mCurrentProfile = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.unverified_profile).withIcon(getResources().getDrawable(R.mipmap.ic_report_problem_black_24dp));
        }
        return mCurrentProfile;
    }

    private boolean isUserSignedIn(){
        return mFirebaseUser != null;
    }
}
