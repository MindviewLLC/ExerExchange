package models

case class Exercise(
  body: String,
  hints: Seq[String] = Seq[String]()
) extends Content