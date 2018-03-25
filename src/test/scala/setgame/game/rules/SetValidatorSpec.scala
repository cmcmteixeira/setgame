package setgame.game.rules

import org.scalatest.{Matchers, WordSpec}
import setgame.game.model.Card
import setgame.game.model.enums.{Color, Count, Shading, Shape}

class SetValidatorSpec extends WordSpec with Matchers {

  "A set validator should" should {
    val validator = new SetValidator

    "identify that a set of three cards sharing the same value for 3 characteristics and having the remaining one be unique amongst them IS a set" in {
      validator.validate(
        Card(Shape.Oval, Color.Red,Count.Two, Shading.Outlined),
        Card(Shape.Oval, Color.Red,Count.Two, Shading.Striped),
        Card(Shape.Oval, Color.Red,Count.Two, Shading.Solid),
      ) shouldBe true
    }
    "identify that a set of three cards with only one common characteristics and having all the other ones be distinct IS a set" in {
      validator.validate(
        Card(Shape.Squiggle, Color.Green,Count.Two, Shading.Striped),
        Card(Shape.Oval, Color.Purple,Count.Two, Shading.Striped),
        Card(Shape.Diamond, Color.Red,Count.Two, Shading.Striped),
      ) shouldBe true
    }
    "identify that, if the three cards all have distinct values for each property, then it is a set" in {
      validator.validate(
        Card(Shape.Oval, Color.Purple,Count.One, Shading.Striped),
        Card(Shape.Diamond, Color.Green,Count.Two, Shading.Solid),
        Card(Shape.Squiggle, Color.Red,Count.Three, Shading.Outlined),
      ) shouldBe true
    }

    "identify that a set of three cards sharing the same value for 3 characteristics and having the remaining one be the same for two cars IS NOT a set" in {
      validator.validate(
        Card(Shape.Oval, Color.Red,Count.Two, Shading.Striped),
        Card(Shape.Oval, Color.Red,Count.Two, Shading.Striped),
        Card(Shape.Oval, Color.Red,Count.Two, Shading.Solid),
      ) shouldBe false
    }
  }
}
