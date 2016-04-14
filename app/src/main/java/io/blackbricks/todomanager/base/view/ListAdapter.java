package io.blackbricks.todomanager.base.view;

import android.content.Context;

import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;

import java.util.List;

/**
 * @author Hannes Dorfmann
 */
public class ListAdapter<T extends List>extends SupportAnnotatedAdapter {

  protected T items;

  public ListAdapter(Context context) {
    super(context);
  }

  @Override public int getItemCount() {
    return items == null ? 0 : items.size();
  }

  public T getItems() {
    return items;
  }

  public void setItems(T items) {
    this.items = items;
  }
}
