package me.annard

import me.annard.model.Bet

trait EntityProcessor {
  def nextEntity:(Option[Bet])
}
