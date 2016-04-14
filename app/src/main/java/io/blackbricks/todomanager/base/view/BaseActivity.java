package io.blackbricks.todomanager.base.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import icepick.Icepick;

/**
 * Base class for Activities which already setup butterknife and icepick
 *
 * @author Hannes Dorfmann
 */
public class BaseActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    injectDependencies();
    super.onCreate(savedInstanceState);
    Icepick.restoreInstanceState(this, savedInstanceState);
  }

  @Override public void onContentChanged() {
    super.onContentChanged();
    ButterKnife.bind(this);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Icepick.saveInstanceState(this, outState);
  }

  protected void injectDependencies() {

  }
}
