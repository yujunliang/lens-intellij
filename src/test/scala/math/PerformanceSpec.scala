package math

import org.scalatest._

class PerformanceSpec
  extends WordSpecLike
  with MustMatchers
  with BeforeAndAfterAll {


  "MovingAverage copy and using lens" must {

    "differ in performance" in {

      var movingAverage = MovingAverage()
      val stopWatch = StopWatch()
      (1 to 1000000).foreach { _ =>
        movingAverage = movingAverage.useShapeless(2L)
      }
      println(s"using lens=${stopWatch.duration}")

      var movingAverage2 = MovingAverage()
      val stopWatch2 = StopWatch()
      (1 to 1000000).foreach { _ =>
        movingAverage2 = movingAverage2.copy(2L)
      }
      println(s"using copy=${stopWatch2.duration}")

    }

  }

}
