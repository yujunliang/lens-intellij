package math

import scala.compat.Platform._
import scala.concurrent.duration._

case class StopWatch(
  private val startTime: Long = currentTime,
  private val _duration: Option[FiniteDuration] = None) {

  /**
   * Restart the stopWatch
   * @return a new StopWatch instance with the new startTime
   */
  def start = new StopWatch

  /**
   * Stop the stopWatch
   * @return a StopWatch instance with the duration information.
   */
  def stop = copy(_duration = Some((currentTime - startTime).milliseconds))

  /**
   * the duration when this method is called.
   * @return the duration between now and the time the stop watch started.
   */
  def duration = _duration.getOrElse((currentTime - startTime).milliseconds)
}

