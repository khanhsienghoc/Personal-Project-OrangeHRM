package dataAccessObject;


import database.BaseDBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDataAccess {
    private BaseDBHelper dbHelper = new BaseDBHelper();
    public static EmployeeDataAccess employeeData(){
        return new EmployeeDataAccess();
    }
    public boolean isEmployeeExist(String employeeID) {
        String query = "select * from orangehrm_db.hs_hr_employee WHERE employee_id = ?";
        try (ResultSet rs = dbHelper.executeQuery(query, employeeID)) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
