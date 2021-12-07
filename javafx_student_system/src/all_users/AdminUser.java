package all_users;

/*________________________
 @ Author: _Krill
 @ Data: 2021/11/1 23:12 
 @ Version: 1.0
__________________________*/
public class AdminUser {
    int id;
    String userName;
    String account_id;
    String password;
    private static AdminUser globalOnly = new AdminUser();
    private AdminUser() {
    }
    public static AdminUser getGlobalOnly() {
        return globalOnly;
    }
}
