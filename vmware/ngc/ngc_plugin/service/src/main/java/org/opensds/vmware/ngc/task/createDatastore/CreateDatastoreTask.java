package org.opensds.vmware.ngc.task.createDatastore;


import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vise.usersession.ServerInfo;
import org.opensds.vmware.common.Storage;
import org.opensds.vmware.common.models.VolumeMO;
import org.opensds.vmware.ngc.base.TaskInfoConst;
import org.opensds.vmware.ngc.entity.ResultInfo;
import org.opensds.vmware.ngc.model.DatastoreInfo;
import org.opensds.vmware.ngc.task.AbstractTask;
import org.opensds.vmware.ngc.task.TaskExecution;
import com.vmware.vim25.TaskInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateDatastoreTask extends AbstractTask implements TaskExecution {

    private static Log logger = LogFactory.getLog(CreateDatastoreTask.class);

    private VolumeMO volumeMO;

    public CreateDatastoreTask(DatastoreInfo datastoreInfo,
                               ManagedObjectReference[] hostMos,
                               ServerInfo serverInfo,
                               Storage storage) {
        super.serverInfo = serverInfo;
        super.hostMos = hostMos;
        super.datastoreInfo = datastoreInfo;
        super.storage = storage;
    }

    @Override
    public void setContext(Map context) {
        this.volumeMO = (VolumeMO)context.get(VolumeMO.class.getName());
    }

    @Override
    public void runTask() throws Exception {
        logger.info("---------Step three, running create the datastoreInfo task and convert volume to Datastore...");
        List<TaskInfo> taskInfoList = new ArrayList<>();
        try {
            ResultInfo<Object> resultInfo = hostServiceImpl.convertVmfsDatastore(hostMos, serverInfo, volumeMO, datastoreInfo);
            if (resultInfo.getStatus().equals("ok")) {
                createTaskList(taskInfoList, TaskInfoConst.Type.TASK_CREATE_DATASTORE);
                changeTaskState(taskInfoList, TaskInfoConst.Status.SUCCESS,
                        String.format("Create DatastoreInfo %s finished.", volumeMO.name));
            } else if (resultInfo.getStatus().equals("error")) {
                changeTaskState(taskInfoList, TaskInfoConst.Status.ERROR,
                        String.format("Create DatastoreInfo %s failed.", volumeMO.name));
                throw new Exception(resultInfo.getMsg());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void rollBack() throws Exception {
        logger.info("---------Step three, roll back and create the datastore failed...");
    }
}
