package all_users;

/*________________________
 @ Author: _Krill
 @ Data: 2021/11/1 23:12 
 @ Version: 1.0
__________________________*/
public class StudentUser {
    String account_id;
    String password;
    String stu_id;
    String stuName;
    String forClass;
    public StudentUser() {

    }
    public StudentUser(String stu_id, String forClass, String stuName) {
        this.stu_id = stu_id;
        this.forClass = forClass;
        this.stuName = stuName;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getForClass() {
        return forClass;
    }

    public void setForClass(String forClass) {
        this.forClass = forClass;
    }
}
