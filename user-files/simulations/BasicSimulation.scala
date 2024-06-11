import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class SimulationNest extends Simulation {

  val httpProtocol = http
    .baseUrl("http://api3-auth.azapfy.com.br") // Base URL
    .header("Content-Type", "application/json")
    .header("Accept", "application/json")

  val scn = scenario("API Search Stress Test")
  .exec(
    http("Request Search")
      .post("/api/pesquisar")
      .body(StringBody("""{"grupo_emp":"JC","bases":["PCA","RODOFAR","UBE","RCA","TFO","UBA","DIV","BHZ","TRANSLAUGUA","MHA","UDI","BBC","ARX","MOC","PMI","SUL","MATRIZ","SPO","MRE","IPT","UBR","AUI","GOV","ITO","VIC","TRC","CLT","LEO","PER","GUA","JOM","JFO","PNV","CUR"],"timezone":"America/Sao_Paulo","sem_base":true,"skip":0,"user":{"grupo":"SUPER"},"buscar_tudo":false,"paginacao":500,"filtros":{"dt_emis":{"dt_ini":"2024-05-01 00:00:00","dt_fim":"2024-05-01 23:59:59"}}}"""))
      .check(status.is(200))
  )
  .pause(1.second) 

  setUp(
  scn.inject(
    rampUsersPerSec(5).to(50).during(1.minute), 
    constantUsersPerSec(50).during(3.minutes)
  )
).protocols(httpProtocol)
}
