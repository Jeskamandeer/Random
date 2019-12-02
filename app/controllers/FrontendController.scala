package controllers

import javax.inject._
import play.api.Configuration
import play.api.data.Form
import play.api.data.Forms._
import play.api.http.HttpErrorHandler
import play.api.mvc._
import models.Club
import play.api.libs.json.{JsObject, Json}

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
 * Frontend controller managing all static resource associate routes.
 *
 * @param assets Assets controller reference.
 * @param cc     Controller components reference.
 */
@Singleton
class FrontendController @Inject()(assets: Assets, errorHandler: HttpErrorHandler, config: Configuration, cc: ControllerComponents) extends AbstractController(cc) {

  def index: Action[AnyContent] = assets.at("index.html")

  def assetOrDefault(resource: String): Action[AnyContent] = if (resource.startsWith(config.get[String]("apiPrefix"))) {
    Action.async(r => errorHandler.onClientError(r, NOT_FOUND, "Not found"))
  } else {
    if (resource.contains(".")) assets.at(resource) else index
  }

  //Taulukko johon klubit tallennetaan
  private val clubs = ArrayBuffer(
    Club(clubName = "Boston Bruns", player1 = "red", player2 = "blue", player3 = "teal", player4 = "brown", player5 = "green")
  )
  //Hakee klubit ja muodostaa niistä JSON tiedoston
  def getClubs = Action { implicit request: Request[AnyContent] =>
    val savedClubs = clubs
    var clubList = new ListBuffer[JsObject]()
    for (club <- savedClubs) {
      val js = Json.obj(fields =
        "clubName" -> club.clubName,
        "player1" -> club.player1,
        "player2" -> club.player2,
        "player3" -> club.player3,
        "player4" -> club.player4,
        "player5" -> club.player5)
      clubList += js
    }
    Ok(Json.toJson(clubList.toList))
  }
  //Tallentaa Formin tiedot ja ohjaa takaisin samalle sivulle
  //Ei tarkista lomakkeen tietoja
  def createClub = Action { implicit request =>
    val formValidationResult = ClubForm.createClubForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.listClubs(clubs.toSeq, formWithErrors))
    }, { club =>
      clubs.append(club)
      Redirect("/addClub")
    })
  }

  //Lomake joukkueen tallentamiseen
  object ClubForm {
    val createClubForm = Form(
      mapping(
        "clubName" -> text,
        "player1" -> text,
        "player2" -> text,
        "player3" -> text,
        "player4" -> text,
        "player5" -> text
      )(Club.apply)(Club.unapply)
    )
  }
}

