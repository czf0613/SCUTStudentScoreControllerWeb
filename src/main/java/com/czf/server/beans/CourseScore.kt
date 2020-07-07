package com.czf.server.beans

import java.math.BigDecimal

data class CourseScore(val course:Int,val score:BigDecimal){
    var courseName:String?=null
}