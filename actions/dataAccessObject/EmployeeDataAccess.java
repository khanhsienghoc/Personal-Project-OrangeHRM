package dataAccessObject;


import database.BaseDBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDataAccess {
    private BaseDBHelper dbHelper = new BaseDBHelper();
    public static EmployeeDataAccess employeeData(){
        return new EmployeeDataAccess();
    }
    public boolean isEmailExists(String email) {
        String query = "SELECT emp_number FROM hs_hr_employee WHERE emp_work_email = ?";
        try (ResultSet rs = dbHelper.executeQuery(query, email)) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isEmployeeExists(String email) {
        String query = "SELECT emp_number FROM hs_hr_employee WHERE emp_work_email = ?";
        try (ResultSet rs = dbHelper.executeQuery(query, email)) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
