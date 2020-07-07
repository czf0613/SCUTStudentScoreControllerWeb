package com.czf.server.beans

import java.math.BigDecimal

data class CourseScore constructor(val course:Int,val score:BigDecimal):Comparable<CourseScore> {
    var courseName:String?=null

    constructor(course:Int, score:Double)

    override fun compareTo(other: CourseScore): Int {
        return this.course.compareTo(other.course)
    }
}