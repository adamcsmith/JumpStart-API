
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._
/**/
object index extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[models.User,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(connectedUser: models.User):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.30*/("""
<!DOCTYPE html>

<html ng-app="myApp">
<head>
    <title>Jumpstart</title>
    <link rel="stylesheet" href=""""),_display_(Seq[Any](/*7.35*/routes/*7.41*/.Assets.at("stylesheets/bootstrap.min.css"))),format.raw/*7.84*/(""""/>
    <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*8.50*/routes/*8.56*/.Assets.at("stylesheets/main.css"))),format.raw/*8.90*/("""">
    <link rel="shortcut icon" type="image/png" href=""""),_display_(Seq[Any](/*9.55*/routes/*9.61*/.Assets.at("images/favicon.png"))),format.raw/*9.93*/("""">
    <script src=""""),_display_(Seq[Any](/*10.19*/routes/*10.25*/.Assets.at("javascripts/lib/jquery-1.10.2.min.js"))),format.raw/*10.75*/("""" type="text/javascript"></script>

    <script src=""""),_display_(Seq[Any](/*12.19*/routes/*12.25*/.Assets.at("javascripts/lib/angular/angular-1.0.8.min.js"))),format.raw/*12.83*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*13.19*/routes/*13.25*/.Assets.at("javascripts/lib/angular/angular-resource-1.0.8.min.js"))),format.raw/*13.92*/("""" type="text/javascript"></script>

    <script src=""""),_display_(Seq[Any](/*15.19*/routes/*15.25*/.Assets.at("javascripts/app/app.js"))),format.raw/*15.61*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*16.19*/routes/*16.25*/.Assets.at("javascripts/app/controllers.js"))),format.raw/*16.69*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*17.19*/routes/*17.25*/.Assets.at("javascripts/app/directives.js"))),format.raw/*17.68*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*18.19*/routes/*18.25*/.Assets.at("javascripts/app/filters.js"))),format.raw/*18.65*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*19.19*/routes/*19.25*/.Assets.at("javascripts/app/services.js"))),format.raw/*19.66*/("""" type="text/javascript"></script>

    <script type="text/javascript">
        var Conf = window.Conf ||
            """),format.raw/*23.13*/("""{"""),format.raw/*23.14*/(""" userInfo:
                """),format.raw/*24.17*/("""{"""),format.raw/*24.18*/("""
                    """),_display_(Seq[Any](/*25.22*/if(connectedUser != null)/*25.47*/{_display_(Seq[Any](format.raw/*25.48*/("""id: """),_display_(Seq[Any](/*25.53*/connectedUser/*25.66*/.id))))})),format.raw/*25.70*/("""
                """),format.raw/*26.17*/("""}"""),format.raw/*26.18*/("""
            """),format.raw/*27.13*/("""}"""),format.raw/*27.14*/(""";
    </script>
</head>
<body>
<div class="container" ng-view>
</body>
</html>"""))}
    }
    
    def render(connectedUser:models.User): play.api.templates.HtmlFormat.Appendable = apply(connectedUser)
    
    def f:((models.User) => play.api.templates.HtmlFormat.Appendable) = (connectedUser) => apply(connectedUser)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Nov 04 10:55:11 EST 2013
                    SOURCE: /Users/adamcsmith/Projects/JumpStart/JumpStart-API/app/views/index.scala.html
                    HASH: d1d6875bf31f64307b219d0eaa822184cfb10a65
                    MATRIX: 779->1|901->29|1046->139|1060->145|1124->188|1212->241|1226->247|1281->281|1373->338|1387->344|1440->376|1497->397|1512->403|1584->453|1674->507|1689->513|1769->571|1858->624|1873->630|1962->697|2052->751|2067->757|2125->793|2214->846|2229->852|2295->896|2384->949|2399->955|2464->998|2553->1051|2568->1057|2630->1097|2719->1150|2734->1156|2797->1197|2943->1315|2972->1316|3027->1343|3056->1344|3114->1366|3148->1391|3187->1392|3228->1397|3250->1410|3280->1414|3325->1431|3354->1432|3395->1445|3424->1446
                    LINES: 26->1|29->1|35->7|35->7|35->7|36->8|36->8|36->8|37->9|37->9|37->9|38->10|38->10|38->10|40->12|40->12|40->12|41->13|41->13|41->13|43->15|43->15|43->15|44->16|44->16|44->16|45->17|45->17|45->17|46->18|46->18|46->18|47->19|47->19|47->19|51->23|51->23|52->24|52->24|53->25|53->25|53->25|53->25|53->25|53->25|54->26|54->26|55->27|55->27
                    -- GENERATED --
                */
            