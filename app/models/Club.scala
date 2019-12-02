package models

case class Club(
           var clubName: String = "",
           var player1: String = "",
           var player2: String = "",
           var player3: String = "",
           var player4: String = "",
           var player5: String = ""
          ){

  var clubs: Set[Club] = Set()

  def add(club: Club): Unit ={
    clubs+=club
  }

  def getClubs(): Set[Club] ={
    return clubs
  }
}

object Club {
  var clubs = new Club()
  clubs.add(new Club(clubName = "Boston Bruns", player1 ="red", player2 ="blue", player3 ="teal", player4 ="brown", player5 ="green" ))

  def getAllClubs(): Set[Club] ={
    return clubs.getClubs()
  }
}
