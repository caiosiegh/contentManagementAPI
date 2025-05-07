package org.example;

import io.restassured.internal.path.json.mapping.JsonObjectDeserializer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.json.simple.JSONObject;

import java.util.HashMap;

import com.github.javafaker.Faker;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;

public class apiTesting {
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImRhN2E1OWQyLWRlYjEtNGE5Yy04MDQ5LTQ1NGE0MDI4NDdhZCIsImlhdCI6MTc0NjU2NTE0NCwiZXhwIjoxNzQ2NjUxNTQ0fQ.nW47ZniEcIMWwEjldjHhJkBMDe8_XeDoHYb11mygjqs";
    Faker faker = new Faker();

    @BeforeClass
    public void setup() {
        baseURI = "http://localhost:3000";
    }

    @Test
    public void logarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@gmail.com");
        usuario.setSenha("Teste123!");

        given().contentType(ContentType.JSON).body(usuario).when().post("/auth/login").then().statusCode(200).log().all();

    }

    @Test
    public void criarUmNovoUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha(faker.internet()
                .password(15, 20, true, true, true));


        given().contentType(ContentType.JSON).body(usuario).when().post("/usuarios").then().log().all().statusCode(201);
    }

    @Test
    public void criarUmNovoUsuarioSemNome() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto("");
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha(faker.internet()
                .password(15, 20,
                        true, true, true));

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body(containsString("Nome completo é obrigatório"));
    }

    @Test
    public void criarUmNovoUsuarioSemNomeUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario("");
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha(faker.internet()
                .password(15, 20,
                        true, true, true));

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body(containsString("Nome de usuário é obrigatório"));

    }

    @Test
    public void criarUmNovoUsuarioSemEmail() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail("");
        usuario.setSenha(faker.internet()
                .password(15, 20,
                        true, true, true));

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body(containsString("Email inválido"));

    }

    @Test
    public void criarUmNovoUsuarioEmailFormatadoErrado() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail("testandooteste@@.com");
        usuario.setSenha(faker.internet()
                .password(15, 20,
                        true, true, true));

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body(containsString("Email inválido"));

    }

    @Test
    public void criarUmNovoUsuarioSemSenha() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha("");

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body("errors[0].msg", equalTo("Senha deve ter no mínimo 6 caracteres"),
                        "errors[1].msg", equalTo("Senha deve conter pelo menos um número"),
                        "errors[2].msg", equalTo("Senha deve conter pelo menos uma letra maiúscula"));

    }

    @Test
    public void criarUmNovoUsuarioSenhaMenorValor() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha("Teste");

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body(containsString("Senha deve ter no mínimo 6 caracteres"));

    }

    @Test
    public void criarUmNovoUsuarioSenhaSemMaiusculo() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha("teste1");

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body(containsString("Senha deve conter pelo menos uma letra maiúscula"));

    }

    @Test
    public void criarUmNovoUsuarioSenhaSemCaractere() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha("Testee");

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body(containsString("Senha deve conter pelo menos um número"));

    }

    /*Teste com Erro
    @Test
    public void criarUmNovoUsuarioNomeComlpetoSemString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nomeCompleto", "111111");
        jsonObject.put("nomeUsuario", faker.name().username());
        jsonObject.put("email", faker.internet().emailAddress());
        jsonObject.put("senha", "Teste123!");

        String payload = jsonObject.toJSONString();

        given().contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/usuarios").then().log().all().statusCode(201);

    }
     */

    /*Teste com Erro - Não deveria ser possível criar usuario somente com números
    @Test
    public void criarUmNovoUsuarioNomeUsuarioSemString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nomeCompleto", faker.name().fullName());
        jsonObject.put("nomeUsuario", faker.number().digits(6));
        jsonObject.put("email", faker.internet().emailAddress());
        jsonObject.put("senha", "Teste123!");

        String payload = jsonObject.toJSONString();

        given().contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/usuarios").then().log().all().statusCode(201);

    }
     */

    @Test
    public void criarUmNovoUsuarioEmailSemString() {

        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail("1111111111111111");
        usuario.setSenha(faker.internet()
                .password(15, 20,
                        true, true, true));

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().body("errors[0].msg", equalTo("Email inválido")).log().all().statusCode(400);

    }

    @Test
    public void listarUsuarios() {
        given().contentType(ContentType.JSON).auth().oauth2(token).when()
                .get("/usuarios").then().log().all().statusCode(200).log().all();

    }

    @Test
    public void listarUsuarioNome() {
        given().contentType(ContentType.JSON).queryParam("nomeUsuario", "myles.damore").auth().oauth2(token).when()
                .get("/usuarios").then().body(containsString("myles.damore")).log().all().statusCode(200);

    }

    //Api não retorna Usuário não encontrado.
    @Test
    public void listarUsuarioNomeInexistente() {
        given().contentType(ContentType.JSON).queryParam("nomeUsuario", "123445").auth().oauth2(token).when()
                .get("/usuarios").then().body("", hasSize(0)).log().all().statusCode(200);

    }

    @Test
    public void listarUsuarioEmail() {
        given().contentType(ContentType.JSON).queryParam("email", "teste@gmail.com").auth().oauth2(token).when()
                .get("/usuarios").then().body("email[0]", equalTo("teste@gmail.com")).log().all().statusCode(200);

    }

    //Api não retorna E-mail não encontrado.
    @Test
    public void deveRetornarListaVaziaQuandoEmailNaoExiste() {
        given().contentType(ContentType.JSON).queryParam("email", "notExists@gmail.com").auth().oauth2(token).when()
                .get("/usuarios").then().body("", hasSize(0)).log().all().statusCode(200);

    }

    @Test
    public void listarUsuarioId() {
        String id = "260fb0eb-a4ab-45d8-8304-ad61236500bd";

        given().contentType(ContentType.JSON).auth().oauth2(token).when()
                .get("/usuarios/" + id).then().body("id", equalTo(id)).log().all().statusCode(200);

    }

    @Test
    public void listarUsuarioIdInexistente() {
        String id = "Não existo";

        given().contentType(ContentType.JSON).auth().oauth2(token).when()
                .get("/usuarios/" + id).then().body("erro", equalTo("Usuário não encontrado")).log().all().statusCode(404);

    }

    @Test
    public void listarUsuarioSemToken() {
        String id = "Não existo";
        String tokenJWT = "invalidToken";

        given().contentType(ContentType.JSON).auth().oauth2(tokenJWT).when()
                .get("/usuarios/" + id).then().body("erro", equalTo("Token inválido")).log().all().statusCode(401);

    }

}