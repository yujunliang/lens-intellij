package math

import math.MovingAverage._
import shapeless._

import scala.Long._
import scala.collection.immutable.Queue
import scala.collection.immutable.Queue._

/**
 * For min and max of some data
 * @param min min
 * @param max max
 */
case class Range(min: Long = MaxValue, max: Long = MinValue) {

  /**
   * Modify the range after compare to the new sample
   * @return     modified range
   */
  def update = { data : Long => if (min > data) copy(min = data) else if (max < data) copy(max = data) else this }

  override def toString = s"$min/$max"
}

/**
 * data structure to calculate moving average.
 * @param queue entry of 10 samples
 */
case class MovingAverage(total: Long = 0L, range : Range = Range(), queue: Queue[Long] = empty) {

  /**
   * maintaining max number of 10 samples in the queue, keep track of min and max data entered and total
   * number of samples.
   *
   * @param data  sample data
   * @return      Modified moving average
   */
  def useShapeless(data: Long) = movingAverageLenses.modify(this) { case (t, (r, q)) => (t + 1, (r update data, enqueue1(data, q))) }

  /**
   * maintaining max number of 10 samples in the queue, keep track of min and max data entered and total
   * number of samples.
   *
   * @param data  sample data
   * @return      Modified moving average
   */
  def useCopy(data: Long) = copy(total = total + 1,  range = range update data, queue = enqueue1(data, queue))

  /**
   * the average value of this moving average
   * @return  the average value of this moving average
   */
  def average = if (queue.nonEmpty) queue.sum / queue.size else 0L

  /**
   * The difference of the moving average
   * @param that another moving average
   * @return     the difference of this and another moving average
   */
  def -(that: MovingAverage) = this.average - that.average

  override def toString = s"$average in ($range) of $total samples"
}

/**
 * Shapeless lens objects for Memory case class and nested MovingAverage case class
 */
object MovingAverage {

  /**
   * Educational: This method is for education purpose only
   *
   * The parameters of this method can be curried, but IntelliJ will complain about it.
   * @param d  data to be enqueued
   * @param q  queue
   * @tparam A type parameter of the queue
   * @return   modified queue
   * tupled parameters */
  def enqueue1[A](d: A, q: Queue[A]) = (if (q.size == 10) q.dequeue._2 else q).enqueue(d)

  /**
   * Lens for count property of MovingAverage
   */
  val total = lens[MovingAverage] >> 'total

  /**
   * Lens for range property of MovingAverage
   */
  val range = lens[MovingAverage] >> 'range

  /**
   * Lens for queue property of MovingAverage
   */
  val queue = lens[MovingAverage] >> 'queue

  /**
   *
   *  If you yse the code in the comment block instead of the code above, there is no warning.
   * {{{
  /**
   * Lens for count property of MovingAverage
   */
  val total = (lens[MovingAverage] >> 'total).asInstanceOf[LongLens[MovingAverage]]

  /**
   * Lens for range property of MovingAverage
   */
  val range = (lens[MovingAverage] >> 'range).asInstanceOf[RangeLens]

  /**
   * Lens for queue property of MovingAverage
   */
  val queue = (lens[MovingAverage] >> 'queue).asInstanceOf[QueueLens]
   * }}}
   *
   */


   /**
   * Composed lens is better if used repeatedly.
   */
  val movingAverageLenses = total ~ (range ~ queue)

  type LongLens[A] = Lens[A, Long]
  type RangeLens   = Lens[MovingAverage, Range]
  type QueueLens   = Lens[MovingAverage, Queue[Long]]


}