package schedule_module;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import java.time.LocalDate;

/**
 * 日期格
 */
public class DateItem extends AnchorPane {

    private LocalDate date;
    private Boolean isMarked = false;
    public static final String Mark = "Marked";

    public DateItem(Node... children) {
        super(children);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getMarked() {
        return isMarked;
    }

    public void setMarked(Boolean marked) {
        isMarked = marked;
    }

}
