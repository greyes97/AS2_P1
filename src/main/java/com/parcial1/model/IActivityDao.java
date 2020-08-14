package com.parcial1.model;

import java.sql.SQLException;

public interface IActivityDao {
    public void saveActivityDao(ActivityEntity activity);
    public void deleteActivityDao(int id_activity) throws SQLException;
    public ActaEntity getActa(int id_activity);
}
