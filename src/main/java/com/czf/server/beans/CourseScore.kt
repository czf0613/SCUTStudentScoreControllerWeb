package com.czf.server.beans

import java.math.BigDecimal

data class CourseScore(val course:Int,val score:BigDecimal):Comparable<CourseScore> {
    var courseName:String="未知"

    override fun compareTo(other: CourseScore): Int {
        return this.course.compareTo(other.course)
    }
}