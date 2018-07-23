package de.symeda.sormas.app.task.read;

import android.content.Context;
import android.view.Menu;

import de.symeda.sormas.api.task.TaskStatus;
import de.symeda.sormas.app.BaseReadActivity;
import de.symeda.sormas.app.BaseReadFragment;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.common.DatabaseHelper;
import de.symeda.sormas.app.backend.task.Task;
import de.symeda.sormas.app.component.menu.PageMenuItem;
import de.symeda.sormas.app.shared.TaskFormNavigationCapsule;
import de.symeda.sormas.app.task.edit.TaskEditActivity;

public class TaskReadActivity extends BaseReadActivity<Task> {

    @Override
    protected Task queryRootData(String recordUuid) {
        return DatabaseHelper.getTaskDao().queryUuid(recordUuid);
    }

    @Override
    public TaskStatus getPageStatus() {
        return (TaskStatus) super.getPageStatus();
    }

    @Override
    protected BaseReadFragment buildReadFragment(PageMenuItem menuItem, Task activityRootData) {
        TaskFormNavigationCapsule dataCapsule = new TaskFormNavigationCapsule(this,
                getRootEntityUuid(), getPageStatus());
        return TaskReadFragment.newInstance(dataCapsule, activityRootData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        getEditMenu().setTitle(R.string.action_edit_task);
        return result;
    }

    @Override
    protected int getActivityTitle() {
        return R.string.heading_level3_task_read;
    }

    @Override
    public void goToEditView(PageMenuItem menuItem) {
        TaskFormNavigationCapsule dataCapsule = new TaskFormNavigationCapsule(this, getRootEntityUuid(), getPageStatus());
        if (menuItem != null) dataCapsule.setActiveMenu(menuItem.getKey());
        TaskEditActivity.goToActivity(this, dataCapsule);
    }

    public static void goToActivity(Context fromActivity, TaskFormNavigationCapsule dataCapsule) {
        BaseReadActivity.goToActivity(fromActivity, TaskReadActivity.class, dataCapsule);
    }
}