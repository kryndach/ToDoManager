package io.blackbricks.todomanager.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.ParcelableDataLceViewState;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.blackbricks.todomanager.IntentStarter;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;
import io.blackbricks.todomanager.database.DatabaseModule;
import io.blackbricks.todomanager.database.DatabaseOperationHelper;
import io.blackbricks.todomanager.model.Attachment;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.task.model.AttachmentPresentation;
import io.blackbricks.todomanager.task.model.TaskPresentation;
import io.blackbricks.todomanager.ui.AspectRatioImageView;
import io.blackbricks.todomanager.ui.AttachmentListAdapter;
import io.blackbricks.todomanager.ui.GridInsetDecoration;
import io.blackbricks.todomanager.ui.GroupListAdapter;
import io.blackbricks.todomanager.ui.IconListAdapter;
import rx.functions.Action1;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskFragment extends BaseLceFragment<FrameLayout, TaskPresentation, TaskView, TaskPresenter>
        implements TaskView, AttachmentListAdapter.AttachmentClickListener, GroupListAdapter.GroupClickListener, IconListAdapter.IconClickListener {

    @Arg(required = false)
    Integer taskId;

    @Arg(required = false)
    Integer groupId;

    @Bind(R.id.title_edit_text)
    EditText titleEditText;
    @Bind(R.id.title_clear_view)
    ViewGroup titleClearView;

    @Bind(R.id.description_edit_text)
    EditText descriptionEditText;
    @Bind(R.id.description_clear_view)
    ViewGroup descriptionClearView;

    @Bind(R.id.group_view)
    ViewGroup groupView;
    @Bind(R.id.group_text_view)
    TextView groupTextView;
    @Bind(R.id.group_clear_view)
    ViewGroup groupClearView;

    @Bind(R.id.alarm_view)
    ViewGroup alarmView;
    @Bind(R.id.alarm_text_view)
    TextView alarmTextView;
    @Bind(R.id.alarm_clear_view)
    ViewGroup alarmClearView;

    @Bind(R.id.deadline_view)
    ViewGroup deadlineView;
    @Bind(R.id.deadline_text_view)
    TextView deadlineTextView;
    @Bind(R.id.deadline_clear_view)
    ViewGroup deadlineClearView;

    @Bind(R.id.icon_view)
    ViewGroup iconView;
    @Bind(R.id.icon_text_view)
    TextView iconTextView;
    @Bind(R.id.icon_clear_view)
    ViewGroup iconClearView;
    @Bind(R.id.icon_image)
    ImageView iconImageView;

    @Bind(R.id.photo_view)
    ViewGroup photoView;
    @Bind(R.id.photo_list)
    RecyclerView photoList;

    @Inject
    DatabaseOperationHelper dbOperationHelper;

    @Inject
    IntentStarter intentStarter;

    private AlertDialog dialog;

    private TaskComponent taskComponent;
    private TaskPresentation taskPresentation;

    private AttachmentListAdapter attachmentListAdapter;
    private GroupListAdapter groupListAdapter;
    private IconListAdapter iconListAdapter;

    private Uri tempImageUri;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_task;
    }

    @Override
    public LceViewState<TaskPresentation, TaskView> createViewState() {
        return new ParcelableDataLceViewState<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        attachmentListAdapter = new AttachmentListAdapter(getActivity(), null, this);
        groupListAdapter = new GroupListAdapter(getActivity(), null, this);
        iconListAdapter = new IconListAdapter(getActivity(), null, this);

        final StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        photoList.setLayoutManager(layout);
        photoList.setItemAnimator(new DefaultItemAnimator());
        photoList.addItemDecoration(new GridInsetDecoration(getContext(), R.dimen.grid_inset));
        photoList.setAdapter(attachmentListAdapter);
    }

    @Override
    public TaskPresentation getData() {
        return taskPresentation;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public TaskPresenter createPresenter() {
        return taskComponent.presenter();
    }

    @Override
    protected void injectDependencies() {
        taskComponent = DaggerTaskComponent.builder()
                .toDoManagerAppComponent(ToDoManagerApp.getAppComponent())
                .databaseModule(new DatabaseModule())
                .toDoManagerModule(new ToDoManagerModule(ToDoManagerApp.get(this.getActivity())))
                .build();
        taskComponent.inject(this);
    }

    //// User actions

    // Title
    @OnClick(R.id.title_clear_view)
    void onClickClearTitle() {
        taskPresentation.getTask().setTitle(null);
        titleEditText.setText(null);
        updateTitleClearButton();
    }

    @OnTextChanged(value = R.id.title_edit_text)
    void onTextChangedTitle(CharSequence text) {
        if (text.length() > 0) {
            taskPresentation.getTask().setTitle(text.toString());
        } else {
            taskPresentation.getTask().setTitle(null);
        }
        updateTitleClearButton();
    }

    private void updateTitleClearButton() {
        if (taskPresentation.getTask().getTitle() == null) {
            titleClearView.setVisibility(View.GONE);
        } else {
            titleClearView.setVisibility(View.VISIBLE);
        }
    }

    private void updateTitle() {
        if (taskPresentation.getTask().getTitle() == null) {
            titleEditText.setText(null);
        } else {
            titleEditText.setText(taskPresentation.getTask().getTitle());
        }
        updateTitleClearButton();
    }

    // Description
    @OnClick(R.id.description_clear_view)
    void onClickClearDescription() {
        taskPresentation.getTask().setDescription(null);
        descriptionEditText.setText(null);
        updateDescriptionClearButton();
    }

    @OnTextChanged(value = R.id.description_edit_text)
    void onTextChangedDescription(CharSequence text) {
        if (text.length() > 0) {
            taskPresentation.getTask().setDescription(text.toString());
        } else {
            taskPresentation.getTask().setDescription(null);
        }
        updateDescriptionClearButton();
    }

    private void updateDescriptionClearButton() {
        if (taskPresentation.getTask().getDescription() == null) {
            descriptionClearView.setVisibility(View.GONE);
        } else {
            descriptionClearView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDescription() {
        if (taskPresentation.getTask().getDescription() == null) {
            descriptionEditText.setText(null);
        } else {
            descriptionEditText.setText(taskPresentation.getTask().getDescription());
        }
    }

    // Group
    @OnClick(R.id.group_view)
    void onClickGroup() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RecyclerView groupRecyclerView = (RecyclerView) inflater.inflate(R.layout.dialog_group_list, null);

        LinearLayoutManager linearLayoutManagerFilter = new LinearLayoutManager(getContext());
        groupRecyclerView.setLayoutManager(linearLayoutManagerFilter);
        groupRecyclerView.setAdapter(groupListAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogTheme);
        builder.setView(groupRecyclerView);
        builder.setTitle("Chose group");
        dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.group_clear_view)
    void onClickClearGroup() {
        taskPresentation.setGroup(null);
        taskPresentation.getTask().setGroupId(null);
        updateGroup();
    }

    private void updateGroup() {
        Group group = taskPresentation.getGroup();
        if (group != null) {
            groupTextView.setText(group.getName());
            groupClearView.setVisibility(View.VISIBLE);
        } else {
            groupTextView.setText(null);
            groupClearView.setVisibility(View.GONE);
        }
    }

    // Alert
    @OnClick(R.id.alarm_view)
    void onClickAlarm() {
        showDatePickerDialog(onAlarmDateSetListener);
    }

    @OnClick(R.id.alarm_clear_view)
    void onClickClearAlarm() {
        taskPresentation.getTask().setDateAlarm(null);
        updateAlarm();
    }

    private void updateAlarm() {
        if (taskPresentation.getTask().getDateAlarm() != null) {
            SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
            String textAlarm = format.format(taskPresentation.getTask().getDateAlarm());
            alarmTextView.setText(textAlarm);
            alarmClearView.setVisibility(View.VISIBLE);
        } else {
            alarmTextView.setText(null);
            alarmClearView.setVisibility(View.GONE);
        }
    }

    // Deadline
    @OnClick(R.id.deadline_view)
    void onClickDeadline() {
        showDatePickerDialog(onDeadlineDateSetListener);
    }

    @OnClick(R.id.deadline_clear_view)
    void onClickClearDeadline() {
        taskPresentation.getTask().setDateDeadline(null);
        updateDeadline();
    }

    private void updateDeadline() {
        if (taskPresentation.getTask().getDateDeadline() != null) {
            SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy");
            String textAlarm = format.format(taskPresentation.getTask().getDateDeadline());
            deadlineTextView.setText(textAlarm);
            deadlineClearView.setVisibility(View.VISIBLE);
        } else {
            deadlineTextView.setText(null);
            deadlineClearView.setVisibility(View.GONE);
        }
    }

    private void showDatePickerDialog(DatePickerDialog.OnDateSetListener onDateSetListener) {
        Calendar currentTime = Calendar.getInstance();
        int year = currentTime.get(Calendar.YEAR);
        int month = currentTime.get(Calendar.MONTH);
        int day = currentTime.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), R.style.DialogTheme, onDateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private int datePickerYear;
    private int datePickerMonth;
    private int datePickerDay;

    TimePickerDialog.OnTimeSetListener onAlarmTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(datePickerYear, datePickerMonth, datePickerDay, hourOfDay, minute);
            taskPresentation.getTask().setDateAlarm(calendar.getTime());
            updateAlarm();
        }
    };

    DatePickerDialog.OnDateSetListener onAlarmDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            datePickerYear = year;
            datePickerMonth = monthOfYear;
            datePickerDay = dayOfMonth;

            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePicker;
            timePicker = new TimePickerDialog(getContext(), R.style.DialogTheme, onAlarmTimeSetListener, hour, minute, true);
            timePicker.show();
        }
    };

    DatePickerDialog.OnDateSetListener onDeadlineDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            taskPresentation.getTask().setDateDeadline(calendar.getTime());
            updateDeadline();
        }
    };

    // Icon
    @OnClick(R.id.icon_view)
    void onClickIcon() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RecyclerView iconRecyclerView = (RecyclerView) inflater.inflate(R.layout.dialog_icon_list, null);

        final StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        iconRecyclerView.setLayoutManager(layout);
        iconRecyclerView.setItemAnimator(new DefaultItemAnimator());
        iconRecyclerView.addItemDecoration(new GridInsetDecoration(getContext(), R.dimen.grid_inset));
        iconRecyclerView.setAdapter(iconListAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogTheme);
        builder.setView(iconRecyclerView);
        builder.setTitle("Chose icon");
        dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.icon_clear_view)
    void onClickClearIcon() {
        taskPresentation.getTask().setIconId(null);
        updateIcon();
    }

    private void updateIcon() {
        if (taskPresentation.getTask().getIconId() != null) {
            iconImageView.setImageResource(taskPresentation.getTask().getIconId());
            iconClearView.setVisibility(View.VISIBLE);
        } else {
            //TODO set default image
            iconClearView.setVisibility(View.GONE);
        }
    }

    // Attachments
    @OnClick(R.id.photo_view)
    void onClickAddAttachment() {
        final CharSequence[] attachmentTypes = {"Photo", "Library"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(attachmentTypes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: {
                        RxImagePicker
                                .with(getActivity())
                                .requestImage(Sources.CAMERA)
                                .subscribe(new Action1<Uri>() {
                                    @Override
                                    public void call(Uri uri) {
                                        Uri destinationUri = getImageUri();
                                        tempImageUri = destinationUri;
                                        Crop.of(uri, destinationUri)
                                                .asSquare()
                                                .start(getActivity(), TaskFragment.this);
                                    }
                                });
                    }
                    break;
                    case 1: {
                        Crop.pickImage(getActivity(), TaskFragment.this);
                    }
                    break;
                    default:
                        break;
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private Uri getImageUri() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "images");
        imagesFolder.mkdirs();
        File image = new File(imagesFolder, timeStamp + ".jpg");
        return Uri.fromFile(image);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == Activity.RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = getImageUri();
        Crop.of(source, destination).asSquare().start(getActivity(), this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == Activity.RESULT_OK) {
            String path = Crop.getOutput(result).getPath();
            addAttachmentByFilePath(path);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addAttachmentByFilePath(String path) {
        Attachment attachment = new Attachment.Builder()
                .path(path)
                .build();
        File file = new File(path);
        Bitmap bitmap = null;
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        AttachmentPresentation attachmentPresentation = new AttachmentPresentation.Builder()
                .attachment(attachment)
                .bitmap(bitmap)
                .build();
        taskPresentation.getAddedAttachmentPresentations()
                .add(attachmentPresentation);
        taskPresentation.getAttachmentPresentations()
                .add(attachmentPresentation);
        attachmentListAdapter.notifyDataSetChanged();
    }

    //// View interface implementation

    @Override
    public void setData(TaskPresentation data) {
        this.taskPresentation = data;
        updateTitle();
        updateDescription();
        updateGroup();
        updateAlarm();
        updateDeadline();
        updateIcon();
        attachmentListAdapter.setAttachmentPresentationList(data.getAttachmentPresentations());
        attachmentListAdapter.notifyDataSetChanged();
        iconListAdapter.setIconList(data.getIconList());
        iconListAdapter.notifyDataSetChanged();
        groupListAdapter.setGroupList(data.getGroupList());
        groupListAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadTask(taskId, groupId);
    }

    public void done() {
        dbOperationHelper.putTask(taskPresentation.getTask());
        getActivity().finish();
    }

    public void save() {
        dbOperationHelper.putTask(taskPresentation.getTask());
        getActivity().finish();
    }

    //// Adapters interface

    @Override
    public void onAttachmentClicked(AttachmentPresentation attachmentPresentation) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_attachment, null);
        AspectRatioImageView imageView = (AspectRatioImageView) view.findViewById(R.id.image);
        imageView.setImageBitmap(attachmentPresentation.getBitmap());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogTheme);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onGroupClicked(Group group) {
        taskPresentation.setGroup(group);
        taskPresentation.getTask().setGroupId(group.getId());
        updateGroup();
        dialog.dismiss();
    }

    @Override
    public void onIconClicked(Integer iconId) {
        taskPresentation.getTask().setIconId(iconId);
        updateIcon();
        dialog.dismiss();
    }
}
