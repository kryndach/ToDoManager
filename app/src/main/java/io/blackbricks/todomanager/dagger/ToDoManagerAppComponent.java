package io.blackbricks.todomanager.dagger;

import android.content.Context;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Component;
import io.blackbricks.todomanager.IntentStarter;
import io.blackbricks.todomanager.background.Alarm;
import io.blackbricks.todomanager.background.AlarmEndOfDayReceiver;
import io.blackbricks.todomanager.database.DatabaseHelper;
import io.blackbricks.todomanager.database.DatabaseModule;
import io.blackbricks.todomanager.database.DatabaseOperationHelper;
import io.blackbricks.todomanager.menu.MenuComponent;
import io.blackbricks.todomanager.menu.MenuView;
import io.blackbricks.todomanager.menu.model.MenuProvider;
import io.blackbricks.todomanager.model.AttachmentProvider;
import io.blackbricks.todomanager.model.GroupProvider;
import io.blackbricks.todomanager.model.IconProvider;
import io.blackbricks.todomanager.model.TaskProvider;
import io.blackbricks.todomanager.photoPicker.model.PhotoPickerProvider;
import io.blackbricks.todomanager.taskList.model.TaskListPresentationProvider;

/**
 * Created by yegorkryndach on 15/04/16.
 */
@Singleton
@Component(
        modules = {ToDoManagerModule.class, DatabaseModule.class}
)
public interface ToDoManagerAppComponent {
    // Singletons
    IntentStarter intentStarter();
    EventBus eventBus();
    StorIOSQLite storio();
    Alarm alarm();
    DatabaseOperationHelper dbOperationHelper();

    // Providers
    MenuProvider menuProvider();
    GroupProvider groupProvider();
    IconProvider iconProvider();
    TaskProvider taskProvider();
    AttachmentProvider attachmentProvider();
    PhotoPickerProvider photoPickerProvider();
}
