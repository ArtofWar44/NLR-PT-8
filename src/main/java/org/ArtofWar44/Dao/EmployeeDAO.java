
package org.ArtofWar44.Dao;

import org.ArtofWar44.Model.Employee;

public interface EmployeeDAO {
    Employee getEmployeeByUsernameAndPassword(String username, String password);
}
