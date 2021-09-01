// 1 - Pacote
package datadriven;

// 2 - Bibliotecas
import org.json.JSONObject;
import org.testng.annotations.*;
import utils.Data;
import utils.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

// 3 - Classe
public class UserDD {
    // 3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/user";
    Data data; // objeto que representa a classe utils.Data
    Log log; // objeto que representa a classe utils.Log
    int contador; // ajudar a contar o número de linhas executadas
    double soma; // somar os valores dos registros (brincadeira somando o valor das senhas)


    // 3.2 - Métodos e Funções

    @DataProvider // Provedor de dados para os testes
    public Iterator<Object[]> provider() throws IOException {
        List<Object[]> testCases = new ArrayList<>();
        //List<String[]> testCases = new ArrayList<>();
        String[] testCase;
        String linha;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("db/usersPairwise.csv"));
        while((linha = bufferedReader.readLine()) != null){
            testCase = linha.split(",");
            testCases.add(testCase);
        }

        return testCases.iterator();
    }


    @BeforeClass // Antes da classe que executa os testes
    public void setup(){
        data = new Data();
        log = new Log();
    }

    @AfterClass // Depois que a classe terminar de executar todos os seus testes
    public void tearDown(){
        System.out.println("TOTAL DE REGISTROS = " + contador);
        System.out.println("SOMA TOTAL = " + soma);
    }

    @Test(dataProvider = "provider") // Método de teste
    public void incluirUsuario(
            String id,
            String username,
            String firstName,
            String lastName,
            String email,
            String password,
            String phone,
            String userStatus) throws IOException {

        log.iniciarLog(); // criar o arquivo e escrever a linha de cabecalho

        String jsonBody = new JSONObject()
            .put("id", id)
            .put("username", username)
            .put("firstName", firstName)
            .put("lastName", lastName)
            .put("email", email)
            .put("password", password)
            .put("phone", phone)
            .put("userStatus", userStatus)
            .toString();



        String userId =
                given()
                        .contentType("application/json")
                        .log().all()
                        .body(jsonBody)
                .when()
                        .post(uri)
                .then()
                        .log().all()
                        .statusCode(200)
                        .body("code", is(200))
                        .body("type", is("unknown"))
                .extract()
                        .path("message")
                ;
        contador += 1; // somar +1 na variável contador --> contador = contador + 1
        System.out.println("O userID é " + userId);
        System.out.println("Essa é a linha nº " + contador);

        soma = soma + Double.parseDouble(password);
    }


}
