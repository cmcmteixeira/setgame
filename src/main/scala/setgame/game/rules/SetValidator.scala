package setgame.game.rules

import setgame.game.model.Card

class SetValidator {

  def validate(set: (Card,Card,Card)) : Boolean = {
    val (c1,c2,c3) = set
    val cards = List(c1,c2,c3)

    val sameShapeCount = cards.groupBy(_.shape).size
    val sameColorCount = cards.groupBy(_.color).size
    val sameNumberCount = cards.groupBy(_.count).size
    val sameShadingCount = cards.groupBy(_.shading).size

    !List(
      sameShapeCount,
      sameColorCount,
      sameNumberCount,
      sameShadingCount
    ).contains(2)
  }
}
