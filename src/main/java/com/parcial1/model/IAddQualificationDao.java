package com.parcial1.model;

import java.util.List;

public interface IAddQualificationDao {
    public List<StudentEntity> getListStudent(String whatCourse);
    public List<ActivityEntity> getListActivity(String whatCourse);
    public boolean saveResult(ResultEntity entity);
    public float validationAdd(int id_course);
}
