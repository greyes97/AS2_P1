package com.parcial1.model;

import java.util.List;
import java.util.Map;

public interface ICouseDao {
    public CourseEntity getCourseDao(String whatCourse);
    public List<ActivityEntity> getListActividadCourse(String whatCourse);
}
