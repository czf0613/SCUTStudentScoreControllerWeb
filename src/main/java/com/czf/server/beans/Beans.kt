package com.czf.server.beans

import java.math.BigDecimal

class Beans {
    data class CourseScore(val course:Int,val score:BigDecimal){
        var courseName:String?=null
    }


}