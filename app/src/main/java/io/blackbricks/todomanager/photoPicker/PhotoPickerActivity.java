package io.blackbricks.todomanager.photoPicker;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.base.view.BaseActivity;
import io.blackbricks.todomanager.utils.BuildUtils;

/**
 * Created by yegorkryndach on 10/05/16.
 */
public class PhotoPickerActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.contentView)
    ViewGroup contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker);

        // Activity Transitions
        if (BuildUtils.isMinApi21()) {
            postponeEnterTransition();
        }

        toolbar.setNavigationIcon(BuildUtils.getBackArrowDrawable(this));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 21) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });

        toolbar.inflateMenu(R.menu.add_photo_menu);

        final PhotoPickerFragment fragment = new PhotoPickerFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, fragment)
                .commit();

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.save) {
                    fragment.save();
                    return true;
                }
                return false;
            }
        });
        toolbarTitle.setText(R.string.chose_photo);
    }
}
