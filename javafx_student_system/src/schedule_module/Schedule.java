package schedule_module;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 
 */
public class Schedule {
    private String itemName;
    private String itemRemark;
    private LocalDateTime startDate;

    public Schedule(String itemName, String itemRemark, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime reminderTime, Boolean isRemind) {
        this.itemName = itemName;
        this.itemRemark = itemRemark;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reminderTime = reminderTime;
        this.isRemind = isRemind;
    }

    private LocalDateTime endDate;
    private LocalDateTime reminderTime;
    private Boolean isRemind = false;

    public Schedule(String itemName, String itemRemark, LocalDateTime startDate, LocalDateTime endDate) {
        this.itemName = itemName;
        this.itemRemark = itemRemark;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemRemark() {
        return itemRemark;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Boolean getRemind() {
        return isRemind;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public void setRemind(Boolean remind) {
        isRemind = remind;
    }

    public void setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
    }

}