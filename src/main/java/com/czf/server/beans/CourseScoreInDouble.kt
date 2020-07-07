package com.czf.server.beans

data class CourseScoreInDouble(val course:Int,val score:Double):Comparable<CourseScoreInDouble>{
    var courseName:String="未知"

    override fun compareTo(other: CourseScoreInDouble): Int {
        return this.course.compareTo(other.course)
    }
}