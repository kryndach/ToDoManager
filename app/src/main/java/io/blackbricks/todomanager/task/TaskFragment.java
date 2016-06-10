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
import android.databinding.DataBindingUtil;
import android.databinding.adapters.FrameLayoutBindingAdapter;
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
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
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
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.blackbricks.todomanager.IntentStarter;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.background.Alarm;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;
import io.blackbricks.todomanager.database.DatabaseModule;
import io.blackbricks.todomanager.database.DatabaseOperationHelper;
import io.blackbricks.todomanager.databinding.FragmentTaskBinding;
import io.blackbricks.todomanager.model.Attachment;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.task.model.AttachmentPresentation;
import io.blackbricks.todomanager.task.model.TaskPresentation;
import io.blackbricks.todomanager.ui.AttachmentListAdapter;
import io.blackbricks.todomanager.ui.GridInsetDecoration;
import io.blackbricks.todomanager.ui.GroupListAdapter;
import io.blackbricks.todomanager.ui.IconListAdapter;
import rx.functions.Action1;


/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskFragment extends BaseLceFragment<FrameLayout, TaskPresentation, TaskView, TaskPresenter>
        implements TaskView, AttachmentListAdapter.AttachmentClickListener,
        GroupListAdapter.GroupClickListener, IconListAdapter.IconClickListener,
        Validator.ValidationListener, AttachmentListAdapter.AttachmentLongClickListener {

    @Arg(required = false)
    Integer taskId;

    @Arg(required = false)
    Integer groupId;

    @NotEmpty
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

    @Inject
    Alarm alarm;

    private FragmentTaskBinding binding;

    private AlertDialog dialog;

    private TaskComponent taskComponent;
    private TaskPresentation taskPresentation;

    private AttachmentListAdapter attachmentListAdapter;
    private GroupListAdapter groupListAdapter;
    private IconListAdapter iconListAdapter;

    private Uri tempImageUri;

    private Validator validator;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_task;
    }

    @Override
    public LceViewState<TaskPresentation, TaskView> createViewState() {
        return new ParcelableDataLceViewState<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        binding.setTaskHandlers(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        validator = new Validator(this);
        validator.setValidationListener(this);

        attachmentListAdapter = new AttachmentListAdapter(getActivity(), null, this, this);
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
                .build();
        taskComponent.inject(this);
    }

    //// User actions

    // Title
    public void onClickClearTitle(View view) {
        taskPresentation.getTask().setTitle(null);
    }

    // Description
    public void onClickClearDescription(View view) {
        taskPresentation.getTask().setDescription(null);
    }

    // Group
    public void onClickGroup(View view) {
        if(taskPresentation.getGroupList().size() != 0) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            RecyclerView groupRecyclerView = (RecyclerView) inflater.inflate(R.layout.dialog_group_list, null);

            LinearLayoutManager linearLayoutManagerFilter = new LinearLayoutManager(getContext());
            groupRecyclerView.setLayoutManager(linearLayoutManagerFilter);
            groupRecyclerView.setAdapter(groupListAdapter);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogTheme);
            builder.setView(groupRecyclerView);
            builder.setTitle(R.string.chose_group);
            dialog = builder.create();
            dialog.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.create_group_question);

            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    // Group creation
                    AlertDialog.Builder builder = new AlertDialog.Builder(TaskFragment.this.getContext());
                    builder.setTitle(R.string.name);

                    final EditText input = new EditText(TaskFragment.this.getContext());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String resultText = input.getText().toString();
                            if (resultText.length() > 0) {
                                Group group = new Group.Builder()
                                        .name(resultText)
                                        .taskCount(0)
                                        .hotTaskCount(0)
                                        .order(0)
                                        .build();
                                int groupId = dbOperationHelper.putGroup(group);
                                group.setGroupId(groupId);
                                taskPresentation.getGroupList().add(group);
                                taskPresentation.setGroup(group);
                                taskPresentation.getTask().setGroupId(group.getGroupId());
                                dialog.dismiss();
                            } else {
                                new AlertDialog.Builder(TaskFragment.this.getContext())
                                        .setTitle(R.string.error)
                                        .setMessage(R.string.need_some_symbols)
                                        .show();
                            }
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    dialogInterface.cancel();
                    AlertDialog dialog = builder.create();
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    dialog.show();
                }
            });

            builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void onClickClearGroup(View view) {
        taskPresentation.setGroup(null);
        taskPresentation.getTask().setGroupId(null);
    }

    // Alert
    public void onClickAlarm(View view) {
        showDatePickerDialog(onAlarmDateSetListener);
    }

    public void onClickClearAlarm(View view) {
        taskPresentation.getTask().setDateAlarm(null);
    }

    // Deadline
    public void onClickDeadline(View view) {
        showDatePickerDialog(onDeadlineDateSetListener);
    }

    public void onClickClearDeadline(View view) {
        taskPresentation.getTask().setDateDeadline(null);
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
        }
    };

    // Icon
    public void onClickIcon(View view) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RecyclerView iconRecyclerView = (RecyclerView) inflater.inflate(R.layout.dialog_icon_list, null);

        final StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        iconRecyclerView.setLayoutManager(layout);
        iconRecyclerView.setItemAnimator(new DefaultItemAnimator());
        iconRecyclerView.addItemDecoration(new GridInsetDecoration(getContext(), R.dimen.grid_inset));
        iconRecyclerView.setAdapter(iconListAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogTheme);
        builder.setView(iconRecyclerView);
        builder.setTitle(R.string.chose_icon);
        dialog = builder.create();
        dialog.show();
    }

    public void onClickClearIcon(View view) {
        taskPresentation.getTask().setIconId(null);
    }

    // Attachments
    public void onClickAddAttachment(View view) {
        final CharSequence[] attachmentTypes = {getContext().getString(R.string.photo), getContext().getString(R.string.library)};
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
        String timeStamp = new SimpleDateFormat(getString(R.string.TimeStampDateFormat))
                .format(new Date());
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), Attachment.image_path);
        imagesFolder.mkdirs();
        File image = new File(imagesFolder, timeStamp + Attachment.image_extension);
        return Uri.fromFile(image);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
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
        binding.setTaskPresentation(data);
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
        validator.validate();
    }

    public void save() {
        validator.validate();
    }

    private void putTask() {
        int taskId = dbOperationHelper.putTask(taskPresentation.getTask());
        taskPresentation.getTask().setTaskId(taskId);

        for(AttachmentPresentation attachmentPresentation : taskPresentation.getRemovedAttachmentPresentations()) {
            dbOperationHelper.deleteAttachment(attachmentPresentation.getAttachment().getAttachmentId());
        }
        for(AttachmentPresentation attachmentPresentation : taskPresentation.getAddedAttachmentPresentations()) {
            attachmentPresentation.getAttachment().setTaskId(taskId);
            dbOperationHelper.putAttachment(attachmentPresentation.getAttachment());
        }
        if(taskPresentation.getTask().getDateAlarm() != null) {
            alarm.setAlarm(getContext().getApplicationContext(), taskPresentation.getTask());
        } else {
            alarm.cancelAlarm(getContext().getApplicationContext(), taskPresentation.getTask());
        }
        if(taskPresentation.getTask().getStatus() == Task.Status.OVERDUE) {
            taskPresentation.getTask().setStatus(Task.Status.UNDONE);
        }
    }

    //// Adapters interface

    @Override
    public void onAttachmentClicked(AttachmentPresentation attachmentPresentation) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_attachment, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
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
    public void onAttachmentLongClicked(final AttachmentPresentation attachmentPresentation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.remove_image_question);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(taskPresentation.getAddedAttachmentPresentations().contains(attachmentPresentation)){
                    taskPresentation.getAddedAttachmentPresentations().remove(attachmentPresentation);
                } else {
                    taskPresentation.getRemovedAttachmentPresentations().add(attachmentPresentation);
                }
                taskPresentation.getAttachmentPresentations()
                        .remove(attachmentPresentation);
                attachmentListAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onGroupClicked(Group group) {
        taskPresentation.setGroup(group);
        taskPresentation.getTask().setGroupId(group.getGroupId());
        dialog.dismiss();
    }

    @Override
    public void onIconClicked(Integer iconId) {
        taskPresentation.getTask().setIconId(iconId);
        dialog.dismiss();
    }

    @Override
    public void onValidationSucceeded() {
        putTask();
        getActivity().finish();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
