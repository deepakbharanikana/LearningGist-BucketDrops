package learninggist.com.bucketdrops.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Deepak on 5/21/16.
 */
public class Drop extends RealmObject {

    private String whatJob;
    @PrimaryKey
    private long startTime;
    private long endTime;
    private boolean isCompleted;
    public Drop(){

    }

    public Drop(String whatJob, long startTime, long endTime, boolean isCompleted)
    {
        this.whatJob = whatJob;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCompleted = isCompleted;
    }

    public String getWhatJob() {
        return whatJob;
    }

    public void setWhatJob(String whatJob) {
        this.whatJob = whatJob;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
